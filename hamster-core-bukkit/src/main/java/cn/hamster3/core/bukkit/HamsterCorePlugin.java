package cn.hamster3.core.bukkit;

import cn.hamster3.core.bukkit.page.listener.PageListener;
import cn.hamster3.core.common.constant.ConstantObjects;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class HamsterCorePlugin extends JavaPlugin {
    private static HamsterCorePlugin instance;

    public static HamsterCorePlugin getInstance() {
        return instance;
    }

    public static void sync(Runnable runnable) {
        Bukkit.getScheduler().runTask(instance, runnable);
    }

    public static void async(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(instance, runnable);
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        getLogger().info("仓鼠核心正在启动...");
        Bukkit.getPluginManager().registerEvents(PageListener.INSTANCE, this);
        getLogger().info("已注册 PageListener.");
        long time = System.currentTimeMillis() - start;
        getLogger().info("仓鼠核心已启动，总计耗时 " + time + " ms.");
    }

    @Override
    public void onDisable() {
        long start = System.currentTimeMillis();
        getLogger().info("仓鼠核心正在关闭...");
        ConstantObjects.WORKER_EXECUTOR.shutdownNow();
        getLogger().info("已暂停 WORKER_EXECUTOR.");
        ConstantObjects.SCHEDULED_EXECUTOR.shutdownNow();
        getLogger().info("已暂停 SCHEDULED_EXECUTOR.");
        long time = System.currentTimeMillis() - start;
        getLogger().info("仓鼠核心已关闭，总计耗时 " + time + " ms.");
    }
}
