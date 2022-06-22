package dev.abstractgames.object;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.utils.TextFormat;
import dev.abstractgames.AbstractPlugin;
import dev.abstractgames.object.player.GamePlayer;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor @Getter
public final class GameArena {

    public enum GameStatus {
        IDLE(),
        IN_GAME(),
        ENDED()
    }

    private final int id;
    private final @NonNull GameMap map;
    private final @NonNull String worldName;

    @Setter private @NonNull GameStatus status = GameStatus.IDLE;

    private final Set<GamePlayer> players = new HashSet<>();

    public GameArena(int id, @NonNull GameMap map) {
        this.id = id;

        this.map = map;

        this.worldName = AbstractPlugin.getInstance().getAlias() + "-" + map.getName() + "-" + id;
    }

    public @Nullable Level getWorld() {
        return Server.getInstance().getLevelByName(this.worldName);
    }

    public @NonNull Level getWorldNonNull() {
        Level level = this.getWorld();

        if (level == null) {
            throw new RuntimeException("World '" + this.worldName + "' returned 'null' by the method declared as '@NonNull'");
        }

        return level;
    }

    public boolean worldGenerated() {
        return Server.getInstance().isLevelGenerated(this.worldName);
    }

    public boolean isAllowedJoin() {
        return this.status.ordinal() < GameStatus.IN_GAME.ordinal() && !this.isFull();
    }

    public boolean isFull() {
        return this.getPlayers().size() > this.map.getMaxSlots();
    }

    public void joinAsPlayer(@NonNull Player player) {
        if (!this.worldGenerated()) {
            // TODO: Add me to queue...

            return;
        }

        if (!Server.getInstance().loadLevel(this.worldName)) {
            player.sendMessage(TextFormat.RED + "An error occurred while tried join to the game '" + this.id + "'");

            return;
        }

        if (!player.isConnected() || this.inArena(player) || !this.isAllowedJoin()) {
            return;
        }

        GamePlayer gamePlayer = AbstractPlugin.getInstance().registerNewPlayer(player, this);

        this.players.add(gamePlayer);

        gamePlayer.lobbyAttributes();

        this.pushScoreboardUpdate();

        this.broadcastMessage(
                "PLAYER_JOINED",
                player.getName(),
                String.valueOf(this.getPlayers().size()),
                String.valueOf(this.map.getMaxSlots())
        );
    }

    public @NonNull Set<GamePlayer> getPlayers() {
        return Collections.unmodifiableSet(this.players.stream()
                .filter(gamePlayer -> !gamePlayer.isSpectator())
                .collect(Collectors.toSet())
        );
    }

    public boolean inArenaAsPlayer(Player player) {
        GamePlayer gamePlayer = this.getPlayer(player);

        return gamePlayer != null && !gamePlayer.isSpectator();
    }

    public void joinAsSpectator(Player player) {

    }

    public boolean inArenaAsSpectator(Player player) {
        GamePlayer gamePlayer = this.getPlayer(player);

        return gamePlayer != null && gamePlayer.isSpectator();
    }

    public @NonNull Set<GamePlayer> getSpectators() {
        return Collections.unmodifiableSet(this.players.stream()
                .filter(GamePlayer::isSpectator)
                .collect(Collectors.toSet())
        );
    }

    public boolean inArena(Player player) {
        return this.players.stream()
                .anyMatch(gamePlayer -> gamePlayer.getXuid().equalsIgnoreCase(player.getLoginChainData().getXUID()));
    }

    public void removePlayer(Player player) {
        this.players.removeIf(gamePlayer -> gamePlayer.getXuid().equalsIgnoreCase(player.getLoginChainData().getXUID()));
    }

    public @Nullable GamePlayer getPlayer(Player player) {
        return this.players.stream()
                .filter(gamePlayer -> gamePlayer.getXuid().equals(player.getLoginChainData().getXUID()))
                .findFirst().orElse(null);
    }

    public void broadcastMessage(String message, String... args) {

    }

    public void pushScoreboardUpdate() {
        // TODO: Push scoreboard update
    }

    public void forceClose() {

    }
}