package io.trizgay.quantx.domain;

import io.trizgay.quantx.db.DataFetcher;
import io.trizgay.quantx.domain.plate.PlateInfo;
import io.trizgay.quantx.ft.client.QuoteRequestSender;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.serviceproxy.ServiceProxyBuilder;

@ProxyGen
@VertxGen
public interface BizService {
    @GenIgnore
    static BizService create(Vertx vertx,
                             DataFetcher dataFetcher,
                             QuoteRequestSender sender,
                             Handler<AsyncResult<BizService>> readyHandler) {
        return new BizServiceImpl(vertx, dataFetcher, sender, readyHandler);
    }

    @GenIgnore
    static BizService createProxy(Vertx vertx, String address) {
        return new ServiceProxyBuilder(vertx)
                .setAddress(address)
                .build(BizService.class);
    }

    @Fluent
    BizService saveOrUpdatePlateInfo(PlateInfo plateInfo, Handler<AsyncResult<BizCommonResult>> resultHandler);
}
