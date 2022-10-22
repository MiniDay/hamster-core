package cn.hamster3.mc.plugin.core.bukkit.command;

import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ChildCommand implements TabExecutor {
    @NotNull
    public abstract String getName();

    @NotNull
    public abstract String getUsage();

    @Nullable
    public abstract String getPermission();

    @NotNull
    public abstract String getDescription();

}
