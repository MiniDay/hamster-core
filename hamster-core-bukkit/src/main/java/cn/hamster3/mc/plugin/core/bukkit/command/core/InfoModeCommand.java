package cn.hamster3.mc.plugin.core.bukkit.command.core;

import cn.hamster3.mc.plugin.core.bukkit.command.ChildCommand;
import cn.hamster3.mc.plugin.core.bukkit.constant.CoreMessage;
import cn.hamster3.mc.plugin.core.bukkit.listener.DebugListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class InfoModeCommand extends ChildCommand {
    public static final InfoModeCommand INSTANCE = new InfoModeCommand();

    private InfoModeCommand() {
    }

    @Override
    @SuppressWarnings("SpellCheckingInspection")
    public @NotNull String getName() {
        return "infomode";
    }

    @Override
    @SuppressWarnings("SpellCheckingInspection")
    public @NotNull String getUsage() {
        return "infomode [on/off]";
    }

    @Override
    public boolean hasPermission(@NotNull CommandSender sender) {
        return true;
    }

    @Override
    public @NotNull String getDescription() {
        return "开启信息查询模式";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            CoreMessage.COMMAND_MUST_USED_BY_PLAYER.show(sender);
            return true;
        }
        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();
        if (args.length >= 1) {
            switch (args[0]) {
                case "1":
                case "on": {
                    DebugListener.INFO_MODE_PLAYERS.add(uuid);
                    CoreMessage.COMMAND_DEBUG_INFO_MODE_ON.show(player);
                    return true;
                }
                case "0":
                case "off": {
                    DebugListener.INFO_MODE_PLAYERS.remove(uuid);
                    CoreMessage.COMMAND_DEBUG_INFO_MODE_OFF.show(player);
                    return true;
                }
            }
        }

        if (DebugListener.INFO_MODE_PLAYERS.contains(uuid)) {
            DebugListener.INFO_MODE_PLAYERS.remove(uuid);
            CoreMessage.COMMAND_DEBUG_INFO_MODE_OFF.show(player);
        } else {
            DebugListener.INFO_MODE_PLAYERS.add(uuid);
            CoreMessage.COMMAND_DEBUG_INFO_MODE_ON.show(player);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
