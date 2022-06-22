package dev.abstractgames.command.arguments;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import dev.abstractgames.command.Argument;
import dev.abstractgames.factory.ArenaFactory;
import dev.abstractgames.object.GameArena;
import lombok.NonNull;

public final class JoinArgument extends Argument {

    public JoinArgument(String name, String permission) {
        super(name, permission);
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NonNull String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(TextFormat.RED + "Run this command in-game");

            return;
        }

        GameArena arena = ArenaFactory.getInstance().getRandomArena();

        if (arena == null) {
            arena = ArenaFactory.getInstance().registerNewArena(null);
        }

        if (arena == null) {
            sender.sendMessage(TextFormat.RED + "There are currently no games available");

            return;
        }

        arena.joinAsPlayer((Player) sender);
    }
}