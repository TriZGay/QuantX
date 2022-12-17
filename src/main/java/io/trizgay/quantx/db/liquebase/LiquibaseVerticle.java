package io.trizgay.quantx.db.liquebase;

import io.trizgay.quantx.conf.LocalConfig;
import io.trizgay.quantx.utils.Config;
import io.trizgay.quantx.utils.Log;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.jdbc.impl.JDBCClientImpl;
import io.vertx.ext.sql.SQLConnection;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;

import java.sql.Connection;

public class LiquibaseVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        this.init(startPromise);
    }

    @Override
    public void stop(Promise<Void> stopPromise) throws Exception {
        super.stop(stopPromise);
    }

    private void init(Promise<Void> startPromise) {
        LocalConfig.LiquibaseConfig liquibaseConfig = Config.localConfig().getLiquibase();
        JDBCClientImpl client = (JDBCClientImpl) JDBCClient.createShared(vertx,
                new JsonObject().put("url", liquibaseConfig.getUrl())
                        .put("driver_class", liquibaseConfig.getDriverClass())
                        .put("max_pool_size", liquibaseConfig.getMaxPoolSize())
                        .put("user", liquibaseConfig.getUser())
                        .put("password", liquibaseConfig.getPassword()),
                "liquibaseDS"
        );
        this.getConnection(client)
                .compose(this::getDatabase)
                .compose(this::liquibase)
                .compose(this::applyDatabaseChange)
                .onComplete(startPromise);
    }

    private Future<Connection> getConnection(JDBCClientImpl jdbcClient) {
        return jdbcClient.getConnection().map(SQLConnection::unwrap);
    }

    private Future<Database> getDatabase(Connection connection) {
        Promise<Database> promise = Promise.promise();
        try {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            promise.complete(database);
        } catch (DatabaseException databaseException) {
            promise.fail(databaseException);
        }
        return promise.future();
    }

    private Future<Liquibase> liquibase(Database database) {
        String changeLog = "classpath:/liquibase-changelog.xml";
        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();
        return Future.succeededFuture(new Liquibase(changeLog, resourceAccessor, database));
    }

    private Future<Void> applyDatabaseChange(Liquibase liquibase) {
        Promise<Void> promise = Promise.promise();
        try {
            Log.info("执行数据库版本控制...");
            liquibase.update();
            promise.complete();
        } catch (LiquibaseException e) {
            promise.fail(e);
        }
        return promise.future();
    }

}
