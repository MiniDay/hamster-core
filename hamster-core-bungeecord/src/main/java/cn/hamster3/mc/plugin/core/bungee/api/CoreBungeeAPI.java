package cn.hamster3.mc.plugin.core.bungee.api;

import cn.hamster3.mc.plugin.core.bungee.HamsterCorePlugin;
import cn.hamster3.mc.plugin.core.bungee.util.BungeeCordUtils;
import cn.hamster3.mc.plugin.core.common.api.CoreAPI;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.kyori.adventure.platform.AudienceProvider;
import net.md_5.bungee.config.Configuration;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public final class CoreBungeeAPI extends CoreAPI {
    private final HikariDataSource datasource;

    private CoreBungeeAPI() {
        HamsterCorePlugin plugin = HamsterCorePlugin.getInstance();

        Configuration config = BungeeCordUtils.getPluginConfig(plugin);

        Configuration datasourceConfig = config.getSection("datasource");
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
        hikariConfig.setIdleTimeout(datasourceConfig.getLong("idle-timeout", 10 * 60 * 1000));
        hikariConfig.setMaxLifetime(datasourceConfig.getLong("max-lifetime", 30 * 60 * 1000));
        hikariConfig.setValidationTimeout(datasourceConfig.getLong("validation-timeout", 5000));
        hikariConfig.setPoolName("HamsterCore-Pool");
        datasource = new HikariDataSource(hikariConfig);
    }

    public static CoreBungeeAPI getInstance() {
        return (CoreBungeeAPI) instance;
    }

    public static void init() {
        if (instance != null) {
            return;
        }
        instance = new CoreBungeeAPI();
    }

    @Override
    public @NotNull AudienceProvider getAudienceProvider() {
        return HamsterCorePlugin.getInstance().getAudienceProvider();
    }

    @Override
    public @NotNull HikariDataSource getDataSource() {
        return datasource;
    }
}
