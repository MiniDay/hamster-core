package cn.hamster3.mc.plugin.core.bukkit.command.debug;

import cn.hamster3.mc.plugin.core.bukkit.command.ChildCommand;
import cn.hamster3.mc.plugin.core.bukkit.constant.CoreMessage;
import cn.hamster3.mc.plugin.core.bukkit.listener.DebugListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

@SuppressWarnings("SpellCheckingInspection")
public class BlockInfoCommand extends ChildCommand {
    public static final BlockInfoCommand INSTANCE = new BlockInfoCommand();

    private BlockInfoCommand() {
    }

    @Override
    public @NotNull String getName() {
        return "blockinfo";
    }

    @Override
    public @NotNull String getUsage() {
        return "blockinfo [on/off]";
    }

    @Override
    public @Nullable String getPermission() {
        return null;
    }

    @Override
    public @NotNull String getDescription() {
        return "开启方块信息查询模式";
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
                    DebugListener.BLOCK_INFO.add(uuid);
                    CoreMessage.COMMAND_DEBUG_BLOCK_INFO_ON.show(player);
                    return true;
                }
                case "0":
                case "off": {
                    DebugListener.BLOCK_INFO.remove(uuid);
                    CoreMessage.COMMAND_DEBUG_BLOCK_INFO_OFF.show(player);
                    return true;
                }
            }
        }

        if (DebugListener.BLOCK_INFO.contains(uuid)) {
            DebugListener.BLOCK_INFO.remove(uuid);
            CoreMessage.COMMAND_DEBUG_BLOCK_INFO_OFF.show(player);
        } else {
            DebugListener.BLOCK_INFO.add(uuid);
            CoreMessage.COMMAND_DEBUG_BLOCK_INFO_ON.show(player);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
