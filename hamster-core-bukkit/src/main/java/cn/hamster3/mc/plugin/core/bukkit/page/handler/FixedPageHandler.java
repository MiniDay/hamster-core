package cn.hamster3.mc.plugin.core.bukkit.page.handler;

import cn.hamster3.mc.plugin.core.bukkit.page.ButtonGroup;
import cn.hamster3.mc.plugin.core.bukkit.page.PageConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 固定页面的 GUI
 */
@SuppressWarnings("unused")
public class FixedPageHandler extends PageHandler {
    public FixedPageHandler(@NotNull HumanEntity player) {
        super(player);
    }

    public FixedPageHandler(@NotNull PageConfig config, @NotNull HumanEntity player) {
        super(config, player);
    }

    @Override
    public void initPage() {
        if (inventory == null) {
            inventory = Bukkit.createInventory(this, getConfig().getInventory().getSize(), getTitle());
        }
        HumanEntity player = getPlayer();

        PageConfig config = getConfig();
        ButtonGroup group = getButtonGroup();

        List<String> graphic = config.getGraphic();
        for (int i = 0; i < graphic.size(); i++) {
            char[] chars = graphic.get(i).toCharArray();
            for (int j = 0; j < chars.length; j++) {
                char c = chars[j];
                int index = i * 9 + j;
                inventory.setItem(index, group.getButton(c));
            }
        }
    }
}
