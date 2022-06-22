package dev.abstractgames;

import cn.nukkit.Player;
import cn.nukkit.plugin.PluginBase;
import dev.abstractgames.factory.ArenaFactory;
import dev.abstractgames.factory.MapFactory;
import dev.abstractgames.object.player.GamePlayer;
import lombok.Getter;
import lombok.NonNull;
import org.omg.CORBA.PUBLIC_MEMBER;

public abstract class AbstractPlugin extends PluginBase {

    @Getter private static AbstractPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        ArenaFactory.getInstance().init();
        MapFactory.getInstance().init();

        this.registerPluginLoader();
    }

    public abstract void registerPluginLoader();

    public abstract @NonNull GamePlayer registerNewPlayer(Player player);

    public abstract String getAlias();
}