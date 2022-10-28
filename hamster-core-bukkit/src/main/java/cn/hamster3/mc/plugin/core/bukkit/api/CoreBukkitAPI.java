package cn.hamster3.mc.plugin.core.bukkit.api;

import cn.hamster3.mc.plugin.core.bukkit.HamsterCorePlugin;
import cn.hamster3.mc.plugin.core.common.api.CoreAPI;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public final class CoreBukkitAPI extends CoreAPI {
    private final HikariDataSource datasource;

    private CoreBukkitAPI() {
        FileConfiguration config = HamsterCorePlugin.getInstance().getConfig();

        ConfigurationSection datasourceConfig = config.getConfigurationSection("datasource");
        if (datasourceConfig == null) {
            throw new IllegalArgumentException("配置文件中未找到 datasource 节点！");
        }
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
    public @NotNull BukkitAudiences getAudienceProvider() {
        return HamsterCorePlugin.getInstance().getAudienceProvider();
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
