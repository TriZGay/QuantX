package io.trizgay.quantx.http.controller;

import io.trizgay.quantx.domain.BizServiceDispatcher;
import io.trizgay.quantx.http.pojo.GetPlateSetRequest;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.api.service.ServiceRequest;
import io.vertx.ext.web.api.service.ServiceResponse;

public class BizControllerImpl implements BizController {
    private final BizServiceDispatcher dispatcher;

    public BizControllerImpl(BizServiceDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void updatePlateInfo(GetPlateSetRequest body,
                                ServiceRequest request,
                                Handler<AsyncResult<ServiceResponse>> resultHandler) {
        resultHandler.handle(Future.succeededFuture(
                ServiceResponse.completedWithPlainText(Buffer.buffer("yes"))
        ));
    }
}
