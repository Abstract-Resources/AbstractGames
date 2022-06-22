package dev.abstractgames.factory;

import cn.nukkit.Player;
import cn.nukkit.Server;
import dev.abstractgames.AbstractPlugin;
import dev.abstractgames.TaskUtil;
import dev.abstractgames.object.GameArena;
import dev.abstractgames.object.GameMap;
import dev.abstractgames.utils.GameUtils;
import lombok.Getter;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class ArenaFactory {

    @Getter private final static ArenaFactory instance = new ArenaFactory();

    private final Set<GameArena> arenas = new HashSet<>();

    private int gamesPlayed = 1;

    public void init() {

    }

    public GameArena registerNewArena(GameMap map) {
        if (map == null) {
            map = MapFactory.getInstance().getRandomMap();
        }

        if (map == null) {
            AbstractPlugin.getInstance().getLogger().alert("An error occurred while tried register a new arena... Cannot found any map.");

            return null;
        }

        GameArena arena = new GameArena(this.gamesPlayed++, map);

        this.arenas.add(arena);

        TaskUtil.runAsync(() -> {
            GameUtils.copyMap(
                    new File(AbstractPlugin.getInstance().getDataFolder(), "backups/" + arena.getMap().getName()),
                    new File(Server.getInstance().getDataPath(), "worlds/" + arena.getWorldName())
            );

            Server.getInstance().loadLevel(arena.getWorldName());
        });

        return arena;
    }

    public Set<GameArena> getMapArenas(GameMap map) {
        return Collections.unmodifiableSet(this.arenas.stream()
                .filter(arena -> arena.getMap().getName().equalsIgnoreCase(map.getName()))
                .collect(Collectors.toSet())
        );
    }

    public GameArena getArena(Player player) {
        return this.arenas.stream()
                .filter(arena -> arena.inArena(player))
                .findFirst().orElse(null);
    }

    public @Nullable GameArena getRandomArena() {
        GameArena betterArena = null;

        for (GameArena arena : this.arenas) {
            if (!arena.isAllowedJoin()) {
                continue;
            }

            if (betterArena == null) {
                betterArena = arena;

                continue;
            }

            if (betterArena.getPlayers().size() > arena.getPlayers().size()) {
                continue;
            }

            betterArena = arena;
        }

        return betterArena;
    }
}