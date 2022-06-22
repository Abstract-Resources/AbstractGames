package dev.abstractgames;

import cn.nukkit.Player;
import dev.abstractgames.object.player.GamePlayer;
import lombok.NonNull;

public final class TagGamePlugin extends AbstractPlugin {

    @Override
    public void registerPluginLoader() {
        // TODO: register listeners, scheduler phase
    }

    @Override
    public @NonNull GamePlayer registerNewPlayer(Player player) {
        return new GamePlayer(player.getLoginChainData().getXUID(), player.getName());
    }

    @Override
    public String getAlias() {
        return "TT"; // TODO: TT = TNT Tag
    }
}