package cn.hamster3.mc.plugin.core.bukkit.command.lore;

import cn.hamster3.mc.plugin.core.bukkit.command.ChildCommand;
import cn.hamster3.mc.plugin.core.bukkit.constant.CoreMessage;
import cn.hamster3.mc.plugin.core.bukkit.util.BukkitUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LoreClearCommand extends ChildCommand {
    public static final LoreClearCommand INSTANCE = new LoreClearCommand();

    private LoreClearCommand() {
    }

    @Override
    public @NotNull String getName() {
        return "clear";
    }

    @Override
    public @NotNull String getUsage() {
        return "clear";
    }

    @Override
    public boolean hasPermission(@NotNull CommandSender sender) {
        return true;
    }

    @Override
    public @NotNull String getDescription() {
        return "清除手持物品的所有 lore";
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            CoreMessage.COMMAND_MUST_USED_BY_PLAYER.show(sender);
            return true;
        }
        Player player = (Player) sender;
        ItemStack stack = player.getItemInHand();
        if (BukkitUtils.isEmptyItemStack(stack)) {
            CoreMessage.COMMAND_LORE_HAND_EMPTY.show(player);
            return true;
        }
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            List<String> lore = meta.getLore();
            if (lore == null || lore.isEmpty()) {
                CoreMessage.COMMAND_LORE_CLEAR_NOTHING.show(player);
                return true;
            }
            meta.setLore(new ArrayList<>());
        }
        stack.setItemMeta(meta);
        CoreMessage.COMMAND_LORE_CLEAR_SUCCESS.show(player);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
