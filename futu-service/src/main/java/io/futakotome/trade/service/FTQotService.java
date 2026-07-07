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
import io.futakotome.trade.domain.code.*;
import io.futakotome.trade.dto.PlateDto;
import io.futakotome.trade.dto.StockDto;
import io.futakotome.trade.dto.SubDto;
import io.futakotome.trade.dto.message.*;
import io.futakotome.trade.dto.ws.*;
import io.futakotome.trade.event.*;
import io.futakotome.trade.utils.CacheManager;
import io.futakotome.trade.utils.RequestCount;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
    private final ApplicationEventPublisher eventPublisher;

    private final PlateDtoService plateService;

    private final SubDtoService subService;
    private final TradeDateDtoService tradeDateService;
    private final FutuConfig futuConfig;
    private final QuantxFutuWsService quantxFutuWsService;
    private final KLineService kLineService;
    private static final String clientID = "javaclient";

    private static final FTAPI_Conn_Qot qot = new FTAPI_Conn_Qot();

    public FTQotService(ApplicationEventPublisher eventPublisher, PlateDtoService plateService,
                        SubDtoService subService, TradeDateDtoService tradeDateService, FutuConfig futuConfig,
                        QuantxFutuWsService quantxFutuWsService, KLineService kLineService) {
        this.eventPublisher = eventPublisher;
        qot.setClientInfo(clientID, 1);
        qot.setConnSpi(this);
        qot.setQotSpi(this);
        this.plateService = plateService;
        this.subService = subService;
        this.tradeDateService = tradeDateService;
        this.kLineService = kLineService;

        this.futuConfig = futuConfig;
        this.quantxFutuWsService = quantxFutuWsService;
    }

    public void syncStockInPlate(CommonSecurity plate) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setMarket(plate.getMarket())
                .setCode(plate.getCode())
                .build();
        QotGetPlateSecurity.C2S c2s = QotGetPlateSecurity.C2S.newBuilder()
                .setPlate(sec)
                .build();
        QotGetPlateSecurity.Request req = QotGetPlateSecurity.Request.newBuilder()
                .setC2S(c2s)
                .build();
        int seqNo = qot.getPlateSecurity(req);
        CacheManager.put(String.valueOf(seqNo), plate);
        LOGGER.info("{}-{}:请求板块下股票数据,seq={}", MarketType.getNameByCode(plate.getMarket()), plate.getCode(), seqNo);
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
            CommonSecurity commonSecurity = new CommonSecurity(market, null);
            CacheManager.put(String.valueOf(seqNo), commonSecurity);
            LOGGER.info("{}:请求板块数据,seq={}", MarketType.getName(market), seqNo);
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
        CacheManager.put(String.valueOf(seqNo), new CommonStaticInfo(market, stockType));
        LOGGER.info("{}-{}:请求静态数据,seq={}", MarketType.getName(market), StockType.getNameByCode(stockType), seqNo);
    }

    public void syncStockOwnerPlateInfo(List<CommonSecurity> securities) {
        QotGetOwnerPlate.C2S.Builder c2SBuilder = QotGetOwnerPlate.C2S.newBuilder();
        List<QotCommon.Security> securitiesToReq = new ArrayList<>();
        for (CommonSecurity security : securities) {
            QotCommon.Security sec = QotCommon.Security.newBuilder()
                    .setMarket(security.getMarket())
                    .setCode(security.getCode())
                    .build();
            securitiesToReq.add(sec);
        }
        QotGetOwnerPlate.Request request = QotGetOwnerPlate.Request.newBuilder()
                .setC2S(c2SBuilder.addAllSecurityList(securitiesToReq).build())
                .build();
        int seqNo = qot.getOwnerPlate(request);
        LOGGER.info("请求股票所属板块数据,seq={}", seqNo);
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

    /**
     * @param periodType 0-实时 1-日 2-周 3-月
     * @param beginTime  yyyy-MM-dd
     * @param endTime    yyyy-MM-dd
     */
    public void syncCapitalFlow(Integer market, String code, Integer periodType, String beginTime, String endTime) {
        QotCommon.Security security = QotCommon.Security.newBuilder()
                .setMarket(market)
                .setCode(code)
                .build();
        QotGetCapitalFlow.C2S.Builder builder = QotGetCapitalFlow.C2S.newBuilder();
        builder.setSecurity(security);
        builder.setPeriodType(periodType);
        if (!periodType.equals(0)) {
            //非实时需要开始、结束时间
            builder.setBeginTime(beginTime);
            builder.setEndTime(endTime);
        }
        QotGetCapitalFlow.Request request = QotGetCapitalFlow.Request.newBuilder()
                .setC2S(builder.build())
                .build();
        int seqNo = qot.getCapitalFlow(request);
        CommonSecurity commonSecurity = new CommonSecurity(market, code);
        CacheManager.put(String.valueOf(seqNo), commonSecurity);
        LOGGER.info("{}-{}请求资金流向.seqNo={}", MarketType.getNameByCode(market), code, seqNo);
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

    public void sendUserGroupRequest() {
        QotGetUserSecurityGroup.C2S c2s = QotGetUserSecurityGroup.C2S.newBuilder()
                .setGroupType(QotGetUserSecurityGroup.GroupType.GroupType_All_VALUE)
                .build();
        QotGetUserSecurityGroup.Request request = QotGetUserSecurityGroup.Request.newBuilder()
                .setC2S(c2s)
                .build();
        int seqNo = qot.getUserSecurityGroup(request);
        LOGGER.info("请求自选股分组.seq={}", seqNo);
    }

    public void sendUserSecurityRequest(UserSecurityWsMessage request) {
        QotGetUserSecurity.C2S c2S = QotGetUserSecurity.C2S.newBuilder()
                .setGroupName(request.getGroupName())
                .build();
        QotGetUserSecurity.Request req = QotGetUserSecurity.Request.newBuilder()
                .setC2S(c2S)
                .build();
        int seqNo = qot.getUserSecurity(req);
        LOGGER.info("请求自选股列表.seq={}", seqNo);
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
    public void onReply_GetUserSecurity(FTAPI_Conn client, int nSerialNo, QotGetUserSecurity.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "获取自选股列表失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("connID=" + client.getConnectID() + "获取自选股列表失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                if (Objects.nonNull(ftGrpcReturnResult.getS2c().get("staticInfoList"))) {
                    List<StockContent> staticInfoList = GSON.fromJson(ftGrpcReturnResult.getS2c().get("staticInfoList").getAsJsonArray(), new TypeToken<List<StockContent>>() {
                    }.getType());
                    UserSecurityWsMessage message = new UserSecurityWsMessage();
                    message.setStocks(staticInfoList);
                    quantxFutuWsService.sendUserSecurity(message);
                } else {
                    UserSecurityWsMessage message = new UserSecurityWsMessage();
                    quantxFutuWsService.sendUserSecurity(message);
                }
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("解析自选股分组结果失败.", e);
            }
        }
    }

    @Override
    public void onReply_GetUserSecurityGroup(FTAPI_Conn client, int nSerialNo, QotGetUserSecurityGroup.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "获取自选股分组失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("connID=" + client.getConnectID() + "获取自选股分组失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                List<GroupData> groupDataList = GSON.fromJson(ftGrpcReturnResult.getS2c().get("groupList").getAsJsonArray(), new TypeToken<List<GroupData>>() {
                }.getType());
                UserGroupWsMessage message = new UserGroupWsMessage();
                message.setGroupDataList(groupDataList);
                quantxFutuWsService.sendUserGroup(message);
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("解析自选股分组结果失败.", e);
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
                logFTResult("查询历史K线", ftGrpcReturnResult);
                Iterator<JsonElement> klListIterator = ftGrpcReturnResult.getS2c().getAsJsonArray("klList").iterator();
                if (ftGrpcReturnResult.getS2c().has("nextReqKey")) {
                    sendNotifyMessage("market:" + market + ",code:" + code + ",klType:" + klType + ",rehabType" + rehabType + "需要分页,参数为:" + ftGrpcReturnResult.getS2c().get("nextReqKey").getAsString());
                } else {
                    sendNotifyMessage("market:" + market + ",code:" + code + ",klType:" + klType + ",rehabType" + rehabType + "无须分页.");
                }
                List<KLMessageContent> klMessageContents = new ArrayList<>();
                while (klListIterator.hasNext()) {
                    JsonObject kl = klListIterator.next().getAsJsonObject();
                    KLMessageContent klMessageContent = GSON.fromJson(kl, KLMessageContent.class);
                    klMessageContent.setKlType(klType);
                    klMessageContent.setRehabType(rehabType);
                    klMessageContent.setMarket(market);
                    klMessageContent.setCode(code);
                    klMessageContents.add(klMessageContent);
                }
                int insertNum = kLineService.insertHistoryKLines(klMessageContents);
                sendNotifyMessage("历史K线插入数据.条数:" + insertNum);
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("解析历史K线失败.", e);
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
                CapitalFlowWsMessage capitalFlowWsMessage = new CapitalFlowWsMessage();
                capitalFlowWsMessage.setContentList(capitalFlowMessageContents);
                capitalFlowWsMessage.setSecurity(security);
                quantxFutuWsService.sendCapitalFlow(capitalFlowWsMessage);
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("解析资金流向结果失败.", e);
            } catch (NullPointerException e) {
                LOGGER.error("资金流向回调异常.", e);
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
                List<BasicQuoteMessageContent> basicQuoteMessageContents = GSON.fromJson(ftGrpcReturnResult.getS2c().getAsJsonArray("basicQotList"), new TypeToken<List<BasicQuoteMessageContent>>() {
                }.getType());
                for (BasicQuoteMessageContent basicQuoteMessageContent : basicQuoteMessageContents) {
                    sendBasicQuoteMessage(basicQuoteMessageContent);
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
                    eventPublisher.publishEvent(new KLineUpdateEvent(klMessageContent));
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

    public void sendStockFilterRequest(StockFilterWsMessage request) {
        QotStockFilter.C2S.Builder c2s = QotStockFilter.C2S.newBuilder()
                .setBegin(request.getBegin())
                .setNum(request.getNum())
                .setMarket(request.getMarket());
        if (Objects.nonNull(request.getPlate())) {
            c2s.setPlate(QotCommon.Security.newBuilder()
                    .setMarket(request.getPlate().getMarket())
                    .setCode(request.getPlate().getCode())
                    .build());
        }
        if (Objects.nonNull(request.getBaseFilterList())
                && !request.getBaseFilterList().isEmpty()) {
            List<QotStockFilter.BaseFilter> baseFilters = request.getBaseFilterList()
                    .stream().map(baseFilterWsMessage -> QotStockFilter.BaseFilter
                            .newBuilder()
                            .setFieldName(baseFilterWsMessage.getFieldName())
                            .setFilterMin(baseFilterWsMessage.getFilterMin())
                            .setFilterMax(baseFilterWsMessage.getFilterMax())
                            .setIsNoFilter(baseFilterWsMessage.isNoFilter())
                            .setSortDir(baseFilterWsMessage.getSortDir())
                            .build())
                    .collect(Collectors.toList());
            c2s.addAllBaseFilterList(baseFilters);
        }
        if (Objects.nonNull(request.getAccumulateFilterList())
                && !request.getAccumulateFilterList().isEmpty()) {
            List<QotStockFilter.AccumulateFilter> accumulateFilters = request.getAccumulateFilterList()
                    .stream()
                    .map(accumulateFilterWsMessage -> QotStockFilter.AccumulateFilter.newBuilder()
                            .setFieldName(accumulateFilterWsMessage.getFieldName())
                            .setFilterMin(accumulateFilterWsMessage.getFilterMin())
                            .setFilterMax(accumulateFilterWsMessage.getFilterMax())
                            .setIsNoFilter(accumulateFilterWsMessage.isNoFilter())
                            .setSortDir(accumulateFilterWsMessage.getSortDir())
                            .setDays(accumulateFilterWsMessage.getDays())
                            .build())
                    .collect(Collectors.toList());
            c2s.addAllAccumulateFilterList(accumulateFilters);
        }
        if (Objects.nonNull(request.getFinancialFilterList())
                && !request.getFinancialFilterList().isEmpty()) {
            List<QotStockFilter.FinancialFilter> financialFilters = request.getFinancialFilterList()
                    .stream()
                    .map(financialFilterWsMessage -> QotStockFilter.FinancialFilter.newBuilder()
                            .setFieldName(financialFilterWsMessage.getFieldName())
                            .setFilterMin(financialFilterWsMessage.getFilterMin())
                            .setFilterMax(financialFilterWsMessage.getFilterMax())
                            .setIsNoFilter(financialFilterWsMessage.isNoFilter())
                            .setSortDir(financialFilterWsMessage.getSortDir())
                            .setQuarter(financialFilterWsMessage.getQuarter())
                            .build())
                    .collect(Collectors.toList());
            c2s.addAllFinancialFilterList(financialFilters);
        }
        if (Objects.nonNull(request.getPatternFilterList())
                && !request.getPatternFilterList().isEmpty()) {
            List<QotStockFilter.PatternFilter> patternFilters = request.getPatternFilterList()
                    .stream()
                    .map(patternFilterWsMessage -> QotStockFilter.PatternFilter.newBuilder()
                            .setFieldName(patternFilterWsMessage.getFieldName())
                            .setKlType(patternFilterWsMessage.getKlType())
                            .setIsNoFilter(patternFilterWsMessage.isNoFilter())
                            .setConsecutivePeriod(patternFilterWsMessage.getConsecutivePeriod())
                            .build()).collect(Collectors.toList());
            c2s.addAllPatternFilterList(patternFilters);
        }
        if (Objects.nonNull(request.getCustomIndicatorFilterList())
                && !request.getCustomIndicatorFilterList().isEmpty()) {
            List<QotStockFilter.CustomIndicatorFilter> customIndicatorFilters = request.getCustomIndicatorFilterList()
                    .stream()
                    .map(customIndicatorFilterWsMessage -> QotStockFilter.CustomIndicatorFilter.newBuilder()
                            .setFirstFieldName(customIndicatorFilterWsMessage.getFirstFieldName())
                            .setSecondFieldName(customIndicatorFilterWsMessage.getSecondFieldName())
                            .setRelativePosition(customIndicatorFilterWsMessage.getRelativePosition())
                            .setFieldValue(customIndicatorFilterWsMessage.getFieldValue())
                            .setKlType(customIndicatorFilterWsMessage.getKlType())
                            .setIsNoFilter(customIndicatorFilterWsMessage.isNoFilter())
                            .addAllFirstFieldParaList(customIndicatorFilterWsMessage.getFirstFieldParaList())
                            .addAllSecondFieldParaList(customIndicatorFilterWsMessage.getSecondFieldParaList())
                            .setConsecutivePeriod(customIndicatorFilterWsMessage.getConsecutivePeriod())
                            .build())
                    .collect(Collectors.toList());
            c2s.addAllCustomIndicatorFilterList(customIndicatorFilters);
        }
        QotStockFilter.Request req = QotStockFilter.Request.newBuilder()
                .setC2S(c2s.build())
                .build();
        int seq = qot.stockFilter(req);
        LOGGER.info("选股.seq={}", seq);
    }

    @Override
    public void onReply_StockFilter(FTAPI_Conn client, int nSerialNo, QotStockFilter.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "选股失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "选股失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                StockFilterContent stockFilterContent = GSON.fromJson(ftGrpcReturnResult.getS2c(), new TypeToken<StockFilterContent>() {
                }.getType());
                sendStockFilterResult(stockFilterContent);
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("选股结果解析失败.", e);
            }
        }
    }

    private void sendStockFilterResult(StockFilterContent content) {
        StockFilterWsMessage wsMessage = new StockFilterWsMessage();
        wsMessage.setStockFilterContent(content);
        quantxFutuWsService.sendStockFilterMessage(wsMessage);
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
                LOGGER.error("订阅结果解析失败.", e);
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
    public void onReply_GetIpoList(FTAPI_Conn client, int nSerialNo, QotGetIpoList.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询IPO信息失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询IPO信息失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                CommonSecurity commonSecurity = (CommonSecurity) CacheManager.get(String.valueOf(nSerialNo));
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询IPO信息", ftGrpcReturnResult);
                List<IpoData> ipos = GSON.fromJson(ftGrpcReturnResult.getS2c().getAsJsonArray("ipoList"), new TypeToken<List<IpoData>>() {
                }.getType());
                GetIpoWsMessage message = new GetIpoWsMessage();
                message.setIpoList(ipos);
                message.setMarket(commonSecurity.getMarket());
                sendIpoWsMessage(message);
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("查询IPO信息解析结果失败!", e);
            } catch (NullPointerException e) {
                LOGGER.error("查询IPO信息回调失败.", e);
            }

        }
    }

    private void sendIpoWsMessage(GetIpoWsMessage message) {
        this.quantxFutuWsService.sendIpoMessage(message);
    }

    @Override
    public void onReply_GetOwnerPlate(FTAPI_Conn client, int nSerialNo, QotGetOwnerPlate.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询股票所属板块信息失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询股票所属板块信息失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询股票所属板块信息", ftGrpcReturnResult);
                List<StockOwnerPlateContent> stockOwnerPlateContents = GSON.fromJson(ftGrpcReturnResult.getS2c().getAsJsonArray("ownerPlateList"), new TypeToken<List<StockOwnerPlateContent>>() {
                }.getType());
//                int totalInsert = 0;
//                for (StockOwnerPlateContent stockOwnerPlateContent : stockOwnerPlateContents) {
//                    StockDto stockDto = new StockDto();
//                    stockDto.setMarket(stockOwnerPlateContent.getSecurity().getMarket());
//                    stockDto.setCode(stockOwnerPlateContent.getSecurity().getCode());
//                    List<PlateDto> toInsertPlates = stockOwnerPlateContent.getPlateInfoList()
//                            .stream().map(plateInfoContent -> {
//                                PlateDto plateDto = new PlateDto();
//                                plateDto.setName(plateInfoContent.getName());
//                                plateDto.setCode(plateInfoContent.getPlate().getCode());
//                                plateDto.setMarket(plateInfoContent.getPlate().getMarket());
//                                plateDto.setPlateType(plateInfoContent.getPlateType());
//                                return plateDto;
//                            }).collect(Collectors.toList());
//                    totalInsert += plateService.insertBatch(stockDto, toInsertPlates);
//                }
//                String notify = "查询股票对应板块数据,插入条数:" + totalInsert;
//                LOGGER.info(notify);
//                sendNotifyMessage(notify);
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
                logFTResult("查询静态信息", ftGrpcReturnResult);
                CommonStaticInfo commonStaticInfo = (CommonStaticInfo) CacheManager.get(String.valueOf(nSerialNo));
                List<StockContent> stockContents = GSON.fromJson(ftGrpcReturnResult.getS2c().getAsJsonArray("staticInfoList"), new TypeToken<List<StockContent>>() {
                }.getType());
                eventPublisher.publishEvent(new StaticInfoUpdateEvent(commonStaticInfo, stockContents));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询静态信息解析结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询静态信息空指针.";
                LOGGER.error(errMsg, e);
                LOGGER.error(errMsg, e);
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
                logFTResult("查询板块下股票信息结果", ftGrpcReturnResult);
                List<StockContent> stockContents = GSON.fromJson(ftGrpcReturnResult.getS2c().getAsJsonArray("staticInfoList"), new TypeToken<List<StockContent>>() {
                }.getType());
                sendStocksInPlateMessage(stockContents);
                CommonSecurity plateItem = (CommonSecurity) CacheManager.get(String.valueOf(nSerialNo));
                PlateDto plateDto = new PlateDto(plateItem.getMarket(), plateItem.getCode());
                eventPublisher.publishEvent(new StockInPlateUpdateEvent(plateDto, stockContents));
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("查询板块下股票解析结果失败!", e);
            } catch (NullPointerException e) {
                LOGGER.error("查询板块下股票解析回调空指针.", e);
            }
        }
    }

    private void sendStocksInPlateMessage(List<StockContent> stockContents) {
        StockInPlateWsMessage message = new StockInPlateWsMessage();
        message.setStocks(stockContents);
        this.quantxFutuWsService.sendStocksInPlateMessage(message);
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
                logFTResult("查询板块信息", ftGrpcReturnResult);
                List<PlateInfoContent> plateInfos = GSON.fromJson(ftGrpcReturnResult.getS2c().getAsJsonArray("plateInfoList"), new TypeToken<List<PlateInfoContent>>() {
                }.getType());
                CommonSecurity commonSecurity = (CommonSecurity) CacheManager.get(String.valueOf(nSerialNo));
                eventPublisher.publishEvent(new PlateSetUpdateEvent(commonSecurity.getMarket(), plateInfos));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询板块信息解析结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询板块信息解析结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询板块信息有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }

    }

    private void syncSnapshotDataInternal(List<CommonSecurity> securities) {
        QotGetSecuritySnapshot.C2S.Builder c2sBuilder = QotGetSecuritySnapshot.C2S.newBuilder();
        List<QotCommon.Security> securityListToReq = new ArrayList<>();
        List<CommonSecurity> filteredSecurities = securities.stream().filter(sec -> {
            String code = sec.getCode();
            if (futuConfig.getExclusionCodesWhenSnapshot().contains(code)) {
                LOGGER.info("{}不参与查询快照数据", code);
                return false;
            } else {
                return true;
            }
        }).collect(Collectors.toList());
        for (CommonSecurity security : filteredSecurities) {
            QotCommon.Security sec = QotCommon.Security.newBuilder()
                    .setMarket(security.getMarket())
                    .setCode(security.getCode())
                    .build();
            securityListToReq.add(sec);
        }
        c2sBuilder.addAllSecurityList(securityListToReq);
        QotGetSecuritySnapshot.Request request = QotGetSecuritySnapshot.Request
                .newBuilder().setC2S(c2sBuilder.build()).build();
        int seqNo = qot.getSecuritySnapshot(request);
        LOGGER.info("请求快照数据.seqNo={}", seqNo);
    }

    private void syncSnapshotDataBatch(List<CommonSecurity> securities, int batchLimit) {
        if (!securities.isEmpty()) {
            RequestCount requestCount = new RequestCount(30L * 1000, 59);
            int sendLength = securities.size();
            int i = 0;
            while (sendLength > batchLimit) {
                List<CommonSecurity> batchSendSecurities = securities.subList(i, i + batchLimit);
                syncSnapshotDataInternal(batchSendSecurities);
                i += batchLimit;
                sendLength -= batchLimit;
                requestCount.count();
            }
            if (sendLength > 0) {
                List<CommonSecurity> batchSendSecurities = securities.subList(i, i + sendLength);
                syncSnapshotDataInternal(batchSendSecurities);
                requestCount.count();
            }
        }
    }

    public void syncSnapshotData(SnapshotWsMessage snapshotWsMessage) {
        Integer reqMarket = snapshotWsMessage.getMarket();
        if (Objects.isNull(reqMarket)) {
            syncSnapshotDataBatch(snapshotWsMessage.getSecurities(), 400);
        } else {
            if (snapshotWsMessage.getIsPlate().equals(1)) {
                //按市场-按板块
                List<PlateDto> plateDtos = plateService.list(Wrappers.query(new PlateDto()).eq("market", reqMarket));
                List<CommonSecurity> platesToRequest = plateDtos.stream().map(p -> new CommonSecurity(p.getMarket(), p.getCode()))
                        .collect(Collectors.toList());
                syncSnapshotDataBatch(platesToRequest, 400);
            }
        }
    }

    public void sendSetReminderRequest(SetPriceReminderWsMessage request) {
        QotSetPriceReminder.C2S.Builder c2s = QotSetPriceReminder.C2S.newBuilder();
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setCode(request.getCode())
                .setMarket(request.getMarket())
                .build();
        c2s.setOp(request.getOp());
        c2s.setSecurity(sec);
        if (Objects.nonNull(request.getKey())) {
            c2s.setKey(request.getKey());
        }
        if (Objects.nonNull(request.getRemindType())) {
            c2s.setType(request.getRemindType());
        }
        if (Objects.nonNull(request.getRemindFreq())) {
            c2s.setFreq(request.getRemindFreq());
        }
        if (Objects.nonNull(request.getValue())) {
            c2s.setValue(request.getValue());
        }
        if (Objects.nonNull(request.getNote())) {
            c2s.setNote(request.getNote());
        }
        QotSetPriceReminder.Request req = QotSetPriceReminder.Request.newBuilder().setC2S(c2s).build();
        int seqNo = qot.setPriceReminder(req);
        LOGGER.info("设置到价提醒.seq={}", seqNo);
    }

    public void syncStockInPlateByMarket(Integer market) {
        List<PlateDto> plateByMarket = plateService.list(Wrappers.query(new PlateDto()).eq("market", market));
        RequestCount requestCount = new RequestCount(30L * 1000, 8);
        for (int i = 0; i < plateByMarket.size(); i++) {
            PlateDto plate = plateByMarket.get(i);
            syncStockInPlate(new CommonSecurity(plate.getMarket(), plate.getCode()));
            requestCount.count();
        }
    }

    public void syncHotList(HotListWsMessage req) {
        QotGetHotList.C2S.Builder c2sBuilder = QotGetHotList.C2S.newBuilder()
                .setMarket(req.getMarket());
        if (Objects.nonNull(req.getSortField())) {
            c2sBuilder.setSortField(req.getSortField());
        }
        if (Objects.nonNull(req.getSortDir())) {
            c2sBuilder.setSortDir(req.getSortDir());
        }
        if (Objects.nonNull(req.getOffset())) {
            c2sBuilder.setOffset(req.getOffset());
        }
        if (Objects.nonNull(req.getCount())) {
            c2sBuilder.setCount(req.getCount());
        }
        if (Objects.nonNull(req.getFilterList())) {
            List<QotGetHotList.Indicator> filterList = req.getFilterList()
                    .stream().map(f -> QotGetHotList.Indicator.newBuilder()
                            .setIndicatorType(f.getIndicatorType())
                            .setIndicatorValue(QotOptionCommon.Interval.newBuilder()
                                    .setFilterMin(QotOptionCommon.Boundary.newBuilder().setValue(f.getMin()).build())
                                    .setFilterMax(QotOptionCommon.Boundary.newBuilder().setValue(f.getMax()).build())
                                    .build())
                            .build()).collect(Collectors.toList());
            c2sBuilder.addAllFilterList(filterList);
        }
        QotGetHotList.Request request = QotGetHotList.Request.newBuilder()
                .setC2S(c2sBuilder).build();
        int seqNo = qot.getHotList(request);
        LOGGER.info("市场={},查询热议榜.seq={}", MarketType.getName(req.getMarket()), seqNo);
    }

    @Override
    public void onReply_GetHotList(FTAPI_Conn client, int nSerialNo, QotGetHotList.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询热议榜失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询热议榜失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询热议榜央企", ftGrpcReturnResult);
                HotListContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), HotListContent.class);
                eventPublisher.publishEvent(new HotListUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询热议榜结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询热议榜结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询热议榜有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncHighDividendSoeRank(HighDividendSoeRankWsMessage req) {
        QotGetHighDividendSOERank.C2S.Builder c2s = QotGetHighDividendSOERank.C2S.newBuilder();
        if (Objects.nonNull(req.getCount())) {
            c2s.setCount(req.getCount());
        }
        if (Objects.nonNull(req.getOffset())) {
            c2s.setOffset(req.getOffset());
        }
        if (Objects.nonNull(req.getSortDir())) {
            c2s.setSortDir(req.getSortDir());
        }
        if (Objects.nonNull(req.getSortField())) {
            c2s.setSortField(req.getSortField());
        }
        if (Objects.nonNull(req.getFilterList())) {
            List<QotGetHighDividendSOERank.Indicator> filterList = req.getFilterList().stream().map(f ->
                    QotGetHighDividendSOERank.Indicator.newBuilder()
                            .setIndicatorType(f.getIndicatorType())
                            .setIndicatorValue(QotOptionCommon.Interval.newBuilder()
                                    .setFilterMax(QotOptionCommon.Boundary.newBuilder().setValue(f.getMax()).build())
                                    .setFilterMin(QotOptionCommon.Boundary.newBuilder().setValue(f.getMin()).build())
                                    .build())
                            .build()).collect(Collectors.toList());
            c2s.addAllFilterList(filterList);
        }
        QotGetHighDividendSOERank.Request request = QotGetHighDividendSOERank.Request.newBuilder().setC2S(c2s).build();
        int seqNo = qot.getHighDividendSOERank(request);
        LOGGER.info("查询破净高股息国央企.seq={}", seqNo);
    }

    @Override
    public void onReply_GetHighDividendSOERank(FTAPI_Conn client, int nSerialNo, QotGetHighDividendSOERank.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询破净高股息国央企失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询破净高股息国央企失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询破净高股息国央企", ftGrpcReturnResult);
                HighDividendSoeRankContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), HighDividendSoeRankContent.class);
                eventPublisher.publishEvent(new HighDividendSoeRankUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询破净高股息国央企结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询破净高股息国央企结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询破净高股息国央企有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncShortSellRank(ShortSellRankWsMessage req) {
        QotGetShortSellingRank.C2S.Builder c2sBuilder = QotGetShortSellingRank.C2S.newBuilder()
                .setMarket(req.getMarket());
        if (Objects.nonNull(req.getSortField())) {
            c2sBuilder.setSortField(req.getSortField());
        }
        if (Objects.nonNull(req.getSortDir())) {
            c2sBuilder.setSortDir(req.getSortDir());
        }
        if (Objects.nonNull(req.getOffset())) {
            c2sBuilder.setOffset(req.getOffset());
        }
        if (Objects.nonNull(req.getCount())) {
            c2sBuilder.setCount(req.getCount());
        }
        if (Objects.nonNull(req.getPlateList())) {
            c2sBuilder.addAllPlateList(req.getPlateList().stream()
                    .map(p -> QotCommon.Security.newBuilder()
                            .setCode(p.getCode())
                            .setMarket(p.getMarket())
                            .build())
                    .collect(Collectors.toList()));
        }
        QotGetShortSellingRank.Request request = QotGetShortSellingRank.Request.newBuilder()
                .setC2S(c2sBuilder.build()).build();
        int seqNo = qot.getShortSellingRank(request);
        LOGGER.info("市场={},查询卖空异动榜.seq={}", MarketType.getName(req.getMarket()), seqNo);
    }

    @Override
    public void onReply_GetShortSellingRank(FTAPI_Conn client, int nSerialNo, QotGetShortSellingRank.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询卖空异动榜失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询卖空异动榜失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询卖空异动榜", ftGrpcReturnResult);
                ShortSellRankContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), ShortSellRankContent.class);
                eventPublisher.publishEvent(new ShortSellRankUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询卖空异动榜结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询卖空异动榜结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询卖空异动榜有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncRiseFallDistribution(RiseFallDistributionWsMessage req) {
        QotGetRiseFallDistribution.C2S.Builder c2sBuilder = QotGetRiseFallDistribution.C2S.newBuilder()
                .setMarket(req.getMarket());
        if (Objects.nonNull(req.getPlateCode()) && Objects.nonNull(req.getPlateMarket())) {
            c2sBuilder.setSecurity(
                    QotCommon.Security.newBuilder()
                            .setMarket(req.getPlateMarket())
                            .setCode(req.getPlateCode())
                            .build()
            );
        }
        QotGetRiseFallDistribution.Request request = QotGetRiseFallDistribution.Request.newBuilder()
                .setC2S(c2sBuilder.build()).build();
        int seqNo = qot.getRiseFallDistribution(request);
        LOGGER.info("市场={},查询涨跌分布.seq={}", MarketType.getName(req.getMarket()), seqNo);
    }

    @Override
    public void onReply_GetRiseFallDistribution(FTAPI_Conn client, int nSerialNo, QotGetRiseFallDistribution.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询涨跌分布失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询涨跌分布失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询涨跌分布", ftGrpcReturnResult);
                RiseFallDistributionContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), RiseFallDistributionContent.class);
                eventPublisher.publishEvent(new RiseFallDistributionUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询涨跌分布结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询涨跌分布结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询涨跌分布有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncHeatMapData(HeatMapDataWsMessage req) {
        QotGetHeatMapData.C2S.Builder c2sBuilder = QotGetHeatMapData.C2S.newBuilder()
                .setMarket(req.getMarket());
        if (Objects.nonNull(req.getSortField())) {
            c2sBuilder.setSortField(req.getSortField());
        }
        if (Objects.nonNull(req.getAscend())) {
            c2sBuilder.setAscend(req.getAscend());
        }
        if (Objects.nonNull(req.getCount())) {
            c2sBuilder.setCount(req.getCount());
        }
        if (Objects.nonNull(req.getPage())) {
            c2sBuilder.setPage(req.getPage());
        }
        if (Objects.nonNull(req.getPlateType())) {
            c2sBuilder.setPlateType(req.getPlateType());
        }
        QotGetHeatMapData.Request request = QotGetHeatMapData.Request.newBuilder()
                .setC2S(c2sBuilder.build()).build();
        int seqNo = qot.getHeatMapData(request);
        LOGGER.info("市场={},查询热力图数据.seq={}", MarketType.getName(req.getMarket()), seqNo);
    }

    @Override
    public void onReply_GetHeatMapData(FTAPI_Conn client, int nSerialNo, QotGetHeatMapData.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询热力图数据失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询热力图数据失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询热力图数据", ftGrpcReturnResult);
                HeatMapDataContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), HeatMapDataContent.class);
                eventPublisher.publishEvent(new HeatMapDataUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询热力图数据结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询热力图数据结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询热力图数据有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncIndustrialPlateStock(IndustrialPlateStockWsMessage req) {
        QotGetIndustrialPlateStock.C2S.Builder c2sBuilder = QotGetIndustrialPlateStock.C2S.newBuilder()
                .setPlateId(req.getPlateId())
                .setChainId(req.getChainId());
        if (Objects.nonNull(req.getMarketList())) {
            c2sBuilder.addAllMarketList(req.getMarketList());
        }
        if (Objects.nonNull(req.getSortField())) {
            c2sBuilder.setSortField(req.getSortField());
        }
        if (Objects.nonNull(req.getAscend())) {
            c2sBuilder.setAscend(req.getAscend());
        }
        if (Objects.nonNull(req.getCount())) {
            c2sBuilder.setCount(req.getCount());
        }
        if (Objects.nonNull(req.getPage())) {
            c2sBuilder.setPage(req.getPage());
        }
        QotGetIndustrialPlateStock.Request request = QotGetIndustrialPlateStock.Request.newBuilder()
                .setC2S(c2sBuilder.build()).build();
        int seqNo = qot.getIndustrialPlateStock(request);
        LOGGER.info("plateId={},chainId={},查询产业板块成分股.seq={}", req.getPlateId(), req.getChainId(), seqNo);
    }

    @Override
    public void onReply_GetIndustrialPlateStock(FTAPI_Conn client, int nSerialNo, QotGetIndustrialPlateStock.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询产业板块成分股失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询产业板块成分股失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询产业板块成分股", ftGrpcReturnResult);
                IndustrialPlateStockContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), IndustrialPlateStockContent.class);
                eventPublisher.publishEvent(new IndustrialPlateStockUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询产业板块成分股结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询产业板块成分股结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询产业板块成分股有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncIndustrialPlateInfo(IndustrialPlateInfoWsMessage req) {
        QotGetIndustrialPlateInfo.C2S c2s = QotGetIndustrialPlateInfo.C2S.newBuilder()
                .setPlateId(req.getPlateId())
                .build();
        QotGetIndustrialPlateInfo.Request request = QotGetIndustrialPlateInfo.Request.newBuilder().setC2S(c2s).build();
        int seqNo = qot.getIndustrialPlateInfo(request);
        LOGGER.info("plateId={},查询产业板块信息.seq={}", req.getPlateId(), seqNo);
    }

    @Override
    public void onReply_GetIndustrialPlateInfo(FTAPI_Conn client, int nSerialNo, QotGetIndustrialPlateInfo.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询产业板块信息失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询产业板块信息失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询产业板块信息", ftGrpcReturnResult);
                IndustrialPlateInfoContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), IndustrialPlateInfoContent.class);
                eventPublisher.publishEvent(new IndustrialPlateInfoUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询产业板块信息结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询产业板块信息结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询产业板块信息有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncIndustrialChainByPlate(IndustrialChainByPlateWsMessage req) {
        QotGetIndustrialChainByPlate.C2S c2s = QotGetIndustrialChainByPlate.C2S.newBuilder()
                .setPlateId(req.getPlateId())
                .build();
        QotGetIndustrialChainByPlate.Request request = QotGetIndustrialChainByPlate.Request.newBuilder().setC2S(c2s).build();
        int seqNo = qot.getIndustrialChainByPlate(request);
        LOGGER.info("plateId={},查询板块关联产业链.seq={}", req.getPlateId(), seqNo);
    }

    @Override
    public void onReply_GetIndustrialChainByPlate(FTAPI_Conn client, int nSerialNo, QotGetIndustrialChainByPlate.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询板块关联产业链失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询板块关联产业链失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询板块关联产业链", ftGrpcReturnResult);
                IndustrialChainByPlateContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), IndustrialChainByPlateContent.class);
                eventPublisher.publishEvent(new IndustrialChainByPlateUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询板块关联产业链结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询板块关联产业链结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询板块关联产业链有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncIndustrialChainDetail(IndustrialChainDetailWsMessage req) {
        QotGetIndustrialChainDetail.C2S c2s = QotGetIndustrialChainDetail.C2S.newBuilder()
                .setChainId(req.getChainId())
                .build();
        QotGetIndustrialChainDetail.Request request = QotGetIndustrialChainDetail.Request.newBuilder().setC2S(c2s).build();
        int seqNo = qot.getIndustrialChainDetail(request);
        LOGGER.info("chainId={},查询产业链详情.seq={}", req.getChainId(), seqNo);
    }

    @Override
    public void onReply_GetIndustrialChainDetail(FTAPI_Conn client, int nSerialNo, QotGetIndustrialChainDetail.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询产业链详情失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询产业链详情失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询产业链详情", ftGrpcReturnResult);
                IndustrialChainDetailContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), IndustrialChainDetailContent.class);
                eventPublisher.publishEvent(new IndustrialChainDetailUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询产业链详情结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询产业链详情结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询产业链详情有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncIndustrialChainList(IndustrialChainListWsMessage req) {
        QotGetIndustrialChainList.C2S.Builder c2sBuilder = QotGetIndustrialChainList.C2S.newBuilder()
                .setMarket(req.getMarket());
        if (Objects.nonNull(req.getKeyword())) {
            c2sBuilder.setKeyword(req.getKeyword());
        }
        if (Objects.nonNull(req.getCount())) {
            c2sBuilder.setCount(req.getCount());
        }
        if (Objects.nonNull(req.getPage())) {
            c2sBuilder.setPage(req.getPage());
        }
        QotGetIndustrialChainList.Request request = QotGetIndustrialChainList.Request.newBuilder()
                .setC2S(c2sBuilder.build()).build();
        int seqNo = qot.getIndustrialChainList(request);
        LOGGER.info("市场{},查询产业链列表.seq={}", MarketType.getName(req.getMarket()), seqNo);
    }

    @Override
    public void onReply_GetIndustrialChainList(FTAPI_Conn client, int nSerialNo, QotGetIndustrialChainList.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询产业链列表失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询产业链列表失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询产业链列表", ftGrpcReturnResult);
                IndustrialChainListContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), IndustrialChainListContent.class);
                eventPublisher.publishEvent(new IndustrialChainListUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询产业链列表结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询产业链列表结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询产业链列表有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncArkActiveTransaction(ArkActiveTransactionWsMessage req) {
        QotGetArkActiveTransaction.C2S.Builder c2sBuilder = QotGetArkActiveTransaction.C2S.newBuilder();
        if (Objects.nonNull(req.getHoldingType())) {
            c2sBuilder.setHoldingType(req.getHoldingType());
        }
        if (Objects.nonNull(req.getCycleType())) {
            c2sBuilder.setCycleType(req.getCycleType());
        }
        if (Objects.nonNull(req.getSortField())) {
            c2sBuilder.setSortField(req.getSortField());
        }
        if (Objects.nonNull(req.getSortDir())) {
            c2sBuilder.setSortDir(req.getSortDir());
        }
        if (Objects.nonNull(req.getCount())) {
            c2sBuilder.setCount(req.getCount());
        }
        if (Objects.nonNull(req.getPage())) {
            c2sBuilder.setPage(req.getPage());
        }
        QotGetArkActiveTransaction.Request request = QotGetArkActiveTransaction.Request.newBuilder()
                .setC2S(c2sBuilder.build()).build();
        int seqNo = qot.getArkActiveTransaction(request);
        LOGGER.info("ARK主动交易聚合.seq={}", seqNo);
    }

    @Override
    public void onReply_GetArkActiveTransaction(FTAPI_Conn client, int nSerialNo, QotGetArkActiveTransaction.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询ARK主动交易聚合失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询ARK主动交易聚合失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询ARK主动交易聚合", ftGrpcReturnResult);
                ArkActiveTransactionContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), ArkActiveTransactionContent.class);
                eventPublisher.publishEvent(new ArkActiveTransactionUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询ARK主动交易聚合结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询ARK主动交易聚合结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询ARK主动交易聚合有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncArkStockDynamic(ArkStockDynamicWsMessage req) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setMarket(req.getMarket())
                .setCode(req.getCode())
                .build();
        QotGetArkStockDynamic.C2S c2s = QotGetArkStockDynamic.C2S.newBuilder()
                .setSecurity(sec)
                .build();
        QotGetArkStockDynamic.Request request = QotGetArkStockDynamic.Request.newBuilder().setC2S(c2s).build();
        int seqNo = qot.getArkStockDynamic(request);
        LOGGER.info("市场{},code={},查询ARK个股交易动态.seq={}", MarketType.getName(req.getMarket()), req.getCode(), seqNo);
    }

    @Override
    public void onReply_GetArkStockDynamic(FTAPI_Conn client, int nSerialNo, QotGetArkStockDynamic.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询ARK个股交易动态失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询ARK个股交易动态失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询ARK个股交易动态", ftGrpcReturnResult);
                ArkStockDynamicContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), ArkStockDynamicContent.class);
                eventPublisher.publishEvent(new ArkStockDynamicUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询ARK个股交易动态结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询ARK个股交易动态结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询ARK个股交易动态有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncArkFundHolding(ArkFundHoldingWsMessage req) {
        QotGetArkFundHolding.C2S.Builder c2sBuilder = QotGetArkFundHolding.C2S.newBuilder();
        if (Objects.nonNull(req.getHoldingType())) {
            c2sBuilder.setHoldingType(req.getHoldingType());
        }
        if (Objects.nonNull(req.getCycleType())) {
            c2sBuilder.setCycleType(req.getCycleType());
        }
        if (Objects.nonNull(req.getSortField())) {
            c2sBuilder.setSortField(req.getSortField());
        }
        if (Objects.nonNull(req.getSortDir())) {
            c2sBuilder.setSortDir(req.getSortDir());
        }
        if (Objects.nonNull(req.getCount())) {
            c2sBuilder.setCount(req.getCount());
        }
        if (Objects.nonNull(req.getPage())) {
            c2sBuilder.setPage(req.getPage());
        }
        QotGetArkFundHolding.Request request = QotGetArkFundHolding.Request.newBuilder()
                .setC2S(c2sBuilder.build()).build();
        int seqNo = qot.getArkFundHolding(request);
        LOGGER.info("ARK基金持仓.seq={}", seqNo);
    }

    @Override
    public void onReply_GetArkFundHolding(FTAPI_Conn client, int nSerialNo, QotGetArkFundHolding.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询ARK基金持仓失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询ARK基金持仓失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询ARK基金持仓", ftGrpcReturnResult);
                ArkFundHoldingContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), ArkFundHoldingContent.class);
                eventPublisher.publishEvent(new ArkFundHoldingUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询ARK基金持仓结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询ARK基金持仓结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询ARK基金持仓有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncInstitutionHoldingList(InstitutionHoldingListWsMessage req) {
        QotGetInstitutionHoldingList.C2S.Builder c2sBuilder = QotGetInstitutionHoldingList.C2S.newBuilder()
                .setMarket(req.getMarket())
                .setInstitutionId(req.getInstitutionId());
        if (Objects.nonNull(req.getChangeType())) {
            c2sBuilder.setChangeType(req.getChangeType());
        }
        if (Objects.nonNull(req.getSortField())) {
            c2sBuilder.setSortField(req.getSortField());
        }
        if (Objects.nonNull(req.getSortDir())) {
            c2sBuilder.setSortDir(req.getSortDir());
        }
        if (Objects.nonNull(req.getCount())) {
            c2sBuilder.setCount(req.getCount());
        }
        if (Objects.nonNull(req.getPage())) {
            c2sBuilder.setPage(req.getPage());
        }
        if (Objects.nonNull(req.getKeyword())) {
            c2sBuilder.setKeyword(req.getKeyword());
        }
        QotGetInstitutionHoldingList.Request request = QotGetInstitutionHoldingList.Request.newBuilder()
                .setC2S(c2sBuilder.build()).build();
        int seqNo = qot.getInstitutionHoldingList(request);
        LOGGER.info("市场{},机构ID={},查询机构持股列表.seq={}", MarketType.getName(req.getMarket()), req.getInstitutionId(), seqNo);
    }

    @Override
    public void onReply_GetInstitutionHoldingList(FTAPI_Conn client, int nSerialNo, QotGetInstitutionHoldingList.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询机构持股列表失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询机构持股列表失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询机构持股列表", ftGrpcReturnResult);
                InstitutionHoldingListContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), InstitutionHoldingListContent.class);
                eventPublisher.publishEvent(new InstitutionHoldingListUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询机构持股列表结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询机构持股列表结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询机构持股列表有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }


    public void syncInstitutionHoldingChange(InstitutionHoldingChangeWsMessage req) {
        QotGetInstitutionHoldingChange.C2S.Builder c2sBuilder = QotGetInstitutionHoldingChange.C2S.newBuilder()
                .setMarket(req.getMarket())
                .setInstitutionId(req.getInstitutionId());
        if (Objects.nonNull(req.getChangeType())) {
            c2sBuilder.setChangeType(req.getChangeType());
        }
        if (Objects.nonNull(req.getSortField())) {
            c2sBuilder.setSortField(req.getSortField());
        }
        if (Objects.nonNull(req.getSortDir())) {
            c2sBuilder.setSortDir(req.getSortDir());
        }
        if (Objects.nonNull(req.getCount())) {
            c2sBuilder.setCount(req.getCount());
        }
        if (Objects.nonNull(req.getPage())) {
            c2sBuilder.setPage(req.getPage());
        }
        QotGetInstitutionHoldingChange.Request request = QotGetInstitutionHoldingChange.Request.newBuilder()
                .setC2S(c2sBuilder.build()).build();
        int seqNo = qot.getInstitutionHoldingChange(request);
        LOGGER.info("市场{},机构ID={},查询机构持仓变动.seq={}", MarketType.getName(req.getMarket()), req.getInstitutionId(), seqNo);
    }

    @Override
    public void onReply_GetInstitutionHoldingChange(FTAPI_Conn client, int nSerialNo, QotGetInstitutionHoldingChange.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询机构持仓变动失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询机构持仓变动失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询机构持仓变动", ftGrpcReturnResult);
                InstitutionHoldingChangeContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), InstitutionHoldingChangeContent.class);
                eventPublisher.publishEvent(new InstitutionHoldingChangeUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询机构持仓变动结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询机构持仓变动结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询机构持仓变动有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncInstitutionDistribution(InstitutionDistributionWsMessage req) {
        QotGetInstitutionDistribution.C2S c2s = QotGetInstitutionDistribution.C2S.newBuilder()
                .setMarket(req.getMarket())
                .setInstitutionId(req.getInstitutionId())
                .build();
        QotGetInstitutionDistribution.Request request = QotGetInstitutionDistribution.Request.newBuilder().setC2S(c2s).build();
        int seqNo = qot.getInstitutionDistribution(request);
        LOGGER.info("市场{},机构ID={},查询机构持仓行业分布.seq={}", MarketType.getName(req.getMarket()), req.getInstitutionId(), seqNo);
    }

    @Override
    public void onReply_GetInstitutionDistribution(FTAPI_Conn client, int nSerialNo, QotGetInstitutionDistribution.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询机构持仓行业分布失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询机构持仓行业分布失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询机构持仓行业分布", ftGrpcReturnResult);
                InstitutionDistributionContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), InstitutionDistributionContent.class);
                eventPublisher.publishEvent(new InstitutionDistributionUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询机构持仓行业分布结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询机构持仓行业分布结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询机构持仓行业分布有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncInstitutionProfile(InstitutionProfileWsMessage req) {
        QotGetInstitutionProfile.C2S c2s = QotGetInstitutionProfile.C2S.newBuilder()
                .setMarket(req.getMarket())
                .setInstitutionId(req.getInstitutionId())
                .build();
        QotGetInstitutionProfile.Request request = QotGetInstitutionProfile.Request.newBuilder()
                .setC2S(c2s).build();
        int seqNo = qot.getInstitutionProfile(request);
        LOGGER.info("市场{},机构ID={},查询机构概况.seq={}", MarketType.getName(req.getMarket()), req.getInstitutionId(), seqNo);
    }

    @Override
    public void onReply_GetInstitutionProfile(FTAPI_Conn client, int nSerialNo, QotGetInstitutionProfile.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询机构概况失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询机构概况失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询机构概况", ftGrpcReturnResult);
                InstitutionProfileContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), InstitutionProfileContent.class);
                eventPublisher.publishEvent(new InstitutionProfileUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询机构概况结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询机构概况结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询机构概况有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncInstitutionList(InstitutionListWsMessage req) {
        QotGetInstitutionList.C2S.Builder c2sBuilder = QotGetInstitutionList.C2S.newBuilder()
                .setMarket(req.getMarket());
        if (Objects.nonNull(req.getSortField())) {
            c2sBuilder.setSortField(req.getSortField());
        }
        if (Objects.nonNull(req.getSortDir())) {
            c2sBuilder.setSortDir(req.getSortDir());
        }
        if (Objects.nonNull(req.getCount())) {
            c2sBuilder.setCount(req.getCount());
        }
        if (Objects.nonNull(req.getPage())) {
            c2sBuilder.setPage(req.getPage());
        }
        if (Objects.nonNull(req.getNamePart())) {
            c2sBuilder.setNamePart(req.getNamePart());
        }
        QotGetInstitutionList.Request request = QotGetInstitutionList.Request.newBuilder()
                .setC2S(c2sBuilder.build()).build();
        int seqNo = qot.getInstitutionList(request);
        LOGGER.info("市场{},查询机构列表.seq={}", MarketType.getName(req.getMarket()), seqNo);
    }

    @Override
    public void onReply_GetInstitutionList(FTAPI_Conn client, int nSerialNo, QotGetInstitutionList.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询机构列表失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询机构列表失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询机构列表", ftGrpcReturnResult);
                InstitutionListContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), InstitutionListContent.class);
                eventPublisher.publishEvent(new InstitutionListUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询机构列表结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询机构列表结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询机构列表有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncShortInterest(ShortInterestWsMessage req) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setCode(req.getCode())
                .setMarket(req.getMarket())
                .build();
        QotGetShortInterest.C2S.Builder c2sBuilder = QotGetShortInterest.C2S.newBuilder()
                .setSecurity(sec);
        if (Objects.nonNull(req.getNextKey())) {
            c2sBuilder.setNextKey(req.getNextKey());
        }
        if (Objects.nonNull(req.getNum())) {
            c2sBuilder.setNum(req.getNum());
        }
        QotGetShortInterest.Request request = QotGetShortInterest.Request.newBuilder()
                .setC2S(c2sBuilder.build()).build();
        int seqNo = qot.getShortInterest(request);
        LOGGER.info("市场{},code={}查询空头持仓.seq={}", MarketType.getName(req.getMarket()), req.getCode(), seqNo);
    }

    @Override
    public void onReply_GetShortInterest(FTAPI_Conn client, int nSerialNo, QotGetShortInterest.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询空头持仓失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询空头持仓失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询空头持仓", ftGrpcReturnResult);
                ShortInterestContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), ShortInterestContent.class);
                eventPublisher.publishEvent(new ShortInterestUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询空头持仓结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询空头持仓结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询空头持仓有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncDailyShortVolume(DailyShortVolumeWsMessage req) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setCode(req.getCode())
                .setMarket(req.getMarket())
                .build();
        QotGetDailyShortVolume.C2S.Builder c2sBuilder = QotGetDailyShortVolume.C2S.newBuilder()
                .setSecurity(sec);
        if (Objects.nonNull(req.getNextKey())) {
            c2sBuilder.setNextKey(req.getNextKey());
        }
        if (Objects.nonNull(req.getNum())) {
            c2sBuilder.setNum(req.getNum());
        }
        QotGetDailyShortVolume.Request request = QotGetDailyShortVolume.Request.newBuilder()
                .setC2S(c2sBuilder.build()).build();
        int seqNo = qot.getDailyShortVolume(request);
        LOGGER.info("市场{},code={}查询每日卖空成交.seq={}", MarketType.getName(req.getMarket()), req.getCode(), seqNo);
    }

    @Override
    public void onReply_GetDailyShortVolume(FTAPI_Conn client, int nSerialNo, QotGetDailyShortVolume.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询每日卖空成交失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询每日卖空成交失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询每日卖空成交", ftGrpcReturnResult);
                DailyShotVolumeContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), DailyShotVolumeContent.class);
                eventPublisher.publishEvent(new DailyShortVolumeUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询每日卖空成交结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询每日卖空成交结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询每日卖空成交有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncTopTenBrokersBuySell(TopTenBrokersWsMessage req) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setCode(req.getCode())
                .setMarket(req.getMarket())
                .build();
        QotGetTopTenBuySellBrokers.C2S.Builder c2sBuilder = QotGetTopTenBuySellBrokers.C2S.newBuilder()
                .setSecurity(sec);
        if (Objects.nonNull(req.getDaysBefore())) {
            c2sBuilder.setDaysBefore(req.getDaysBefore());
        }
        QotGetTopTenBuySellBrokers.Request request = QotGetTopTenBuySellBrokers.Request.newBuilder()
                .setC2S(c2sBuilder.build()).build();
        int seqNo = qot.getTopTenBuySellBrokers(request);
        LOGGER.info("市场{},code={}查询十大经纪商买卖数据.seq={}", MarketType.getName(req.getMarket()), req.getCode(), seqNo);
    }

    @Override
    public void onReply_GetTopTenBuySellBrokers(FTAPI_Conn client, int nSerialNo, QotGetTopTenBuySellBrokers.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询十大经纪商买卖数据失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询十大经纪商买卖数据失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询十大经纪商买卖数据", ftGrpcReturnResult);
                TopTenBrokersContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), TopTenBrokersContent.class);
                eventPublisher.publishEvent(new TopTenBrokersUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询十大经纪商买卖数据结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询十大经纪商买卖数据结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询十大经纪商买卖数据有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncCompanyOperateEfficiency(CompanyOpEfficiencyWsMessage req) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setCode(req.getCode())
                .setMarket(req.getMarket())
                .build();
        QotGetCompanyOperationalEfficiency.C2S.Builder c2sBuilder = QotGetCompanyOperationalEfficiency.C2S.newBuilder()
                .setSecurity(sec);
        if (Objects.nonNull(req.getNextKey())) {
            c2sBuilder.setNextKey(req.getNextKey());
        }
        if (Objects.nonNull(req.getNum())) {
            c2sBuilder.setNum(req.getNum());
        }
        if (Objects.nonNull(req.getCurrencyCode())) {
            c2sBuilder.setCurrencyCode(req.getCurrencyCode());
        }
        if (Objects.nonNull(req.getFinancialType())) {
            c2sBuilder.setFinancialType(QotCommon.F10Type.forNumber(req.getFinancialType()));
        }
        QotGetCompanyOperationalEfficiency.Request request = QotGetCompanyOperationalEfficiency.Request.newBuilder()
                .setC2S(c2sBuilder.build()).build();
        int seqNo = qot.getCompanyOperationalEfficiency(request);
        LOGGER.info("市场{},code={}查询公司经营效率.seq={}", MarketType.getName(req.getMarket()), req.getCode(), seqNo);
    }

    @Override
    public void onReply_GetCompanyOperationalEfficiency(FTAPI_Conn client, int nSerialNo, QotGetCompanyOperationalEfficiency.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询公司经营效率失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询公司经营效率失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询公司经营效率", ftGrpcReturnResult);
                CompanyOpEfficiencyContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), CompanyOpEfficiencyContent.class);
                eventPublisher.publishEvent(new CompanyOpUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询公司经营效率结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询公司经营效率结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询公司经营效率有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncCompanyExecutiveBackground(CompanyExecutiveBackgroungWsMessage req) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setCode(req.getCode())
                .setMarket(req.getMarket())
                .build();
        QotGetCompanyExecutiveBackground.C2S.Builder c2sBuilder = QotGetCompanyExecutiveBackground.C2S.newBuilder()
                .setSecurity(sec);
        if (Objects.nonNull(req.getLeaderName())) {
            c2sBuilder.setLeaderName(req.getLeaderName());
        }
        QotGetCompanyExecutiveBackground.Request request = QotGetCompanyExecutiveBackground.Request
                .newBuilder().setC2S(c2sBuilder.build()).build();
        int seqNo = qot.getCompanyExecutiveBackground(request);
        LOGGER.info("市场{},code={}查询公司高管背景.seq={}", MarketType.getName(req.getMarket()), req.getCode(), seqNo);
    }

    @Override
    public void onReply_GetCompanyExecutiveBackground(FTAPI_Conn client, int nSerialNo, QotGetCompanyExecutiveBackground.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询公司高管背景失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询公司高管背景失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询公司高管背景", ftGrpcReturnResult);
                CompanyExecutiveBackgroundContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), CompanyExecutiveBackgroundContent.class);
                eventPublisher.publishEvent(new CompanyExecutiveBackgroundUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询公司高管背景结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询公司高管背景结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询公司高管背景有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncCompanyExecutives(CompanyExecutivesWsMessage req) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setCode(req.getCode())
                .setMarket(req.getMarket())
                .build();
        QotGetCompanyExecutives.C2S c2s = QotGetCompanyExecutives.C2S.newBuilder()
                .setSecurity(sec)
                .build();
        QotGetCompanyExecutives.Request request = QotGetCompanyExecutives.Request.newBuilder().setC2S(c2s).build();
        int seqNo = qot.getCompanyExecutives(request);
        LOGGER.info("市场{},code={}查询公司高管信息.seq={}", MarketType.getName(req.getMarket()), req.getCode(), seqNo);
    }

    @Override
    public void onReply_GetCompanyExecutives(FTAPI_Conn client, int nSerialNo, QotGetCompanyExecutives.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询公司高管信息失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询公司高管信息失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询公司高管信息", ftGrpcReturnResult);
                CompanyExecutivesContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), CompanyExecutivesContent.class);
                eventPublisher.publishEvent(new CompanyExecutivesUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询公司高管信息结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询公司高管信息结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询公司高管信息有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncCompanyProfile(CompanyProfileWsMessage req) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setCode(req.getCode())
                .setMarket(req.getMarket())
                .build();
        QotGetCompanyProfile.C2S c2s = QotGetCompanyProfile.C2S.newBuilder()
                .setSecurity(sec)
                .build();
        QotGetCompanyProfile.Request request = QotGetCompanyProfile.Request.newBuilder().setC2S(c2s).build();
        int seqNo = qot.getCompanyProfile(request);
        LOGGER.info("市场{},code={}查询公司概况.seq={}", MarketType.getName(req.getMarket()), req.getCode(), seqNo);
    }

    @Override
    public void onReply_GetCompanyProfile(FTAPI_Conn client, int nSerialNo, QotGetCompanyProfile.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询公司概况失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询公司概况失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询公司概况", ftGrpcReturnResult);
                CompanyProfileContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), CompanyProfileContent.class);
                eventPublisher.publishEvent(new CompanyProfileUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询公司概况结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询公司概况结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询公司概况有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncInsiderTradeList(InsiderTradeListWsMessage req) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setCode(req.getCode())
                .setMarket(req.getMarket())
                .build();
        QotGetInsiderTradeList.C2S.Builder c2sBuilder = QotGetInsiderTradeList.C2S.newBuilder()
                .setSecurity(sec);
        if (Objects.nonNull(req.getHolderId())) {
            c2sBuilder.setHolderId(req.getHolderId());
        }
        if (Objects.nonNull(req.getNextKey())) {
            c2sBuilder.setNextKey(req.getNextKey());
        }
        if (Objects.nonNull(req.getNum())) {
            c2sBuilder.setNum(req.getNum());
        }
        QotGetInsiderTradeList.Request request = QotGetInsiderTradeList.Request.newBuilder()
                .setC2S(c2sBuilder.build()).build();
        int seqNo = qot.getInsiderTradeList(request);
        LOGGER.info("市场{},code={}查询内部人交易.seq={}", MarketType.getName(req.getMarket()), req.getCode(), seqNo);
    }

    @Override
    public void onReply_GetInsiderTradeList(FTAPI_Conn client, int nSerialNo, QotGetInsiderTradeList.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询内部人交易失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询内部人交易失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询内部人交易", ftGrpcReturnResult);
                InsiderTradeListContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), InsiderTradeListContent.class);
                eventPublisher.publishEvent(new InsiderTradeUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询内部人交易结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询内部人交易结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询内部人交易有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncInsiderHolderList(InsiderHolderListWsMessage req) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setCode(req.getCode())
                .setMarket(req.getMarket())
                .build();
        QotGetInsiderHolderList.C2S.Builder c2sBuilder = QotGetInsiderHolderList.C2S.newBuilder()
                .setSecurity(sec);
        if (Objects.nonNull(req.getNextKey())) {
            c2sBuilder.setNextKey(req.getNextKey());
        }
        if (Objects.nonNull(req.getNum())) {
            c2sBuilder.setNum(req.getNum());
        }
        QotGetInsiderHolderList.Request request = QotGetInsiderHolderList.Request.newBuilder()
                .setC2S(c2sBuilder.build()).build();
        int seqNo = qot.getInsiderHolderList(request);
        LOGGER.info("市场{},code={}查询内部人持股列表.seq={}", MarketType.getName(req.getMarket()), req.getCode(), seqNo);
    }

    @Override
    public void onReply_GetInsiderHolderList(FTAPI_Conn client, int nSerialNo, QotGetInsiderHolderList.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询内部人持股列表失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询内部人持股列表失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询内部人持股列表", ftGrpcReturnResult);
                InsiderHolderListContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), InsiderHolderListContent.class);
                eventPublisher.publishEvent(new InsiderHolderListUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询内部人持股列表结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询内部人持股列表结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询内部人持股列表有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncShareholderInstitutional(ShareholderInstitutionalWsMessage req) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setCode(req.getCode())
                .setMarket(req.getMarket())
                .build();
        QotGetShareholdersInstitutional.C2S.Builder c2sBuilder = QotGetShareholdersInstitutional.C2S.newBuilder()
                .setSecurity(sec);
        if (Objects.nonNull(req.getNextKey())) {
            c2sBuilder.setNextKey(req.getNextKey());
        }
        if (Objects.nonNull(req.getNum())) {
            c2sBuilder.setNum(req.getNum());
        }
        QotGetShareholdersInstitutional.Request request = QotGetShareholdersInstitutional.Request.newBuilder()
                .setC2S(c2sBuilder.build()).build();
        int seqNo = qot.getShareholdersInstitutional(request);
        LOGGER.info("市场{},code={}查询机构持股.seq={}", MarketType.getName(req.getMarket()), req.getCode(), seqNo);
    }

    @Override
    public void onReply_GetShareholdersInstitutional(FTAPI_Conn client, int nSerialNo, QotGetShareholdersInstitutional.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询机构持股失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询机构持股失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询机构持股", ftGrpcReturnResult);
                ShareholderInstitutionalContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), ShareholderInstitutionalContent.class);
                eventPublisher.publishEvent(new ShareholderInstitutionalUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询机构持股结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询机构持股结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询机构持股有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncShareholderHolderDetail(ShareholderHolderDetailWsMessage req) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setCode(req.getCode())
                .setMarket(req.getMarket())
                .build();
        QotGetShareholdersHolderDetail.C2S.Builder c2sBuilder = QotGetShareholdersHolderDetail.C2S.newBuilder()
                .setSecurity(sec);
        if (Objects.nonNull(req.getRequestType())) {
            c2sBuilder.setRequestType(QotCommon.HolderDetailType.forNumber(req.getRequestType()));
        }
        if (Objects.nonNull(req.getNextKey())) {
            c2sBuilder.setNextKey(req.getNextKey());
        }
        if (Objects.nonNull(req.getNum())) {
            c2sBuilder.setNum(req.getNum());
        }
        if (Objects.nonNull(req.getSortColumn())) {
            c2sBuilder.setSortColumn(QotCommon.SortField.forNumber(req.getSortColumn()));
        }
        if (Objects.nonNull(req.getSortType())) {
            c2sBuilder.setSortType(QotCommon.SortType.forNumber(req.getSortType()));
        }
        if (Objects.nonNull(req.getPeriodId())) {
            c2sBuilder.setPeriodId(req.getPeriodId());
        }
        if (Objects.nonNull(req.getHolderId())) {
            c2sBuilder.setHolderId(req.getHolderId());
        }
        QotGetShareholdersHolderDetail.Request request = QotGetShareholdersHolderDetail.Request.newBuilder()
                .setC2S(c2sBuilder.build()).build();
        int seqNo = qot.getShareholdersHolderDetail(request);
        LOGGER.info("市场{},code={}查询持股明细.seq={}", MarketType.getName(req.getMarket()), req.getCode(), seqNo);
    }

    @Override
    public void onReply_GetShareholdersHolderDetail(FTAPI_Conn client, int nSerialNo, QotGetShareholdersHolderDetail.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询持股明细失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询持股明细失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询持股明细", ftGrpcReturnResult);
                ShareholderHolderDetailContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), ShareholderHolderDetailContent.class);
                eventPublisher.publishEvent(new ShareholderHolderDetailUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询持股明细结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询持股明细结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询持股明细有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncShareholderHoldingChange(ShareholderHoldingChangeWsMessage req) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setCode(req.getCode())
                .setMarket(req.getMarket())
                .build();
        QotGetShareholdersHoldingChanges.C2S.Builder c2sBuilder = QotGetShareholdersHoldingChanges.C2S.newBuilder()
                .setSecurity(sec);
        if (Objects.nonNull(req.getNextKey())) {
            c2sBuilder.setNextKey(req.getNextKey());
        }
        if (Objects.nonNull(req.getNum())) {
            c2sBuilder.setNum(req.getNum());
        }
        if (Objects.nonNull(req.getSortType())) {
            c2sBuilder.setSortType(
                    QotCommon.SortType.forNumber(req.getSortType())
            );
        }
        if (Objects.nonNull(req.getSortColumn())) {
            c2sBuilder.setSortColumn(
                    QotCommon.SortField.forNumber(req.getSortColumn())
            );
        }
        if (Objects.nonNull(req.getFilterType())) {
            c2sBuilder.setFilterType(
                    QotCommon.HoldingChangesFilterType.forNumber(req.getFilterType())
            );
        }
        QotGetShareholdersHoldingChanges.Request request = QotGetShareholdersHoldingChanges.Request.newBuilder()
                .setC2S(c2sBuilder.build()).build();
        int seqNo = qot.getShareholdersHoldingChanges(request);
        LOGGER.info("市场{},code={}查询持股变动.seq={}", MarketType.getName(req.getMarket()), req.getCode(), seqNo);
    }

    @Override
    public void onReply_GetShareholdersHoldingChanges(FTAPI_Conn client, int nSerialNo, QotGetShareholdersHoldingChanges.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询持股变动失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询持股变动失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询持股变动", ftGrpcReturnResult);
                ShareholderHoldingChangeContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), ShareholderHoldingChangeContent.class);
                eventPublisher.publishEvent(new ShareholderHoldingChangeUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询持股变动结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询持股变动结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询持股变动有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncShareholderOvr(ShareholderOvrWsMessage req) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setCode(req.getCode())
                .setMarket(req.getMarket())
                .build();
        QotGetShareholdersOverview.C2S.Builder c2sBuilder = QotGetShareholdersOverview.C2S.newBuilder()
                .setSecurity(sec);
        if (Objects.nonNull(req.getPeriodId())) {
            c2sBuilder.setPeriodId(req.getPeriodId());
        }
        QotGetShareholdersOverview.Request request = QotGetShareholdersOverview.Request.newBuilder()
                .setC2S(c2sBuilder.build()).build();
        int seqNo = qot.getShareholdersOverview(request);
        LOGGER.info("市场{},code={}查询持股统计.seq={}", MarketType.getName(req.getMarket()), req.getCode(), seqNo);
    }

    @Override
    public void onReply_GetShareholdersOverview(FTAPI_Conn client, int nSerialNo, QotGetShareholdersOverview.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询持股统计失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询持股统计失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询持股统计", ftGrpcReturnResult);
                ShareholderOvrContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), ShareholderOvrContent.class);
                eventPublisher.publishEvent(new ShareholderOvrUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询持股统计结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询持股统计结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询持股统计有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncCorporateActionsStockSplits(CorporateActionsStockSplitsWsMessage req) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setCode(req.getCode())
                .setMarket(req.getMarket())
                .build();
        QotGetCorporateActionsStockSplits.C2S.Builder c2sBuilder = QotGetCorporateActionsStockSplits.C2S.newBuilder()
                .setSecurity(sec);
        if (Objects.nonNull(req.getNextKey())) {
            c2sBuilder.setNextKey(req.getNextKey());
        }
        if (Objects.nonNull(req.getNum())) {
            c2sBuilder.setNum(req.getNum());
        }
        QotGetCorporateActionsStockSplits.Request request = QotGetCorporateActionsStockSplits.Request.newBuilder()
                .setC2S(c2sBuilder.build()).build();
        int seqNo = qot.getCorporateActionsStockSplits(request);
        LOGGER.info("市场{},code={}查询拆合股.seq={}", MarketType.getName(req.getMarket()), req.getCode(), seqNo);
    }

    @Override
    public void onReply_GetCorporateActionsStockSplits(FTAPI_Conn client, int nSerialNo, QotGetCorporateActionsStockSplits.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询拆合股失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询拆合股失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询拆合股", ftGrpcReturnResult);
                CorporateActionsStockSplitsContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), CorporateActionsStockSplitsContent.class);
                eventPublisher.publishEvent(new CorporateActionsStockSplitsUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询拆合股结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询拆合股结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询拆合股有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncCorporateActionsBuyback(CorporateActionsBuybackWsMessage req) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setCode(req.getCode())
                .setMarket(req.getMarket())
                .build();
        QotGetCorporateActionsBuybacks.C2S.Builder c2sBuilder = QotGetCorporateActionsBuybacks.C2S.newBuilder()
                .setSecurity(sec);
        if (Objects.nonNull(req.getNextKey())) {
            c2sBuilder.setNextKey(req.getNextKey());
        }
        if (Objects.nonNull(req.getNum())) {
            c2sBuilder.setNum(req.getNum());
        }
        QotGetCorporateActionsBuybacks.Request request = QotGetCorporateActionsBuybacks.Request.newBuilder()
                .setC2S(c2sBuilder.build()).build();
        int seqNo = qot.getCorporateActionsBuybacks(request);
        LOGGER.info("市场{},code={}查询回购.seq={}", MarketType.getName(req.getMarket()), req.getCode(), seqNo);
    }

    @Override
    public void onReply_GetCorporateActionsBuybacks(FTAPI_Conn client, int nSerialNo, QotGetCorporateActionsBuybacks.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询回购失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询回购失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询回购", ftGrpcReturnResult);
                CorporateActionsBuybackContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), CorporateActionsBuybackContent.class);
                eventPublisher.publishEvent(new CorporateActionsBuybackUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询回购结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询回购结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询回购有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncCorporateActionsDividend(CorporateActionsDividendsWsMessage req) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setCode(req.getCode())
                .setMarket(req.getMarket())
                .build();
        QotGetCorporateActionsDividends.C2S c2s = QotGetCorporateActionsDividends.C2S.newBuilder()
                .setSecurity(sec)
                .build();
        QotGetCorporateActionsDividends.Request request = QotGetCorporateActionsDividends.Request.newBuilder()
                .setC2S(c2s).build();
        int seqNo = qot.getCorporateActionsDividends(request);
        LOGGER.info("市场{},code={}查询分红派息.seq={}", MarketType.getName(req.getMarket()), req.getCode(), seqNo);
    }

    @Override
    public void onReply_GetCorporateActionsDividends(FTAPI_Conn client, int nSerialNo, QotGetCorporateActionsDividends.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询分红派息失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询分红派息失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询分红派息", ftGrpcReturnResult);
                List<CorporateActionsDividendContent> contents = GSON.fromJson(ftGrpcReturnResult.getS2c().get("dividendList").getAsJsonArray(), new TypeToken<List<CorporateActionsDividendContent>>() {
                }.getType());
                eventPublisher.publishEvent(new CorporateActionsDividendUpdateEvent(contents));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询分红派息结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询分红派息结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询分红派息有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncValuationPlateStockList(ValuationPlateStockListWsMessage req) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setCode(req.getCode())
                .setMarket(req.getMarket())
                .build();
        QotGetValuationPlateStockList.C2S.Builder c2sBuilder = QotGetValuationPlateStockList.C2S.newBuilder()
                .setSecurity(sec);
        if (Objects.nonNull(req.getValuationType())) {
            c2sBuilder.setValuationType(
                    QotCommon.ValuationType.forNumber(req.getValuationType())
            );
        }
        if (Objects.nonNull(req.getNextKey())) {
            c2sBuilder.setNextKey(req.getNextKey());
        }
        if (Objects.nonNull(req.getNum())) {
            c2sBuilder.setNum(req.getNum());
        }
        if (Objects.nonNull(req.getSortType())) {
            c2sBuilder.setSortType(
                    QotCommon.SortType.forNumber(req.getSortType())
            );
        }
        if (Objects.nonNull(req.getSortId())) {
            c2sBuilder.setSortId(
                    QotCommon.SortField.forNumber(req.getSortId())
            );
        }
        if (Objects.nonNull(req.getFilterMarket()) && Objects.nonNull(req.getFilterCode())) {
            QotCommon.Security filterSec = QotCommon.Security.newBuilder()
                    .setCode(req.getFilterCode())
                    .setMarket(req.getFilterMarket())
                    .build();
            c2sBuilder.setFilterSecurity(filterSec);
        }
        QotGetValuationPlateStockList.Request request = QotGetValuationPlateStockList.Request.newBuilder()
                .setC2S(c2sBuilder.build())
                .build();
        int seqNo = qot.getValuationPlateStockList(request);
        LOGGER.info("市场{},code={}查询板块/指数成分股估值列表.seq={}", MarketType.getName(req.getMarket()), req.getCode(), seqNo);
    }

    @Override
    public void onReply_GetValuationPlateStockList(FTAPI_Conn client, int nSerialNo, QotGetValuationPlateStockList.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询板块/指数成分股估值列表失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询板块/指数成分股估值列表失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询板块/指数成分股估值列表", ftGrpcReturnResult);
                ValuationPlateStockListContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), ValuationPlateStockListContent.class);
                eventPublisher.publishEvent(new ValuationPlateStockListUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询板块/指数成分股估值列表结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询板块/指数成分股估值列表结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询板块/指数成分股估值列表有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncValuationDetail(ValuationDetailWsMessage req) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setCode(req.getCode())
                .setMarket(req.getMarket())
                .build();
        QotGetValuationDetail.C2S.Builder c2sBuilder = QotGetValuationDetail.C2S.newBuilder()
                .setSecurity(sec);
        if (Objects.nonNull(req.getValuationType())) {
            c2sBuilder.setValuationType(
                    QotCommon.ValuationType.forNumber(req.getValuationType())
            );
        }
        if (Objects.nonNull(req.getIntervalType())) {
            c2sBuilder.setIntervalType(
                    QotCommon.ValuationIntervalType.forNumber(req.getIntervalType())
            );
        }
        QotGetValuationDetail.Request request = QotGetValuationDetail.Request.newBuilder()
                .setC2S(c2sBuilder.build())
                .build();
        int seqNo = qot.getValuationDetail(request);
        LOGGER.info("市场{},code={}查询个股/指数估值详情.seq={}", MarketType.getName(req.getMarket()), req.getCode(), seqNo);
    }

    @Override
    public void onReply_GetValuationDetail(FTAPI_Conn client, int nSerialNo, QotGetValuationDetail.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询个股/指数估值详情失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询个股/指数估值详情失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询个股/指数估值详情", ftGrpcReturnResult);
                ValuationDetailContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), ValuationDetailContent.class);
                eventPublisher.publishEvent(new ValuationDetailUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询个股/指数估值详情结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询个股/指数估值详情结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询个股/指数估值详情有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncFinancialStatements(FinancialStatementWsMessage req) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setCode(req.getCode())
                .setMarket(req.getMarket())
                .build();
        QotGetFinancialsStatements.C2S.Builder c2sBuilder = QotGetFinancialsStatements.C2S.newBuilder()
                .setSecurity(sec);
        if (Objects.nonNull(req.getStatementType())) {
            c2sBuilder.setStatementType(QotCommon.FinancialStatementsType.forNumber(req.getStatementType()));
        }
        if (Objects.nonNull(req.getFinancialType())) {
            c2sBuilder.setFinancialType(QotCommon.F10Type.forNumber(req.getFinancialType()));
        }
        if (Objects.nonNull(req.getCurrencyCode())) {
            c2sBuilder.setCurrencyCode(req.getCurrencyCode());
        }
        if (Objects.nonNull(req.getNextKey())) {
            c2sBuilder.setNextKey(req.getNextKey());
        }
        if (Objects.nonNull(req.getNum())) {
            c2sBuilder.setNum(req.getNum());
        }
        QotGetFinancialsStatements.Request request = QotGetFinancialsStatements.Request.newBuilder()
                .setC2S(c2sBuilder.build())
                .build();
        int seqNo = qot.getFinancialsStatements(request);
        LOGGER.info("市场{},code={}查询财务报表.seq={}", MarketType.getName(req.getMarket()), req.getCode(), seqNo);
    }

    @Override
    public void onReply_GetFinancialsStatements(FTAPI_Conn client, int nSerialNo, QotGetFinancialsStatements.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询财务报表失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询财务报表失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询财务报表", ftGrpcReturnResult);
                FinancialStatementsContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), FinancialStatementsContent.class);
                eventPublisher.publishEvent(new FinancialStatementsUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询财务报表结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询财务报表结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询财务报表有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncResearchRatingSummary(ResearchRatingSummaryWsMessage req) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setCode(req.getCode())
                .setMarket(req.getMarket())
                .build();
        QotGetResearchRatingSummary.C2S.Builder c2sBuilder = QotGetResearchRatingSummary.C2S.newBuilder()
                .setSecurity(sec);
        if (Objects.nonNull(req.getRatingDimensionType())) {
            c2sBuilder.setRatingDimensionType(
                    QotCommon.ResearchRatingDimensionType.forNumber(req.getRatingDimensionType())
            );
        }
        if (Objects.nonNull(req.getUid())) {
            c2sBuilder.setUid(req.getUid());
        }
        if (Objects.nonNull(req.getNextKey())) {
            c2sBuilder.setNextKey(req.getNextKey());
        }
        if (Objects.nonNull(req.getNum())) {
            c2sBuilder.setNum(req.getNum());
        }
        QotGetResearchRatingSummary.Request request = QotGetResearchRatingSummary.Request.newBuilder()
                .setC2S(c2sBuilder.build())
                .build();
        int seqNo = qot.getResearchRatingSummary(request);
        LOGGER.info("市场{},code={}查询评级汇总.seq={}", MarketType.getName(req.getMarket()), req.getCode(), seqNo);
    }

    @Override
    public void onReply_GetResearchRatingSummary(FTAPI_Conn client, int nSerialNo, QotGetResearchRatingSummary.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询评级汇总失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询评级汇总失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询评级汇总", ftGrpcReturnResult);
                ResearchRatingSummaryContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), ResearchRatingSummaryContent.class);
                eventPublisher.publishEvent(new ResearchRatingSummaryUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询评级汇总结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询评级汇总结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询评级汇总有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncMorningStarReport(MorningstarReportWsMessage req) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setCode(req.getCode())
                .setMarket(req.getMarket())
                .build();
        QotGetResearchMorningstarReport.C2S c2S = QotGetResearchMorningstarReport.C2S.newBuilder()
                .setSecurity(sec)
                .build();
        QotGetResearchMorningstarReport.Request request = QotGetResearchMorningstarReport.Request.newBuilder()
                .setC2S(c2S)
                .build();
        int seqNo = qot.getResearchMorningstarReport(request);
        LOGGER.info("市场{},code={}查询晨星研究报告.seq={}", MarketType.getName(req.getMarket()), req.getCode(), seqNo);
    }

    @Override
    public void onReply_GetResearchMorningstarReport(FTAPI_Conn client,
                                                     int nSerialNo, QotGetResearchMorningstarReport.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询晨星研究报告失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询晨星研究报告失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询晨星研究报告", ftGrpcReturnResult);
                MorningstarReportContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), MorningstarReportContent.class);
                eventPublisher.publishEvent(new MorningstarReportUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询晨星研究报告结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询晨星研究报告结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询晨星研究报告有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncFinancialEarningPriceHistory(FinancialEarningPriceHistoryWsMessage req) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setCode(req.getCode())
                .setMarket(req.getMarket())
                .build();
        QotGetFinancialsEarningsPriceHistory.C2S c2S = QotGetFinancialsEarningsPriceHistory.C2S.newBuilder()
                .setSecurity(sec)
                .build();
        QotGetFinancialsEarningsPriceHistory.Request request = QotGetFinancialsEarningsPriceHistory.Request.newBuilder()
                .setC2S(c2S)
                .build();
        int seqNo = qot.getFinancialsEarningsPriceHistory(request);
        LOGGER.info("市场{},code={}查询财报日前后股价历史.seq={}", MarketType.getName(req.getMarket()), req.getCode(), seqNo);
    }

    @Override
    public void onReply_GetFinancialsEarningsPriceHistory(FTAPI_Conn client,
                                                          int nSerialNo, QotGetFinancialsEarningsPriceHistory.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询财报日前后股价历史失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询财报日前后股价历史失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询财报日前后股价历史", ftGrpcReturnResult);
                List<FinancialEarningPriceHistoryContent> contents = GSON.fromJson(ftGrpcReturnResult.getS2c().get("detailList").getAsJsonArray(), new TypeToken<List<FinancialEarningPriceHistoryContent>>() {
                }.getType());
                eventPublisher.publishEvent(new FinancialEarningPriceHistoryUpdateEvent(contents));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询财报日前后股价历史结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询财报日前后股价历史结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询财报日前后股价历史有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncFinancialEarningMove(FinancialEarningMoveWsMessage req) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setCode(req.getCode())
                .setMarket(req.getMarket())
                .build();
        QotGetFinancialsEarningsPriceMove.C2S.Builder c2sBuilder = QotGetFinancialsEarningsPriceMove.C2S.newBuilder()
                .setSecurity(sec);
        if (Objects.nonNull(req.getPeriodCount())) {
            c2sBuilder.setPeriodCount(req.getPeriodCount());
        }
        QotGetFinancialsEarningsPriceMove.Request request = QotGetFinancialsEarningsPriceMove.Request.newBuilder()
                .setC2S(c2sBuilder.build())
                .build();
        int seqNo = qot.getFinancialsEarningsPriceMove(request);
        LOGGER.info("市场{},code={}查询财报日前后价格涨跌幅表现.seq={}", MarketType.getName(req.getMarket()), req.getCode(), seqNo);
    }

    @Override
    public void onReply_GetFinancialsEarningsPriceMove(FTAPI_Conn client,
                                                       int nSerialNo, QotGetFinancialsEarningsPriceMove.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询财报日前后价格涨跌幅表现失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询财报日前后价格涨跌幅表现失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询财报日前后价格涨跌幅表现", ftGrpcReturnResult);
                List<FinancialEarningMoveContent> contents = GSON.fromJson(ftGrpcReturnResult.getS2c().get("detailList").getAsJsonArray(), new TypeToken<List<FinancialEarningMoveContent>>() {
                }.getType());
                eventPublisher.publishEvent(new FinancialEarningMoveUpdateEvent(contents));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询财报日前后价格涨跌幅表现结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询财报日前后价格涨跌幅表现结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询财报日前后价格涨跌幅表现有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncAnalystConsensus(AnalystConsensusWsMessage req) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setCode(req.getCode())
                .setMarket(req.getMarket())
                .build();
        QotGetResearchAnalystConsensus.C2S c2S = QotGetResearchAnalystConsensus.C2S.newBuilder()
                .setSecurity(sec)
                .build();
        QotGetResearchAnalystConsensus.Request request = QotGetResearchAnalystConsensus.Request.newBuilder()
                .setC2S(c2S)
                .build();
        int seqNo = qot.getResearchAnalystConsensus(request);
        LOGGER.info("市场{},code={}查询分析师评级概述.seq={}", MarketType.getName(req.getMarket()), req.getCode(), seqNo);
    }

    @Override
    public void onReply_GetResearchAnalystConsensus(FTAPI_Conn client, int nSerialNo, QotGetResearchAnalystConsensus.
            Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询分析师评级概述失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询分析师评级概述失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询分析师评级概述", ftGrpcReturnResult);
                AnalystConsensusContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), AnalystConsensusContent.class);
                eventPublisher.publishEvent(new AnalystConsensusUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询分析师评级概述解析结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询分析师评级概述解析结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询分析师评级概述有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void syncFinancialRevenueBreakDown(FinancialReWsMessage req) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setMarket(req.getMarket())
                .setCode(req.getCode())
                .build();
        QotGetFinancialsRevenueBreakdown.C2S.Builder c2SBuilder = QotGetFinancialsRevenueBreakdown.C2S.newBuilder();
        c2SBuilder.setSecurity(sec);
        if (Objects.nonNull(req.getDate())) {
            c2SBuilder.setDate(req.getDate());
        }
        if (Objects.nonNull(req.getFinancialType())) {
            c2SBuilder.setFinancialType(QotCommon.F10Type.forNumber(req.getFinancialType()));
        }
        if (Objects.nonNull(req.getCurrencyCode())) {
            c2SBuilder.setCurrencyCode(req.getCurrencyCode());
        }
        QotGetFinancialsRevenueBreakdown.Request request = QotGetFinancialsRevenueBreakdown.Request.newBuilder()
                .setC2S(c2SBuilder.build()).build();
        int seqNo = qot.getFinancialsRevenueBreakdown(request);
        LOGGER.info("市场{},code={}查询主营构成.seq={}", MarketType.getName(req.getMarket()), req.getCode(), seqNo);

    }

    @Override
    public void onReply_GetFinancialsRevenueBreakdown(FTAPI_Conn client,
                                                      int nSerialNo, QotGetFinancialsRevenueBreakdown.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询主营构成失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询主营构成失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询主营构成", ftGrpcReturnResult);
                FinancialRevenueBreakDownContent content = GSON.fromJson(ftGrpcReturnResult.getS2c(), FinancialRevenueBreakDownContent.class);
                eventPublisher.publishEvent(new FinancialRevenueBreakDownUpdateEvent(content));
            } catch (InvalidProtocolBufferException e) {
                String errMsg = "查询主营构成解析结果失败.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (NullPointerException e) {
                String errMsg = "查询主营构成解析结果空指针.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            } catch (Exception e) {
                String errMsg = "查询主营构成有其他错误.";
                LOGGER.error(errMsg, e);
                sendNotifyMessage(errMsg);
            }
        }
    }

    public void sendGetIpoRequest(GetIpoWsMessage req) {
        QotGetIpoList.C2S c2S = QotGetIpoList.C2S.newBuilder()
                .setMarket(req.getMarket())
                .build();
        QotGetIpoList.Request request = QotGetIpoList.Request.newBuilder()
                .setC2S(c2S)
                .build();
        int seqNo = qot.getIpoList(request);
        CommonSecurity commonSecurity = new CommonSecurity(req.getMarket(), null);
        CacheManager.put(String.valueOf(seqNo), commonSecurity);
        LOGGER.info("{}市场获取IPO列表.seq={}", MarketType.getName(req.getMarket()), seqNo);
    }

    public void sendGetReminderRequest(GetPriceReminderWsMessage request) {
        QotGetPriceReminder.C2S.Builder c2s = QotGetPriceReminder.C2S.newBuilder();
        if (Objects.nonNull(request.getSecMarket()) && Objects.nonNull(request.getCode())) {
            QotCommon.Security sec = QotCommon.Security.newBuilder()
                    .setMarket(request.getSecMarket())
                    .setCode(request.getCode())
                    .build();
            c2s.setSecurity(sec);
        }
        if (Objects.nonNull(request.getMarket())) {
            c2s.setMarket(request.getMarket());
        }
        QotGetPriceReminder.Request req = QotGetPriceReminder.Request.newBuilder()
                .setC2S(c2s.build())
                .build();
        int seqNo = qot.getPriceReminder(req);
        LOGGER.info("获取到价提醒列表.seq={}", seqNo);
    }

    private void logFTResult(String desc, FTGrpcReturnResult result) {
        LOGGER.info("{}返回结果:{}", desc, result.toString());
    }

    @Override
    public void onReply_GetPriceReminder(FTAPI_Conn client, int nSerialNo, QotGetPriceReminder.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询到价提醒列表失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询到价提醒列表失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("查询到价提醒列表", ftGrpcReturnResult);
                PriceReminderContent result = GSON.fromJson(ftGrpcReturnResult.getS2c(), PriceReminderContent.class);
                sendGetPriceReminderList(result);
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("查询到价提醒列表解析结果失败!", e);
            }
        }
    }

    private void sendGetPriceReminderList(PriceReminderContent priceReminderContent) {
        GetPriceReminderWsMessage message = new GetPriceReminderWsMessage();
        message.setPriceReminderList(priceReminderContent.getPriceReminderList());
        quantxFutuWsService.sendGetPriceReminderListMessage(message);
    }

    @Override
    public void onReply_SetPriceReminder(FTAPI_Conn client, int nSerialNo, QotSetPriceReminder.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "设置到价提醒失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "设置到价提醒失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                logFTResult("设置到价提醒", ftGrpcReturnResult);
                SetPriceReminderContent setPriceResult = GSON.fromJson(ftGrpcReturnResult.getS2c(), SetPriceReminderContent.class);
                sendNotifyMessage("设置到价提醒结果:" + setPriceResult.getKey());
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("设置到价提醒解析结果失败!", e);
            }
        }
    }

    @Override
    public void onReply_GetSecuritySnapshot(FTAPI_Conn client, int nSerialNo, QotGetSecuritySnapshot.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询快照数据失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询快照数据失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                List<SnapshotContent> snapshotContents = GSON.fromJson(ftGrpcReturnResult.getS2c().getAsJsonArray("snapshotList"), new TypeToken<List<SnapshotContent>>() {
                }.getType());
                eventPublisher.publishEvent(new SnapshotUpdateEvent(snapshotContents));
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("查询快照数据解析结果失败!", e);
            } catch (NullPointerException e) {
                LOGGER.error("查询快照数据回调空指针异常!", e);
            }
        }
    }

}
