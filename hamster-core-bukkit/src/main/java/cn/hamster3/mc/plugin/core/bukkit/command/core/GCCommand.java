package cn.hamster3.mc.plugin.core.bukkit.command.core;

import cn.hamster3.mc.plugin.core.bukkit.command.ChildCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class GCCommand extends ChildCommand {
    public static final GCCommand INSTANCE = new GCCommand();

    private GCCommand() {
    }

    @Override
    public @NotNull String getName() {
        return "gc";
    }

    @Override
    public @NotNull String getUsage() {
        return "gc";
    }

    @Override
    public boolean hasPermission(@NotNull CommandSender sender) {
        return true;
    }

    @Override
    public @NotNull String getDescription() {
        return "通知JVM进行一次垃圾回收";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        System.gc();
        sender.sendMessage("§a已发送 GC 信号.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
