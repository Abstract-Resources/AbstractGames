package dev.abstractgames;

import cn.nukkit.plugin.PluginBase;
import dev.abstractgames.factory.ArenaFactory;
import dev.abstractgames.factory.MapFactory;
import lombok.Getter;

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

    public abstract String getAlias();
}