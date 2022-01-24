package io.trizgay.quantx.db;

import io.trizgay.quantx.domain.BizServiceDispatcher;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.serviceproxy.ServiceBinder;

import static io.trizgay.quantx.utils.Constants.CONFIG_PG_EVENT_BUS;
import static io.trizgay.quantx.utils.Constants.CONFIG_PG_SQL_FILE;

public class ReadWriteVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        DBRunner.loadSqlToMap(vertx, getClass().getResourceAsStream(CONFIG_PG_SQL_FILE))
                .onSuccess(sql -> BizServiceDispatcher.create(vertx, DBRunner.pool(vertx), ready -> {
                    if (ready.succeeded()) {
                        ServiceBinder binder = new ServiceBinder(vertx);
                        binder.setAddress(CONFIG_PG_EVENT_BUS)
                                .register(BizServiceDispatcher.class, ready.result());
                        startPromise.complete();
                    } else {
                        startPromise.fail(ready.cause());
                    }
                }))
                .onFailure(startPromise::fail);
    }

    @Override
    public void stop(Promise<Void> stopPromise) throws Exception {
        super.stop(stopPromise);
    }
}
