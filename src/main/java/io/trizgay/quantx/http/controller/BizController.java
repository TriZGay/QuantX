package io.trizgay.quantx.http.controller;

import io.trizgay.quantx.domain.BizServiceDispatcher;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.ext.web.api.service.ServiceRequest;
import io.vertx.ext.web.api.service.ServiceResponse;
import io.vertx.ext.web.api.service.WebApiServiceGen;


@WebApiServiceGen
public interface BizController {
    @GenIgnore
    static BizController create(BizServiceDispatcher dispatcher) {
        return new BizControllerImpl(dispatcher);
    }

    void updatePlateInfo(
            ServiceRequest request,
            Handler<AsyncResult<ServiceResponse>> resultHandler);

}
