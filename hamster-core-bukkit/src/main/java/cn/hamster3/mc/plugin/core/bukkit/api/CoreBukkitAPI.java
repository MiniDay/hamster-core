package cn.hamster3.mc.plugin.core.bukkit.api;

import cn.hamster3.mc.plugin.core.bukkit.HamsterCorePlugin;
import cn.hamster3.mc.plugin.core.common.api.CoreAPI;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public final class CoreBukkitAPI extends CoreAPI {
    private final BukkitAudiences audienceProvider;
    private final HikariDataSource datasource;

    private CoreBukkitAPI() {
        HamsterCorePlugin plugin = HamsterCorePlugin.getInstance();
        audienceProvider = BukkitAudiences.create(plugin);

        plugin.saveDefaultConfig();
        FileConfiguration config = plugin.getConfig();

        ConfigurationSection datasourceConfig = config.getConfigurationSection("datasource");
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(datasourceConfig.getString("driver"));
        hikariConfig.setJdbcUrl(datasourceConfig.getString("url"));
        hikariConfig.setUsername(datasourceConfig.getString("username"));
        hikariConfig.setPassword(datasourceConfig.getString("password"));
        hikariConfig.setMaximumPoolSize(datasourceConfig.getInt("maximum-pool-size", 3));
        hikariConfig.setMinimumIdle(datasourceConfig.getInt("minimum-idle", 1));
        hikariConfig.setIdleTimeout(datasourceConfig.getLong("idle-timeout", 5 * 60 * 1000));
        hikariConfig.setMaxLifetime(datasourceConfig.getLong("max-lifetime", 0));
        datasource = new HikariDataSource(hikariConfig);
    }

    public static CoreBukkitAPI getInstance() {
        return (CoreBukkitAPI) instance;
    }

    public static void init() {
        if (instance != null) {
            return;
        }
        instance = new CoreBukkitAPI();
    }

    @Override
    public @NotNull AudienceProvider getAudienceProvider() {
        return audienceProvider;
    }

    @Override
    public @NotNull HikariDataSource getDataSource() {
        return datasource;
    }

    public void reportError(@NotNull String projectID, @NotNull Throwable exception) {
    }

    public void reportFile(@NotNull String projectID, @NotNull String filename, byte @NotNull [] bytes) {
    }
}
