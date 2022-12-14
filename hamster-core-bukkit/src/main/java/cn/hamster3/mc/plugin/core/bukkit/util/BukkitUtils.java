package cn.hamster3.mc.plugin.core.bukkit.util;

import cn.hamster3.mc.plugin.core.bukkit.HamsterCorePlugin;
import cn.hamster3.mc.plugin.core.common.data.DisplayMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@SuppressWarnings("unused")
public final class BukkitUtils {
    private BukkitUtils() {
    }

    @NotNull
    public static String getMCVersion() {
        return Bukkit.getBukkitVersion().split("-")[0];
    }

    @NotNull
    public static String getNMSVersion() {
        return Bukkit.getServer().getClass().getName().split("\\.")[3];
    }

    @SuppressWarnings("deprecation")
    @NotNull
    public static Package getNMSPackage() {
        String nmsVersion = getNMSVersion();
        return Package.getPackage("net.minecraft.server." + nmsVersion);
    }

    @NotNull
    public static Class<?> getNMSClass(@NotNull String className) throws ClassNotFoundException {
        String nmsVersion = getNMSVersion();
        return Class.forName("net.minecraft.server." + nmsVersion + "." + className);
    }

    /**
     * 让玩家以最高权限执行命令
     *
     * @param player  玩家
     * @param command 要执行的命令
     */
    public static void opCommand(@NotNull Player player, @NotNull String command) {
        boolean isOp = player.isOp();
        player.setOp(true);
        Bukkit.dispatchCommand(player, command);
        player.setOp(isOp);
    }

    /**
     * 获取玩家的头颅
     * 在1.11以上的服务端中获取头颅材质是在服务器上运行的
     * 因此建议使用异步线程调用该方法
     *
     * @param uuid 要获取的玩家
     * @return 玩家的头颅物品
     */
    @NotNull
    public static ItemStack getPlayerHead(@NotNull UUID uuid) {
        return getPlayerHead(Bukkit.getOfflinePlayer(uuid));
    }

    /**
     * 获取玩家的头颅
     * <p>
     * 在 1.11 以上的服务端建议使用异步线程调用该方法
     * <p>
     * 因为这些服务端中通过网络获取头颅材质是在服务器上运行的
     *
     * @param offlinePlayer 要获取的玩家
     * @return 玩家的头颅物品
     */
    @NotNull
    public static ItemStack getPlayerHead(@NotNull OfflinePlayer offlinePlayer) {
        ItemStack stack;
        try {
            stack = new ItemStack(Material.valueOf("PLAYER_HEAD"));
        } catch (IllegalArgumentException e) {
            //noinspection deprecation
            stack = new ItemStack(Material.valueOf("SKULL_ITEM"), 1, (short) 3);
        }
        SkullMeta meta = (SkullMeta) stack.getItemMeta();
        if (meta != null) {
            meta.setOwningPlayer(offlinePlayer);
        }
        stack.setItemMeta(meta);
        return stack;
    }

    /**
     * 获取玩家的头颅
     * 在1.11以上的服务端中获取头颅材质是在服务器上运行的
     * 因此建议使用异步线程调用该方法
     *
     * @param name 要获取的玩家
     * @return 玩家的头颅物品
     */
    @NotNull
    @SuppressWarnings("deprecation")
    public static ItemStack getPlayerHead(@NotNull String name) {
        ItemStack stack;
        try {
            stack = new ItemStack(Material.valueOf("PLAYER_HEAD"));
        } catch (IllegalArgumentException e) {
            stack = new ItemStack(Material.valueOf("SKULL_ITEM"), 1, (short) 3);
        }
        SkullMeta meta = (SkullMeta) stack.getItemMeta();
        if (meta != null) {
            meta.setOwner(name);
            stack.setItemMeta(meta);
        }
        return stack;
    }

    /**
     * 判断物品是否为空
     * 当对象为null时返回true
     * 当物品的Material为AIR时返回true
     * 当物品的数量小于1时返回true
     *
     * @param stack 物品
     * @return 是否为空
     */
    public static boolean isEmptyItemStack(ItemStack stack) {
        return stack == null || stack.getAmount() < 1 || stack.getType() == Material.AIR;
    }

    /**
     * 给予玩家一个物品, 当玩家背包满时
     * 将会在玩家附近生成这个物品的掉落物
     *
     * @param player 玩家
     * @param stack  物品
     */
    public static void giveItem(@NotNull HumanEntity player, @NotNull ItemStack stack) {
        if (isEmptyItemStack(stack)) {
            return;
        }
        World world = player.getWorld();
        for (ItemStack dropItem : player.getInventory().addItem(stack).values()) {
            world.dropItem(player.getLocation(), dropItem);
        }
    }

    /**
     * 获取物品的名称
     * 当物品为null时返回"null"
     * 当物品拥有DisplayName时返回DisplayName
     * 否则返回物品的Material的name
     *
     * @param stack 物品
     * @return 物品的名称
     */
    @NotNull
    public static String getItemName(ItemStack stack) {
        if (stack == null) {
            return "null";
        }
        if (stack.hasItemMeta()) {
            ItemMeta meta = stack.getItemMeta();
            if (meta != null && meta.hasDisplayName()) {
                return meta.getDisplayName();
            }
        }
        return stack.getType().name();
    }

    public static DisplayMessage getDisplayMessage(ConfigurationSection config) {
        if (config == null) {
            return null;
        }
        DisplayMessage displayMessage = new DisplayMessage();
        String message = config.getString("message");
        if (message != null) {
            displayMessage.setMessage(message);
        }
        String actionbar = config.getString("actionbar");
        if (actionbar != null) {
            displayMessage.setActionBar(actionbar);
        }
        String title = config.getString("title");
        String subtitle = config.getString("subtitle");
        if (title != null || subtitle != null) {
            displayMessage.setTitle(
                    title == null ? "" : title,
                    subtitle == null ? "" : subtitle,
                    config.getInt("fade-in", 10),
                    config.getInt("stay", 70),
                    config.getInt("fade-out", 20)
            );
        }
        String sound = config.getString("sound");
        if (sound != null) {
            displayMessage.setSound(sound,
                    (float) config.getDouble("volume", 1f),
                    (float) config.getDouble("pitch", 1f)
            );
        }
        return displayMessage;
    }

    @NotNull
    public static YamlConfiguration getPluginConfig(@NotNull Plugin plugin, @NotNull String filename) {
        File dataFolder = plugin.getDataFolder();
        if (dataFolder.mkdirs()) {
            HamsterCorePlugin.getInstance().getLogger().info("已为插件 %s 生成存档文件夹...");
        }
        File file = new File(dataFolder, filename);
        if (!file.exists()) {
            try (InputStream stream = plugin.getClass().getResourceAsStream("/" + filename)) {
                if (stream == null) {
                    throw new IllegalArgumentException("在插件 " + plugin.getName() + " 的文件内部未找到 " + filename + " !");
                }
                Files.copy(stream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new IllegalArgumentException("在插件 " + plugin.getName() + " 内部读取文件 " + filename + " 时发生错误!");
            }
        }
        return YamlConfiguration.loadConfiguration(file);
    }
}
