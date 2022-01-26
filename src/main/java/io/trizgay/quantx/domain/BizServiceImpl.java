package io.trizgay.quantx.domain;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.pgclient.PgPool;

public class BizServiceImpl implements BizService {
    private final Vertx vertx;
    private final PgPool pool;

    public BizServiceImpl(Vertx vertx, PgPool pool, Handler<AsyncResult<BizService>> readyHandler) {
        this.vertx = vertx;
        this.pool = pool;
        readyHandler.handle(Future.succeededFuture(this));
    }
}
