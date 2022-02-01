package io.trizgay.quantx.domain;

import io.trizgay.quantx.db.DataFetcher;
import io.trizgay.quantx.domain.ipo.IpoInfo;
import io.trizgay.quantx.domain.plate.PlateInfo;
import io.trizgay.quantx.domain.security.SecurityInfo;
import io.trizgay.quantx.ft.client.QuoteRequestSender;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

//TODO 业务层看以后做不做结果监控
public class BizServiceImpl implements BizService {
    private final Vertx vertx;
    private final DataFetcher mapper;
    private final QuoteRequestSender sender;

    public BizServiceImpl(Vertx vertx,
                          DataFetcher mapper,
                          QuoteRequestSender sender,
                          Handler<AsyncResult<BizService>> readyHandler) {
        this.vertx = vertx;
        this.mapper = mapper;
        this.sender = sender;
        readyHandler.handle(Future.succeededFuture(this));
    }

    @Override
    public BizService saveOrUpdatePlateInfo(PlateInfo plateInfo,
                                            Handler<AsyncResult<BizCommonResult>> resultHandler) {
        sender.sendGetPlateInfoRequest(plateInfo.toFTGrpcRequest())
                .onSuccess(result -> resultHandler.handle(Future.succeededFuture(
                        new BizCommonResult(BizCommonResultCode.QUERY_PLATE_INFO_SUCCESS, "查询板块信息成功!"))))
                .onFailure(err -> resultHandler.handle(Future.succeededFuture(
                        new BizCommonResult(BizCommonResultCode.QUERY_PLATE_INFO_FAILED, "查询板块信息失败!" + err.getMessage())
                )));
        return this;
    }

    @Override
    public BizService saveOrUpdateIpoInfo(IpoInfo ipoInfo, Handler<AsyncResult<BizCommonResult>> resultHandler) {
        sender.sendGetIpoInfoRequest(ipoInfo.toFTGrpcRequest())
                .onSuccess(result -> resultHandler.handle(Future.succeededFuture(
                        new BizCommonResult(BizCommonResultCode.QUERY_IPO_INFO_SUCCESS, "查询IPO信息成功!")
                )))
                .onFailure(err -> resultHandler.handle(Future.succeededFuture(
                        new BizCommonResult(BizCommonResultCode.QUERY_IPO_INFO_FAILED, "查询IPO信息失败!" + err.getMessage())
                )));
        return this;
    }

    @Override
    public BizService saveOrUpdateSecurityList(SecurityInfo securityInfo, Handler<AsyncResult<BizCommonResult>> resultHandler) {
        sender.sendGetSecurityListRequest(securityInfo.toFTGrpcRequest())
                .onSuccess(result -> resultHandler.handle(Future.succeededFuture(
                        new BizCommonResult(BizCommonResultCode.QUERY_SECURITY_SUCCESS, "查询板块股票数据成功!")
                )))
                .onFailure(err -> resultHandler.handle(Future.succeededFuture(
                        new BizCommonResult(BizCommonResultCode.QUERY_SECURITY_FAILED, "查询板块股票数据失败!" + err.getMessage())
                )));
        return this;
    }
}
