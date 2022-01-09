package io.trizgay.quantx.http.controller;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.api.service.ServiceRequest;
import io.vertx.ext.web.api.service.ServiceResponse;

public class BizControllerImpl implements BizController {
    @Override
    public void updatePlateInfo(ServiceRequest request,
                                Handler<AsyncResult<ServiceResponse>> resultHandler) {
        resultHandler.handle(Future.succeededFuture(
                ServiceResponse.completedWithPlainText(Buffer.buffer("yes"))
        ));
    }
}
