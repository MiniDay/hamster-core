package cn.hamster3.mc.plugin.core.bukkit.util;

import cn.hamster3.mc.plugin.core.bukkit.listener.CallbackListener;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public final class CallbackUtils {
    private CallbackUtils() {
    }

    public static CompletableFuture<String> getPlayerChat(HumanEntity player) {
        return CallbackListener.CHATS.computeIfAbsent(player.getUniqueId(), o -> new CompletableFuture<>());
    }

    public static CompletableFuture<Block> getPlayerClickedBlock(HumanEntity player) {
        return CallbackListener.BLOCKS.computeIfAbsent(player.getUniqueId(), o -> new CompletableFuture<>());
    }

    public static CompletableFuture<Entity> getPlayerClickedEntity(HumanEntity player) {
        return CallbackListener.ENTITIES.computeIfAbsent(player.getUniqueId(), o -> new CompletableFuture<>());
    }
}
