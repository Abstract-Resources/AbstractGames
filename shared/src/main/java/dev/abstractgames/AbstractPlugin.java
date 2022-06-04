package dev.abstractgames;

import cn.nukkit.plugin.PluginBase;
import lombok.Getter;

public abstract class AbstractPlugin extends PluginBase {

    @Getter private static AbstractPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    public abstract void registerPluginLoader();
}