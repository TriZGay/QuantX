package io.trizgay;

import io.trizgay.http.HttpVerticle;
import io.trizgay.utils.Config;
import io.trizgay.utils.Log;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;

public class MainVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        Log.init();
        Config.initLocalConfig(vertx)
                .compose(v -> startHttpServer())
                .onSuccess(ar -> {
                    Log.info("启动成功!");
                    startPromise.complete();
                }).onFailure(startPromise::fail);
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
