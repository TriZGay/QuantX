package io.trizgay.quantx.http.controller;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.ext.web.api.service.ServiceRequest;
import io.vertx.ext.web.api.service.ServiceResponse;
import io.vertx.ext.web.api.service.WebApiServiceGen;


@WebApiServiceGen
public interface BizController {
    static BizController create() {
        return new BizControllerImpl();
    }

    void updatePlateInfo(
            ServiceRequest request,
            Handler<AsyncResult<ServiceResponse>> resultHandler);

}
