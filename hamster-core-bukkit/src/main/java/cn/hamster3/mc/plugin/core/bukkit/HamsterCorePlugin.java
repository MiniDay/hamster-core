package cn.hamster3.mc.plugin.core.bukkit;

import cn.hamster3.mc.plugin.core.bukkit.api.CoreBukkitAPI;
import cn.hamster3.mc.plugin.core.bukkit.command.core.ParentCoreCommand;
import cn.hamster3.mc.plugin.core.bukkit.command.lore.ParentLoreCommand;
import cn.hamster3.mc.plugin.core.bukkit.constant.CoreMessage;
import cn.hamster3.mc.plugin.core.bukkit.hook.PointAPI;
import cn.hamster3.mc.plugin.core.bukkit.hook.VaultAPI;
import cn.hamster3.mc.plugin.core.bukkit.listener.CallbackListener;
import cn.hamster3.mc.plugin.core.bukkit.listener.DebugListener;
import cn.hamster3.mc.plugin.core.bukkit.page.listener.PageListener;
import cn.hamster3.mc.plugin.core.bukkit.util.ItemStackAdapter;
import cn.hamster3.mc.plugin.core.common.constant.CoreConstantObjects;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class HamsterCorePlugin extends JavaPlugin {
    private static HamsterCorePlugin instance;

    private BukkitAudiences audienceProvider;

    public static HamsterCorePlugin getInstance() {
        return instance;
    }

    public static void sync(Runnable runnable) {
        Bukkit.getScheduler().runTask(instance, runnable);
    }

    @Override
    public void onLoad() {
        instance = this;
        Logger logger = getLogger();
        long start = System.currentTimeMillis();
        logger.info("仓鼠核心正在初始化...");
        saveDefaultConfig();
        reloadConfig();
        logger.info("已读取配置文件.");
        CoreConstantObjects.GSON = CoreConstantObjects.GSON.newBuilder()
                .registerTypeAdapter(ItemStack.class, ItemStackAdapter.INSTANCE)
                .create();
        CoreBukkitAPI.init();
        logger.info("已初始化 CoreBukkitAPI.");
        CoreMessage.init(this);
        logger.info("已初始化语言文本.");
        long time = System.currentTimeMillis() - start;
        logger.info("仓鼠核心初始化完成，总计耗时 " + time + " ms.");
    }

    @Override
    public void onEnable() {
        Logger logger = getLogger();
        long start = System.currentTimeMillis();
        logger.info("仓鼠核心正在启动...");
        VaultAPI.reloadVaultHook();
        logger.info("已完成 VaultAPI 挂载.");
        PointAPI.reloadPlayerPointAPIHook();
        logger.info("已完成 PlayerPoints 挂载.");
        audienceProvider = BukkitAudiences.create(this);
        logger.info("已完成 BukkitAudiences 挂载.");
        Bukkit.getPluginManager().registerEvents(PageListener.INSTANCE, this);
        logger.info("已注册 PageListener.");
        Bukkit.getPluginManager().registerEvents(CallbackListener.INSTANCE, this);
        logger.info("已注册 CallbackListener.");
        Bukkit.getPluginManager().registerEvents(DebugListener.INSTANCE, this);
        logger.info("已注册 DebugListener.");
        ParentCoreCommand.INSTANCE.hook(getCommand("HamsterCore"));
        ParentLoreCommand.INSTANCE.hook(getCommand("lore"));
        long time = System.currentTimeMillis() - start;
        logger.info("仓鼠核心启动完成，总计耗时 " + time + " ms.");
    }

    @Override
    public void onDisable() {
        Logger logger = getLogger();
        long start = System.currentTimeMillis();
        logger.info("仓鼠核心正在关闭...");
        CoreConstantObjects.WORKER_EXECUTOR.shutdownNow();
        logger.info("已暂停 WORKER_EXECUTOR.");
        CoreConstantObjects.SCHEDULED_EXECUTOR.shutdownNow();
        logger.info("已暂停 SCHEDULED_EXECUTOR.");
        long time = System.currentTimeMillis() - start;
        logger.info("仓鼠核心已关闭，总计耗时 " + time + " ms.");
    }

    public BukkitAudiences getAudienceProvider() {
        return audienceProvider;
    }
}

