package cn.hamster3.mc.plugin.core.bungee;

import cn.hamster3.mc.plugin.core.bungee.api.CoreBungeeAPI;
import cn.hamster3.mc.plugin.core.common.constant.CoreConstantObjects;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Logger;

public class HamsterCorePlugin extends Plugin {
    private static HamsterCorePlugin instance;
    private BungeeAudiences audienceProvider;

    public static HamsterCorePlugin getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
        Logger logger = getLogger();
        long start = System.currentTimeMillis();
        logger.info("仓鼠核心正在初始化...");
        CoreBungeeAPI.init();
        logger.info("CoreBungeeAPI 已初始化.");
        long time = System.currentTimeMillis() - start;
        logger.info("仓鼠核心初始化完成，总计耗时 " + time + " ms.");
    }

    @Override
    public void onEnable() {
        Logger logger = getLogger();
        long start = System.currentTimeMillis();
        logger.info("仓鼠核心正在启动...");
        audienceProvider = BungeeAudiences.create(this);
        logger.info("完成 BungeeAudiences 挂载.");
        long time = System.currentTimeMillis() - start;
        logger.info("仓鼠核心启动完成，总计耗时 " + time + " ms.");
    }

    @Override
    public void onDisable() {
        Logger logger = getLogger();
        long start = System.currentTimeMillis();
        logger.info("仓鼠核心正在关闭...");
        CoreConstantObjects.SCHEDULED_EXECUTOR.shutdownNow();
        logger.info("已暂停 SCHEDULED_EXECUTOR.");
        long time = System.currentTimeMillis() - start;
        logger.info("仓鼠核心已关闭，总计耗时 " + time + " ms.");
    }

    public BungeeAudiences getAudienceProvider() {
        return audienceProvider;
    }
}
