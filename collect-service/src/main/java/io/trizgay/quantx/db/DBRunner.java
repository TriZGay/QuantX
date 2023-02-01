package io.trizgay.quantx.db;

import io.trizgay.quantx.utils.Config;
import io.trizgay.quantx.utils.Log;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class DBRunner {
    public static PgPool pool(Vertx vertx) {
        return PgPool.pool(vertx,
                new PgConnectOptions()
                        .setHost(Config.localConfig().getReadWrite().getHost())
                        .setPort(Config.localConfig().getReadWrite().getPort())
                        .setDatabase(Config.localConfig().getReadWrite().getDb())
                        .setUser(Config.localConfig().getReadWrite().getUser())
                        .setPassword(Config.localConfig().getReadWrite().getPassword()),
                new PoolOptions()
                        .setShared(true)
                        .setName(Config.localConfig().getReadWrite().getPool().getPoolName())
                        .setMaxSize(Config.localConfig().getReadWrite().getPool().getMaxSize())
                        .setMaxWaitQueueSize(Config.localConfig().getReadWrite().getPool().getMaxWaitQueueSize())
                        .setConnectionTimeout(Config.localConfig().getReadWrite().getPool().getConnectionTimeout())
                        .setPoolCleanerPeriod(Config.localConfig().getReadWrite().getPool().getPoolCleanerPeriod())
                        .setIdleTimeout(Config.localConfig().getReadWrite().getPool().getIdleTimeout()));
    }

    public static Future<Map<String, String>> loadSqlToMap(Vertx vertx, InputStream sqlInput) {
        return vertx.executeBlocking(loader -> {
            try (InputStream loaderInputStream = sqlInput) {
                Properties properties = new Properties();
                properties.load(loaderInputStream);
                HashMap<String, String> sqlQueries = new HashMap<>();
                properties.forEach((key, value) -> {
                    Log.info("读取到SQL:" + key + "=" + value);
                    sqlQueries.put((String) key, (String) value);
                });
                loader.complete(sqlQueries);
            } catch (IOException e) {
                Log.error("读取sql失败", e);
                loader.fail(e);
            }
        });
    }
}
