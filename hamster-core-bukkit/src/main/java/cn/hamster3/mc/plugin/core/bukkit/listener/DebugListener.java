package cn.hamster3.mc.plugin.core.bukkit.listener;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashSet;
import java.util.UUID;

public class DebugListener implements Listener {
    public static final DebugListener INSTANCE = new DebugListener();
    /**
     * 要查看方块信息的玩家
     */
    public static final HashSet<UUID> INFO_MODE_PLAYERS = new HashSet<>();

    private DebugListener() {
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        INFO_MODE_PLAYERS.remove(uuid);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!INFO_MODE_PLAYERS.contains(uuid)) {
            return;
        }
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        event.setCancelled(true);
        Block block = event.getClickedBlock();
        player.sendMessage("§e==============================");
        player.sendMessage(String.format("§a方块位置: %s %d %d %d",
                block.getWorld().getName(),
                block.getX(), block.getY(),
                block.getZ()
        ));
        player.sendMessage("§a方块材质: " + block.getType().name());
        player.sendMessage("§a方块能量: " + block.getBlockPower());
        player.sendMessage("§a方块湿度: " + block.getHumidity());
        player.sendMessage("§a自发光亮度: " + block.getLightLevel());
        player.sendMessage("§a获取天空亮度: " + block.getLightFromSky());
        player.sendMessage("§a方块吸收亮度: " + block.getLightFromBlocks());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!INFO_MODE_PLAYERS.contains(uuid)) {
            return;
        }
        event.setCancelled(true);
        Entity entity = event.getRightClicked();
        player.sendMessage("§e==============================");
        Location location = entity.getLocation();
        player.sendMessage(String.format("§a实体位置: %s %.2f %.2f %.2f %.2f %.2f",
                entity.getWorld().getName(), location.getX(),
                location.getY(),
                location.getZ(),
                location.getPitch(),
                location.getYaw()
        ));
        player.sendMessage("§a实体UUID: " + entity.getUniqueId());
        player.sendMessage("§a实体类型: " + entity.getType().name());
        player.sendMessage("§a实体名称: " + entity.getName());
        player.sendMessage("§a自定义名称: " + entity.getCustomName());
        player.sendMessage("§a名称可见: " + entity.isCustomNameVisible());
        player.sendMessage("§a计分板标签: " + entity.getScoreboardTags());
    }

}
