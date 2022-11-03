package cn.hamster3.mc.plugin.core.bukkit.command.core;

import cn.hamster3.mc.plugin.core.bukkit.command.ChildCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class EnvCommand extends ChildCommand {
    public static final EnvCommand INSTANCE = new EnvCommand();

    private EnvCommand() {
    }

    @Override
    public @NotNull String getName() {
        return "env";
    }

    @Override
    public @NotNull String getUsage() {
        return "env [变量名]";
    }

    @Override
    public boolean hasPermission(@NotNull CommandSender sender) {
        return true;
    }

    @Override
    public @NotNull String getDescription() {
        return "获取系统环境变量";
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(System.getenv().toString());
            return true;
        }
        sender.sendMessage(args[0] + "=" + System.getenv().getOrDefault(args[0], ""));
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        return Collections.emptyList();
    }
}
