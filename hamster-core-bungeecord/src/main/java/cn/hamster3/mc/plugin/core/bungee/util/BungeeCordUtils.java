package cn.hamster3.mc.plugin.core.bungee.util;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public final class BungeeCordUtils {
    private BungeeCordUtils() {
    }

    public static Configuration getPluginConfig(Plugin plugin) {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            return saveDefaultConfig(plugin);
        }

        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Configuration saveDefaultConfig(Plugin plugin) {
        try {
            if (plugin.getDataFolder().mkdir()) {
                plugin.getLogger().info("创建插件文件夹...");
            }
            File configFile = new File(plugin.getDataFolder(), "config.yml");
            InputStream in = plugin.getResourceAsStream("config.yml");
            Files.copy(in, configFile.toPath());
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (Exception ignored) {
        }
        return null;
    }
}
