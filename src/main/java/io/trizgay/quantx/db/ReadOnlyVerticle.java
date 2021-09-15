package io.trizgay.quantx.db;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class ReadOnlyVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        super.start(startPromise);
    }

    @Override
    public void stop(Promise<Void> stopPromise) throws Exception {
        super.stop(stopPromise);
    }
}
