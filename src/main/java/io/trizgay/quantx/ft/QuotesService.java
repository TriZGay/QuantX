package io.trizgay.quantx.ft;

import com.futu.openapi.FTAPI_Conn;
import com.futu.openapi.FTAPI_Conn_Qot;
import com.futu.openapi.FTSPI_Conn;
import com.futu.openapi.FTSPI_Qot;
import com.futu.openapi.pb.*;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.trizgay.quantx.db.DataFetcher;
import io.trizgay.quantx.db.pojo.Plate;
import io.trizgay.quantx.db.pojo.Security;
import io.trizgay.quantx.utils.Config;
import io.trizgay.quantx.utils.DateUtils;
import io.trizgay.quantx.utils.Log;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class QuotesService implements FTSPI_Conn, FTSPI_Qot {
    private static final String clientID = "javaclient";
    private final FTAPI_Conn_Qot qot = new FTAPI_Conn_Qot();
    private final DataFetcher mapper;

    public QuotesService(DataFetcher mapper) {
        qot.setClientInfo(clientID, 1);
        qot.setConnSpi(this);
        qot.setQotSpi(this);
        this.mapper = mapper;
    }

    public void start() {
        qot.initConnect(Config.localConfig().getFt().getUrl(),
                Config.localConfig().getFt().getPort(),
                Config.localConfig().getFt().getIsEnableEncrypt());
    }

    @Override
    public void onInitConnect(FTAPI_Conn client, long errCode, String desc) {
        Log.info("行情回调 onInitConnect: ret=" + errCode + ",desc=" + desc + ",connID=" + client.getConnectID());
    }

    @Override
    public void onDisconnect(FTAPI_Conn client, long errCode) {
        Log.info("行情回调 onDisconnect: connID=" + client.getConnectID() + ",ret=" + errCode);
    }

    @Override
    public void onReply_GetGlobalState(FTAPI_Conn client, int nSerialNo, GetGlobalState.Response rsp) {

    }

    @Override
    public void onReply_Sub(FTAPI_Conn client, int nSerialNo, QotSub.Response rsp) {

    }

    @Override
    public void onReply_RegQotPush(FTAPI_Conn client, int nSerialNo, QotRegQotPush.Response rsp) {

    }

    @Override
    public void onReply_GetSubInfo(FTAPI_Conn client, int nSerialNo, QotGetSubInfo.Response rsp) {

    }

    @Override
    public void onReply_GetTicker(FTAPI_Conn client, int nSerialNo, QotGetTicker.Response rsp) {

    }

    @Override
    public void onReply_GetBasicQot(FTAPI_Conn client, int nSerialNo, QotGetBasicQot.Response rsp) {

    }

    @Override
    public void onReply_GetOrderBook(FTAPI_Conn client, int nSerialNo, QotGetOrderBook.Response rsp) {

    }

    @Override
    public void onReply_GetKL(FTAPI_Conn client, int nSerialNo, QotGetKL.Response rsp) {

    }

    @Override
    public void onReply_GetRT(FTAPI_Conn client, int nSerialNo, QotGetRT.Response rsp) {

    }

    @Override
    public void onReply_GetBroker(FTAPI_Conn client, int nSerialNo, QotGetBroker.Response rsp) {

    }

    @Override
    public void onReply_GetRehab(FTAPI_Conn client, int nSerialNo, QotGetRehab.Response rsp) {

    }

    @Override
    public void onReply_RequestRehab(FTAPI_Conn client, int nSerialNo, QotRequestRehab.Response rsp) {

    }

    @Override
    public void onReply_RequestHistoryKL(FTAPI_Conn client, int nSerialNo, QotRequestHistoryKL.Response rsp) {

    }

    @Override
    public void onReply_RequestHistoryKLQuota(FTAPI_Conn client, int nSerialNo, QotRequestHistoryKLQuota.Response rsp) {

    }

    @Override
    public void onReply_GetStaticInfo(FTAPI_Conn client, int nSerialNo, QotGetStaticInfo.Response rsp) {

    }

    @Override
    public void onReply_GetSecuritySnapshot(FTAPI_Conn client, int nSerialNo, QotGetSecuritySnapshot.Response rsp) {

    }

    public void sendGetPlateSet(QotGetPlateSet.C2S getPlateSetC2s, Handler<AsyncResult<JsonObject>> resultHandler) {
        QotGetPlateSet.Request request = QotGetPlateSet.Request.newBuilder().setC2S(getPlateSetC2s).build();
        Integer seqNo = qot.getPlateSet(request);
        resultHandler.handle(Future.succeededFuture(
                new FTCommonResult(seqNo, "查询板块信息:plateSetType="
                        + getPlateSetC2s.getPlateSetType()
                        + ",market=" + getPlateSetC2s.getMarket()).toJson()
        ));
    }

    public void sendGetIpoList(QotGetIpoList.C2S getIpoListC2s, Handler<AsyncResult<JsonObject>> resultHandler) {
        QotGetIpoList.Request request = QotGetIpoList.Request.newBuilder().setC2S(getIpoListC2s).build();
        Integer seqNo = qot.getIpoList(request);
        resultHandler.handle(Future.succeededFuture(
                new FTCommonResult(seqNo, "查询IPO信息:market=" + getIpoListC2s.getMarket()).toJson()
        ));
    }

    public void sendGetSecurity(QotGetPlateSecurity.C2S getSecurityC2s, Handler<AsyncResult<JsonObject>> resultHandler) {
        QotGetPlateSecurity.Request request = QotGetPlateSecurity.Request.newBuilder().setC2S(getSecurityC2s).build();
        Integer seqNo = qot.getPlateSecurity(request);
        resultHandler.handle(Future.succeededFuture(
                new FTCommonResult(seqNo, "查询板块股票信息:plate=" + getSecurityC2s.getPlate().toString()).toJson()
        ));
    }

    @Override
    public void onReply_GetPlateSet(FTAPI_Conn client, int nSerialNo, QotGetPlateSet.Response rsp) {
        if (rsp.getRetType() != 0) {
            Log.error("查询板块信息失败:" + rsp.getRetMsg(),
                    new IllegalStateException("请求序列号:" + nSerialNo + "查询板块信息失败,code:" + rsp.getRetType()));
        } else {
            Log.info("connID=" + client.getConnectID() + "查询板块信息...");
            try {
                String plateInfoJson = JsonFormat.printer().print(rsp);
                FTGrpcReturnResult parsedResult = new FTGrpcReturnResult(new JsonObject(plateInfoJson));
                JsonArray plateInfoList = parsedResult.getS2c().getJsonArray("plateInfoList");
                List<Plate> platesToDb = plateInfoList.stream()
                        .map(JsonObject::mapFrom)
                        .map(jsonObject -> new Plate(jsonObject.getString("name"),
                                jsonObject.getJsonObject("plate").getInteger("market"),
                                jsonObject.getJsonObject("plate").getString("code"))).collect(Collectors.toList());
                mapper.insertPlateBatch(platesToDb)
                        .onSuccess(size -> Log.info("插入板块信息条成功!"))
                        .onFailure(err -> Log.error("插入板块信息出错!", err));
            } catch (InvalidProtocolBufferException e) {
                Log.error("查询板块信息解析结果失败!", e);
            }

        }
    }

    @Override
    public void onReply_GetPlateSecurity(FTAPI_Conn client, int nSerialNo, QotGetPlateSecurity.Response rsp) {
        if (rsp.getRetType() != 0) {
            Log.error("查询板块股票信息失败:" + rsp.getRetMsg(),
                    new IllegalStateException("请求序列号:" + nSerialNo + "查询板块股票信息失败,code:" + rsp.getRetType()));
        } else {
            Log.info("connID=" + client.getConnectID() + "查询板块股票信息...");
            try {
                String plateInfoJson = JsonFormat.printer().print(rsp);
                Log.info(plateInfoJson);
                FTGrpcReturnResult parsedResult = new FTGrpcReturnResult(new JsonObject(plateInfoJson));
                JsonArray staticSecurityInfos = parsedResult.getS2c().getJsonArray("staticInfoList");
                List<Security> securities2Db = staticSecurityInfos.stream()
                        .map(JsonObject::mapFrom)
                        .map(jsonObject -> {
                            JsonObject basic = jsonObject.getJsonObject("basic");
                            JsonObject securityObj = basic.getJsonObject("security");
                            return new Security(
                                    basic.getString("name"),
                                    basic.getInteger("lotSize"),
                                    basic.getInteger("secType"),
                                    basic.containsKey("listTimestamp") ?
                                            DateUtils.getDateTimeOfTimeStamp(basic.getDouble("listTimestamp").longValue()) :
                                            DateUtils.getDateTimePattern(basic.getString("listTime")),
                                    basic.getBoolean("delisting") ? 1 : 0,
                                    basic.getInteger("exchType"),
                                    basic.getString("id"),
                                    securityObj.getInteger("market"),
                                    securityObj.getString("code")
                            );
                        })
                        .collect(Collectors.toList());
                mapper.insertSecurityBatch(securities2Db)
                        .onSuccess(size -> Log.info("插入板块股票信息成功!"))
                        .onFailure(err -> Log.error("插入板块股票信息失败!", err));
            } catch (InvalidProtocolBufferException e) {
                Log.error("查询板块股票信息解析结果失败!", e);
            }
        }
    }

    @Override
    public void onReply_GetReference(FTAPI_Conn client, int nSerialNo, QotGetReference.Response rsp) {

    }

    @Override
    public void onReply_GetOwnerPlate(FTAPI_Conn client, int nSerialNo, QotGetOwnerPlate.Response rsp) {

    }

    @Override
    public void onReply_GetHoldingChangeList(FTAPI_Conn client, int nSerialNo, QotGetHoldingChangeList.Response rsp) {

    }

    @Override
    public void onReply_GetOptionChain(FTAPI_Conn client, int nSerialNo, QotGetOptionChain.Response rsp) {

    }

    @Override
    public void onReply_GetWarrant(FTAPI_Conn client, int nSerialNo, QotGetWarrant.Response rsp) {

    }

    @Override
    public void onReply_GetCapitalFlow(FTAPI_Conn client, int nSerialNo, QotGetCapitalFlow.Response rsp) {

    }

    @Override
    public void onReply_GetCapitalDistribution(FTAPI_Conn client, int nSerialNo, QotGetCapitalDistribution.Response rsp) {

    }

    @Override
    public void onReply_GetUserSecurity(FTAPI_Conn client, int nSerialNo, QotGetUserSecurity.Response rsp) {

    }

    @Override
    public void onReply_ModifyUserSecurity(FTAPI_Conn client, int nSerialNo, QotModifyUserSecurity.Response rsp) {

    }

    @Override
    public void onReply_StockFilter(FTAPI_Conn client, int nSerialNo, QotStockFilter.Response rsp) {

    }

    @Override
    public void onReply_GetCodeChange(FTAPI_Conn client, int nSerialNo, QotGetCodeChange.Response rsp) {

    }

    @Override
    public void onReply_GetIpoList(FTAPI_Conn client, int nSerialNo, QotGetIpoList.Response rsp) {
        if (rsp.getRetType() != 0) {
            Log.error("查询IPO信息失败:" + rsp.getRetMsg(),
                    new IllegalStateException("请求序列号:" + nSerialNo + "查询IPO信息失败,code:" + rsp.getRetType()));
        } else {
            Log.info("connID=" + client.getConnectID() + "查询IPO信息...");
            try {
                String plateInfoJson = JsonFormat.printer().print(rsp);
                //TODO 入库待完成
                Log.info(plateInfoJson);
            } catch (InvalidProtocolBufferException e) {
                Log.error("查询IPO信息解析结果失败!", e);
            }

        }
    }

    @Override
    public void onReply_GetFutureInfo(FTAPI_Conn client, int nSerialNo, QotGetFutureInfo.Response rsp) {

    }

    @Override
    public void onReply_RequestTradeDate(FTAPI_Conn client, int nSerialNo, QotRequestTradeDate.Response rsp) {

    }

    @Override
    public void onReply_SetPriceReminder(FTAPI_Conn client, int nSerialNo, QotSetPriceReminder.Response rsp) {

    }

    @Override
    public void onReply_GetPriceReminder(FTAPI_Conn client, int nSerialNo, QotGetPriceReminder.Response rsp) {

    }

    @Override
    public void onReply_GetUserSecurityGroup(FTAPI_Conn client, int nSerialNo, QotGetUserSecurityGroup.Response rsp) {

    }

    @Override
    public void onReply_GetMarketState(FTAPI_Conn client, int nSerialNo, QotGetMarketState.Response rsp) {

    }

    @Override
    public void onReply_GetOptionExpirationDate(FTAPI_Conn client, int nSerialNo, QotGetOptionExpirationDate.Response rsp) {

    }

    @Override
    public void onPush_Notify(FTAPI_Conn client, Notify.Response rsp) {

    }

    @Override
    public void onPush_UpdateOrderBook(FTAPI_Conn client, QotUpdateOrderBook.Response rsp) {

    }

    @Override
    public void onPush_UpdateBasicQuote(FTAPI_Conn client, QotUpdateBasicQot.Response rsp) {

    }

    @Override
    public void onPush_UpdateKL(FTAPI_Conn client, QotUpdateKL.Response rsp) {

    }

    @Override
    public void onPush_UpdateRT(FTAPI_Conn client, QotUpdateRT.Response rsp) {

    }

    @Override
    public void onPush_UpdateTicker(FTAPI_Conn client, QotUpdateTicker.Response rsp) {

    }

    @Override
    public void onPush_UpdateBroker(FTAPI_Conn client, QotUpdateBroker.Response rsp) {

    }

    @Override
    public void onPush_UpdatePriceReminder(FTAPI_Conn client, QotUpdatePriceReminder.Response rsp) {

    }
}
