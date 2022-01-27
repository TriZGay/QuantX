package io.trizgay.quantx.http.controller;

import io.trizgay.quantx.domain.BizService;
import io.trizgay.quantx.domain.plate.PlateInfo;
import io.trizgay.quantx.http.pojo.GetPlateSetRequest;
import io.trizgay.quantx.utils.Log;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
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
        bizService.saveOrUpdatePlateInfo(new PlateInfo(), size -> {
            if (size.succeeded()) {
                resultHandler.handle(Future.succeededFuture(
                        ServiceResponse.completedWithJson(
                                new JsonObject()
                                        .put("message", "成功更新" + size.result() + "条数据!")
                        )
                ));
            } else {
                resultHandler.handle(Future.succeededFuture(
                        new ServiceResponse()
                                .setStatusCode(500)
                                .setPayload(Buffer.buffer("更新板块信息失败!" + size.cause().getMessage()))
                ));
            }
        });
    }
}
