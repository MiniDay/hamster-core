package cn.hamster3.mc.plugin.core.bungee.api;

import cn.hamster3.mc.plugin.core.bungee.HamsterCorePlugin;
import cn.hamster3.mc.plugin.core.bungee.util.BungeeCordUtils;
import cn.hamster3.mc.plugin.core.common.api.CoreAPI;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.config.Configuration;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class CoreBungeeAPI extends CoreAPI {
    private final BungeeAudiences audienceProvider;
    private final HikariDataSource datasource;

    public CoreBungeeAPI() {
        HamsterCorePlugin plugin = HamsterCorePlugin.getInstance();
        audienceProvider = BungeeAudiences.create(plugin);

        Configuration config = BungeeCordUtils.getPluginConfig(plugin);

        Configuration datasourceConfig = config.getSection("datasource");
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

    public static CoreBungeeAPI getInstance() {
        return (CoreBungeeAPI) instance;
    }

    public static void init() {
        instance = new CoreBungeeAPI();
    }

    @Override
    public @NotNull AudienceProvider getAudienceProvider() {
        return audienceProvider;
    }

    @Override
    public @NotNull HikariDataSource getDataSource() {
        return datasource;
    }
}
