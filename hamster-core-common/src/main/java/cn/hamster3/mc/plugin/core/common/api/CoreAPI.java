package cn.hamster3.mc.plugin.core.common.api;

import net.kyori.adventure.platform.AudienceProvider;
import org.jetbrains.annotations.NotNull;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SuppressWarnings("unused")
public abstract class CoreAPI {
    protected static CoreAPI instance;

    public static CoreAPI getInstance() {
        return instance;
    }

    @NotNull
    public abstract AudienceProvider getAudienceProvider();

    @NotNull
    public abstract DataSource getDataSource();

    @NotNull
    public Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    public void reportError(@NotNull String apiKey, @NotNull String projectID, @NotNull Throwable exception) {
        // todo
    }

    public void reportFile(@NotNull String apiKey, @NotNull String projectID, @NotNull String filename, byte @NotNull [] bytes) {
        // todo
    }
}
