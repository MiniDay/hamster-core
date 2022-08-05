package cn.hamster3.core.bukkit.page;

import cn.hamster3.core.bukkit.HamsterCorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class PageConfig implements InventoryHolder {
    @NotNull
    private final Plugin plugin;
    @NotNull
    private final ConfigurationSection config;

    @NotNull
    private final String title;
    @NotNull
    private final List<String> graphic;

    @NotNull
    private final ArrayList<ButtonGroup> buttonGroups;
    @NotNull
    private final HashMap<String, Sound> buttonSounds;
    @NotNull
    private final HashMap<String, ItemStack> buttons;

    @NotNull
    private final Inventory inventory;

    public PageConfig(@NotNull Plugin plugin, @NotNull ConfigurationSection config) {
        this.plugin = plugin;
        this.config = config;
        title = config.getString("title", "").replace("&", "§");

        List<String> graphicString = config.getStringList("graphic");
        if (graphicString.size() > 6) {
            graphicString = graphicString.subList(0, 6);
        }
        for (int i = 0; i < graphicString.size(); i++) {
            String s = graphicString.get(i);
            if (s.length() > 9) {
                s = s.substring(0, 9);
            }
            graphicString.set(i, s);
        }
        graphic = graphicString;

        inventory = Bukkit.createInventory(this, graphicString.size() * 9, title);

        buttons = new HashMap<>();
        ConfigurationSection buttonsConfig = config.getConfigurationSection("buttons");
        for (String key : buttonsConfig.getKeys(false)) {
            buttons.put(key, buttonsConfig.getItemStack(key));
        }

        buttonGroups = new ArrayList<>();
        ConfigurationSection buttonGroupsConfig = config.getConfigurationSection("groups");
        for (String key : buttonGroupsConfig.getKeys(false)) {
            buttonGroups.add(
                    new ButtonGroup(this, buttonGroupsConfig.getConfigurationSection(key))
            );
        }

        ButtonGroup group = getButtonGroup("default");
        for (int i = 0; i < graphicString.size(); i++) {
            char[] chars = graphicString.get(i).toCharArray();
            for (int j = 0; j < chars.length; j++) {
                char c = chars[j];
                int index = i * 9 + j;
                inventory.setItem(index, group.getButton(c));
            }
        }

        buttonSounds = new HashMap<>();
        ConfigurationSection buttonSoundConfig = config.getConfigurationSection("sounds");
        for (String key : buttonSoundConfig.getKeys(false)) {
            try {
                buttonSounds.put(key, Sound.valueOf(buttonSoundConfig.getString(key)));
            } catch (IllegalArgumentException e) {
                HamsterCorePlugin.getInstance().getLogger().warning("初始化 PageConfig 时遇到了一个异常:");
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取把 buttonName 映射到 展示物品 的表
     *
     * @return 把 buttonName 映射到 展示物品 的表
     */
    @NotNull
    public HashMap<String, ItemStack> getButtons() {
        return buttons;
    }

    /**
     * 获取索引位置上的 graphicKey
     *
     * @param index 索引
     * @return 若超出 graphic 范围则返回 null
     */
    @Nullable
    public Character getButtonKey(int index) {
        if (index < 0) return null;
        if (index / 9 >= graphic.size()) return null;
        String s = graphic.get(index / 9);
        return s.charAt(index % 9);
    }

    /**
     * 获取该显示物品对应的 buttonName
     *
     * @param stack 显示物品
     * @return 按钮名称，若无法找到则返回 "empty"
     */
    @NotNull
    public String getButtonName(@Nullable ItemStack stack) {
        if (stack == null) {
            return "empty";
        }
        for (Map.Entry<String, ItemStack> entry : buttons.entrySet()) {
            if (entry.getValue().isSimilar(stack)) {
                return entry.getKey();
            }
        }
        return "empty";
    }

    @Nullable
    public Sound getButtonSound(@NotNull String buttonName) {
        return buttonSounds.get(buttonName);
    }

    @NotNull
    public ButtonGroup getButtonGroup(@NotNull String groupName) {
        for (ButtonGroup group : buttonGroups) {
            if (group.getName().equalsIgnoreCase(groupName)) {
                return group;
            }
        }
        return buttonGroups.get(0);
    }

    @NotNull
    public Plugin getPlugin() {
        return plugin;
    }

    @NotNull
    public ConfigurationSection getConfig() {
        return config;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    @NotNull
    public List<String> getGraphic() {
        return graphic;
    }

    @NotNull
    public ArrayList<ButtonGroup> getButtonGroups() {
        return buttonGroups;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public String toString() {
        return "PageConfig{" +
                ", title='" + title + '\'' +
                ", graphic=" + graphic +
                ", buttonMap=" + buttons +
                ", buttonGroups=" + buttonGroups +
                '}';
    }
}
