package cn.hamster3.mc.plugin.core.proxy;

import cn.hamster3.mc.plugin.core.common.constant.CoreConstantObjects;
import net.md_5.bungee.api.plugin.Plugin;

public class HamsterCorePlugin extends Plugin {
    private static HamsterCorePlugin instance;

    public static HamsterCorePlugin getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        getLogger().info("仓鼠核心正在启动...");
        long time = System.currentTimeMillis() - start;
        getLogger().info("仓鼠核心已启动，总计耗时 " + time + " ms.");
    }

    @Override
    public void onDisable() {
        long start = System.currentTimeMillis();
        getLogger().info("仓鼠核心正在关闭...");
        CoreConstantObjects.WORKER_EXECUTOR.shutdownNow();
        getLogger().info("已暂停 WORKER_EXECUTOR.");
        CoreConstantObjects.SCHEDULED_EXECUTOR.shutdownNow();
        getLogger().info("已暂停 SCHEDULED_EXECUTOR.");
        long time = System.currentTimeMillis() - start;
        getLogger().info("仓鼠核心已关闭，总计耗时 " + time + " ms.");
    }
}
