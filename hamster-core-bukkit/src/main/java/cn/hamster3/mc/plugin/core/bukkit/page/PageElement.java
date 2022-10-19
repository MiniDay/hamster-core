package cn.hamster3.mc.plugin.core.bukkit.page;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public interface PageElement {

    /**
     * 获取展示物品
     * <p>
     * 若返回 null 则使用 config 中的全局设置值
     *
     * @param player 占位符显示的目标玩家
     * @return 展示物品
     */
    default ItemStack getDisplayItem(HumanEntity player) {
        return null;
    }

    /**
     * 获取展示物品的显示材质
     * <p>
     * 若返回 null 则使用 config 中的全局设置值
     *
     * @param player 占位符显示的目标玩家
     * @return 展示物品的显示材质
     */
    default Material getMaterial(HumanEntity player) {
        return null;
    }

    /**
     * 替换物品的信息
     *
     * @param player         玩家
     * @param stack          物品
     * @param globalVariable 全局变量
     */
    default void replaceItemInfo(HumanEntity player, ItemStack stack, HashMap<String, String> globalVariable) {
        if (stack == null) {
            return;
        }
        Material type = getMaterial(player);
        if (type != null) {
            stack.setType(type);
        }

        ItemMeta meta = stack.getItemMeta();
        replaceMetaInfo(player, meta, globalVariable);
        stack.setItemMeta(meta);
    }

    /**
     * 替换物品的信息
     *
     * @param player         玩家
     * @param meta           物品信息
     * @param globalVariable 全局变量
     */
    default void replaceMetaInfo(HumanEntity player, ItemMeta meta, HashMap<String, String> globalVariable) {
        if (meta == null) {
            return;
        }

        Map<String, String> replacer = getVariable(player, globalVariable);

        if (meta.hasDisplayName()) {
            String displayName = replaceDisplayName(player, meta.getDisplayName(), globalVariable);
            meta.setDisplayName(displayName);
        }

        List<String> lore = meta.getLore();
        if (lore != null) {
            lore = replaceLore(player, lore, globalVariable);
            meta.setLore(lore);
        }
    }

    /**
     * 替换物品展示名称
     *
     * @param player         玩家
     * @param displayName    物品原名称
     * @param globalVariable 全局变量
     * @return 替换后的物品展示名称
     */
    default String replaceDisplayName(HumanEntity player, String displayName, HashMap<String, String> globalVariable) {
        return replacePlaceholder(player, displayName, globalVariable);
    }

    /**
     * 替换物品 lore 信息
     *
     * @param player         玩家
     * @param lore           物品原 lore 信息
     * @param globalReplacer 全局变量
     * @return 替换后的物品 lore 信息
     */
    default List<String> replaceLore(HumanEntity player, List<String> lore, HashMap<String, String> globalReplacer) {
        if (lore == null) {
            return null;
        }
        Map<String, String> replacer = getVariable(player, globalReplacer);
        for (int i = 0; i < lore.size(); i++) {
            String s = lore.get(i);
            for (Map.Entry<String, String> entry : replacer.entrySet()) {
                s = s.replace(entry.getKey(), entry.getValue());
            }
            lore.set(i, s);
        }
        return lore;
    }

    default String replacePlaceholder(HumanEntity player, String string, HashMap<String, String> globalVariable) {
        Map<String, String> replacer = getVariable(player, globalVariable);
        for (Map.Entry<String, String> entry : replacer.entrySet()) {
            string = string.replace(entry.getKey(), entry.getValue());
        }
        return string;
    }

    default Map<String, String> getVariable(HumanEntity player, HashMap<String, String> globalVariable) {
        return globalVariable;
    }

}
