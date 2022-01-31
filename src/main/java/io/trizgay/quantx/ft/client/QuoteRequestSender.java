package io.trizgay.quantx.ft.client;

import com.futu.openapi.pb.QotGetIpoList;
import com.futu.openapi.pb.QotGetPlateSecurity;
import com.futu.openapi.pb.QotGetPlateSet;
import io.trizgay.quantx.domain.plate.PlateInfo;
import io.trizgay.quantx.ft.FTCommonResult;
import io.trizgay.quantx.ft.QuotesService;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

public class QuoteRequestSender {
    private final QuotesService quotesService;

    public QuoteRequestSender(QuotesService quotesService) {
        this.quotesService = quotesService;
    }

    public Future<FTCommonResult> sendGetPlateInfoRequest(QotGetPlateSet.C2S getPlateSetC2s) {
        Promise<FTCommonResult> promise = Promise.promise();
        quotesService.sendGetPlateSet(getPlateSetC2s, resultJson -> {
            if (resultJson.succeeded()) {
                JsonObject ftCommonResultJson = resultJson.result();
                promise.complete(new FTCommonResult(ftCommonResultJson));
            } else {
                promise.fail(resultJson.cause());
            }
        });
        return promise.future();
    }

    public Future<FTCommonResult> sendGetIpoInfoRequest(QotGetIpoList.C2S getIpoListC2s) {
        Promise<FTCommonResult> promise = Promise.promise();
        quotesService.sendGetIpoList(getIpoListC2s, resultJson -> {
            if (resultJson.succeeded()) {
                JsonObject ftCommonResultJson = resultJson.result();
                promise.complete(new FTCommonResult(ftCommonResultJson));
            } else {
                promise.fail(resultJson.cause());
            }
        });
        return promise.future();
    }

    public Future<FTCommonResult> sendGetSecurityListRequest(QotGetPlateSecurity.C2S getSecurityList) {
        Promise<FTCommonResult> promise = Promise.promise();

        return promise.future();
    }
}
