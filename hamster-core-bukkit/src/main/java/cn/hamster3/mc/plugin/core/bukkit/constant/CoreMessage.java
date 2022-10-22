package cn.hamster3.mc.plugin.core.bukkit.constant;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public enum CoreMessage {
    COMMAND_NOT_FOUND,
    COMMAND_MUST_USED_BY_PLAYER,

    COMMAND_DEBUG_BLOCK_INFO_ON,
    COMMAND_DEBUG_BLOCK_INFO_OFF;

    public void show(CommandSender sender) {
        sender.sendMessage(name());
    }

    public void show(Player player) {
        player.sendMessage(name());
    }
}
