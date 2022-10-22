package cn.hamster3.mc.plugin.core.bukkit.listener;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashSet;
import java.util.UUID;

public class DebugListener implements Listener {
    public static final DebugListener INSTANCE = new DebugListener();
    /**
     * 要查看方块信息的玩家
     */
    public static final HashSet<UUID> BLOCK_INFO = new HashSet<>();

    private DebugListener() {
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        BLOCK_INFO.remove(uuid);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!BLOCK_INFO.contains(uuid)) {
            return;
        }
        switch (event.getAction()) {
            case LEFT_CLICK_BLOCK:
            case LEFT_CLICK_AIR: {
                break;
            }
            default: {
                return;
            }
        }
        Block block = event.getClickedBlock();
        player.sendMessage("§e==============================");
        player.sendMessage(String.format("§a方块位置: %s %d %d %d", block.getWorld().getName(), block.getX(), block.getY(), block.getZ()));
        player.sendMessage("§a方块材质: " + block.getType().name());
        player.sendMessage("§a方块能量: " + block.getBlockPower());
        player.sendMessage("§a方块湿度: " + block.getHumidity());
        player.sendMessage("§a自发光亮度: " + block.getLightLevel());
        player.sendMessage("§a获取天空亮度: " + block.getLightFromSky());
        player.sendMessage("§a方块吸收亮度: " + block.getLightFromBlocks());
        event.setCancelled(true);
    }
}
