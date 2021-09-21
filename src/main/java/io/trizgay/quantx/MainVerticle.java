package io.trizgay.quantx;

import io.trizgay.quantx.db.ReadOnlyVerticle;
import io.trizgay.quantx.db.ReadWriteVerticle;
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
                .compose(v -> startReadOnlyDbClient())
                .compose(v -> startReadWriteDbClient())
                .compose(v -> startHttpServer())
                .compose(v -> startCollector())
                .onSuccess(ar -> {
                    Log.info("启动成功!");
                    startPromise.complete();
                }).onFailure(startPromise::fail);
    }

    private Future<Void> startCollector() {
        Promise<Void> promise = Promise.promise();
        vertx.setPeriodic(2000L, ar -> {

        });
        return promise.future();
    }

    private Future<Void> startReadOnlyDbClient() {
        Promise<Void> promise = Promise.promise();
        vertx.deployVerticle(ReadOnlyVerticle.class.getName(),
                new DeploymentOptions(),
                ar -> {
                    if (ar.succeeded()) {
                        Log.info("数据库只读客户端启动成功!");
                        promise.complete();
                    } else {
                        Log.error("数据库只读客户端启动失败!", ar.cause());
                        promise.fail(ar.cause());
                    }
                });
        return promise.future();
    }

    private Future<Void> startReadWriteDbClient() {
        Promise<Void> promise = Promise.promise();
        vertx.deployVerticle(ReadWriteVerticle.class.getName(),
                new DeploymentOptions(),
                ar -> {
                    if (ar.succeeded()) {
                        Log.info("数据库读写客户端启动成功!");
                        promise.complete();
                    } else {
                        Log.error("数据库读写客户端启动失败!", ar.cause());
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
