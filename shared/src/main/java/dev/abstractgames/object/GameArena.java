package dev.abstractgames.object;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Level;
import dev.abstractgames.AbstractPlugin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor @Getter
public final class GameArena {

    public enum GameStatus {
        WAITING(),
        STARTING(),
        IN_GAME(),
        ENDED()
    }

    private final int id;
    private final GameMap map;
    private final String worldName;

    @Setter private GameStatus status = GameStatus.WAITING;

    public GameArena(int id, GameMap map) {
        this.id = id;

        this.map = map;

        this.worldName = AbstractPlugin.getInstance().getAlias() + "-" + map.getName() + "-" + id;
    }

    public Level getWorld() {
        return Server.getInstance().getLevelByName(this.worldName);
    }

    public void joinAsPlayer(Player player) {
        Server.getInstance().loadLevel(this.worldName);


    }

    public void removePlayer(Player player) {

    }

    public Object getPlayer(Player player) {
        return null;
    }

    public boolean inArenaAsPlayer(Player player) {
        return false;
    }

    public void joinAsSpectator(Player player) {

    }

    public boolean inArenaAsSpectator(Player player) {
        return false;
    }

    public void broadcastMessage(String message, String... args) {

    }

    public void forceClose() {

    }
}