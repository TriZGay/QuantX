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
    public void onReply_GetFinancialsEarningsPriceMove(FTAPI_Conn client, int nSerialNo, QotGetFinancialsEarningsPriceMove.Response rsp) {
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
    public void onReply_GetResearchAnalystConsensus(FTAPI_Conn client, int nSerialNo, QotGetResearchAnalystConsensus.Response rsp) {
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
    public void onReply_GetFinancialsRevenueBreakdown(FTAPI_Conn client, int nSerialNo, QotGetFinancialsRevenueBreakdown.Response rsp) {
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
