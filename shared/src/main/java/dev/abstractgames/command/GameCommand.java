package dev.abstractgames.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import dev.abstractgames.command.arguments.CreateArgument;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class GameCommand extends Command {

    private final Set<Argument> arguments = new HashSet<>();

    public GameCommand(String name, String description) {
        super(name, description);

        this.registerArgument(
                new CreateArgument("create", "game.command.create")
        );
    }

    private void registerArgument(Argument... arguments) {
        this.arguments.addAll(Arrays.asList(arguments));
    }

    private Argument getArgument(String argumentLabel) {
        return this.arguments.stream()
                .filter(argument -> argument.getName().equalsIgnoreCase(argumentLabel))
                .findAny().orElse(null);
    }

    @Override
    public boolean execute(CommandSender commandSender, String label, String[] args) {
        if (args.length == 0) {
            commandSender.sendMessage(TextFormat.RED + "Use /" + label + " help");

            return false;
        }

        Argument argument = this.getArgument(args[0]);

        if (argument == null) {
            commandSender.sendMessage(TextFormat.RED + "Use /" + label + " help");

            return false;
        }

        if (argument.getPermission() != null && !commandSender.hasPermission(argument.getPermission())) {
            commandSender.sendMessage(TextFormat.RED + "You don't have permissions to use this command");

            return false;
        }

        argument.execute(commandSender, label, Arrays.copyOfRange(args, 1, args.length));

        return false;
    }
}