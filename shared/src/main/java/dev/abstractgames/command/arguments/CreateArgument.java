package dev.abstractgames.command.arguments;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Level;
import cn.nukkit.utils.TextFormat;
import dev.abstractgames.AbstractPlugin;
import dev.abstractgames.TaskUtil;
import dev.abstractgames.object.GameMap;
import dev.abstractgames.command.Argument;
import dev.abstractgames.factory.MapFactory;
import dev.abstractgames.utils.GameUtils;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashSet;

public final class CreateArgument extends Argument {

    public CreateArgument(String name, String permission) {
        super(name, permission);
    }

    @Override
    public void execute(CommandSender sender, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(TextFormat.RED + "Run this command in-game");

            return;
        }

        if (args.length < 2) {
            sender.sendMessage(TextFormat.RED + "Usage: /" + commandLabel + " create <minSlots> <maxSlots>");

            return;
        }

        int minSlots;
        int maxSlots;

        try {
            minSlots = Integer.parseInt(args[0]);
            maxSlots = Integer.parseInt(args[1]);
        } catch (Exception e) {
            sender.sendMessage(TextFormat.RED + "Usage: /" + commandLabel + " create <minSlots> <maxSlots>");

            return;
        }

        Level level = ((Player) sender).getLevel();

        if (level.equals(Server.getInstance().getDefaultLevel())) {
            sender.sendMessage(TextFormat.RED + "You can't configure maps in default world");

            return;
        }

        if (MapFactory.getInstance().getMap(level.getFolderName()) != null) {
            sender.sendMessage(TextFormat.RED + "Map " + level.getFolderName() + " already exists.");

            return;
        }

        TaskUtil.runAsync(() -> {
            GameUtils.copyMap(
                    Paths.get(Server.getInstance().getDataPath(), "/worlds/" + level.getFolderName()).toFile(),
                    new File(AbstractPlugin.getInstance().getDataFolder(), "backup/" + level.getFolderName())
            );

            MapFactory.getInstance().registerNewMap(new GameMap(
                    level.getFolderName(),
                    minSlots,
                    maxSlots,
                    new HashSet<>()
            ), true);
        });
    }
}