package cn.hamster3.mc.plugin.core.bukkit.constant;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public enum CoreMessage {
    COMMAND_NOT_FOUND,
    COMMAND_MUST_USED_BY_PLAYER,

    COMMAND_DEBUG_INFO_MODE_ON,
    COMMAND_DEBUG_INFO_MODE_OFF,

    COMMAND_LORE_HAND_EMPTY,
    COMMAND_LORE_EMPTY_INPUT,
    COMMAND_LORE_ADD_SUCCESS,
    COMMAND_LORE_CLEAR_NOTHING,
    COMMAND_LORE_CLEAR_SUCCESS,
    COMMAND_LORE_NAME_SUCCESS,
    COMMAND_LORE_REMOVE_NOT_INPUT_NUMBER,
    COMMAND_LORE_REMOVE_NOT_INPUT_NUMBER_ERROR,
    COMMAND_LORE_REMOVE_INDEX_OUT_OF_RANGE,
    COMMAND_LORE_REMOVE_SUCCESS,
    COMMAND_LORE_SET_NOT_INPUT_NUMBER,
    COMMAND_LORE_SET_NOT_INPUT_NUMBER_ERROR,
    COMMAND_LORE_SET_NOT_INPUT_TEXT,
    COMMAND_LORE_SET_INDEX_OUT_OF_RANGE,
    COMMAND_LORE_SET_SUCCESS;

    public void show(CommandSender sender) {
        sender.sendMessage(name());
    }

    public void show(Player player) {
        player.sendMessage(name());
    }
}
