package dev.abstractgames.command;

import cn.nukkit.command.CommandSender;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;

@RequiredArgsConstructor @Getter
public abstract class Argument {

    private final @NonNull String name;
    private final @Nullable String permission;

    public abstract void execute(@NonNull CommandSender sender, @NonNull String commandLabel, String[] args);
}