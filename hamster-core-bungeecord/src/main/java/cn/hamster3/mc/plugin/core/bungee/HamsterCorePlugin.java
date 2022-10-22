package cn.hamster3.mc.plugin.core.bungee;

import cn.hamster3.mc.plugin.core.bungee.api.CoreBungeeAPI;
import cn.hamster3.mc.plugin.core.common.constant.CoreConstantObjects;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Logger;

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
        Logger logger = getLogger();
        long start = System.currentTimeMillis();
        logger.info("仓鼠核心正在启动...");
        CoreBungeeAPI.init();
        logger.info("CoreAPI 已初始化.");
        long time = System.currentTimeMillis() - start;
        logger.info("仓鼠核心已启动，总计耗时 " + time + " ms.");
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
}
