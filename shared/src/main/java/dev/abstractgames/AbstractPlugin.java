package dev.abstractgames;

import cn.nukkit.Player;
import cn.nukkit.plugin.PluginBase;
import dev.abstractgames.command.GameCommand;
import dev.abstractgames.factory.ArenaFactory;
import dev.abstractgames.factory.MapFactory;
import dev.abstractgames.object.GameArena;
import dev.abstractgames.object.player.GamePlayer;
import lombok.Getter;
import lombok.NonNull;

public abstract class AbstractPlugin extends PluginBase {

    @Getter private static AbstractPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        ArenaFactory.getInstance().init();
        MapFactory.getInstance().init();

        this.registerPluginLoader();

        this.getServer().getCommandMap().register("abstractgames", new GameCommand("game", "AbstractGames management command."));
    }

    public abstract void registerPluginLoader();

    public abstract @NonNull GamePlayer registerNewPlayer(Player player, GameArena arena);

    public abstract String getAlias();
}