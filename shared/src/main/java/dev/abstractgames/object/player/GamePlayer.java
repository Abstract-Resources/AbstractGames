package dev.abstractgames.object.player;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.inventory.Inventory;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.annotation.Nullable;

@RequiredArgsConstructor @Getter
public final class GamePlayer {

    private final @NonNull String xuid;
    private final @NonNull String name;

    @Setter private boolean spectator = false;

    public @Nullable Player getInstance() {
        return Server.getInstance().getPlayerExact(this.name);
    }

    public @NonNull Player getInstanceNonNull() {
        Player instance = this.getInstance();

        if (instance == null || !instance.isConnected()) {
            throw new RuntimeException("Player '" + this.name + "' returned 'null' by the method declared as '@NonNull'");
        }

        return instance;
    }

    public @NonNull Inventory getInventory() {
        return this.getInstanceNonNull().getInventory();
    }

    public void clearInventory() {
        this.getInventory().clearAll();

        Player instance = this.getInstanceNonNull();

        instance.getOffhandInventory().clearAll();
        instance.getCursorInventory().clearAll();
        instance.getUIInventory().clearAll();
    }
}