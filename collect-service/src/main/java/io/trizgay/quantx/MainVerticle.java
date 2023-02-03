package io.trizgay.quantx;

import io.trizgay.quantx.db.PgDatabaseVerticle;
import io.trizgay.quantx.db.liquebase.LiquibaseVerticle;
import io.trizgay.quantx.http.HttpVerticle;
import io.trizgay.quantx.utils.Config;
import io.trizgay.quantx.utils.Log;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;

public class MainVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        Log.init();
        Config.initLocalConfig(vertx)
                .compose(v -> startLiquibaseVerticle().compose(vertx::undeploy))
                .compose(v -> startDbVerticle())
                .compose(v -> startHttpServer())
                .onSuccess(ar -> {
                    Log.info("启动成功!");
                    startPromise.complete();
                }).onFailure(startPromise::fail);
    }

    private Future<String> startLiquibaseVerticle() {
        Promise<String> promise = Promise.promise();
        vertx.deployVerticle(LiquibaseVerticle.class.getName(),
                new DeploymentOptions().setWorker(true),
                ar -> {
                    if (ar.succeeded()) {
                        Log.info("执行数据库版本控制成功!");
                        promise.complete(ar.result());
                    } else {
                        Log.error("执行数据库版本控制失败!", ar.cause());
                        promise.fail(ar.cause());
                    }
                });
        return promise.future();
    }

    private Future<Void> startDbVerticle() {
        Promise<Void> promise = Promise.promise();
        vertx.deployVerticle(PgDatabaseVerticle.class.getName(),
                new DeploymentOptions(),
                ar -> {
                    if (ar.succeeded()) {
                        Log.info("Pg CLIENT 启动成功!");
                        promise.complete();
                    } else {
                        Log.error("Pg CLIENT 启动失败!", ar.cause());
                        promise.fail(ar.cause());
                    }
                });
        return promise.future();
    }

    private Future<Void> startHttpServer() {
        Promise<Void> promise = Promise.promise();
        vertx.deployVerticle(HttpVerticle.class.getName(),
                new DeploymentOptions(),
                ar -> {
                    if (ar.succeeded()) {
                        Log.info("HTTP SERVER 启动成功!");
                        promise.complete();
                    } else {
                        Log.error("HTTP SERVER 启动失败!", ar.cause());
                        promise.fail(ar.cause());
                    }
                });
        return promise.future();
    }


    @Override
    public void stop(Promise<Void> stopPromise) throws Exception {
        super.stop(stopPromise);
    }
}
