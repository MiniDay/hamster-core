package cn.hamster3.mc.plugin.core.bukkit.page;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@SuppressWarnings("unused")
public interface PageElement {
    @NotNull
    default ItemStack getDisplayItem(@NotNull HumanEntity player, @NotNull ItemStack defaultItem, @NotNull Map<String, String> pageVariables) {
        return defaultItem.clone();
    }

    default Map<String, String> getVariables(@NotNull HumanEntity player, @NotNull Map<String, String> pageVariables) {
        return pageVariables;
    }

}
