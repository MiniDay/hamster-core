package cn.hamster3.mc.plugin.core.bukkit.command.core;

import cn.hamster3.mc.plugin.core.bukkit.HamsterCorePlugin;
import cn.hamster3.mc.plugin.core.bukkit.command.ChildCommand;
import cn.hamster3.mc.plugin.core.bukkit.util.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class YamlCommand extends ChildCommand {
    public static final YamlCommand INSTANCE = new YamlCommand();

    private YamlCommand() {
    }

    @Override
    public @NotNull String getName() {
        return "yaml";
    }

    @Override
    public @NotNull String getUsage() {
        return "yaml";
    }

    @Override
    public boolean hasPermission(@NotNull CommandSender sender) {
        return true;
    }

    @Override
    public @NotNull String getDescription() {
        return "将当前信息保存至 yaml 中";
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        YamlConfiguration config = new YamlConfiguration();
        config.set("mc-version", BukkitUtils.getMCVersion());
        config.set("nms-version", BukkitUtils.getNMSVersion());
        config.set("server-version", Bukkit.getBukkitVersion());
        config.set("bukkit-version", Bukkit.getVersion());

        if (sender instanceof Player) {
            Player player = (Player) sender;
            config.set("player-name", player.getName());
            config.set("player-uuid", player.getUniqueId().toString());
            config.set("location", player.getLocation());
            config.set("hand-item", player.getInventory().getItemInHand());
        }

        ItemStack stack = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = stack.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add("§e测试 lore 1");
        lore.add("§e测试 lore 2");
        lore.add("§e测试 lore 3");
        meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        meta.setDisplayName("§a测试物品");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.values());
        stack.setItemMeta(meta);
        config.set("test-item", stack);
        File dataFolder = new File(HamsterCorePlugin.getInstance().getDataFolder(), "yaml");
        if (dataFolder.mkdirs()) {
            HamsterCorePlugin.getInstance().getLogger().info("创建 yaml 存档文件夹...");
        }
        File saveFile = new File(dataFolder, sender.getName() + "_" + System.currentTimeMillis() + ".yml");
        try {
            config.save(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sender.sendMessage("§a信息已保存至文件 " + saveFile.getAbsolutePath());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
