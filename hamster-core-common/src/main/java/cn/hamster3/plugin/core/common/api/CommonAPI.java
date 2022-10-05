package cn.hamster3.plugin.core.common.api;

import net.kyori.adventure.platform.AudienceProvider;
import org.jetbrains.annotations.NotNull;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SuppressWarnings("unused")
public abstract class CommonAPI {
    protected static CommonAPI instance;

    public static CommonAPI getInstance() {
        return instance;
    }

    @NotNull
    public abstract AudienceProvider getAudienceProvider();

    @NotNull
    public abstract DataSource getDataSource();

    @NotNull
    public abstract Connection getConnection() throws SQLException;

    public abstract void reportError(@NotNull String apiKey, @NotNull String projectID, @NotNull Throwable exception);

    public abstract void reportFile(@NotNull String apiKey, @NotNull String projectID, @NotNull String filename, byte @NotNull [] bytes);
}
