package cn.hamster3.mc.plugin.core.bukkit.constant;

import cn.hamster3.mc.plugin.core.bukkit.api.CoreBukkitAPI;
import cn.hamster3.mc.plugin.core.bukkit.util.BukkitUtils;
import cn.hamster3.mc.plugin.core.common.data.DisplayMessage;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public enum CoreMessage {
    COMMAND_NOT_FOUND,
    COMMAND_NOT_HAS_PERMISSION,
    COMMAND_MUST_USED_BY_PLAYER,
    COMMAND_DEBUG_INFO_MODE_ON,
    COMMAND_DEBUG_INFO_MODE_OFF,
    COMMAND_LORE_HAND_EMPTY,
    COMMAND_LORE_EMPTY_INPUT,
    COMMAND_LORE_ADD_SUCCESS,
    COMMAND_LORE_CLEAR_NOTHING,
    COMMAND_LORE_CLEAR_SUCCESS,
    COMMAND_LORE_CMD_CLEAR_SUCCESS,
    COMMAND_LORE_CMD_SET_SUCCESS,
    COMMAND_LORE_CMD_SET_INPUT_ERROR,
    COMMAND_LORE_NAME_SUCCESS,
    COMMAND_LORE_REMOVE_NOT_INPUT_NUMBER,
    COMMAND_LORE_REMOVE_INPUT_NUMBER_ERROR,
    COMMAND_LORE_REMOVE_INDEX_OUT_OF_RANGE,
    COMMAND_LORE_REMOVE_SUCCESS,
    COMMAND_LORE_SET_NOT_INPUT_NUMBER,
    COMMAND_LORE_SET_INPUT_NUMBER_ERROR,
    COMMAND_LORE_SET_NOT_INPUT_TEXT,
    COMMAND_LORE_SET_INDEX_OUT_OF_RANGE,
    COMMAND_LORE_SET_SUCCESS;

    private DisplayMessage message;

    public static void init(@NotNull Plugin plugin) {
        ConfigurationSection config = plugin.getConfig().getConfigurationSection("messages");
        if (config == null) {
            plugin.getLogger().warning("加载消息失败: 配置文件中未找到 messages 节点！");
            return;
        }
        for (CoreMessage value : values()) {
            try {
                value.message = BukkitUtils.getDisplayMessage(config.getConfigurationSection(value.name()));
            } catch (Exception e) {
                plugin.getLogger().warning("加载消息设置 " + value.name() + " 时遇到了一个异常: ");
                e.printStackTrace();
            }
        }
    }

    public void show(CommandSender sender) {
        if (message == null) {
            sender.sendMessage(name());
        }
        Audience audience = CoreBukkitAPI.getInstance().getAudienceProvider().sender(sender);
        message.show(audience);
    }

    @SuppressWarnings("unused")
    public void show(CommandSender sender, TextReplacementConfig replacement) {
        if (message == null) {
            sender.sendMessage(name());
        }
        Audience audience = CoreBukkitAPI.getInstance().getAudienceProvider().sender(sender);
        message.show(audience, replacement);
    }

    @SuppressWarnings("unused")
    public void show(CommandSender sender, TextReplacementConfig... replacement) {
        if (message == null) {
            sender.sendMessage(name());
        }
        Audience audience = CoreBukkitAPI.getInstance().getAudienceProvider().sender(sender);
        message.show(audience, replacement);
    }

    @SuppressWarnings("unused")
    public DisplayMessage getMessage() {
        return message;
    }
}
