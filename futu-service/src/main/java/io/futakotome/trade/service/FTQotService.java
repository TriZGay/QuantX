package io.futakotome.trade.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.futu.openapi.*;
import com.futu.openapi.pb.*;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.futakotome.common.message.RTBasicQuoteMessage;
import io.futakotome.common.message.RTBrokerMessage;
import io.futakotome.common.message.RTTickerMessage;
import io.futakotome.common.message.RTTimeShareMessage;
import io.futakotome.trade.config.FutuConfig;
import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.domain.Snapshot;
import io.futakotome.trade.domain.SnapshotService;
import io.futakotome.trade.domain.code.*;
import io.futakotome.trade.dto.*;
import io.futakotome.trade.dto.message.*;
import io.futakotome.trade.dto.ws.*;
import io.futakotome.trade.utils.CacheManager;
import io.futakotome.trade.utils.RequestCount;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Service
public class FTQotService implements FTSPI_Conn, FTSPI_Qot, InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(FTQotService.class);
    private static final Gson GSON = new Gson();

    private final PlateDtoService plateService;
    private final StockDtoService stockService;
    private final SnapshotService snapshotService;

    private final SubDtoService subService;
    private final TradeDateDtoService tradeDateService;
    private final FutuConfig futuConfig;
    private final QuantxFutuWsService quantxFutuWsService;
    private final KafkaService kafkaService;

    private static final String clientID = "javaclient";

    private static final FTAPI_Conn_Qot qot = new FTAPI_Conn_Qot();

    public FTQotService(PlateDtoService plateService, StockDtoService stockService, SnapshotService snapshotService,
                        SubDtoService subService, TradeDateDtoService tradeDateService, FutuConfig futuConfig,
                        QuantxFutuWsService quantxFutuWsService, KafkaService kafkaService) {
        qot.setClientInfo(clientID, 1);
        qot.setConnSpi(this);
        qot.setQotSpi(this);
        this.plateService = plateService;
        this.stockService = stockService;
        this.snapshotService = snapshotService;
        this.subService = subService;
        this.tradeDateService = tradeDateService;

        this.futuConfig = futuConfig;
        this.quantxFutuWsService = quantxFutuWsService;
        this.kafkaService = kafkaService;
    }

    public void syncStockInPlate(List<CommonSecurity> plates) {
        RequestCount requestLock = new RequestCount(40000L, 10);
        for (CommonSecurity plateItem : plates) {
            QotCommon.Security sec = QotCommon.Security.newBuilder()
                    .setMarket(plateItem.getMarket())
                    .setCode(plateItem.getCode())
                    .build();
            QotGetPlateSecurity.C2S c2s = QotGetPlateSecurity.C2S.newBuilder()
                    .setPlate(sec)
                    .build();
            QotGetPlateSecurity.Request req = QotGetPlateSecurity.Request.newBuilder()
                    .setC2S(c2s)
                    .build();
            int seqNo = qot.getPlateSecurity(req);
            CacheManager.put(String.valueOf(seqNo), plateItem);
            LOGGER.info("{}-{}:请求股票数据,seq={}", MarketType.getNameByCode(plateItem.getMarket()),
                    plateItem.getCode(), seqNo);
            requestLock.count();
        }

    }

    public void syncPlateInfo(List<Integer> markets) {
        for (Integer market : markets) {
            QotGetPlateSet.C2S c2S = QotGetPlateSet.C2S.newBuilder()
                    .setMarket(market)
                    .setPlateSetType(PlateSetType.ALL.getCode())
                    .build();
            QotGetPlateSet.Request req = QotGetPlateSet.Request.newBuilder()
                    .setC2S(c2S).build();
            int seqNo = qot.getPlateSet(req);
            LOGGER.info("{}:请求板块数据,seq={}", MarketType.getNameByCode(market), seqNo);
        }
    }

    public void syncStaticInfo(Integer market, Integer stockType) {
        QotGetStaticInfo.C2S c2S = QotGetStaticInfo.C2S.newBuilder()
                .setMarket(market)
                .setSecType(stockType)
                .build();
        QotGetStaticInfo.Request request = QotGetStaticInfo.Request.newBuilder()
                .setC2S(c2S).build();
        int seqNo = qot.getStaticInfo(request);
        LOGGER.info("{}-{}:请求静态数据,seq={}", MarketType.getNameByCode(market), StockType.getNameByCode(stockType), seqNo);
    }

    public void syncStockOwnerPlateInfo(List<CommonSecurity> securities) {
        for (CommonSecurity security : securities) {
            QotGetOwnerPlate.C2S c2S = QotGetOwnerPlate.C2S.newBuilder()
                    .addSecurityList(QotCommon.Security.newBuilder()
                            .setMarket(security.getMarket())
                            .setCode(security.getCode())
                            .build()).build();
            QotGetOwnerPlate.Request request = QotGetOwnerPlate.Request.newBuilder()
                    .setC2S(c2S)
                    .build();
            int seqNo = qot.getOwnerPlate(request);
            LOGGER.info("{}-{}:请求板块数据,seq={}", MarketType.getNameByCode(security.getMarket()), security.getCode(), seqNo);
        }
    }

    public void syncTradeDate() {
//        market.sendTradeDateRequest();
    }

    public void sendSubInfoRequest() {
        QotGetSubInfo.Request request = QotGetSubInfo.Request.newBuilder()
                .setC2S(QotGetSubInfo.C2S.newBuilder()
                        .setIsReqAllConn(true)
                        .build())
                .build();
        int seqNo = qot.getSubInfo(request);
        LOGGER.info("查询订阅信息.seqNo={}", seqNo);
    }

    public void syncCapitalFlow(Integer market, String code) {
        QotCommon.Security security = QotCommon.Security.newBuilder()
                .setMarket(market)
                .setCode(code)
                .build();
        QotGetCapitalFlow.Request request = QotGetCapitalFlow.Request.newBuilder()
                .setC2S(QotGetCapitalFlow.C2S.newBuilder()
                        .setSecurity(security).build())
                .build();
        int seqNo = qot.getCapitalFlow(request);
        CommonSecurity commonSecurity = new CommonSecurity(market, code);
        CacheManager.put(String.valueOf(seqNo), commonSecurity);
        LOGGER.info("{}-{}请求资金流向.seqNo={}", MarketType.getNameByCode(market), code, seqNo);
//        if (request.getPeriodType() == 1) {
//            //实时
//            ftRequest.setC2S(QotGetCapitalFlow.C2S.newBuilder()
//                    .setPeriodType(request.getPeriodType())
//                    .setSecurity(security)
//                    .build());
//        } else {
//            ftRequest.setC2S(QotGetCapitalFlow.C2S.newBuilder()
//                    .setPeriodType(request.getPeriodType())
//                    .setSecurity(security)
//                    .setBeginTime(request.getBeginDate())
//                    .setEndTime(request.getEndDate())
//                    .build());
//        }
//        int seqNo = qot.getCapitalFlow(ftRequest.build());
//        String marketAndCode = request.getMarket() + "-" + request.getCode();
//        CacheManager.put(String.valueOf(seqNo), marketAndCode);
//        LOGGER.info("请求资金流向.seqNo=" + seqNo);
    }

    public void syncCapitalDistribution(Integer market, String code) {
        QotGetCapitalDistribution.Request request = QotGetCapitalDistribution.Request.newBuilder()
                .setC2S(QotGetCapitalDistribution.C2S.newBuilder()
                        .setSecurity(QotCommon.Security.newBuilder()
                                .setMarket(market)
                                .setCode(code)
                                .build())
                        .build())
                .build();
        int seqNo = qot.getCapitalDistribution(request);
        CommonSecurity commonSecurity = new CommonSecurity(market, code);
        CacheManager.put(String.valueOf(seqNo), commonSecurity);
        LOGGER.info("{}-{}请求资金分布.seqNo={}", MarketType.getNameByCode(market), code, seqNo);
    }

    public void cancelSubscribe(SubOrUnSubWsMessage subscribeRequest) {
        QotSub.Request request = QotSub.Request.newBuilder()
                .setC2S(QotSub.C2S.newBuilder()
                        .addAllSubTypeList(subscribeRequest.getSubTypeList())
                        .addAllSecurityList(subscribeRequest.getSecurityList()
                                .stream().map(security ->
                                        QotCommon.Security.newBuilder()
                                                .setMarket(security.getMarket())
                                                .setCode(security.getCode())
                                                .build())
                                .collect(Collectors.toList()))
                        .setIsSubOrUnSub(false)
                        .build())
                .build();
        int seqNo = qot.sub(request);
        //下面订阅成功之后再拿出来插入订阅信息
        CacheManager.put(String.valueOf(seqNo), subscribeRequest);
        LOGGER.info("取消订阅.seqNo={}", seqNo);
    }

    public void subscribeRequest(SubOrUnSubWsMessage subscribeRequest) {
        QotSub.Request.Builder requestBuilder = QotSub.Request.newBuilder();
        if (CollectionUtils.intersection(subscribeRequest.getSubTypeList(),
                Arrays.asList(
                        SubType.KL_1MIN.getCode(),
                        SubType.KL_3MIN.getCode(),
                        SubType.KL_15MIN.getCode(),
                        SubType.KL_30MIN.getCode(),
                        SubType.KL_60MIN.getCode(),
                        SubType.KL_DAY.getCode(),
                        SubType.KL_WEEK.getCode(),
                        SubType.KL_MONTH.getCode(),
                        SubType.KL_QUARTER.getCode(),
                        SubType.KL_YEAR.getCode()
                )).size() != 0) {
            //K线订阅类型默认 前、无、后复权都订阅
            requestBuilder.setC2S(QotSub.C2S.newBuilder()
                    .addAllSubTypeList(subscribeRequest.getSubTypeList())
                    .addAllRegPushRehabTypeList(Arrays.asList(
                            RehabType.NONE.getCode(),
                            RehabType.FORWARD.getCode(),
                            RehabType.BACKWARD.getCode()
                    ))
                    .addAllSecurityList(subscribeRequest.getSecurityList()
                            .stream().map(security ->
                                    QotCommon.Security.newBuilder()
                                            .setMarket(security.getMarket())
                                            .setCode(security.getCode())
                                            .build())
                            .collect(Collectors.toList()))
                    .setIsRegOrUnRegPush(true)
                    .setIsSubOrUnSub(true)
                    .build());
        } else {
            QotSub.C2S.Builder c2sBuilder = QotSub.C2S.newBuilder();
            c2sBuilder.addAllSecurityList(subscribeRequest.getSecurityList()
                            .stream().map(security ->
                                    QotCommon.Security.newBuilder()
                                            .setMarket(security.getMarket())
                                            .setCode(security.getCode())
                                            .build())
                            .collect(Collectors.toList()))
                    .setIsRegOrUnRegPush(true)
                    .setIsSubOrUnSub(true);
            if (!subscribeRequest.getSubTypeList().isEmpty()) {
                c2sBuilder.addAllSubTypeList(subscribeRequest.getSubTypeList());
            }
            requestBuilder.setC2S(c2sBuilder.build());
        }
        int seqNo = qot.sub(requestBuilder.build());
        //下面订阅成功之后再拿出来插入订阅信息
        CacheManager.put(String.valueOf(seqNo), subscribeRequest);
        LOGGER.info("发起订阅.seqNo={}", seqNo);
    }

    public void sendHistoryKLineRequest(HistoryKLWsMessage syncHistoryKRequest) {
        QotCommon.Security security = QotCommon.Security.newBuilder()
                .setMarket(syncHistoryKRequest.getMarket())
                .setCode(syncHistoryKRequest.getCode())
                .build();
        Arrays.asList(RehabType.NONE.getCode(),
                        RehabType.FORWARD.getCode(),
                        RehabType.BACKWARD.getCode())
                .forEach(rehabType -> {
                    QotRequestHistoryKL.C2S c2S = QotRequestHistoryKL.C2S.newBuilder()
                            .setRehabType(rehabType)
                            .setKlType(syncHistoryKRequest.getKlType())
                            .setSecurity(security)
                            .setBeginTime(syncHistoryKRequest.getBeginDate())
                            .setEndTime(syncHistoryKRequest.getEndDate())
                            .build();
                    QotRequestHistoryKL.Request request = QotRequestHistoryKL.Request.newBuilder()
                            .setC2S(c2S)
                            .build();
                    int seqNo = qot.requestHistoryKL(request);
                    String value = syncHistoryKRequest.getMarket() + "-" + syncHistoryKRequest.getCode() + "-" + syncHistoryKRequest.getKlType() + "-" + rehabType;
                    CacheManager.put(String.valueOf(seqNo), value);
                    LOGGER.info("查询历史K线数据.seqNo={}", seqNo);
                });
    }

    public void sendHistoryKLineDetailRequest() {
        QotRequestHistoryKLQuota.Request request = QotRequestHistoryKLQuota.Request.newBuilder()
                .setC2S(QotRequestHistoryKLQuota.C2S.newBuilder().setBGetDetail(true).build())
                .build();
        int seqNo = qot.requestHistoryKLQuota(request);
        LOGGER.info("查询历史K线数据额度明细.seq={}", seqNo);
    }

    public void sendGlobalMarketStateRequest() {
        GetGlobalState.Request request = GetGlobalState.Request.newBuilder()
                .setC2S(GetGlobalState.C2S.newBuilder()
                        .setUserID(0)
                        .build())
                .build();
        int seqNo = qot.getGlobalState(request);
        LOGGER.info("查询全局市场状态.seq={}", seqNo);
    }

    public void sendRehabRequest(Integer market, String code) {
        QotRequestRehab.Request request = QotRequestRehab.Request.newBuilder()
                .setC2S(QotRequestRehab.C2S.newBuilder()
                        .setSecurity(QotCommon.Security.newBuilder()
                                .setMarket(market)
                                .setCode(code)
                                .build())
                        .build())
                .build();
        int seqNo = qot.requestRehab(request);
        CommonSecurity commonSecurity = new CommonSecurity(market, code);
        CacheManager.put(String.valueOf(seqNo), commonSecurity);
        LOGGER.info("{}-{}查询复权因子.seq={}", MarketType.getNameByCode(market), code, seqNo);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.connect();
        this.subscribeOnStartup();
    }

    private void subscribeOnStartup() {
        List<SubDto> subscribeInfos = this.subService.list();
        if (!subscribeInfos.isEmpty()) {
            Map<SubscribeSecurity, List<Integer>> groupBySecurity = subscribeInfos.stream().collect(
                    toMap(subDto -> new SubscribeSecurity(subDto.getSecurityMarket(), subDto.getSecurityCode()),
                            subDto -> {
                                List<Integer> subType = new ArrayList<>();
                                subType.add(subDto.getSubType());
                                return subType;
                            },
                            (u1, u2) -> {
                                u1.addAll(u2);
                                return u1;
                            }));
            groupBySecurity.keySet()
                    .forEach(subscribeSecurity ->
                            this.subscribeRequest(new SubOrUnSubWsMessage(Collections.singletonList(subscribeSecurity),
                                    groupBySecurity.get(subscribeSecurity), false)));
        }
    }

    public void connect() {
        FTAPI.init();
        qot.initConnect(futuConfig.getUrl(), futuConfig.getPort(), futuConfig.isEnableEncrypt());
    }

    public void disconnect() {
        qot.close();
    }

    @Override
    public void onInitConnect(FTAPI_Conn client, long errCode, String desc) {
        String content = "FUTU API 初始化行情连接 onInitConnect: ret=" + errCode + ",desc=" + desc + ",connID=" + client.getConnectID();
        LOGGER.info(content);
        sendNotifyMessage(content);
    }

    @Override
    public void onDisconnect(FTAPI_Conn client, long errCode) {
        String content = "FUTU API 关闭行情连接 onDisconnect: connID=" + client.getConnectID() + ",ret=" + errCode;
        LOGGER.info(content);
        sendNotifyMessage(content);
    }

    @Override
    public void onPush_Notify(FTAPI_Conn client, Notify.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "获取FutuD通知推送失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("connID=" + client.getConnectID() + "获取FutuD通知推送失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                String notify = "FutuD通知推送:" + ftGrpcReturnResult.toString();
                LOGGER.info(notify);
                sendNotifyMessage(notify);
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("FutuD通知推送结果解析失败.", e);
            }
        }
    }

    @Override
    public void onReply_RequestHistoryKL(FTAPI_Conn client, int nSerialNo, QotRequestHistoryKL.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "获取历史K线失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("connID=" + client.getConnectID() + "获取历史K线失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            String[] splitCachedValue = ((String) CacheManager.get(String.valueOf(nSerialNo))).split("-");
            Integer market = Integer.valueOf(splitCachedValue[0]);
            String code = splitCachedValue[1];
            Integer klType = Integer.valueOf(splitCachedValue[2]);
            Integer rehabType = Integer.valueOf(splitCachedValue[3]);
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                Iterator<JsonElement> klListIterator = ftGrpcReturnResult.getS2c().getAsJsonArray("klList").iterator();
                while (klListIterator.hasNext()) {
                    JsonObject kl = klListIterator.next().getAsJsonObject();
                    KLMessageContent klMessageContent = GSON.fromJson(kl, KLMessageContent.class);
                    klMessageContent.setKlType(klType);
                    klMessageContent.setRehabType(rehabType);
                    klMessageContent.setMarket(market);
                    klMessageContent.setCode(code);
                    kafkaService.sendRTKLineMessage(klMessageContent);
                }
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("解析历史K线额度使用明细结果失败.", e);
            }
        }
    }

    @Override
    public void onReply_RequestHistoryKLQuota(FTAPI_Conn client, int nSerialNo, QotRequestHistoryKLQuota.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "获取历史K线额度使用明细失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("connID=" + client.getConnectID() + "获取历史K线额度使用明细失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                HistoryKLDetailMessageContent historyKLDetailMessage = GSON.fromJson(ftGrpcReturnResult.getS2c(), HistoryKLDetailMessageContent.class);
                sendHistoryKLDetailMessage(historyKLDetailMessage);
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("解析历史K线额度使用明细结果失败.", e);
            }
        }
    }

    @Override
    public void onReply_RequestRehab(FTAPI_Conn client, int nSerialNo, QotRequestRehab.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "获取复权因子失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("connID=" + client.getConnectID() + "获取复权因子失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                CommonSecurity commonSecurity = (CommonSecurity) CacheManager.get(String.valueOf(nSerialNo));
                List<RehabMessageContent> rehabMessageContents = GSON.fromJson(ftGrpcReturnResult.getS2c().get("rehabList").getAsJsonArray(), new TypeToken<List<RehabMessageContent>>() {
                }.getType());
                RehabsWsMessage rehabsWsMessage = new RehabsWsMessage();
                rehabsWsMessage.setSecurity(commonSecurity);
                rehabsWsMessage.setRehabs(rehabMessageContents);
                quantxFutuWsService.sendRehabs(rehabsWsMessage);
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("解析复权因子结果失败.", e);
            }
        }
    }

    @Override
    public void onReply_GetCapitalFlow(FTAPI_Conn client, int nSerialNo, QotGetCapitalFlow.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "获取资金流向失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("connID=" + client.getConnectID() + "获取资金流向失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                CommonSecurity security = (CommonSecurity) CacheManager.get(String.valueOf(nSerialNo));
                List<CapitalFlowMessageContent> capitalFlowMessageContents = GSON.fromJson(ftGrpcReturnResult.getS2c().get("flowItemList").getAsJsonArray(), new TypeToken<List<CapitalFlowMessageContent>>() {
                }.getType());
                String lastValidTime = ftGrpcReturnResult.getS2c().get("lastValidTime").getAsString();
                CapitalFlowWsMessage capitalFlowWsMessage = new CapitalFlowWsMessage();
                capitalFlowWsMessage.setContentList(capitalFlowMessageContents);
                capitalFlowWsMessage.setSecurity(security);
                capitalFlowWsMessage.setLastValidTime(lastValidTime);
                quantxFutuWsService.sendCapitalFlow(capitalFlowWsMessage);
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("解析资金流向结果失败.", e);
            }
        }
    }

    @Override
    public void onReply_GetCapitalDistribution(FTAPI_Conn client, int nSerialNo, QotGetCapitalDistribution.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "获取资金分布失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("connID=" + client.getConnectID() + "获取资金分布失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                CommonSecurity security = (CommonSecurity) CacheManager.get(String.valueOf(nSerialNo));
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                CapitalDistributionContent capitalDistributionContent = GSON.fromJson(ftGrpcReturnResult.getS2c(), CapitalDistributionContent.class);
                CapitalDistributionWsMessage message = new CapitalDistributionWsMessage();
                message.setSecurity(security);
                message.setContent(capitalDistributionContent);
                quantxFutuWsService.sendCapitalDistribution(message);
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("解析资金分布结果失败.", e);
            }
        }
    }

    @Override
    //todo trade date
    public void onReply_RequestTradeDate(FTAPI_Conn client, int nSerialNo, QotRequestTradeDate.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "获取交易日失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("connID=" + client.getConnectID() + "获取交易日失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
//            try {
//                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
//                Object marketType = CacheManager.get(String.valueOf(nSerialNo));
//                Iterator<JsonElement> tradeDateList = ftGrpcReturnResult.getS2c().getAsJsonArray("tradeDateList").iterator();
//                if (marketType instanceof Integer) {
//                    Integer tradeDateMarket = (Integer) marketType;
//                    List<TradeDateDto> newTradeDateDtos = new ArrayList<>();
//                    if (tradeDateMarket.equals(TradeDateMarketType.HK.getCode())) {
//                        while (tradeDateList.hasNext()) {
//                            JsonObject tradeDateJsonObj = tradeDateList.next().getAsJsonObject();
//                            TradeDateDto tradeDateDto = new TradeDateDto();
//                            tradeDateDto.setMarketOrSecurity(String.valueOf(MarketType.HK.getCode()));
//                            tradeDateDto.setTime(tradeDateJsonObj.get("time").getAsString());
//                            tradeDateDto.setTradeDateType(tradeDateJsonObj.get("tradeDateType").getAsInt());
//                            newTradeDateDtos.add(tradeDateDto);
//                        }
//                    } else if (tradeDateMarket.equals(TradeDateMarketType.CN.getCode())) {
//                        while (tradeDateList.hasNext()) {
//                            JsonObject tradeDateJsonObj = tradeDateList.next().getAsJsonObject();
//                            TradeDateDto tradeDateDto = new TradeDateDto();
//                            tradeDateDto.setMarketOrSecurity(MarketType.CN_SH.getCode() + "," + MarketType.CN_SZ.getCode());
//                            tradeDateDto.setTime(tradeDateJsonObj.get("time").getAsString());
//                            tradeDateDto.setTradeDateType(tradeDateJsonObj.get("tradeDateType").getAsInt());
//                            newTradeDateDtos.add(tradeDateDto);
//                        }
//                    } else if (tradeDateMarket.equals(TradeDateMarketType.US.getCode())) {
//                        while (tradeDateList.hasNext()) {
//                            JsonObject tradeDateJsonObj = tradeDateList.next().getAsJsonObject();
//                            TradeDateDto tradeDateDto = new TradeDateDto();
//                            tradeDateDto.setMarketOrSecurity(String.valueOf(MarketType.US.getCode()));
//                            tradeDateDto.setTime(tradeDateJsonObj.get("time").getAsString());
//                            tradeDateDto.setTradeDateType(tradeDateJsonObj.get("tradeDateType").getAsInt());
//                            newTradeDateDtos.add(tradeDateDto);
//                        }
//                    }
//                    //todo 其他的以后再算
//                    List<TradeDateDto> existTradeDates = tradeDateDtoMapper.selectList(null);
//                    newTradeDateDtos.removeIf(existTradeDates::contains);
//                    int insertRow = tradeDateDtoMapper.insertBatch(newTradeDateDtos);
//                    if (insertRow > 0) {
//                        String str = "交易日数据插入条数" + insertRow;
//                        LOGGER.info(str);
//                        sendNotifyMessage(str);
//                    }
//                }
//            } catch (InvalidProtocolBufferException e) {
//                LOGGER.error("解析交易日结果失败.", e);
//            }
        }
    }

    @Override
    public void onReply_GetGlobalState(FTAPI_Conn client, int nSerialNo, GetGlobalState.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "获取全局市场状态失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("connID=" + client.getConnectID() + "获取全局市场状态失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                MarketStateContent marketStateVo = GSON.fromJson(ftGrpcReturnResult.getS2c(), MarketStateContent.class);
                sendMarketStateMessage(marketStateVo);
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("解析全局市场状态结果失败.", e);
            }
        }
    }

    @Override
    public void onPush_UpdateOrderBook(FTAPI_Conn client, QotUpdateOrderBook.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "获取摆盘推送失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("connID=" + client.getConnectID() + "获取摆盘失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                LOGGER.info("摆盘" + ftGrpcReturnResult.toString());
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("摆盘结果解析失败.", e);
            }
        }
    }

    private void sendHistoryKLDetailMessage(HistoryKLDetailMessageContent messageContent) {
        HistoryKLDetailWsMessage message = new HistoryKLDetailWsMessage();
        message.setUsedQuota(messageContent.getUsedQuota());
        message.setRemainQuota(messageContent.getRemainQuota());
        message.setItemList(messageContent.getDetailList() == null ? new ArrayList<>() :
                messageContent.getDetailList().stream().map(item -> {
                    HistoryKLDetailWsMessage.HistoryKLDetailItemWsMessage itemMessage = new HistoryKLDetailWsMessage.HistoryKLDetailItemWsMessage();
                    itemMessage.setMarket(item.getSecurity().getMarket());
                    itemMessage.setCode(item.getSecurity().getCode());
                    itemMessage.setName(item.getName());
                    itemMessage.setRequestTime(item.getRequestTime());
                    itemMessage.setRequestTimeStamp(item.getRequestTimeStamp());
                    return itemMessage;
                }).collect(Collectors.toList()));
        quantxFutuWsService.sendHistoryKQuotaDetails(message);
    }

    private void sendMarketStateMessage(MarketStateContent marketStateVo) {
        MarketStateWsMessage marketStateMessage = new MarketStateWsMessage();
        marketStateMessage.setMarketHK(MarketState.mapFrom(marketStateVo.getMarketHK()));
        marketStateMessage.setMarketUS(MarketState.mapFrom(marketStateVo.getMarketUS()));
        marketStateMessage.setMarketSH(MarketState.mapFrom(marketStateVo.getMarketSH()));
        marketStateMessage.setMarketSZ(MarketState.mapFrom(marketStateVo.getMarketSZ()));
        marketStateMessage.setMarketHKFuture(MarketState.mapFrom(marketStateVo.getMarketHKFuture()));
        marketStateMessage.setTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(marketStateVo.getTime()), ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        marketStateMessage.setLocalTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(marketStateVo.getLocalTime()), ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        marketStateMessage.setMarketUSFuture(MarketState.mapFrom(marketStateVo.getMarketUSFuture()));
        marketStateMessage.setMarketSGFuture(MarketState.mapFrom(marketStateVo.getMarketSGFuture()));
        marketStateMessage.setMarketJPFuture(MarketState.mapFrom(marketStateVo.getMarketJPFuture()));
        this.quantxFutuWsService.sendMarketState(marketStateMessage);
    }

    private void sendNotifyMessage(String notifyContent) {
        if (Objects.nonNull(notifyContent) && !notifyContent.isEmpty()) {
            quantxFutuWsService.sendNotify(notifyContent);
        }
    }

    private void sendBrokersMessage(BrokerMessageContent content) {
        RTBrokerMessage brokerMessage = new RTBrokerMessage();
        brokerMessage.setMarket(content.getMarket());
        brokerMessage.setCode(content.getCode());
        brokerMessage.setBrokerId(content.getId());
        brokerMessage.setBrokerName(content.getName());
        brokerMessage.setBrokerPos(content.getPos());
        brokerMessage.setAskOrBid(content.getAskOrBid());
        brokerMessage.setOrderId(content.getOrderID());
        brokerMessage.setVolume(content.getVolume());

//        rocketMQTemplate.asyncSend(MessageCommon.RT_BROKER_TOPIC, brokerMessage, new SendCallback() {
//            @Override
//            public void onSuccess(SendResult sendResult) {
//                LOGGER.info("经纪队列数据投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
//                        sendResult.getSendStatus());
//            }
//
//            @Override
//            public void onException(Throwable throwable) {
//                LOGGER.error("经纪队列数据投递失败", throwable);
//            }
//        });
    }

    private void sendTimeShareMessage(TimeShareMessageContent content) {
        if (!content.getBlank()) {
            //非空才发
            RTTimeShareMessage message = new RTTimeShareMessage();
            message.setMarket(content.getMarket());
            message.setCode(content.getCode());
            message.setMinute(content.getMinute());
            message.setPrice(content.getPrice());
            message.setLastClosePrice(content.getLastClosePrice());
            message.setAvgPrice(content.getAvgPrice());
            message.setVolume(content.getVolume());
            message.setTurnover(content.getTurnover());
            message.setUpdateTime(content.getTime());

//            rocketMQTemplate.asyncSend(MessageCommon.RT_TIMESHARE_TOPIC, message, new SendCallback() {
//                @Override
//                public void onSuccess(SendResult sendResult) {
//                    LOGGER.info("分时数据投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
//                            sendResult.getSendStatus());
//                }
//
//                @Override
//                public void onException(Throwable throwable) {
//                    LOGGER.error("分时数据投递失败", throwable);
//                }
//            });
        }
    }

    private void sendRTTickerMessage(RealTimeTickerMessageContent content) {
        RTTickerMessage message = new RTTickerMessage();
        message.setMarket(content.getMarket());
        message.setCode(content.getCode());
        message.setSequence(content.getSequence());
        message.setTickerDirection(content.getDir());
        message.setPrice(content.getPrice());
        message.setVolume(content.getVolume());
        message.setTurnover(content.getTurnover());
        message.setTickerType(content.getType());
        message.setTypeSign(content.getTypeSign());
        message.setUpdateTime(content.getTime());
//        rocketMQTemplate.asyncSend(MessageCommon.RT_TICKER_TOPIC, message, new SendCallback() {
//            @Override
//            public void onSuccess(SendResult sendResult) {
//                LOGGER.info("逐笔数据投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
//                        sendResult.getSendStatus());
//            }
//
//            @Override
//            public void onException(Throwable throwable) {
//                LOGGER.error("逐笔数据投递失败", throwable);
//            }
//        });

    }


    private void sendBasicQuoteMessage(BasicQuoteMessageContent basicQuoteMessageContent) {
        String cacheKey = basicQuoteMessageContent.getSecurity().getMarket() + "+" + basicQuoteMessageContent.getSecurity().getCode();
        StockDto stockDto;
        Object cachedStock = CacheManager.get(cacheKey);
        if (cachedStock instanceof StockDto) {
            stockDto = (StockDto) cachedStock;
        } else {
            QueryWrapper<StockDto> queryWrapper = Wrappers.query();
            queryWrapper.eq("market", basicQuoteMessageContent.getSecurity().getMarket());
            queryWrapper.eq("code", basicQuoteMessageContent.getSecurity().getCode());
//            stockDto = stockMapper.selectOne(queryWrapper);
            stockDto = new StockDto();
//            CacheManager.put(cacheKey, stockDto);
        }
        RTBasicQuoteMessage rtBasicQuoteMessage = new RTBasicQuoteMessage();
        rtBasicQuoteMessage.setMarket(basicQuoteMessageContent.getSecurity().getMarket());
        rtBasicQuoteMessage.setCode(basicQuoteMessageContent.getSecurity().getCode());
        rtBasicQuoteMessage.setPriceSpread(basicQuoteMessageContent.getPriceSpread());
        rtBasicQuoteMessage.setUpdateTime(basicQuoteMessageContent.getUpdateTime());
        rtBasicQuoteMessage.setHighPrice(basicQuoteMessageContent.getHighPrice());
        rtBasicQuoteMessage.setOpenPrice(basicQuoteMessageContent.getOpenPrice());
        rtBasicQuoteMessage.setLowPrice(basicQuoteMessageContent.getLowPrice());
        rtBasicQuoteMessage.setCurPrice(basicQuoteMessageContent.getCurPrice());
        rtBasicQuoteMessage.setLastClosePrice(basicQuoteMessageContent.getLastClosePrice());
        rtBasicQuoteMessage.setVolume(basicQuoteMessageContent.getVolume());
        rtBasicQuoteMessage.setTurnover(basicQuoteMessageContent.getTurnover());
        rtBasicQuoteMessage.setTurnoverRate(basicQuoteMessageContent.getTurnoverRate());
        rtBasicQuoteMessage.setAmplitude(basicQuoteMessageContent.getAmplitude());
        rtBasicQuoteMessage.setDarkStatus(basicQuoteMessageContent.getDarkStatus());
        rtBasicQuoteMessage.setSecStatus(basicQuoteMessageContent.getSecStatus());

        String hashKey = rtBasicQuoteMessage.getMarket() + "-" + rtBasicQuoteMessage.getCode();
        if (stockDto.getStockType().equals(StockType.Eqty.getCode())) {
            //正股
//            rocketMQTemplate.asyncSendOrderly(MessageCommon.RT_BASIC_QUO_TOPIC_STOCK, rtBasicQuoteMessage, hashKey + "-stock", new SendCallback() {
//                @Override
//                public void onSuccess(SendResult sendResult) {
//                    LOGGER.info("实时正股报价信息投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
//                            sendResult.getSendStatus());
//                }
//
//                @Override
//                public void onException(Throwable throwable) {
//                    LOGGER.error("实时正股报价信息投递失败", throwable);
//                }
//            });
        } else if (stockDto.getStockType().equals(StockType.Index.getCode())) {
            //指数
//            rocketMQTemplate.asyncSendOrderly(MessageCommon.RT_BASIC_QUO_TOPIC_INDEX, rtBasicQuoteMessage, hashKey + "-index", new SendCallback() {
//                @Override
//                public void onSuccess(SendResult sendResult) {
//                    LOGGER.info("实时指数报价信息投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
//                            sendResult.getSendStatus());
//                }
//
//                @Override
//                public void onException(Throwable throwable) {
//                    LOGGER.error("实时指数报价信息投递失败", throwable);
//                }
//            });
        } else if (stockDto.getStockType().equals(StockType.Future.getCode())) {
            //期货


        } else if (stockDto.getStockType().equals(StockType.Plate.getCode())) {
            //板块
//            rocketMQTemplate.asyncSendOrderly(MessageCommon.RT_BASIC_QUO_TOPIC_PLATE, rtBasicQuoteMessage, hashKey + "-plate", new SendCallback() {
//                @Override
//                public void onSuccess(SendResult sendResult) {
//                    LOGGER.info("实时板块报价信息投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
//                            sendResult.getSendStatus());
//                }
//
//                @Override
//                public void onException(Throwable throwable) {
//                    LOGGER.error("实时板块报价信息投递失败", throwable);
//                }
//            });
        }
    }

    @Override
    public void onPush_UpdateBasicQuote(FTAPI_Conn client, QotUpdateBasicQot.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "获取报价推送失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("connID=" + client.getConnectID() + "获取报价失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                Iterator<JsonElement> basicQotIterator = ftGrpcReturnResult.getS2c().getAsJsonArray("basicQotList").iterator();
                while (basicQotIterator.hasNext()) {
                    JsonObject oneBasicQotInfo = basicQotIterator.next().getAsJsonObject();
                    BasicQuoteMessageContent messageContent = GSON.fromJson(oneBasicQotInfo, BasicQuoteMessageContent.class);
                    sendBasicQuoteMessage(messageContent);
                }

            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("报价推送结果解析失败.", e);
            }
        }
    }

    @Override
    public void onPush_UpdateKL(FTAPI_Conn client, QotUpdateKL.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "获取K线推送失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("connID=" + client.getConnectID() + "获取K线数据失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                Integer klType = ftGrpcReturnResult.getS2c().get("klType").getAsInt();
                Integer rehabType = ftGrpcReturnResult.getS2c().get("rehabType").getAsInt();
                Integer market = ftGrpcReturnResult.getS2c().get("security").getAsJsonObject().get("market").getAsInt();
                String code = ftGrpcReturnResult.getS2c().get("security").getAsJsonObject().get("code").getAsString();
                Iterator<JsonElement> klListIterator = ftGrpcReturnResult.getS2c().getAsJsonArray("klList").iterator();
                while (klListIterator.hasNext()) {
                    JsonObject kl = klListIterator.next().getAsJsonObject();
                    KLMessageContent klMessageContent = GSON.fromJson(kl, KLMessageContent.class);
                    klMessageContent.setKlType(klType);
                    klMessageContent.setRehabType(rehabType);
                    klMessageContent.setMarket(market);
                    klMessageContent.setCode(code);
                    kafkaService.sendRTKLineMessage(klMessageContent);
                }
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("K线推送结果解析失败.", e);
            }
        }
    }

    @Override
    public void onPush_UpdateRT(FTAPI_Conn client, QotUpdateRT.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "获取分时推送失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("connID=" + client.getConnectID() + "获取分时数据失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                Integer market = ftGrpcReturnResult.getS2c().get("security").getAsJsonObject().get("market").getAsInt();
                String code = ftGrpcReturnResult.getS2c().get("security").getAsJsonObject().get("code").getAsString();
                Iterator<JsonElement> rtIterator = ftGrpcReturnResult.getS2c().getAsJsonArray("rtList").iterator();
                while (rtIterator.hasNext()) {
                    JsonObject rt = rtIterator.next().getAsJsonObject();
                    TimeShareMessageContent content = GSON.fromJson(rt, TimeShareMessageContent.class);
                    content.setMarket(market);
                    content.setCode(code);
                    sendTimeShareMessage(content);
                }
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("分时推送结果解析失败.", e);
            }
        }
    }

    @Override
    public void onPush_UpdateTicker(FTAPI_Conn client, QotUpdateTicker.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "获取逐笔推送失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("connID=" + client.getConnectID() + "获取逐笔数据失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                Integer market = ftGrpcReturnResult.getS2c().get("security").getAsJsonObject().get("market").getAsInt();
                String code = ftGrpcReturnResult.getS2c().get("security").getAsJsonObject().get("code").getAsString();
                Iterator<JsonElement> tickerIterator = ftGrpcReturnResult.getS2c().getAsJsonArray("tickerList").iterator();
                while (tickerIterator.hasNext()) {
                    JsonObject ticker = tickerIterator.next().getAsJsonObject();
                    RealTimeTickerMessageContent content = GSON.fromJson(ticker, RealTimeTickerMessageContent.class);
                    content.setMarket(market);
                    content.setCode(code);
                    sendRTTickerMessage(content);
                }
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("逐笔推送结果解析失败.", e);
            }
        }
    }

    @Override
    public void onPush_UpdateBroker(FTAPI_Conn client, QotUpdateBroker.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "获取经纪队列推送失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("connID=" + client.getConnectID() + "获取经纪队列数据失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                Integer market = ftGrpcReturnResult.getS2c().get("security").getAsJsonObject().get("market").getAsInt();
                String code = ftGrpcReturnResult.getS2c().get("security").getAsJsonObject().get("code").getAsString();
                if (ftGrpcReturnResult.getS2c().has("brokerAskList")) {
                    Iterator<JsonElement> askIterator = ftGrpcReturnResult.getS2c().getAsJsonArray("brokerAskList").iterator();
                    while (askIterator.hasNext()) {
                        JsonObject ask = askIterator.next().getAsJsonObject();
                        BrokerMessageContent askBrokerMessageContent = GSON.fromJson(ask, BrokerMessageContent.class);
                        askBrokerMessageContent.setMarket(market);
                        askBrokerMessageContent.setCode(code);
                        askBrokerMessageContent.setAskOrBid(1);//1卖,2买
                        sendBrokersMessage(askBrokerMessageContent);
                    }
                }
                if (ftGrpcReturnResult.getS2c().has("brokerBidList")) {
                    Iterator<JsonElement> bidIterator = ftGrpcReturnResult.getS2c().getAsJsonArray("brokerBidList").iterator();
                    while (bidIterator.hasNext()) {
                        JsonObject bid = bidIterator.next().getAsJsonObject();
                        BrokerMessageContent bidMessageContent = GSON.fromJson(bid, BrokerMessageContent.class);
                        bidMessageContent.setMarket(market);
                        bidMessageContent.setCode(code);
                        bidMessageContent.setAskOrBid(2);
                        sendBrokersMessage(bidMessageContent);
                    }
                }
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("经纪队列推送结果解析失败.", e);
            }
        }
    }

    @Override
    public void onReply_Sub(FTAPI_Conn client, int nSerialNo, QotSub.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "订阅失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "订阅失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                SubOrUnSubWsMessage subOrUnSubWsMessage = (SubOrUnSubWsMessage) CacheManager.get(String.valueOf(nSerialNo));
                if (subOrUnSubWsMessage.getUnsub() != null && subOrUnSubWsMessage.getUnsub()) {
                    List<SubDto> toDelList = new ArrayList<>();
                    subOrUnSubWsMessage.getSecurityList().forEach(subscribeSecurity -> {
                        subOrUnSubWsMessage.getSubTypeList().forEach(subType -> {
                            SubDto toDelSub = new SubDto();
                            toDelSub.setSecurityCode(subscribeSecurity.getCode());
                            toDelSub.setSecurityName(subscribeSecurity.getName());
                            toDelSub.setSecurityMarket(subscribeSecurity.getMarket());
                            toDelSub.setSecurityType(subscribeSecurity.getType());
                            toDelSub.setSubType(subType);
                            toDelList.add(toDelSub);
                        });
                    });
                    int unsubRow = subService.cancelSubscribe(toDelList);
                    if (unsubRow > 0) {
                        String notify = "取消订阅成功";
                        LOGGER.info(notify);
                        sendNotifyMessage(notify);
                    }
                } else {
                    List<SubDto> toAddList = new ArrayList<>();
                    subOrUnSubWsMessage.getSecurityList().forEach(subscribeSecurity -> {
                        subOrUnSubWsMessage.getSubTypeList().forEach(subType -> {
                            SubDto toAddSub = new SubDto();
                            toAddSub.setSecurityCode(subscribeSecurity.getCode());
                            toAddSub.setSecurityName(subscribeSecurity.getName());
                            toAddSub.setSecurityMarket(subscribeSecurity.getMarket());
                            toAddSub.setSecurityType(subscribeSecurity.getType());
                            toAddSub.setSubType(subType);
                            toAddList.add(toAddSub);
                        });
                    });
                    //todo 不进入这个方法 会卡死
//                    int subRow = subService.subscribe(toAddList);
//                    if (subRow > 0) {
//                        String notify = "订阅成功";
//                        LOGGER.info(notify);
//                        sendNotifyMessage(notify);
//                    }
                }
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("订阅解结果解析失败.", e);
            }

        }
    }

    @Override
    //todo GetSubInfo
    public void onReply_GetSubInfo(FTAPI_Conn client, int nSerialNo, QotGetSubInfo.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询订阅信息失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询订阅信息失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
//            try {
//                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
//                if (ftGrpcReturnResult.getS2c().has("connSubInfoList")) {
//                    JsonArray connSubInfoList = ftGrpcReturnResult.getS2c().get("connSubInfoList").getAsJsonArray();
//                    Iterator<JsonElement> connSubInfoListIterator = connSubInfoList.iterator();
//                    List<SubDto> subDtoList = new ArrayList<>();
//                    while (connSubInfoListIterator.hasNext()) {
//                        JsonObject perConnSubInfo = connSubInfoListIterator.next().getAsJsonObject();
//                        JsonArray subInfoList = perConnSubInfo.getAsJsonArray("subInfoList");
//                        Iterator<JsonElement> subInfoIterator = subInfoList.iterator();
//                        while (subInfoIterator.hasNext()) {
//                            JsonObject perSubInfo = subInfoIterator.next().getAsJsonObject();
//                            Integer subType = perSubInfo.get("subType").getAsInt();
//                            JsonArray securityList = perSubInfo.getAsJsonArray("securityList");
//                            Iterator<JsonElement> securityIterator = securityList.iterator();
//                            while (securityIterator.hasNext()) {
//                                JsonObject perSecurity = securityIterator.next().getAsJsonObject();
//                                SubDto subDto = new SubDto();
////                                subDto.setUsedQuota(perConnSubInfo.get("usedQuota").getAsInt());
////                                subDto.setIsOwnConn(perConnSubInfo.get("isOwnConnData").getAsBoolean() ? 1 : 0);
//                                subDto.setSecurityMarket(perSecurity.get("market").getAsInt());
//                                subDto.setSecurityCode(perSecurity.get("code").getAsString());
//                                subDto.setSubType(subType);
//                                subDtoList.add(subDto);
//                            }
//                        }
//                    }
//                    List<SubDto> existSubscribeInfo = subDtoMapper.selectList(null);
//                    //差集,新增数据
//                    Collection<SubDto> subtractForInsert = CollectionUtils.subtract(subDtoList, existSubscribeInfo);
//                    if (subtractForInsert.size() > 0) {
//                        int insertRow = subDtoMapper.insertBatch(subtractForInsert);
//                        String notify = "订阅信息表插入条数." + insertRow;
//                        LOGGER.info(notify);
//                        sendNotifyMessage(notify);
//                    }
//                    //差集,删除数据
//                    Collection<SubDto> subtractForDel = CollectionUtils.subtract(existSubscribeInfo, subDtoList);
//                    if (subtractForDel.size() > 0) {
//                        int delRow = subDtoMapper.deleteBatchIds(subtractForDel
//                                .stream().map(SubDto::getId).collect(Collectors.toList()));
//                        String notify = "订阅信息表删除条数." + delRow;
//                        LOGGER.info(notify);
//                        sendNotifyMessage(notify);
//                    }
//                }
//            } catch (InvalidProtocolBufferException e) {
//                LOGGER.error("查询订阅信息解析结果失败.", e);
//            }
        }
    }

    @Override
    //todo GetIpoList
    public void onReply_GetIpoList(FTAPI_Conn client, int nSerialNo, QotGetIpoList.Response rsp) {
//        if (rsp.getRetType() != 0) {
//            String notify = "查询IPO信息失败:" + rsp.getRetMsg();
//            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询IPO信息失败,code:" + rsp.getRetType()));
//            sendNotifyMessage(notify);
//        } else {
//            try {
//                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
//                Iterator<JsonElement> iterator = ftGrpcReturnResult.getS2c().getAsJsonArray("ipoList").iterator();
//                List<IpoHkDto> ipoHkDtos = new ArrayList<>();
//                List<IpoUsDto> ipoUsDtos = new ArrayList<>();
//                List<IpoCnDto> ipoCnDtos = new ArrayList<>();
//                while (iterator.hasNext()) {
//                    JsonElement jsonElement = iterator.next();
//                    JsonObject basic = jsonElement.getAsJsonObject().get("basic").getAsJsonObject();
//                    String name = basic.get("name").getAsString();
//                    Integer market = basic.get("security").getAsJsonObject().get("market").getAsInt();
//                    String code = basic.get("security").getAsJsonObject().get("code").getAsString();
//                    if (jsonElement.getAsJsonObject().has("usExData")) {
//                        //美股IPO
//                        JsonObject usExData = jsonElement.getAsJsonObject().get("usExData").getAsJsonObject();
//                        IpoUsDto ipoUsDto = new IpoUsDto();
//                        ipoUsDto.setName(name);
//                        ipoUsDto.setCode(code);
//                        ipoUsDto.setMarket(market);
//                        if (basic.has("listTime")) {
//                            ipoUsDto.setListTime(LocalDate.parse(basic.get("listTime").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//                        }
//                        ipoUsDto.setIpoPriceMin(usExData.get("ipoPriceMin").getAsDouble());
//                        ipoUsDto.setIpoPriceMax(usExData.get("ipoPriceMax").getAsDouble());
//                        ipoUsDto.setIssueSize(usExData.get("issueSize").getAsLong());
//                        ipoUsDtos.add(ipoUsDto);
//                    } else if (jsonElement.getAsJsonObject().has("cnExData")) {
//                        //A股IPO
//                        JsonObject cnExData = jsonElement.getAsJsonObject().get("cnExData").getAsJsonObject();
//                        IpoCnDto ipoCnDto = new IpoCnDto();
//                        ipoCnDto.setName(name);
//                        ipoCnDto.setCode(code);
//                        ipoCnDto.setMarket(market);
//                        if (basic.has("listTime")) {
//                            ipoCnDto.setListTime(LocalDate.parse(basic.get("listTime").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//                        }
//                        ipoCnDto.setApplyCode(cnExData.get("applyCode").getAsString());
//                        ipoCnDto.setIssueSize(cnExData.get("issueSize").getAsLong());
//                        ipoCnDto.setOnlineIssueSize(cnExData.get("onlineIssueSize").getAsLong());
//                        ipoCnDto.setApplyUpperLimit(cnExData.get("applyUpperLimit").getAsLong());
//                        ipoCnDto.setApplyLimitMarketValue(cnExData.get("applyLimitMarketValue").getAsLong());
//                        ipoCnDto.setIsEstimateIpoPrice(cnExData.get("isEstimateIpoPrice").getAsBoolean() ? 1 : 0);
//                        ipoCnDto.setIpoPrice(cnExData.get("ipoPrice").getAsDouble());
//                        ipoCnDto.setIndustryPeRate(cnExData.get("industryPeRate").getAsDouble());
//                        ipoCnDto.setIsEstimateWinningRatio(cnExData.get("isEstimateWinningRatio").getAsBoolean() ? 1 : 0);
//                        ipoCnDto.setWinningRatio(cnExData.get("winningRatio").getAsDouble());
//                        ipoCnDto.setIssuePeRate(cnExData.get("issuePeRate").getAsDouble());
//                        if (cnExData.has("applyTime")) {
//                            ipoCnDto.setApplyTime(LocalDate.parse(cnExData.get("applyTime").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//                        }
//                        if (cnExData.has("winningTime")) {
//                            ipoCnDto.setWinningTime(LocalDate.parse(cnExData.get("winningTime").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//                        }
//                        ipoCnDto.setIsHasWon(cnExData.get("isHasWon").getAsBoolean() ? 1 : 0);
//                        if (cnExData.has("winningNumData")) {
//                            List<IpoCnExWinningDto> ipoCnExWinningDtos = new ArrayList<>();
//                            Iterator<JsonElement> winningNumDatas = cnExData.get("winningNumData").getAsJsonArray().iterator();
//                            while (winningNumDatas.hasNext()) {
//                                JsonElement winningNumData = winningNumDatas.next();
//                                IpoCnExWinningDto ipoCnExWinningDto = new IpoCnExWinningDto();
//                                ipoCnExWinningDto.setWinningInfo(winningNumData.getAsJsonObject().get("winningInfo").getAsString());
//                                ipoCnExWinningDto.setWinningName(winningNumData.getAsJsonObject().get("winningName").getAsString());
//                                ipoCnExWinningDtos.add(ipoCnExWinningDto);
//                            }
//                            ipoCnDto.setCnExWinningDtos(ipoCnExWinningDtos);
//                        }
//                        ipoCnDtos.add(ipoCnDto);
//                    } else if (jsonElement.getAsJsonObject().has("hkExData")) {
//                        //港股IPO
//                        JsonObject hkExData = jsonElement.getAsJsonObject().get("hkExData").getAsJsonObject();
//                        IpoHkDto ipoHkDto = new IpoHkDto();
//                        ipoHkDto.setCode(code);
//                        ipoHkDto.setName(name);
//                        ipoHkDto.setMarket(market);
//                        if (basic.has("listTime")) {
//                            ipoHkDto.setListTime(LocalDate.parse(basic.get("listTime").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//                        }
//                        ipoHkDto.setLotSize(hkExData.get("lotSize").getAsInt());
//                        ipoHkDto.setIpoPriceMin(hkExData.get("ipoPriceMin").getAsDouble());
//                        ipoHkDto.setIpoPriceMax(hkExData.get("ipoPriceMax").getAsDouble());
//                        ipoHkDto.setListPrice(hkExData.get("listPrice").getAsDouble());
//                        ipoHkDto.setEntrancePrice(hkExData.get("entrancePrice").getAsDouble());
//                        ipoHkDto.setIsSubscribeStatus(hkExData.get("isSubscribeStatus").getAsBoolean() ? 1 : 0);
//                        if (hkExData.has("applyEndTime")) {
//                            ipoHkDto.setApplyEndtime(LocalDate.parse(hkExData.get("applyEndTime").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//                        }
//                        ipoHkDtos.add(ipoHkDto);
//                    }
//                }
//                if (ipoHkDtos.size() > 0) {
//                    List<IpoHkDto> oldIpoHks = ipoHkMapper.selectList(null);
//                    ipoHkDtos.removeIf(oldIpoHks::contains);
//                    if (ipoHkDtos.size() > 0) {
//                        int insertRow = ipoHkMapper.insertBatch(ipoHkDtos);
//                        LOGGER.info("港股IPO信息插入条数:" + insertRow);
//                    }
//                }
//                if (ipoUsDtos.size() > 0) {
//                    List<IpoUsDto> oldIpoUss = ipoUsMapper.selectList(null);
//                    ipoUsDtos.removeIf(oldIpoUss::contains);
//                    if (ipoUsDtos.size() > 0) {
//                        int insertRow = ipoUsMapper.insertBatch(ipoUsDtos);
//                        LOGGER.info("美股IPO信息插入条数:" + insertRow);
//                    }
//                }
//                if (ipoCnDtos.size() > 0) {
//                    List<IpoCnDto> oldIpoCns = ipoCnMapper.selectList(null);
//                    ipoCnDtos.removeIf(oldIpoCns::contains);
//                    if (ipoCnDtos.size() > 0) {
//                        int insertRow = ipoCnMapper.insertBatch(ipoCnDtos);
//                        LOGGER.info("A股IPO信息插入条数:" + insertRow);
//                        List<IpoCnDto> hasWinningIpoCns = ipoCnDtos.stream()
//                                .filter(ipoCnDto -> ipoCnDto.getCnExWinningDtos() != null)
//                                .collect(Collectors.toList());
//                        hasWinningIpoCns.forEach(hasWinningIpoDto -> {
//                            hasWinningIpoDto.getCnExWinningDtos().forEach(ipoCnExWinningDto -> {
//                                ipoCnExWinningDto.setIpoCnId(hasWinningIpoDto.getId());
//                            });
//                            int insertWinningRow = ipoCnExWinningMapper.insertBatch(hasWinningIpoDto.getCnExWinningDtos());
//                            LOGGER.info("A股IPO中签信息插入条数:" + insertWinningRow);
//                        });
//                    }
//                }
//            } catch (InvalidProtocolBufferException e) {
//                LOGGER.error("查询IPO信息解析结果失败!", e);
//            }
//
//        }
    }

    @Override
    public void onReply_GetOwnerPlate(FTAPI_Conn client, int nSerialNo, QotGetOwnerPlate.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询股票板块信息失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询股票板块信息失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                List<StockOwnerPlateContent> stockOwnerPlateContents = GSON.fromJson(ftGrpcReturnResult.getS2c().getAsJsonArray("ownerPlateList"), new TypeToken<List<StockOwnerPlateContent>>() {
                }.getType());
                int totalInsert = 0;
                for (StockOwnerPlateContent stockOwnerPlateContent : stockOwnerPlateContents) {
                    StockDto stockDto = new StockDto();
                    stockDto.setMarket(stockOwnerPlateContent.getSecurity().getMarket());
                    stockDto.setCode(stockOwnerPlateContent.getSecurity().getCode());
                    List<PlateDto> toInsertPlates = stockOwnerPlateContent.getPlateInfoList()
                            .stream().map(plateInfoContent -> {
                                PlateDto plateDto = new PlateDto();
                                plateDto.setName(plateInfoContent.getName());
                                plateDto.setCode(plateInfoContent.getPlate().getCode());
                                plateDto.setMarket(plateInfoContent.getPlate().getMarket());
                                plateDto.setPlateType(plateInfoContent.getPlateType());
                                return plateDto;
                            }).collect(Collectors.toList());
                    totalInsert += plateService.insertBatch(stockDto, toInsertPlates);
                }
                String notify = "查询股票对应板块数据,插入条数:" + totalInsert;
                LOGGER.info(notify);
                sendNotifyMessage(notify);
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("查询股票板塊信息解析结果失败!", e);
            }
        }
    }

    @Override
    public void onReply_GetStaticInfo(FTAPI_Conn client, int nSerialNo, QotGetStaticInfo.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询静态信息失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询静态信息失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                List<StockContent> stockContents = GSON.fromJson(ftGrpcReturnResult.getS2c().getAsJsonArray("staticInfoList"), new TypeToken<List<StockContent>>() {
                }.getType());
                List<StockDto> toInsertStocks = stockContent2StockDto(stockContents);
                int insertRow = stockService.insertBatch(toInsertStocks);
                String notify = "查询静态标的数据,插入条数:" + insertRow;
                LOGGER.info(notify);
                sendNotifyMessage(notify);
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("查询静态信息解析结果失败!", e);
            }
        }
    }

    @Override
    public void onReply_GetPlateSecurity(FTAPI_Conn client, int nSerialNo, QotGetPlateSecurity.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询板块下股票信息失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询股票信息失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                List<StockContent> stockContents = GSON.fromJson(ftGrpcReturnResult.getS2c().getAsJsonArray("staticInfoList"), new TypeToken<List<StockContent>>() {
                }.getType());
                CommonSecurity plateItem = (CommonSecurity) CacheManager.get(String.valueOf(nSerialNo));
                List<StockDto> toInsertStocks = stockContent2StockDto(stockContents);
                PlateDto plateDto = new PlateDto(plateItem.getMarket(), plateItem.getCode());
                //todo 用板块同步股票数据会无法建立关联关系
                int insertRow = stockService.insertBatch(toInsertStocks);
                String notify = "查询板块下股票信息,插入条数:" + insertRow;
                LOGGER.info(notify);
                sendNotifyMessage(notify);
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("查询板块下股票解析结果失败!", e);
            }
        }
    }

    private List<StockDto> stockContent2StockDto(List<StockContent> stockContents) {
        return stockContents.stream().map(vo -> {
            StockDto dto = new StockDto();
            dto.setName(vo.getBasic().getName());
            dto.setCode(vo.getBasic().getSecurity().getCode());
            dto.setLotSize(vo.getBasic().getLotSize());
            dto.setStockType(vo.getBasic().getSecType());
            dto.setMarket(vo.getBasic().getSecurity().getMarket());
            dto.setListingDate(LocalDate.parse(vo.getBasic().getListTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            dto.setDelisting(vo.getBasic().getDelisting() ? 1 : 0);
            dto.setExchangeType(vo.getBasic().getExchType());
            dto.setStockId(vo.getBasic().getId());
            if (Objects.nonNull(vo.getWarrantExData())) {
                dto.setStockChildType(vo.getWarrantExData().getType());
                dto.setStockOwner(vo.getWarrantExData().getOwner().getCode());
            }
            if (Objects.nonNull(vo.getOptionExData())) {
                dto.setOptionType(vo.getOptionExData().getType());
                dto.setStrikeTime(vo.getOptionExData().getStrikeTime());
                dto.setStrikePrice(vo.getOptionExData().getStrikePrice());
                dto.setOptionMarket(vo.getOptionExData().getMarket());
                dto.setSuspension(vo.getOptionExData().getSuspend());
                dto.setIndexOptionType(vo.getOptionExData().getIndexOptionType());
            }
            if (Objects.nonNull(vo.getFutureExData())) {
                dto.setMainContract(vo.getFutureExData().getMainContract() ? 1 : 0);
                dto.setLastTradeTime(LocalDate.parse(vo.getFutureExData().getLastTradeTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void onReply_GetPlateSet(FTAPI_Conn client, int nSerialNo, QotGetPlateSet.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询板块信息失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询板块信息失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                List<PlateInfoContent> plateInfos = GSON.fromJson(ftGrpcReturnResult.getS2c().getAsJsonArray("plateInfoList"), new TypeToken<List<PlateInfoContent>>() {
                }.getType());
                List<PlateDto> toInsertPlates = plateInfos.stream().map(plateVo -> {
                    PlateDto plateDto = new PlateDto();
                    plateDto.setName(plateVo.getName());
                    plateDto.setCode(plateVo.getPlate().getCode());
                    plateDto.setMarket(plateVo.getPlate().getMarket());
                    return plateDto;
                }).collect(Collectors.toList());
                int insertRow = plateService.insertBatch(toInsertPlates);
                String str = "同步板块数据,插入条数:" + insertRow;
                LOGGER.info(str);
                sendNotifyMessage(str);
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("查询板块信息解析结果失败!", e);
            }
        }

    }

    public void syncSnapshotData(List<CommonSecurity> securities) {
        for (CommonSecurity security : securities) {
            QotGetSecuritySnapshot.C2S c2S = QotGetSecuritySnapshot.C2S.newBuilder()
                    .addSecurityList(QotCommon.Security.newBuilder()
                            .setMarket(security.getMarket())
                            .setCode(security.getCode())
                            .build())
                    .build();
            QotGetSecuritySnapshot.Request request = QotGetSecuritySnapshot.Request
                    .newBuilder().setC2S(c2S).build();
            int seqNo = qot.getSecuritySnapshot(request);
            LOGGER.info("{}-{}请求快照数据.seqNo={}", MarketType.getNameByCode(security.getMarket()), security.getCode(), seqNo);
        }
    }

    @Override
    public void onReply_GetSecuritySnapshot(FTAPI_Conn client, int nSerialNo, QotGetSecuritySnapshot.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询快照数据失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询板块信息失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                List<SnapshotContent> snapshotContents = GSON.fromJson(ftGrpcReturnResult.getS2c().getAsJsonArray("snapshotList"), new TypeToken<List<SnapshotContent>>() {
                }.getType());
                Snapshot snapshot = getSnapshot(snapshotContents);
                int insertRow = snapshotService.insertBatch(snapshot);
                String str = "同步快照数据,插入条数:" + insertRow;
                LOGGER.info(str);
                sendNotifyMessage(str);
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("查询快照数据解析结果失败!", e);
            }
        }
    }

    private Snapshot getSnapshot(List<SnapshotContent> snapshotContents) {
        Snapshot snapshot = new Snapshot();
        List<SnapshotBaseDto> baseDtoList = new ArrayList<>();
        List<SnapshotEquityExDto> equityExDtoList = new ArrayList<>();
        List<SnapshotOptionExDto> optionExDtoList = new ArrayList<>();
        List<SnapshotFutureExDto> futureExDtoList = new ArrayList<>();
        List<SnapshotIndexExDto> indexExDtoList = new ArrayList<>();
        List<SnapshotPlateExDto> plateExDtoList = new ArrayList<>();
        List<SnapshotTrustExDto> trustExDtoList = new ArrayList<>();
        List<SnapshotWarrantExDto> warrantExDtoList = new ArrayList<>();
        for (SnapshotContent snapshotContent : snapshotContents) {
            SnapshotBaseDto baseDto = getSnapshotBase(snapshotContent);
            baseDtoList.add(baseDto);
            if (Objects.nonNull(snapshotContent.getEquityExData())) {
                SnapshotEquityExDto equityExDto = getSnapshotEquityEx(snapshotContent);
                equityExDtoList.add(equityExDto);
            }
            if (Objects.nonNull(snapshotContent.getFutureExData())) {
                SnapshotFutureExDto futureExDto = getSnapshotFutureEx(snapshotContent);
                futureExDtoList.add(futureExDto);
            }
            if (Objects.nonNull(snapshotContent.getIndexExData())) {
                SnapshotIndexExDto indexExDto = getSnapshotIndexEx(snapshotContent);
                indexExDtoList.add(indexExDto);
            }
            if (Objects.nonNull(snapshotContent.getOptionExData())) {
                SnapshotOptionExDto optionExDto = getSnapshotOptionEx(snapshotContent);
                optionExDtoList.add(optionExDto);
            }
            if (Objects.nonNull(snapshotContent.getPlateExData())) {
                SnapshotPlateExDto plateExDto = getSnapshotPlateEx(snapshotContent);
                plateExDtoList.add(plateExDto);
            }
            if (Objects.nonNull(snapshotContent.getTrustExData())) {
                SnapshotTrustExDto trustExDto = getSnapshotTrustEx(snapshotContent);
                trustExDtoList.add(trustExDto);
            }
            if (Objects.nonNull(snapshotContent.getWarrantExData())) {
                SnapshotWarrantExDto warrantExDto = getSnapshotWarrantEx(snapshotContent);
                warrantExDtoList.add(warrantExDto);
            }
        }
        snapshot.setBaseDtoList(baseDtoList);
        snapshot.setEquityExDtoList(equityExDtoList);
        snapshot.setOptionExDtoList(optionExDtoList);
        snapshot.setFutureExDtoList(futureExDtoList);
        snapshot.setIndexExDtoList(indexExDtoList);
        snapshot.setPlateExDtoList(plateExDtoList);
        snapshot.setTrustExDtoList(trustExDtoList);
        snapshot.setWarrantExDtoList(warrantExDtoList);
        return snapshot;
    }

    private SnapshotWarrantExDto getSnapshotWarrantEx(SnapshotContent snapshotContent) {
        SnapshotWarrantExDto warrantExDto = new SnapshotWarrantExDto();
        warrantExDto.setOwnerMarket(snapshotContent.getWarrantExData().getOwner().getMarket());
        warrantExDto.setOwnerCode(snapshotContent.getWarrantExData().getOwner().getCode());
        warrantExDto.setConversionRate(snapshotContent.getWarrantExData().getConversionRate());
        warrantExDto.setWarrantType(snapshotContent.getWarrantExData().getWarrantType());
        warrantExDto.setStrikePrice(snapshotContent.getWarrantExData().getStrikePrice());
        warrantExDto.setMaturityTime(snapshotContent.getWarrantExData().getMaturityTime());
        warrantExDto.setEndTradeTime(snapshotContent.getWarrantExData().getEndTradeTime());
        warrantExDto.setRecoveryPrice(snapshotContent.getWarrantExData().getRecoveryPrice());
        warrantExDto.setStreetVolumn(snapshotContent.getWarrantExData().getStreetVolumn());
        warrantExDto.setIssueVolumn(snapshotContent.getWarrantExData().getIssueVolumn());
        warrantExDto.setStreetRate(snapshotContent.getWarrantExData().getStreetRate());
        warrantExDto.setDelta(snapshotContent.getWarrantExData().getDelta());
        warrantExDto.setImpliedVolatility(snapshotContent.getWarrantExData().getImpliedVolatility());
        warrantExDto.setPremium(snapshotContent.getWarrantExData().getPremium());
        warrantExDto.setMaturityTimestamp(LocalDateTime.parse(snapshotContent.getWarrantExData().getMaturityTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        warrantExDto.setEndTradeTimestamp(LocalDateTime.parse(snapshotContent.getWarrantExData().getEndTradeTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        warrantExDto.setLeverage(snapshotContent.getWarrantExData().getLeverage());
        warrantExDto.setIpop(snapshotContent.getWarrantExData().getIpop());
        warrantExDto.setBreakEventPoint(snapshotContent.getWarrantExData().getBreakEvenPoint());
        warrantExDto.setConversionPrice(snapshotContent.getWarrantExData().getConversionPrice());
        warrantExDto.setPriceRecoveryRatio(snapshotContent.getWarrantExData().getPriceRecoveryRatio());
        warrantExDto.setScore(snapshotContent.getWarrantExData().getScore());
        warrantExDto.setUpperStrikePrice(snapshotContent.getWarrantExData().getUpperStrikePrice());
        warrantExDto.setLowerStrikePrice(snapshotContent.getWarrantExData().getLowerStrikePrice());
        warrantExDto.setInlinePriceStatus(snapshotContent.getWarrantExData().getInLinePriceStatus());
        warrantExDto.setIssuerCode(snapshotContent.getWarrantExData().getIssuerCode());
        return warrantExDto;
    }

    private SnapshotTrustExDto getSnapshotTrustEx(SnapshotContent snapshotContent) {
        SnapshotTrustExDto trustExDto = new SnapshotTrustExDto();
        trustExDto.setMarket(snapshotContent.getBasic().getSecurity().getMarket());
        trustExDto.setCode(snapshotContent.getBasic().getSecurity().getCode());
        trustExDto.setDividendYield(snapshotContent.getTrustExData().getDividendYield());
        trustExDto.setAum(snapshotContent.getTrustExData().getAum());
        trustExDto.setOutstandingUnits(snapshotContent.getTrustExData().getOutstandingUnits());
        trustExDto.setNetAssetValue(snapshotContent.getTrustExData().getNetAssetValue());
        trustExDto.setPremium(snapshotContent.getTrustExData().getPremium());
        trustExDto.setAssetClass(snapshotContent.getTrustExData().getAssetClass());
        return trustExDto;
    }

    private SnapshotPlateExDto getSnapshotPlateEx(SnapshotContent snapshotContent) {
        SnapshotPlateExDto plateExDto = new SnapshotPlateExDto();
        plateExDto.setMarket(snapshotContent.getBasic().getSecurity().getMarket());
        plateExDto.setCode(snapshotContent.getBasic().getSecurity().getCode());
        plateExDto.setRaiseCount(snapshotContent.getPlateExData().getRaiseCount());
        plateExDto.setFallCount(snapshotContent.getPlateExData().getFallCount());
        plateExDto.setEqualCount(snapshotContent.getPlateExData().getEqualCount());
        return plateExDto;
    }

    private SnapshotOptionExDto getSnapshotOptionEx(SnapshotContent snapshotContent) {
        SnapshotOptionExDto optionExDto = new SnapshotOptionExDto();
        optionExDto.setOwnerMarket(snapshotContent.getOptionExData().getOwner().getMarket());
        optionExDto.setOwnerCode(snapshotContent.getOptionExData().getOwner().getCode());
        optionExDto.setOptionType(snapshotContent.getOptionExData().getType());
        optionExDto.setStrikeTime(snapshotContent.getOptionExData().getStrikeTime());
        optionExDto.setStrikePrice(snapshotContent.getOptionExData().getStrikePrice());
        optionExDto.setContractSize(snapshotContent.getOptionExData().getContractSize());
        optionExDto.setContractSizeFloat(snapshotContent.getOptionExData().getContractSizeFloat());
        optionExDto.setOpenInterest(snapshotContent.getOptionExData().getOpenInterest());
        optionExDto.setImpliedVolatility(snapshotContent.getOptionExData().getImpliedVolatility());
        optionExDto.setPremium(snapshotContent.getOptionExData().getPremium());
        optionExDto.setDelta(snapshotContent.getOptionExData().getDelta());
        optionExDto.setGamma(snapshotContent.getOptionExData().getGamma());
        optionExDto.setVega(snapshotContent.getOptionExData().getVega());
        optionExDto.setTheta(snapshotContent.getOptionExData().getTheta());
        optionExDto.setRho(snapshotContent.getOptionExData().getRho());
        optionExDto.setIndexOptionType(snapshotContent.getOptionExData().getIndexOptionType());
        optionExDto.setNetOpenInterest(snapshotContent.getOptionExData().getNetOpenInterest());
        optionExDto.setExpiryDateDistance(snapshotContent.getOptionExData().getExpiryDateDistance());
        optionExDto.setContractNominalValue(snapshotContent.getOptionExData().getContractNominalValue());
        optionExDto.setOwnerLotMultiplier(snapshotContent.getOptionExData().getOwnerLotMultiplier());
        optionExDto.setOptionAreaType(snapshotContent.getOptionExData().getOptionAreaType());
        optionExDto.setContractMultiplier(snapshotContent.getOptionExData().getContractMultiplier());
        return optionExDto;
    }

    private SnapshotIndexExDto getSnapshotIndexEx(SnapshotContent snapshotContent) {
        SnapshotIndexExDto indexExDto = new SnapshotIndexExDto();
        indexExDto.setMarket(snapshotContent.getBasic().getSecurity().getMarket());
        indexExDto.setCode(snapshotContent.getBasic().getSecurity().getCode());
        indexExDto.setRaiseCount(snapshotContent.getIndexExData().getRaiseCount());
        indexExDto.setFallCount(snapshotContent.getIndexExData().getFallCount());
        indexExDto.setEqualCount(snapshotContent.getIndexExData().getEqualCount());
        return indexExDto;
    }

    private SnapshotFutureExDto getSnapshotFutureEx(SnapshotContent snapshotContent) {
        SnapshotFutureExDto futureExDto = new SnapshotFutureExDto();
        futureExDto.setMarket(snapshotContent.getBasic().getSecurity().getMarket());
        futureExDto.setCode(snapshotContent.getBasic().getSecurity().getCode());
        futureExDto.setLastSettlePrice(snapshotContent.getFutureExData().getLastSettlePrice());
        futureExDto.setPosition(snapshotContent.getFutureExData().getPosition());
        futureExDto.setPositionChange(snapshotContent.getFutureExData().getPositionChange());
        futureExDto.setLastTradeTime(LocalDate.parse(snapshotContent.getFutureExData().getLastTradeTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        futureExDto.setIsMainContract(snapshotContent.getFutureExData().getMainContract());
        return futureExDto;
    }

    private SnapshotEquityExDto getSnapshotEquityEx(SnapshotContent snapshotContent) {
        SnapshotEquityExDto equityExDto = new SnapshotEquityExDto();
        equityExDto.setMarket(snapshotContent.getBasic().getSecurity().getMarket());
        equityExDto.setCode(snapshotContent.getBasic().getSecurity().getCode());
        equityExDto.setIssuedShares(snapshotContent.getEquityExData().getIssuedShares());
        equityExDto.setIssuedMarketVal(snapshotContent.getEquityExData().getIssuedMarketVal());
        equityExDto.setNetAsset(snapshotContent.getEquityExData().getNetAsset());
        equityExDto.setNetProfit(snapshotContent.getEquityExData().getNetProfit());
        equityExDto.setEarningsPerShare(snapshotContent.getEquityExData().getEarningsPershare());
        equityExDto.setOutstandingShares(snapshotContent.getEquityExData().getOutstandingShares());
        equityExDto.setOutstandingMarketVal(snapshotContent.getEquityExData().getOutstandingMarketVal());
        equityExDto.setNetAssetPerShare(snapshotContent.getEquityExData().getNetAssetPershare());
        equityExDto.setEyRate(snapshotContent.getEquityExData().getEyRate());
        equityExDto.setPeRate(snapshotContent.getEquityExData().getPeRate());
        equityExDto.setPbRate(snapshotContent.getEquityExData().getPbRate());
        equityExDto.setPeTtmRate(snapshotContent.getEquityExData().getPeTTMRate());
        equityExDto.setDividendTtm(snapshotContent.getEquityExData().getDividendTTM());
        equityExDto.setDividendRatioTtm(snapshotContent.getEquityExData().getDividendRatioTTM());
        equityExDto.setDividendLfy(snapshotContent.getEquityExData().getDividendLFY());
        equityExDto.setDividendLfyRatio(snapshotContent.getEquityExData().getDividendLFYRatio());
        return equityExDto;
    }

    private SnapshotBaseDto getSnapshotBase(SnapshotContent snapshotContent) {
        SnapshotBaseDto baseDto = new SnapshotBaseDto();
        baseDto.setMarket(snapshotContent.getBasic().getSecurity().getMarket());
        baseDto.setCode(snapshotContent.getBasic().getSecurity().getCode());
        baseDto.setName(snapshotContent.getBasic().getName());
        baseDto.setType(snapshotContent.getBasic().getType());
        baseDto.setIsSuspend(snapshotContent.getBasic().getSuspend());
        baseDto.setListTime(LocalDate.parse(snapshotContent.getBasic().getListTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        baseDto.setLotSize(snapshotContent.getBasic().getLotSize());
        baseDto.setPriceSpread(snapshotContent.getBasic().getPriceSpread());
        baseDto.setUpdateTime(LocalDateTime.parse(snapshotContent.getBasic().getUpdateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        baseDto.setHighPrice(snapshotContent.getBasic().getHighPrice());
        baseDto.setOpenPrice(snapshotContent.getBasic().getOpenPrice());
        baseDto.setLowPrice(snapshotContent.getBasic().getLowPrice());
        baseDto.setLastClosePrice(snapshotContent.getBasic().getLastClosePrice());
        baseDto.setCurPrice(snapshotContent.getBasic().getCurPrice());
        baseDto.setVolume(snapshotContent.getBasic().getVolume());
        baseDto.setTurnover(snapshotContent.getBasic().getTurnover());
        baseDto.setTurnoverRate(snapshotContent.getBasic().getTurnoverRate());
        baseDto.setAskPrice(snapshotContent.getBasic().getAskPrice());
        baseDto.setBidPrice(snapshotContent.getBasic().getBidPrice());
        baseDto.setAskVol(snapshotContent.getBasic().getAskVol());
        baseDto.setBidVol(snapshotContent.getBasic().getBidVol());
        baseDto.setAmplitude(snapshotContent.getBasic().getAmplitude());
        baseDto.setAvgPrice(snapshotContent.getBasic().getAvgPrice());
        baseDto.setBidAskRatio(snapshotContent.getBasic().getBidAskRatio());
        baseDto.setVolumeRatio(snapshotContent.getBasic().getVolumeRatio());
        baseDto.setHighest52WeeksPrice(snapshotContent.getBasic().getHighest52WeeksPrice());
        baseDto.setLowest52WeeksPrice(snapshotContent.getBasic().getLowest52WeeksPrice());
        baseDto.setHighestHistoryPrice(snapshotContent.getBasic().getHighestHistoryPrice());
        baseDto.setLowestHistoryPrice(snapshotContent.getBasic().getLowestHistoryPrice());
        if (Objects.nonNull(snapshotContent.getBasic().getPreMarket())) {
            baseDto.setPrePrice(snapshotContent.getBasic().getPreMarket().getPrice());
            baseDto.setPreHighPrice(snapshotContent.getBasic().getPreMarket().getHighPrice());
            baseDto.setPreLowPrice(snapshotContent.getBasic().getPreMarket().getLowPrice());
            baseDto.setPreVolume(snapshotContent.getBasic().getPreMarket().getVolume());
            baseDto.setPreTurnover(snapshotContent.getBasic().getPreMarket().getTurnover());
            baseDto.setPreChangeVal(snapshotContent.getBasic().getPreMarket().getChangeVal());
            baseDto.setPreChangeRate(snapshotContent.getBasic().getPreMarket().getChangeRate());
            baseDto.setPreAmplitude(snapshotContent.getBasic().getPreMarket().getAmplitude());
        }
        if (Objects.nonNull(snapshotContent.getBasic().getAfterMarket())) {
            baseDto.setAfterPrice(snapshotContent.getBasic().getAfterMarket().getPrice());
            baseDto.setAfterHighPrice(snapshotContent.getBasic().getAfterMarket().getHighPrice());
            baseDto.setAfterLowPrice(snapshotContent.getBasic().getAfterMarket().getLowPrice());
            baseDto.setAfterVolume(snapshotContent.getBasic().getAfterMarket().getVolume());
            baseDto.setAfterTurnover(snapshotContent.getBasic().getAfterMarket().getTurnover());
            baseDto.setAfterChangeVal(snapshotContent.getBasic().getAfterMarket().getChangeVal());
            baseDto.setAfterChangeRate(snapshotContent.getBasic().getAfterMarket().getChangeRate());
            baseDto.setAfterAmplitude(snapshotContent.getBasic().getAfterMarket().getAmplitude());
        }
        baseDto.setSecStatus(snapshotContent.getBasic().getSecStatus());
        baseDto.setClosePrice5Minute(snapshotContent.getBasic().getClosePrice5Minute());
        return baseDto;
    }
}
