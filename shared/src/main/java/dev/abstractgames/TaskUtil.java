package dev.abstractgames;

import cn.nukkit.Server;

public final class TaskUtil {

    public static void runAsync(Runnable runnable) {
        if (Server.getInstance().isPrimaryThread()) {
            Server.getInstance().getScheduler().scheduleTask(AbstractPlugin.getInstance(), runnable, true);
        } else {
            runnable.run();
        }
    }
}