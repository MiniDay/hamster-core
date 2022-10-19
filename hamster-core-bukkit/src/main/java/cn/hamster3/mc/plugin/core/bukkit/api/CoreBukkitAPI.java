package cn.hamster3.mc.plugin.core.bukkit.api;

import cn.hamster3.mc.plugin.core.bukkit.HamsterCorePlugin;
import cn.hamster3.mc.plugin.core.common.api.CoreCommonAPI;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class CoreBukkitAPI extends CoreCommonAPI {
    private BukkitAudiences audienceProvider;
    private HikariDataSource datasource;

    public static CoreBukkitAPI getInstance() {
        return (CoreBukkitAPI) instance;
    }

    public static void init() {
        CoreBukkitAPI api = new CoreBukkitAPI();
        instance = api;
        api.audienceProvider = BukkitAudiences.create(HamsterCorePlugin.getInstance());
        HikariConfig hikariConfig = new HikariConfig();

    }

    @Override
    public @NotNull AudienceProvider getAudienceProvider() {
        return audienceProvider;
    }

    @Override
    public @NotNull HikariDataSource getDataSource() {
        return datasource;
    }

    @Override
    public @NotNull Connection getConnection() throws SQLException {
        return datasource.getConnection();
    }

    @Override
    public void reportError(@NotNull String apiKey, @NotNull String projectID, @NotNull Throwable exception) {

    }

    @Override
    public void reportFile(@NotNull String apiKey, @NotNull String projectID, @NotNull String filename, byte @NotNull [] bytes) {
    }
}
