package io.trizgay.quantx.http.controller;

import io.trizgay.quantx.domain.BizService;
import io.trizgay.quantx.http.pojo.PostIpoInfoRequest;
import io.trizgay.quantx.http.pojo.PostPlateSetRequest;
import io.trizgay.quantx.http.pojo.PostSecurityListRequest;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.ext.web.api.service.ServiceRequest;
import io.vertx.ext.web.api.service.ServiceResponse;
import io.vertx.ext.web.api.service.WebApiServiceGen;


@WebApiServiceGen
public interface BizController {
    @GenIgnore
    static BizController create(BizService writeableService) {
        return new BizControllerImpl(writeableService);
    }

    void updatePlateInfo(
            PostPlateSetRequest body,
            ServiceRequest request,
            Handler<AsyncResult<ServiceResponse>> resultHandler);

    void updateIpoInfo(
            PostIpoInfoRequest body,
            ServiceRequest request,
            Handler<AsyncResult<ServiceResponse>> resultHandler);

    void updateSecurityList(
            PostSecurityListRequest body,
            ServiceRequest request,
            Handler<AsyncResult<ServiceResponse>> resultHandler);
}
