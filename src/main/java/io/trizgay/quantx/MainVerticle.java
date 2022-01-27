package io.trizgay.quantx;

import io.trizgay.quantx.db.PgDatabaseVerticle;
import io.trizgay.quantx.http.HttpVerticle;
import io.trizgay.quantx.utils.Config;
import io.trizgay.quantx.utils.Log;
import io.vertx.core.*;
import io.vertx.core.tracing.TracingOptions;

public class MainVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        Log.init();
        Config.initLocalConfig(vertx)
                .compose(v -> startDbVerticle())
                .compose(v -> startHttpServer())
                .onSuccess(ar -> {
                    Log.info("启动成功!");
                    startPromise.complete();
                }).onFailure(startPromise::fail);
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
