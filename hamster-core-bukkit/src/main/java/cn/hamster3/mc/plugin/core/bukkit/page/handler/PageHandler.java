package cn.hamster3.mc.plugin.core.bukkit.page.handler;

import cn.hamster3.mc.plugin.core.bukkit.page.ButtonGroup;
import cn.hamster3.mc.plugin.core.bukkit.page.PageConfig;
import cn.hamster3.mc.plugin.core.bukkit.page.PageManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

/**
 * GUI 处理类
 */
@SuppressWarnings("unused")
public abstract class PageHandler implements InventoryHolder {
    private final PageConfig config;
    private final HumanEntity player;
    protected Inventory inventory;

    public PageHandler(@NotNull HumanEntity player) {
        try {
            config = PageManager.getPageConfig(getClass());
        } catch (Exception e) {
            throw new IllegalArgumentException("加载界面配置时遇到了一个异常!", e);
        }
        this.player = player;
    }

    public PageHandler(@NotNull PageConfig config, @NotNull HumanEntity player) {
        this.config = config;
        this.player = player;
    }

    public abstract void initPage();

    public void onOpen(@NotNull InventoryOpenEvent event) {
    }

    public void onClick(@NotNull InventoryClickEvent event) {
        if (event.getSlot() == event.getRawSlot()) {
            return;
        }
        event.setCancelled(true);
    }

    public void onClickInside(@NotNull InventoryClickEvent event) {
        event.setCancelled(true);
    }

    public void onClickInside(@NotNull ClickType clickType, @NotNull InventoryAction action, int index) {
    }

    public void onPlayButtonSound(@NotNull ClickType clickType, @NotNull InventoryAction action, int index) {
        PageConfig config = getConfig();
        Sound sound = config.getButtonSound(getButtonGroup().getButtonName(index));
        sound = sound == null ? config.getButtonSound("default") : sound;
        if (sound == null) {
            return;
        }
        if (!(player instanceof Player)) {
            return;
        }
        ((Player) player).playSound(player.getLocation(), sound, 1, 1);
    }

    public void onDrag(@NotNull InventoryDragEvent event) {
    }

    public void onDragInside(@NotNull InventoryDragEvent event) {
        event.setCancelled(true);
    }

    public void onClose(@NotNull InventoryCloseEvent event) {
    }

    public void show() {
        show(true);
    }

    public void show(boolean init) {
        if (init) {
            initPage();
        }
        Bukkit.getScheduler().runTask(config.getPlugin(), () -> player.openInventory(getInventory()));
    }

    public void close() {
        Bukkit.getScheduler().runTask(config.getPlugin(), player::closeInventory);
    }

    @NotNull
    public PageConfig getConfig() {
        return config;
    }

    @NotNull
    public HumanEntity getPlayer() {
        return player;
    }

    @NotNull
    public ButtonGroup getButtonGroup() {
        return getConfig().getButtonGroup("default");
    }

    @NotNull
    public String getTitle() {
        return config.getTitle();
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
