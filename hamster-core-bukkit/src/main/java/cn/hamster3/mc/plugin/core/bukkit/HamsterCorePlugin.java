package cn.hamster3.mc.plugin.core.bukkit;

import cn.hamster3.mc.plugin.core.bukkit.api.CoreBukkitAPI;
import cn.hamster3.mc.plugin.core.bukkit.command.ParentCommand;
import cn.hamster3.mc.plugin.core.bukkit.command.debug.BlockInfoCommand;
import cn.hamster3.mc.plugin.core.bukkit.command.debug.YamlCommand;
import cn.hamster3.mc.plugin.core.bukkit.command.lore.ParentLoreCommand;
import cn.hamster3.mc.plugin.core.bukkit.hook.PointAPI;
import cn.hamster3.mc.plugin.core.bukkit.hook.VaultAPI;
import cn.hamster3.mc.plugin.core.bukkit.listener.CallbackListener;
import cn.hamster3.mc.plugin.core.bukkit.listener.DebugListener;
import cn.hamster3.mc.plugin.core.bukkit.page.listener.PageListener;
import cn.hamster3.mc.plugin.core.common.constant.CoreConstantObjects;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.logging.Logger;

public class HamsterCorePlugin extends JavaPlugin {
    public static final ParentCommand COMMAND_EXECUTOR = new ParentCommand("core");
    private static HamsterCorePlugin instance;

    public static HamsterCorePlugin getInstance() {
        return instance;
    }

    public static void sync(Runnable runnable) {
        Bukkit.getScheduler().runTask(instance, runnable);
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
        CoreBukkitAPI.init();
        logger.info("CoreAPI 已初始化.");
        VaultAPI.reloadVaultHook();
        logger.info("完成 VaultAPI 挂载.");
        PointAPI.reloadPlayerPointAPIHook();
        logger.info("完成 PlayerPoints 挂载.");
        Bukkit.getPluginManager().registerEvents(PageListener.INSTANCE, this);
        logger.info("已注册 PageListener.");
        Bukkit.getPluginManager().registerEvents(CallbackListener.INSTANCE, this);
        logger.info("已注册 CallbackListener.");
        Bukkit.getPluginManager().registerEvents(DebugListener.INSTANCE, this);
        logger.info("已注册 DebugListener.");
        COMMAND_EXECUTOR.addChildCommand(BlockInfoCommand.INSTANCE);
        logger.info("已添加指令: " + BlockInfoCommand.INSTANCE.getName() + " .");
        COMMAND_EXECUTOR.addChildCommand(YamlCommand.INSTANCE);
        logger.info("已添加指令: " + YamlCommand.INSTANCE.getName() + " .");
        COMMAND_EXECUTOR.addChildCommand(ParentLoreCommand.INSTANCE);
        logger.info("已添加指令: " + ParentLoreCommand.INSTANCE.getName() + " .");

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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return COMMAND_EXECUTOR.onCommand(sender, command, label, args);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return COMMAND_EXECUTOR.onTabComplete(sender, command, alias, args);
    }
}
