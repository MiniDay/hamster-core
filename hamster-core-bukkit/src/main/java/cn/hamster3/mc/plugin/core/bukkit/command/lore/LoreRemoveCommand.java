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

public class LoreRemoveCommand extends ChildCommand {
    public static final LoreRemoveCommand INSTANCE = new LoreRemoveCommand();

    private LoreRemoveCommand() {
    }

    @Override
    public @NotNull String getName() {
        return "remove";
    }

    @Override
    public @NotNull String getUsage() {
        return "remove <line>";
    }

    @Override
    public boolean hasPermission(@NotNull CommandSender sender) {
        return true;
    }

    @Override
    public @NotNull String getDescription() {
        return "为手持物品删除一条 lore";
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            CoreMessage.COMMAND_LORE_REMOVE_NOT_INPUT_NUMBER.show(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            CoreMessage.COMMAND_MUST_USED_BY_PLAYER.show(sender);
            return true;
        }
        int i = Integer.parseInt(args[0]);
        if (i <= 0) {
            CoreMessage.COMMAND_LORE_REMOVE_NOT_INPUT_NUMBER_ERROR.show(sender);
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
            if (lore == null) {
                lore = new ArrayList<>();
            }
            if (lore.size() < i) {
                CoreMessage.COMMAND_LORE_REMOVE_INDEX_OUT_OF_RANGE.show(player);
                return true;
            }
            lore.remove(i - 1);
            meta.setLore(lore);
        }
        stack.setItemMeta(meta);
        CoreMessage.COMMAND_LORE_REMOVE_SUCCESS.show(player);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
