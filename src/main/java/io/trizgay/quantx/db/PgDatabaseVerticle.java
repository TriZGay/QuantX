package io.trizgay.quantx.db;

import com.futu.openapi.FTAPI;
import io.trizgay.quantx.domain.BizService;
import io.trizgay.quantx.ft.QuotesService;
import io.trizgay.quantx.ft.client.QuoteRequestSender;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.pgclient.PgPool;
import io.vertx.serviceproxy.ServiceBinder;

import static io.trizgay.quantx.utils.Constants.*;

public class PgDatabaseVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        DBRunner.loadSqlToMap(vertx, getClass().getResourceAsStream(CONFIG_PG_SQL_FILE))
                .onSuccess(sql -> {
                    PgPool pool = DBRunner.pool(vertx);
                    DataFetcher mapper = DataFetcher.create(pool, sql);
                    FTAPI.init();
                    QuotesService quotesService = new QuotesService(mapper);
                    quotesService.start();
                    BizService.create(vertx,
                            mapper,
                            new QuoteRequestSender(quotesService),
                            ready -> {
                                if (ready.succeeded()) {
                                    ServiceBinder binder = new ServiceBinder(vertx);
                                    binder.setAddress(CONFIG_PG_EVENT_BUS)
                                            .register(BizService.class, ready.result());
                                    startPromise.complete();
                                } else {
                                    startPromise.fail(ready.cause());
                                }
                            });
                })
                .onFailure(startPromise::fail);
    }

    @Override
    public void stop(Promise<Void> stopPromise) throws Exception {
        super.stop(stopPromise);
    }
}
