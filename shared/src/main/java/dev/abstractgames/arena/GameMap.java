package dev.abstractgames.arena;

import cn.nukkit.level.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor @Getter
public final class GameMap {

    private final String name;

    @Setter private int minSlots;
    @Setter private int maxSlots;

    private Set<GameSpawn> spawns;

    public void registerSpawn(int slot, Location location) {
        GameSpawn gameSpawn = this.getGameSpawn(slot);

        if (gameSpawn != null) {
            gameSpawn.setLocation(location);

            return;
        }

        this.spawns.add(new GameSpawn(slot, location));
    }

    public GameSpawn getGameSpawn(int slot) {
        return this.spawns.stream()
                .filter(gameSpawn -> gameSpawn.getSlot() == slot)
                .findFirst().orElse(null);
    }

    public Location getSpawnLocation(int slot) {
        GameSpawn gameSpawn = this.getGameSpawn(slot);

        return gameSpawn != null ? gameSpawn.getLocation() : null;
    }
}