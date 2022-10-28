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

public class LoreAddCommand extends ChildCommand {
    public static final LoreAddCommand INSTANCE = new LoreAddCommand();

    private LoreAddCommand() {
    }

    @Override
    public @NotNull String getName() {
        return "add";
    }

    @Override
    public @NotNull String getUsage() {
        return "add <lore>";
    }

    @Override
    public boolean hasPermission(@NotNull CommandSender sender) {
        return true;
    }

    @Override
    public @NotNull String getDescription() {
        return "为手持物品添加一条 lore";
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length < 1) {
            CoreMessage.COMMAND_LORE_EMPTY_INPUT.show(sender);
            return true;
        }
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
            if (lore == null) {
                lore = new ArrayList<>();
            }
            lore.add(String.join(" ", args));
            meta.setLore(lore);
        }
        stack.setItemMeta(meta);
        CoreMessage.COMMAND_LORE_ADD_SUCCESS.show(player);
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        return null;
    }
}
