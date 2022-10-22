package cn.hamster3.mc.plugin.core.bukkit.listener;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class CallbackListener implements Listener {
    public static final CallbackListener INSTANCE = new CallbackListener();

    public static final HashMap<UUID, CompletableFuture<String>> CHATS = new HashMap<>();
    public static final HashMap<UUID, CompletableFuture<Block>> BLOCKS = new HashMap<>();
    public static final HashMap<UUID, CompletableFuture<Entity>> ENTITIES = new HashMap<>();

    private CallbackListener() {
    }

    @EventHandler(ignoreCancelled = true)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        CompletableFuture<String> future = CHATS.remove(player.getUniqueId());
        if (future == null) {
            return;
        }
        future.complete(event.getMessage());
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        Player player = event.getPlayer();
        CompletableFuture<Block> future = BLOCKS.remove(player.getUniqueId());
        if (future == null) {
            return;
        }
        future.complete(event.getClickedBlock());
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        CompletableFuture<Entity> future = ENTITIES.remove(player.getUniqueId());
        if (future == null) {
            return;
        }
        future.complete(event.getRightClicked());
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        CompletableFuture<String> chat = CHATS.remove(uuid);
        if (chat != null) {
            chat.cancel(true);
        }
        CompletableFuture<Block> block = BLOCKS.remove(uuid);
        if (block != null) {
            block.cancel(true);
        }
        CompletableFuture<Entity> entity = ENTITIES.remove(uuid);
        if (entity != null) {
            entity.cancel(true);
        }
    }
}
