package cn.hamster3.mc.plugin.core.bukkit.command.lore;

import cn.hamster3.mc.plugin.core.bukkit.command.ChildCommand;
import cn.hamster3.mc.plugin.core.bukkit.constant.CoreMessage;
import cn.hamster3.mc.plugin.core.bukkit.util.BukkitUtils;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LoreCustomModelDataCommand extends ChildCommand {
    public static final LoreCustomModelDataCommand INSTANCE = new LoreCustomModelDataCommand();

    private LoreCustomModelDataCommand() {
    }

    @Override
    public @NotNull String getName() {
        return "cmd";
    }

    @Override
    public @NotNull String getUsage() {
        return "cmd [data]";
    }

    @Override
    public boolean hasPermission(@NotNull CommandSender sender) {
        return true;
    }

    @Override
    public @NotNull String getDescription() {
        return "设置手持物品的自定义模型数据";
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            CoreMessage.COMMAND_MUST_USED_BY_PLAYER.show(sender);
            return true;
        }
        ItemStack stack = player.getItemInHand();
        if (BukkitUtils.isEmptyItemStack(stack)) {
            CoreMessage.COMMAND_LORE_HAND_EMPTY.show(player);
            return true;
        }
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) {
            return true;
        }
        if (args.length < 1) {
            meta.setCustomModelData(null);
            stack.setItemMeta(meta);
            CoreMessage.COMMAND_LORE_CMD_CLEAR_SUCCESS.show(sender);
            return true;
        }
        try {
            int data = Integer.parseInt(args[0]);
            meta.setCustomModelData(data);
            stack.setItemMeta(meta);
            CoreMessage.COMMAND_LORE_CMD_SET_SUCCESS.show(sender, TextReplacementConfig.builder()
                    .matchLiteral("%data%").replacement(args[0])
                    .build());
        } catch (NumberFormatException e) {
            CoreMessage.COMMAND_LORE_CMD_SET_INPUT_ERROR.show(sender);
            return true;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        return null;
    }
}
