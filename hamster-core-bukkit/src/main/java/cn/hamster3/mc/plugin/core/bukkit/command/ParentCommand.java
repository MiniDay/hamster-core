package cn.hamster3.mc.plugin.core.bukkit.command;

import cn.hamster3.mc.plugin.core.bukkit.constant.CoreMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class ParentCommand implements TabExecutor {
    @NotNull
    private final String name;
    @Nullable
    private final ParentCommand parent;
    @NotNull
    private final List<ChildCommand> childCommands;

    public ParentCommand(@NotNull String name) {
        this.name = name;
        parent = null;
        childCommands = new ArrayList<>();
    }

    public ParentCommand(@NotNull String name, @Nullable ParentCommand parent) {
        this.name = name;
        this.parent = parent;
        childCommands = new ArrayList<>();
    }

    @NotNull
    public String getUsage() {
        if (parent == null) {
            return "/" + name;
        }
        return parent.getUsage() + " " + name;
    }

    @NotNull
    public List<ChildCommand> getChildCommands() {
        return childCommands;
    }

    public void addChildCommand(@NotNull ChildCommand command) {
        childCommands.add(command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§e==================== [" + name + "使用帮助] ====================");
            int maxLength = childCommands.stream()
                    .filter(o -> o.getPermission() == null || sender.hasPermission(o.getPermission()))
                    .map(o -> o.getUsage().length())
                    .max(Integer::compareTo)
                    .orElse(-1);
            for (ChildCommand child : childCommands) {
                String permission = child.getPermission();
                if (permission != null && !sender.hasPermission(permission)) {
                    continue;
                }
                sender.sendMessage(String.format("§a%s %-" + maxLength + "s - %s", getUsage(), child.getUsage(), child.getDescription()));
            }
            return true;
        }
        for (ChildCommand childCommand : childCommands) {
            if (childCommand.getName().equalsIgnoreCase(args[0])) {
                return childCommand.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            }
        }
        CoreMessage.COMMAND_NOT_FOUND.show(sender);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        for (ChildCommand child : childCommands) {
            if (args[0].equalsIgnoreCase(child.getName())) {
                return child.onTabComplete(sender, command, alias, Arrays.copyOfRange(args, 1, args.length));
            }
        }
        args[0] = args[0].toLowerCase();
        return childCommands.stream()
                .map(ChildCommand::getName)
                .filter(o -> o.toLowerCase().startsWith(args[0]))
                .collect(Collectors.toList());
    }
}
