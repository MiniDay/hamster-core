package cn.hamster3.mc.plugin.core.bukkit.page;

import cn.hamster3.mc.plugin.core.bukkit.HamsterCorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
        String pageFileName = clazz.getSimpleName() + ".yml";
        File pageFile = new File(pageFolder, pageFileName);
        if (pageFile.exists()) {
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(pageFile);
            pageConfig = new PageConfig(plugin, configuration);
            PAGE_CONFIG.put(clazz.getName(), pageConfig);
            return pageConfig;
        }
        InputStream resource = plugin.getResource("/" + pageFileName);
        if (resource == null) {
            throw new IllegalArgumentException("在插件 " + pluginName + " 的文件内部未找到 " + pageFileName + " !");
        }
        try {
            Files.copy(resource, pageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            resource.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(pageFile);
        pageConfig = new PageConfig(plugin, configuration);
        PAGE_CONFIG.put(clazz.getName(), pageConfig);
        return pageConfig;
    }
}
