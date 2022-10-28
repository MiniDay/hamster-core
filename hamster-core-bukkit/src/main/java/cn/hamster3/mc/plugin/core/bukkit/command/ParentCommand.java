package cn.hamster3.mc.plugin.core.bukkit.command;

import cn.hamster3.mc.plugin.core.bukkit.constant.CoreMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class ParentCommand extends ChildCommand {
    @NotNull
    private final Plugin plugin;
    @NotNull
    private final String name;
    @Nullable
    private final ParentCommand parent;
    @NotNull
    private final List<ChildCommand> childCommands;

    public ParentCommand(@NotNull Plugin plugin, @NotNull String name) {
        this.plugin = plugin;
        this.name = name;
        parent = null;
        childCommands = new ArrayList<>();
    }

    public ParentCommand(@NotNull Plugin plugin, @NotNull String name, @Nullable ParentCommand parent) {
        this.plugin = plugin;
        this.name = name;
        this.parent = parent;
        childCommands = new ArrayList<>();
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @NotNull
    public String getUsage() {
        if (parent == null) {
            return "/" + name;
        }
        return parent.getUsage() + " " + name;
    }

    @Override
    public boolean hasPermission(@NotNull CommandSender sender) {
        return true;
    }

    @Override
    public @NotNull String getDescription() {
        return "";
    }

    @NotNull
    public List<ChildCommand> getChildCommands() {
        return childCommands;
    }

    @NotNull
    public List<ChildCommand> getEndChildCommands() {
        ArrayList<ChildCommand> list = new ArrayList<>();
        for (ChildCommand command : childCommands) {
            if (command instanceof ParentCommand) {
                list.addAll(((ParentCommand) command).getEndChildCommands());
            } else {
                list.add(command);
            }
        }
        return list;
    }

    public void addChildCommand(@NotNull ChildCommand command) {
        childCommands.add(command);
        plugin.getLogger().info("已为 " + getUsage() + " 添加子命令: " + command.getName() + " .");
    }

    @NotNull
    public Map<String, String> getCommandHelp(CommandSender sender) {
        HashMap<String, String> map = new HashMap<>();
        for (ChildCommand child : childCommands) {
            if (!child.hasPermission(sender)) {
                continue;
            }
            if (child instanceof ParentCommand) {
                Map<String, String> childMap = ((ParentCommand) child).getCommandHelp(sender);
                map.putAll(childMap);
                continue;
            }
            map.put(getUsage() + " " + child.getUsage(), child.getDescription());
        }
        return map;
    }

    public void hook(PluginCommand command) {
        if (command == null) {
            return;
        }
        command.setExecutor(this);
        command.setTabCompleter(this);
        plugin.getLogger().info("已注册指令 " + getUsage() + ".");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!hasPermission(sender)) {
            CoreMessage.COMMAND_NOT_HAS_PERMISSION.show(sender);
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("§e==================== [" + name + "使用帮助] ====================");
            Map<String, String> helpMap = getCommandHelp(sender);
            int maxLength = helpMap.keySet().stream()
                    .map(String::length)
                    .max(Integer::compareTo)
                    .orElse(-1);
            ArrayList<Map.Entry<String, String>> list = new ArrayList<>(helpMap.entrySet());
            list.sort(Map.Entry.comparingByKey());
            for (Map.Entry<String, String> entry : list) {
                sender.sendMessage(String.format("§a%-" + maxLength + "s   - %s", entry.getKey(), entry.getValue()));
            }
            return true;
        }
        for (ChildCommand childCommand : childCommands) {
            if (!childCommand.getName().equalsIgnoreCase(args[0])) {
                continue;
            }
            if (childCommand instanceof ParentCommand || childCommand.hasPermission(sender)) {
                return childCommand.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            }
            CoreMessage.COMMAND_NOT_HAS_PERMISSION.show(sender);
            return true;
        }
        CoreMessage.COMMAND_NOT_FOUND.show(sender);
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (args.length == 0) {
            return childCommands.stream()
                    .map(ChildCommand::getName)
                    .collect(Collectors.toList());
        }
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
