package dev.abstractgames.factory;

import cn.nukkit.utils.Config;
import com.google.common.collect.ImmutableMap;
import dev.abstractgames.AbstractPlugin;
import dev.abstractgames.object.GameMap;
import dev.abstractgames.object.GameSpawn;
import lombok.Getter;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public final class MapFactory {

    @Getter private final static MapFactory instance = new MapFactory();

    @Getter private final Set<GameMap> maps = new HashSet<>();

    public void init() {
        Config config = new Config(new File(AbstractPlugin.getInstance().getDataFolder(), "maps.json"));

        for (String mapName : config.getKeys(false)) {
            this.registerNewMap(new GameMap(
                    mapName,
                    config.getInt(mapName + ".minSlots"),
                    config.getInt(mapName + ".maxSlots"),
                    (Set<GameSpawn>) config.getList(mapName + ".spawns").stream()
                            .map(serialized -> GameSpawn.deserialize((Map<String, Object>) serialized))
                            .collect(Collectors.toSet())
            ), false);
        }
    }

    public void registerNewMap(GameMap gameMap, boolean save) {
        this.maps.add(gameMap);

        if (!save) return;

        Config config = new Config(new File(AbstractPlugin.getInstance().getDataFolder(), "maps.json"));

        config.set(gameMap.getName(), ImmutableMap.<String, Object>builder()
                .put("minSlots", gameMap.getMinSlots())
                .put("maxSlots", gameMap.getMaxSlots())
                .put("spawns", gameMap.getSpawns().stream()
                        .map(GameSpawn::serialize)
                        .collect(Collectors.toSet())
                )
        );
        config.save();
    }

    public GameMap getMap(String name) {
        return this.maps.stream()
                .filter(gameMap -> gameMap.getName().equals(name))
                .findFirst().orElse(null);
    }

    public GameMap getRandomMap() {
        // TODO: Find a random map with less games registered

        return null;
    }
}