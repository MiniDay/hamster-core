package cn.hamster3.mc.plugin.core.bukkit.command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;

public abstract class ChildCommand implements TabExecutor {
    @NotNull
    public abstract String getName();

    @NotNull
    public abstract String getUsage();

    public abstract boolean hasPermission(@NotNull CommandSender sender);

    @NotNull
    public abstract String getDescription();

}
