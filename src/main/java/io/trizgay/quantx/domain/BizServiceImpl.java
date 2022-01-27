package io.trizgay.quantx.domain;

import io.trizgay.quantx.db.DataFetcher;
import io.trizgay.quantx.db.pojo.Plate;
import io.trizgay.quantx.domain.plate.PlateInfo;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

public class BizServiceImpl implements BizService {
    private final Vertx vertx;
    private final DataFetcher dataFetcher;

    public BizServiceImpl(Vertx vertx, DataFetcher dataFetcher, Handler<AsyncResult<BizService>> readyHandler) {
        this.vertx = vertx;
        this.dataFetcher = dataFetcher;
        readyHandler.handle(Future.succeededFuture(this));
    }

    @Override
    public BizService saveOrUpdatePlateInfo(PlateInfo plateInfo,
                                            Handler<AsyncResult<Integer>> resultHandler) {
        dataFetcher.insetOnePlate(new Plate("test", "test", 1))
                .onComplete(resultHandler);
        return this;
    }
}
