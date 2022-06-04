package dev.abstractgames.command;

import cn.nukkit.command.CommandSender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public abstract class Argument {

    private final String name;
    private final String permission;

    public abstract void execute(CommandSender sender, String commandLabel, String[] args);
}