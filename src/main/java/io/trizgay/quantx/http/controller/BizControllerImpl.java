package io.trizgay.quantx.http.controller;

import io.trizgay.quantx.domain.BizCommonResult;
import io.trizgay.quantx.domain.BizService;
import io.trizgay.quantx.domain.plate.PlateInfo;
import io.trizgay.quantx.http.pojo.GetPlateSetRequest;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.ext.web.api.service.ServiceRequest;
import io.vertx.ext.web.api.service.ServiceResponse;

public class BizControllerImpl implements BizController {
    private final BizService bizService;

    public BizControllerImpl(BizService bizService) {
        this.bizService = bizService;
    }

    @Override
    public void updatePlateInfo(GetPlateSetRequest body,
                                ServiceRequest request,
                                Handler<AsyncResult<ServiceResponse>> resultHandler) {
        bizService.saveOrUpdatePlateInfo(PlateInfo.fromRequest(body), bizResult -> {
                    if (bizResult.succeeded()) {
                        BizCommonResult result = bizResult.result();
                        resultHandler.handle(Future.succeededFuture(
                                ServiceResponse.completedWithJson(result.toJson())
                        ));
                    } else {
                        resultHandler.handle(Future.failedFuture(bizResult.cause()));
                    }
                }
        );
    }
}
