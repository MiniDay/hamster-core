package cn.hamster3.mc.plugin.core.bukkit.page;

import cn.hamster3.mc.plugin.core.bukkit.HamsterCorePlugin;
import cn.hamster3.mc.plugin.core.bukkit.util.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.HashMap;

public class PageManager {
    private static final HashMap<String, PageConfig> PAGE_CONFIG = new HashMap<>();

    public static PageConfig getPageConfig(Class<?> clazz) {
        PageConfig pageConfig = PAGE_CONFIG.get(clazz.getName());
        if (pageConfig != null) {
            return pageConfig;
        }
        if (!clazz.isAnnotationPresent(PluginPage.class)) {
            throw new IllegalArgumentException(clazz.getName() + " 未被 @PluginPage 注解修饰！");
        }
        PluginPage annotation = clazz.getAnnotation(PluginPage.class);
        String pluginName = annotation.value();
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        if (plugin == null) {
            throw new IllegalArgumentException("未找到插件 " + pluginName + " !");
        }
        File pageFolder = new File(plugin.getDataFolder(), "pages");
        if (pageFolder.mkdirs()) {
            HamsterCorePlugin.getInstance().getLogger().info("为 " + pluginName + " 创建页面配置文件夹...");
        }
        String filename = clazz.getSimpleName() + ".yml";
        YamlConfiguration config = BukkitUtils.getPluginConfig(plugin, filename);
        pageConfig = new PageConfig(plugin, config);
        PAGE_CONFIG.put(clazz.getName(), pageConfig);
        return pageConfig;
    }
}
