package io.trizgay.quantx.domain;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.pgclient.PgPool;

public class DefaultBizServiceDispatcher implements BizServiceDispatcher {
    private final Vertx vertx;
    private final PgPool pool;

    public DefaultBizServiceDispatcher(Vertx vertx, PgPool pool, Handler<AsyncResult<BizServiceDispatcher>> readyHandler) {
        this.vertx = vertx;
        this.pool = pool;
        readyHandler.handle(Future.succeededFuture(this));
    }
}
