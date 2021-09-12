package io.trizgay.utils;

import io.trizgay.conf.LocalConfig;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

public final class Config {
    private static LocalConfig local;

    public static LocalConfig localConfig() {
        return local;
    }

    private static ConfigRetriever initRetriever(Vertx vertx, ConfigStoreOptions storeOptions) {
        ConfigRetrieverOptions options = new ConfigRetrieverOptions()
                .addStore(storeOptions);
        return ConfigRetriever.create(vertx, options);
    }

    public static Future<Void> initLocalConfig(Vertx vertx) {
        Promise<Void> promise = Promise.promise();
        ConfigStoreOptions fileStore = new ConfigStoreOptions()
                .setType("file")
                .setConfig(new JsonObject().put("path", "./conf/config.json"));

        initRetriever(vertx, fileStore).getConfig(ar -> {
            if (ar.failed()) {
                promise.fail(ar.cause());
            } else {
                local = Json.decodeValue(ar.result().toString(), LocalConfig.class);
                Log.info(ar.result().toString());
                promise.complete();
            }
        });
        return promise.future();
    }
}
