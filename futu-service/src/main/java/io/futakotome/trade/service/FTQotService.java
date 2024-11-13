package io.futakotome.trade.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.futu.openapi.*;
import com.futu.openapi.pb.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.futakotome.common.MessageCommon;
import io.futakotome.common.message.*;
import io.futakotome.trade.config.FutuConfig;
import io.futakotome.trade.controller.vo.*;
import io.futakotome.trade.domain.*;
import io.futakotome.trade.dto.*;
import io.futakotome.trade.mapper.*;
import io.futakotome.trade.message.*;
import io.futakotome.trade.utils.CacheManager;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private static final MarketAggregator market = new MarketAggregator();

    private final PlateDtoMapper plateMapper;
    private final StockDtoMapper stockMapper;
    private final PlateStockDtoMapper plateStockMapper;
    private final IpoHkDtoMapper ipoHkMapper;
    private final IpoUsDtoMapper ipoUsMapper;
    private final IpoCnDtoMapper ipoCnMapper;
    private final IpoCnExWinningDtoMapper ipoCnExWinningMapper;
    private final SubDtoMapper subDtoMapper;
    private final TradeDateDtoMapper tradeDateDtoMapper;
    private final CapitalDistributionDtoMapper capitalDistributionDtoMapper;
    private final FutuConfig futuConfig;
    private final RocketMQTemplate rocketMQTemplate;

    private static final String clientID = "javaclient";

    public static final FTAPI_Conn_Qot qot = new FTAPI_Conn_Qot();

    public FTQotService(PlateDtoMapper plateMapper, StockDtoMapper stockMapper, PlateStockDtoMapper plateStockMapper,
                        IpoHkDtoMapper ipoHkMapper, IpoUsDtoMapper ipoUsMapper, IpoCnDtoMapper ipoCnMapper, IpoCnExWinningDtoMapper ipoCnExWinningMapper,
                        SubDtoMapper subDtoMapper, TradeDateDtoMapper tradeDateDtoMapper, CapitalDistributionDtoMapper capitalDistributionDtoMapper, FutuConfig futuConfig, RocketMQTemplate rocketMQTemplate) {
        qot.setClientInfo(clientID, 1);
        qot.setConnSpi(this);
        qot.setQotSpi(this);
        this.subDtoMapper = subDtoMapper;
        this.futuConfig = futuConfig;
        this.plateMapper = plateMapper;
        this.stockMapper = stockMapper;
        this.plateStockMapper = plateStockMapper;
        this.ipoHkMapper = ipoHkMapper;
        this.ipoUsMapper = ipoUsMapper;
        this.ipoCnMapper = ipoCnMapper;
        this.ipoCnExWinningMapper = ipoCnExWinningMapper;
        this.rocketMQTemplate = rocketMQTemplate;
        this.tradeDateDtoMapper = tradeDateDtoMapper;
        this.capitalDistributionDtoMapper = capitalDistributionDtoMapper;
    }

    @Deprecated
    public void syncPlateInfo() {
        market.sendPlateInfoRequest();
    }

    @Deprecated
    public void syncStockInfo() {
        market.sendStockInfoRequest(plateMapper);
    }

    //    @Scheduled(cron = "0 0 0 * * *")
    public void syncStaticInfo() {
        market.sendStaticInfoRequest();
    }

    //    @Scheduled(cron = "0 0 1 * * *")
    public void syncStockOwnerPlateInfo() {
        market.sendPlateInfoRequest(stockMapper);
    }

    //    @Scheduled(cron = "0 0 2 * * *")
    public void syncIpoInfo() {
        market.sendIpoInfoRequest();
    }

    public void syncTradeDate() {
        market.sendTradeDateRequest();
    }

    public void sendSubInfoRequest() {
        QotGetSubInfo.Request request = QotGetSubInfo.Request.newBuilder()
                .setC2S(QotGetSubInfo.C2S.newBuilder()
                        .setIsReqAllConn(true)
                        .build())
                .build();
        int seqNo = qot.getSubInfo(request);
        LOGGER.info("查询订阅信息.seqNo=" + seqNo);
    }

    public void syncCapitalFlow(SyncCapitalFlowRequest request) {
        QotCommon.Security security = QotCommon.Security.newBuilder()
                .setMarket(request.getMarket())
                .setCode(request.getCode())
                .build();
        QotGetCapitalFlow.Request.Builder ftRequest = QotGetCapitalFlow.Request.newBuilder();
        if (request.getPeriodType() == 1) {
            //实时
            ftRequest.setC2S(QotGetCapitalFlow.C2S.newBuilder()
                    .setPeriodType(request.getPeriodType())
                    .setSecurity(security)
                    .build());
        } else {
            ftRequest.setC2S(QotGetCapitalFlow.C2S.newBuilder()
                    .setPeriodType(request.getPeriodType())
                    .setSecurity(security)
                    .setBeginTime(request.getBeginDate())
                    .setEndTime(request.getEndDate())
                    .build());
        }
        int seqNo = qot.getCapitalFlow(ftRequest.build());
        String marketAndCode = request.getMarket() + "-" + request.getCode();
        CacheManager.put(String.valueOf(seqNo), marketAndCode);
        LOGGER.info("请求资金流向.seqNo=" + seqNo);
    }

    public void syncCapitalDistribution(SyncCapitalDistributionRequest distributionRequest) {
        QotGetCapitalDistribution.Request request = QotGetCapitalDistribution.Request.newBuilder()
                .setC2S(QotGetCapitalDistribution.C2S.newBuilder()
                        .setSecurity(QotCommon.Security.newBuilder()
                                .setMarket(distributionRequest.getMarket())
                                .setCode(distributionRequest.getCode())
                                .build())
                        .build())
                .build();
        int seqNo = qot.getCapitalDistribution(request);
        String marketAndCode = distributionRequest.getMarket() + "-" + distributionRequest.getCode();
        CacheManager.put(String.valueOf(seqNo), marketAndCode);
        LOGGER.info("请求资金分布.seqNo=" + seqNo);
    }

    public void cancelSubscribe(SubscribeRequest subscribeRequest) {
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
        LOGGER.info("取消订阅.seqNo=" + seqNo);
    }

    public void subscribeRequest(SubscribeRequest subscribeRequest) {
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
            requestBuilder.setC2S(QotSub.C2S.newBuilder()
                    .addAllSubTypeList(subscribeRequest.getSubTypeList())
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
        }
        int seqNo = qot.sub(requestBuilder.build());
        //下面订阅成功之后再拿出来插入订阅信息
        CacheManager.put(String.valueOf(seqNo), subscribeRequest);
        LOGGER.info("发起订阅.seqNo=" + seqNo);
    }

    public void sendHistoryKLineRequest(SyncHistoryKRequest syncHistoryKRequest) {
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
                    LOGGER.info("查询历史K线数据.seqNo=" + seqNo);
                });
    }

    public void sendHistoryKLineDetailRequest() {
        QotRequestHistoryKLQuota.Request request = QotRequestHistoryKLQuota.Request.newBuilder()
                .setC2S(QotRequestHistoryKLQuota.C2S.newBuilder().setBGetDetail(true).build())
                .build();
        int seqNo = qot.requestHistoryKLQuota(request);
        LOGGER.info("查询历史K线数据额度明细.seq=" + seqNo);
    }

    public void sendGlobalMarketStateRequest() {
        GetGlobalState.Request request = GetGlobalState.Request.newBuilder()
                .setC2S(GetGlobalState.C2S.newBuilder()
                        .setUserID(0)
                        .build())
                .build();
        int seqNo = qot.getGlobalState(request);
        LOGGER.info("查询全局市场状态.seq=" + seqNo);
    }

    public void sendRehabRequest(SyncRehabRequest syncRehabRequest) {
        QotRequestRehab.Request request = QotRequestRehab.Request.newBuilder()
                .setC2S(QotRequestRehab.C2S.newBuilder()
                        .setSecurity(QotCommon.Security.newBuilder()
                                .setMarket(syncRehabRequest.getMarket())
                                .setCode(syncRehabRequest.getCode())
                                .build())
                        .build())
                .build();
        int seqNo = qot.requestRehab(request);
        String marketAndCode = syncRehabRequest.getMarket() + "-" + syncRehabRequest.getCode();
        CacheManager.put(String.valueOf(seqNo), marketAndCode);
        LOGGER.info("查询复权因子.seq=" + seqNo);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.connect();
        this.subscribeOnStartup();
    }

    private void subscribeOnStartup() {
        List<SubDto> subscribeInfos = this.subDtoMapper.selectList(null);
        if (subscribeInfos.size() > 0) {
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
                            this.subscribeRequest(new SubscribeRequest(Collections.singletonList(subscribeSecurity),
                                    groupBySecurity.get(subscribeSecurity))));
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
        String content = "FUTU API 初始化连接 onInitConnect: ret=" + errCode + ",desc=" + desc + ",connID=" + client.getConnectID();
        LOGGER.info(content);
        sendNotifyMessage(content);
    }

    @Override
    public void onDisconnect(FTAPI_Conn client, long errCode) {
        String content = "FUTU API 关闭连接 onDisconnect: connID=" + client.getConnectID() + ",ret=" + errCode;
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
                    sendKLMessage(klMessageContent);
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
            String[] marketAndCode = ((String) CacheManager.get(String.valueOf(nSerialNo))).split("-");
            Integer market = Integer.valueOf(marketAndCode[0]);
            String code = marketAndCode[1];
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                Iterator<JsonElement> rehabListIterator = ftGrpcReturnResult.getS2c().get("rehabList").getAsJsonArray().iterator();
                while (rehabListIterator.hasNext()) {
                    JsonObject rehab = rehabListIterator.next().getAsJsonObject();
                    RehabMessageContent messageContent = GSON.fromJson(rehab, RehabMessageContent.class);
                    messageContent.setMarket(market);
                    messageContent.setCode(code);
                    sendRehabMessage(messageContent);
                }
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
                String[] marketAndCode = ((String) CacheManager.get(String.valueOf(nSerialNo))).split("-");
                Integer market = Integer.valueOf(marketAndCode[0]);
                String code = marketAndCode[1];
                Iterator<JsonElement> flowItemListIterator = ftGrpcReturnResult.getS2c().get("flowItemList").getAsJsonArray().iterator();
                String lastValidTime = ftGrpcReturnResult.getS2c().get("lastValidTime").getAsString();
                // 历史周期 会断开连接?
                while (flowItemListIterator.hasNext()) {
                    JsonObject flowItem = flowItemListIterator.next().getAsJsonObject();
                    CapitalFlowMessageContent messageContent = GSON.fromJson(flowItem, CapitalFlowMessageContent.class);
                    messageContent.setMarket(market);
                    messageContent.setCode(code);
                    messageContent.setLastValidTime(lastValidTime);
                    sendCapitalFlowMessage(messageContent);
                }
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("解析资金流向结果失败.", e);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onReply_GetCapitalDistribution(FTAPI_Conn client, int nSerialNo, QotGetCapitalDistribution.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "获取资金分布失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("connID=" + client.getConnectID() + "获取资金分布失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            String[] marketAndCode = ((String) CacheManager.get(String.valueOf(nSerialNo))).split("-");
            Integer market = Integer.valueOf(marketAndCode[0]);
            String code = marketAndCode[1];
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                CapitalDistributionDto capitalDistributionDto = new CapitalDistributionDto();
                capitalDistributionDto.setMarket(market);
                capitalDistributionDto.setCode(code);

                capitalDistributionDto.setCapitalInSuper(ftGrpcReturnResult.getS2c().get("capitalInSuper").getAsDouble());
                capitalDistributionDto.setCapitalInBig(ftGrpcReturnResult.getS2c().get("capitalInBig").getAsDouble());
                capitalDistributionDto.setCapitalInMid(ftGrpcReturnResult.getS2c().get("capitalInMid").getAsDouble());
                capitalDistributionDto.setCapitalInSmall(ftGrpcReturnResult.getS2c().get("capitalInSmall").getAsDouble());

                capitalDistributionDto.setCapitalOutSuper(ftGrpcReturnResult.getS2c().get("capitalOutSuper").getAsDouble());
                capitalDistributionDto.setCapitalOutBig(ftGrpcReturnResult.getS2c().get("capitalOutBig").getAsDouble());
                capitalDistributionDto.setCapitalOutMid(ftGrpcReturnResult.getS2c().get("capitalOutMid").getAsDouble());
                capitalDistributionDto.setCapitalOutSmall(ftGrpcReturnResult.getS2c().get("capitalOutSmall").getAsDouble());
                capitalDistributionDto.setUpdateTime(LocalDateTime.parse(ftGrpcReturnResult.getS2c().get("updateTime").getAsString(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                CapitalDistributionDto hasOne = capitalDistributionDtoMapper.selectOne(Wrappers.query(new CapitalDistributionDto())
                        .eq("market", market)
                        .eq("code", code));
                if (hasOne == null) {
                    //不存在  新增
                    int insertRow = capitalDistributionDtoMapper.insertSelective(capitalDistributionDto);
                    if (insertRow > 0) {
                        String notify = "资金分布结果入库成功.";
                        LOGGER.info(notify);
                        sendNotifyMessage(notify);
                    }
                } else {
                    //存在更新
                    hasOne.setCapitalInSuper(capitalDistributionDto.getCapitalInSuper());
                    hasOne.setCapitalInBig(capitalDistributionDto.getCapitalInBig());
                    hasOne.setCapitalInMid(capitalDistributionDto.getCapitalInMid());
                    hasOne.setCapitalInSmall(capitalDistributionDto.getCapitalInSmall());
                    hasOne.setCapitalOutSuper(capitalDistributionDto.getCapitalOutSuper());
                    hasOne.setCapitalOutBig(capitalDistributionDto.getCapitalOutBig());
                    hasOne.setCapitalOutMid(capitalDistributionDto.getCapitalOutMid());
                    hasOne.setCapitalOutSmall(capitalDistributionDto.getCapitalOutSmall());
                    hasOne.setUpdateTime(capitalDistributionDto.getUpdateTime());
                    int updateRow = capitalDistributionDtoMapper.updateById(hasOne);
                    if (updateRow > 0) {
                        //todo ws通知
                        String notify = "资金分布结果更新成功.";
                        LOGGER.info(notify);
                        sendNotifyMessage(notify);
                    }
                }
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("解析资金分布结果失败.", e);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onReply_RequestTradeDate(FTAPI_Conn client, int nSerialNo, QotRequestTradeDate.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "获取交易日失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("connID=" + client.getConnectID() + "获取交易日失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                Object marketType = CacheManager.get(String.valueOf(nSerialNo));
                Iterator<JsonElement> tradeDateList = ftGrpcReturnResult.getS2c().getAsJsonArray("tradeDateList").iterator();
                if (marketType instanceof Integer) {
                    Integer tradeDateMarket = (Integer) marketType;
                    List<TradeDateDto> newTradeDateDtos = new ArrayList<>();
                    if (tradeDateMarket.equals(TradeDateMarketType.HK.getCode())) {
                        while (tradeDateList.hasNext()) {
                            JsonObject tradeDateJsonObj = tradeDateList.next().getAsJsonObject();
                            TradeDateDto tradeDateDto = new TradeDateDto();
                            tradeDateDto.setMarketOrSecurity(String.valueOf(MarketType.HK.getCode()));
                            tradeDateDto.setTime(tradeDateJsonObj.get("time").getAsString());
                            tradeDateDto.setTradeDateType(tradeDateJsonObj.get("tradeDateType").getAsInt());
                            newTradeDateDtos.add(tradeDateDto);
                        }
                    } else if (tradeDateMarket.equals(TradeDateMarketType.CN.getCode())) {
                        while (tradeDateList.hasNext()) {
                            JsonObject tradeDateJsonObj = tradeDateList.next().getAsJsonObject();
                            TradeDateDto tradeDateDto = new TradeDateDto();
                            tradeDateDto.setMarketOrSecurity(MarketType.CN_SH.getCode() + "," + MarketType.CN_SZ.getCode());
                            tradeDateDto.setTime(tradeDateJsonObj.get("time").getAsString());
                            tradeDateDto.setTradeDateType(tradeDateJsonObj.get("tradeDateType").getAsInt());
                            newTradeDateDtos.add(tradeDateDto);
                        }
                    } else if (tradeDateMarket.equals(TradeDateMarketType.US.getCode())) {
                        while (tradeDateList.hasNext()) {
                            JsonObject tradeDateJsonObj = tradeDateList.next().getAsJsonObject();
                            TradeDateDto tradeDateDto = new TradeDateDto();
                            tradeDateDto.setMarketOrSecurity(String.valueOf(MarketType.US.getCode()));
                            tradeDateDto.setTime(tradeDateJsonObj.get("time").getAsString());
                            tradeDateDto.setTradeDateType(tradeDateJsonObj.get("tradeDateType").getAsInt());
                            newTradeDateDtos.add(tradeDateDto);
                        }
                    }
                    //todo 其他的以后再算
                    List<TradeDateDto> existTradeDates = tradeDateDtoMapper.selectList(null);
                    newTradeDateDtos.removeIf(existTradeDates::contains);
                    int insertRow = tradeDateDtoMapper.insertBatch(newTradeDateDtos);
                    if (insertRow > 0) {
                        String str = "交易日数据插入条数" + insertRow;
                        LOGGER.info(str);
                        sendNotifyMessage(str);
                    }
                }
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("解析交易日结果失败.", e);
            }
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
                MarketStateVo marketStateVo = GSON.fromJson(ftGrpcReturnResult.getS2c(), MarketStateVo.class);
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
        HistoryKLDetailMessage message = new HistoryKLDetailMessage();
        message.setUsedQuota(messageContent.getUsedQuota());
        message.setRemainQuota(messageContent.getRemainQuota());
        message.setDetailList(messageContent.getDetailList() == null ? new ArrayList<>() :
                messageContent.getDetailList().stream().map(item -> {
                    HistoryKLDetailMessage.HistoryKLDetailItemMessage itemMessage = new HistoryKLDetailMessage.HistoryKLDetailItemMessage();
                    itemMessage.setMarket(item.getSecurity().getMarket());
                    itemMessage.setCode(item.getSecurity().getCode());
                    itemMessage.setName(item.getName());
                    itemMessage.setRequestTime(item.getRequestTime());
                    itemMessage.setRequestTimeStamp(item.getRequestTimeStamp());
                    return itemMessage;
                }).collect(Collectors.toList()));

        rocketMQTemplate.asyncSend(MessageCommon.HISTORY_KL_DETAIL_TOPIC, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                LOGGER.info("历史K线额度使用明细投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
                        sendResult.getSendStatus());
            }

            @Override
            public void onException(Throwable throwable) {
                LOGGER.error("历史K线额度使用明细投递失败", throwable);
            }
        });
    }

    private void sendRehabMessage(RehabMessageContent rehabMessageContent) {
        RehabMessage message = new RehabMessage();
        message.setMarket(rehabMessageContent.getMarket());
        message.setCode(rehabMessageContent.getCode());
        message.setCompanyActFlag(rehabMessageContent.getCompanyActFlag());
        message.setFwdFactorA(rehabMessageContent.getFwdFactorA());
        message.setFwdFactorB(rehabMessageContent.getFwdFactorB());
        message.setBwdFactorA(rehabMessageContent.getBwdFactorA());
        message.setBwdFactorB(rehabMessageContent.getBwdFactorB());
        message.setSplitBase(rehabMessageContent.getSplitBase());
        message.setSplitErt(rehabMessageContent.getSplitErt());
        message.setJoinBase(rehabMessageContent.getJoinBase());
        message.setJoinErt(rehabMessageContent.getJoinErt());
        message.setBonusBase(rehabMessageContent.getBonusBase());
        message.setBonusErt(rehabMessageContent.getBonusErt());
        message.setTransferBase(rehabMessageContent.getTransferBase());
        message.setTransferErt(rehabMessageContent.getTransferErt());
        message.setAllotBase(rehabMessageContent.getAllotBase());
        message.setAllotErt(rehabMessageContent.getAllotErt());
        message.setAllotPrice(rehabMessageContent.getAllotPrice());
        message.setAddBase(rehabMessageContent.getAddBase());
        message.setAddErt(rehabMessageContent.getAddErt());
        message.setAddPrice(rehabMessageContent.getAddPrice());
        message.setDividend(rehabMessageContent.getDividend());
        message.setSpDividend(rehabMessageContent.getSpDividend());
        message.setTime(rehabMessageContent.getTime());

        rocketMQTemplate.asyncSend(MessageCommon.REHAB_TOPIC, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                LOGGER.info("复权因子投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
                        sendResult.getSendStatus());
            }

            @Override
            public void onException(Throwable throwable) {
                LOGGER.error("复权因子投递失败", throwable);
            }
        });
    }

    private void sendCapitalFlowMessage(CapitalFlowMessageContent messageContent) {
        CapitalFlowMessage capitalFlowMessage = new CapitalFlowMessage();
        capitalFlowMessage.setLastValidTime(messageContent.getLastValidTime());
        capitalFlowMessage.setMarket(messageContent.getMarket());
        capitalFlowMessage.setCode(messageContent.getCode());
        capitalFlowMessage.setInFlow(messageContent.getInFlow());
        capitalFlowMessage.setTime(messageContent.getTime());
        //'实时'为空
        capitalFlowMessage.setMainInFlow(messageContent.getMainInFlow());
        capitalFlowMessage.setSuperInFlow(messageContent.getSuperInFlow());
        capitalFlowMessage.setBigInFlow(messageContent.getBigInFlow());
        capitalFlowMessage.setMidInFlow(messageContent.getMidInFlow());
        capitalFlowMessage.setSmlInFlow(messageContent.getSmlInFlow());
        rocketMQTemplate.asyncSend(MessageCommon.CAPITAL_FLOW_TOPIC, capitalFlowMessage, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                LOGGER.info("资金流向投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
                        sendResult.getSendStatus());
            }

            @Override
            public void onException(Throwable throwable) {
                LOGGER.error("资金流向投递失败", throwable);
            }
        });
    }

    private void sendMarketStateMessage(MarketStateVo marketStateVo) {
        MarketStateMessage marketStateMessage = new MarketStateMessage();
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
        rocketMQTemplate.asyncSend(MessageCommon.MARKET_STATE_TOPIC, marketStateMessage, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                LOGGER.info("全局市场状态投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
                        sendResult.getSendStatus());
            }

            @Override
            public void onException(Throwable throwable) {
                LOGGER.error("全局市场状态投递失败", throwable);
            }
        });
    }

    private void sendNotifyMessage(String notifyContent) {
        NotifyMessage notifyMessage = new NotifyMessage();
        notifyMessage.setContent(notifyContent);
        rocketMQTemplate.asyncSend(MessageCommon.NOTIFY_TOPIC, notifyMessage, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                LOGGER.info("FT通知投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
                        sendResult.getSendStatus());
            }

            @Override
            public void onException(Throwable throwable) {
                LOGGER.error("FT通知投递失败", throwable);
            }
        });
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

        rocketMQTemplate.asyncSend(MessageCommon.RT_BROKER_TOPIC, brokerMessage, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                LOGGER.info("经纪队列数据投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
                        sendResult.getSendStatus());
            }

            @Override
            public void onException(Throwable throwable) {
                LOGGER.error("经纪队列数据投递失败", throwable);
            }
        });
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

            rocketMQTemplate.asyncSend(MessageCommon.RT_TIMESHARE_TOPIC, message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    LOGGER.info("分时数据投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
                            sendResult.getSendStatus());
                }

                @Override
                public void onException(Throwable throwable) {
                    LOGGER.error("分时数据投递失败", throwable);
                }
            });
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
        rocketMQTemplate.asyncSend(MessageCommon.RT_TICKER_TOPIC, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                LOGGER.info("逐笔数据投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
                        sendResult.getSendStatus());
            }

            @Override
            public void onException(Throwable throwable) {
                LOGGER.error("逐笔数据投递失败", throwable);
            }
        });

    }

    private void sendKLMessage(KLMessageContent klMessageContent) {
        if (!klMessageContent.getBlank()) {
            //内容不为空才发
            RTKLMessage message = new RTKLMessage();
            message.setMarket(klMessageContent.getMarket());
            message.setCode(klMessageContent.getCode());
            message.setRehabType(klMessageContent.getRehabType());
            message.setHighPrice(klMessageContent.getHighPrice());
            message.setOpenPrice(klMessageContent.getOpenPrice());
            message.setLowPrice(klMessageContent.getLowPrice());
            message.setClosePrice(klMessageContent.getClosePrice());
            message.setLastClosePrice(klMessageContent.getLastClosePrice());
            message.setVolume(klMessageContent.getVolume());
            message.setTurnover(klMessageContent.getTurnover());
            message.setTurnoverRate(klMessageContent.getTurnoverRate());
            message.setPe(klMessageContent.getPe());
            message.setChangeRate(klMessageContent.getChangeRate());
            message.setUpdateTime(klMessageContent.getTime());

            String hashKey = klMessageContent.getMarket() + "-" + klMessageContent.getCode();
            if (klMessageContent.getKlType().equals(KLType.DAY.getCode())) {
                //日K
                rocketMQTemplate.asyncSendOrderly(MessageCommon.RT_KL_DAY_TOPIC, message, hashKey + "-day", new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        LOGGER.info("日K线数据投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
                                sendResult.getSendStatus());
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        LOGGER.error("日K线数据投递失败", throwable);
                    }
                });
            } else if (klMessageContent.getKlType().equals(KLType.WEEK.getCode())) {
                //周K
                rocketMQTemplate.asyncSendOrderly(MessageCommon.RT_KL_WEEK_TOPIC, message, hashKey + "-week", new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        LOGGER.info("周K线数据投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
                                sendResult.getSendStatus());
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        LOGGER.error("周K线数据投递失败", throwable);
                    }
                });
            } else if (klMessageContent.getKlType().equals(KLType.MONTH.getCode())) {
                rocketMQTemplate.asyncSendOrderly(MessageCommon.RT_KL_MONTH_TOPIC, message, hashKey + "-month", new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        LOGGER.info("月K线数据投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
                                sendResult.getSendStatus());
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        LOGGER.error("月K线数据投递失败", throwable);
                    }
                });
            } else if (klMessageContent.getKlType().equals(KLType.QUARTER.getCode())) {
                rocketMQTemplate.asyncSendOrderly(MessageCommon.RT_KL_QUARTER_TOPIC, message, hashKey + "-quarter", new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        LOGGER.info("季K线数据投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
                                sendResult.getSendStatus());
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        LOGGER.error("季K线数据投递失败", throwable);
                    }
                });
            } else if (klMessageContent.getKlType().equals(KLType.YEAR.getCode())) {
                rocketMQTemplate.asyncSendOrderly(MessageCommon.RT_KL_YEAR_TOPIC, message, hashKey + "-year", new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        LOGGER.info("年K线数据投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
                                sendResult.getSendStatus());
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        LOGGER.error("年K线数据投递失败", throwable);
                    }
                });
            } else if (klMessageContent.getKlType().equals(KLType.MIN_1.getCode())) {
                rocketMQTemplate.asyncSendOrderly(MessageCommon.RT_KL_MIN_1_TOPIC, message, hashKey + "-min1", new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        LOGGER.info("1分K线数据投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
                                sendResult.getSendStatus());
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        LOGGER.error("1分K线数据投递失败", throwable);
                    }
                });
            } else if (klMessageContent.getKlType().equals(KLType.MIN_3.getCode())) {
                rocketMQTemplate.asyncSendOrderly(MessageCommon.RT_KL_MIN_3_TOPIC, message, hashKey + "-min3", new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        LOGGER.info("3分K线数据投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
                                sendResult.getSendStatus());
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        LOGGER.error("3分K线数据投递失败", throwable);
                    }
                });
            } else if (klMessageContent.getKlType().equals(KLType.MIN_5.getCode())) {
                //5分K
                rocketMQTemplate.asyncSendOrderly(MessageCommon.RT_KL_MIN_5_TOPIC, message, hashKey + "-min5", new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        LOGGER.info("5分K线数据投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
                                sendResult.getSendStatus());
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        LOGGER.error("5分K线数据投递失败", throwable);
                    }
                });
            } else if (klMessageContent.getKlType().equals(KLType.MIN_15.getCode())) {
                rocketMQTemplate.asyncSendOrderly(MessageCommon.RT_KL_MIN_15_TOPIC, message, hashKey + "-min15", new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        LOGGER.info("15分K线数据投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
                                sendResult.getSendStatus());
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        LOGGER.error("15分K线数据投递失败", throwable);
                    }
                });
            } else if (klMessageContent.getKlType().equals(KLType.MIN_30.getCode())) {
                rocketMQTemplate.asyncSendOrderly(MessageCommon.RT_KL_MIN_30_TOPIC, message, hashKey + "-min30", new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        LOGGER.info("30分K线数据投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
                                sendResult.getSendStatus());
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        LOGGER.error("30分K线数据投递失败", throwable);
                    }
                });
            } else if (klMessageContent.getKlType().equals(KLType.MIN_60.getCode())) {
                rocketMQTemplate.asyncSendOrderly(MessageCommon.RT_KL_MIN_60_TOPIC, message, hashKey + "-min60", new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        LOGGER.info("60分K线数据投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
                                sendResult.getSendStatus());
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        LOGGER.error("60分K线数据投递失败", throwable);
                    }
                });
            }
        }
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
            stockDto = stockMapper.selectOne(queryWrapper);
            CacheManager.put(cacheKey, stockDto);
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
            rocketMQTemplate.asyncSendOrderly(MessageCommon.RT_BASIC_QUO_TOPIC_STOCK, rtBasicQuoteMessage, hashKey + "-stock", new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    LOGGER.info("实时正股报价信息投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
                            sendResult.getSendStatus());
                }

                @Override
                public void onException(Throwable throwable) {
                    LOGGER.error("实时正股报价信息投递失败", throwable);
                }
            });
        } else if (stockDto.getStockType().equals(StockType.Index.getCode())) {
            //指数
            rocketMQTemplate.asyncSendOrderly(MessageCommon.RT_BASIC_QUO_TOPIC_INDEX, rtBasicQuoteMessage, hashKey + "-index", new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    LOGGER.info("实时指数报价信息投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
                            sendResult.getSendStatus());
                }

                @Override
                public void onException(Throwable throwable) {
                    LOGGER.error("实时指数报价信息投递失败", throwable);
                }
            });
        } else if (stockDto.getStockType().equals(StockType.Future.getCode())) {
            //期货


        } else if (stockDto.getStockType().equals(StockType.Plate.getCode())) {
            //板块
            rocketMQTemplate.asyncSendOrderly(MessageCommon.RT_BASIC_QUO_TOPIC_PLATE, rtBasicQuoteMessage, hashKey + "-plate", new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    LOGGER.info("实时板块报价信息投递成功.TransactionId:{}__[{}]", sendResult.getTransactionId(),
                            sendResult.getSendStatus());
                }

                @Override
                public void onException(Throwable throwable) {
                    LOGGER.error("实时板块报价信息投递失败", throwable);
                }
            });
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
                    sendKLMessage(klMessageContent);
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
    @Transactional(rollbackFor = Exception.class)
    public void onReply_Sub(FTAPI_Conn client, int nSerialNo, QotSub.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "订阅失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "订阅失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                Object cacheValue = CacheManager.get(String.valueOf(nSerialNo));
                if (cacheValue instanceof SubscribeRequest) {
                    if (((SubscribeRequest) cacheValue).isUnsub() != null && ((SubscribeRequest) cacheValue).isUnsub()) {
                        ((SubscribeRequest) cacheValue).getSecurityList()
                                .forEach(subscribeSecurity -> ((SubscribeRequest) cacheValue).getSubTypeList()
                                        .forEach(subType -> {
                                            int delRow = subDtoMapper.deleteBySecurityCodeAndSubType(subscribeSecurity.getCode(), subType);
                                            if (delRow > 0) {
                                                String notify = "取消订阅成功";
                                                LOGGER.info(notify);
                                                sendNotifyMessage(notify);
                                            }
                                        }));

                    } else {
                        List<SubDto> toAddList = new ArrayList<>();
                        ((SubscribeRequest) cacheValue).getSecurityList()
                                .forEach(subscribeSecurity -> ((SubscribeRequest) cacheValue).getSubTypeList()
                                        .forEach(subType -> {
                                            SubDto toAddSub = new SubDto();
                                            toAddSub.setSecurityCode(subscribeSecurity.getCode());
                                            toAddSub.setSecurityName(subscribeSecurity.getName());
                                            toAddSub.setSecurityMarket(subscribeSecurity.getMarket());
                                            toAddSub.setSecurityType(subscribeSecurity.getType());
                                            toAddSub.setSubType(subType);
                                            toAddList.add(toAddSub);
                                        }));
                        //新增时候会判断是否存在,对已存在的数据不会更新
                        List<SubDto> existSubList = subDtoMapper.selectList(null);
                        toAddList.removeIf(existSubList::contains);
                        if (toAddList.size() > 0) {
                            int insertRow = subDtoMapper.insertBatch(toAddList);
                            if (insertRow > 0) {
                                String notify = "订阅成功";
                                LOGGER.info(notify);
                                sendNotifyMessage(notify);
                            }
                        }
                    }
                }
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("订阅解结果解析失败.", e);
            }

        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onReply_GetSubInfo(FTAPI_Conn client, int nSerialNo, QotGetSubInfo.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询订阅信息失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询订阅信息失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                if (ftGrpcReturnResult.getS2c().has("connSubInfoList")) {
                    JsonArray connSubInfoList = ftGrpcReturnResult.getS2c().get("connSubInfoList").getAsJsonArray();
                    Iterator<JsonElement> connSubInfoListIterator = connSubInfoList.iterator();
                    List<SubDto> subDtoList = new ArrayList<>();
                    while (connSubInfoListIterator.hasNext()) {
                        JsonObject perConnSubInfo = connSubInfoListIterator.next().getAsJsonObject();
                        JsonArray subInfoList = perConnSubInfo.getAsJsonArray("subInfoList");
                        Iterator<JsonElement> subInfoIterator = subInfoList.iterator();
                        while (subInfoIterator.hasNext()) {
                            JsonObject perSubInfo = subInfoIterator.next().getAsJsonObject();
                            Integer subType = perSubInfo.get("subType").getAsInt();
                            JsonArray securityList = perSubInfo.getAsJsonArray("securityList");
                            Iterator<JsonElement> securityIterator = securityList.iterator();
                            while (securityIterator.hasNext()) {
                                JsonObject perSecurity = securityIterator.next().getAsJsonObject();
                                SubDto subDto = new SubDto();
//                                subDto.setUsedQuota(perConnSubInfo.get("usedQuota").getAsInt());
//                                subDto.setIsOwnConn(perConnSubInfo.get("isOwnConnData").getAsBoolean() ? 1 : 0);
                                subDto.setSecurityMarket(perSecurity.get("market").getAsInt());
                                subDto.setSecurityCode(perSecurity.get("code").getAsString());
                                subDto.setSubType(subType);
                                subDtoList.add(subDto);
                            }
                        }
                    }
                    List<SubDto> existSubscribeInfo = subDtoMapper.selectList(null);
                    //差集,新增数据
                    Collection<SubDto> subtractForInsert = CollectionUtils.subtract(subDtoList, existSubscribeInfo);
                    if (subtractForInsert.size() > 0) {
                        int insertRow = subDtoMapper.insertBatch(subtractForInsert);
                        String notify = "订阅信息表插入条数." + insertRow;
                        LOGGER.info(notify);
                        sendNotifyMessage(notify);
                    }
                    //差集,删除数据
                    Collection<SubDto> subtractForDel = CollectionUtils.subtract(existSubscribeInfo, subDtoList);
                    if (subtractForDel.size() > 0) {
                        int delRow = subDtoMapper.deleteBatchIds(subtractForDel
                                .stream().map(SubDto::getId).collect(Collectors.toList()));
                        String notify = "订阅信息表删除条数." + delRow;
                        LOGGER.info(notify);
                        sendNotifyMessage(notify);
                    }
                }
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("查询订阅信息解析结果失败.", e);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onReply_GetIpoList(FTAPI_Conn client, int nSerialNo, QotGetIpoList.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询IPO信息失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询IPO信息失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                Iterator<JsonElement> iterator = ftGrpcReturnResult.getS2c().getAsJsonArray("ipoList").iterator();
                List<IpoHkDto> ipoHkDtos = new ArrayList<>();
                List<IpoUsDto> ipoUsDtos = new ArrayList<>();
                List<IpoCnDto> ipoCnDtos = new ArrayList<>();
                while (iterator.hasNext()) {
                    JsonElement jsonElement = iterator.next();
                    JsonObject basic = jsonElement.getAsJsonObject().get("basic").getAsJsonObject();
                    String name = basic.get("name").getAsString();
                    Integer market = basic.get("security").getAsJsonObject().get("market").getAsInt();
                    String code = basic.get("security").getAsJsonObject().get("code").getAsString();
                    if (jsonElement.getAsJsonObject().has("usExData")) {
                        //美股IPO
                        JsonObject usExData = jsonElement.getAsJsonObject().get("usExData").getAsJsonObject();
                        IpoUsDto ipoUsDto = new IpoUsDto();
                        ipoUsDto.setName(name);
                        ipoUsDto.setCode(code);
                        ipoUsDto.setMarket(market);
                        if (basic.has("listTime")) {
                            ipoUsDto.setListTime(LocalDate.parse(basic.get("listTime").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        }
                        ipoUsDto.setIpoPriceMin(usExData.get("ipoPriceMin").getAsDouble());
                        ipoUsDto.setIpoPriceMax(usExData.get("ipoPriceMax").getAsDouble());
                        ipoUsDto.setIssueSize(usExData.get("issueSize").getAsLong());
                        ipoUsDtos.add(ipoUsDto);
                    } else if (jsonElement.getAsJsonObject().has("cnExData")) {
                        //A股IPO
                        JsonObject cnExData = jsonElement.getAsJsonObject().get("cnExData").getAsJsonObject();
                        IpoCnDto ipoCnDto = new IpoCnDto();
                        ipoCnDto.setName(name);
                        ipoCnDto.setCode(code);
                        ipoCnDto.setMarket(market);
                        if (basic.has("listTime")) {
                            ipoCnDto.setListTime(LocalDate.parse(basic.get("listTime").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        }
                        ipoCnDto.setApplyCode(cnExData.get("applyCode").getAsString());
                        ipoCnDto.setIssueSize(cnExData.get("issueSize").getAsLong());
                        ipoCnDto.setOnlineIssueSize(cnExData.get("onlineIssueSize").getAsLong());
                        ipoCnDto.setApplyUpperLimit(cnExData.get("applyUpperLimit").getAsLong());
                        ipoCnDto.setApplyLimitMarketValue(cnExData.get("applyLimitMarketValue").getAsLong());
                        ipoCnDto.setIsEstimateIpoPrice(cnExData.get("isEstimateIpoPrice").getAsBoolean() ? 1 : 0);
                        ipoCnDto.setIpoPrice(cnExData.get("ipoPrice").getAsDouble());
                        ipoCnDto.setIndustryPeRate(cnExData.get("industryPeRate").getAsDouble());
                        ipoCnDto.setIsEstimateWinningRatio(cnExData.get("isEstimateWinningRatio").getAsBoolean() ? 1 : 0);
                        ipoCnDto.setWinningRatio(cnExData.get("winningRatio").getAsDouble());
                        ipoCnDto.setIssuePeRate(cnExData.get("issuePeRate").getAsDouble());
                        if (cnExData.has("applyTime")) {
                            ipoCnDto.setApplyTime(LocalDate.parse(cnExData.get("applyTime").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        }
                        if (cnExData.has("winningTime")) {
                            ipoCnDto.setWinningTime(LocalDate.parse(cnExData.get("winningTime").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        }
                        ipoCnDto.setIsHasWon(cnExData.get("isHasWon").getAsBoolean() ? 1 : 0);
                        if (cnExData.has("winningNumData")) {
                            List<IpoCnExWinningDto> ipoCnExWinningDtos = new ArrayList<>();
                            Iterator<JsonElement> winningNumDatas = cnExData.get("winningNumData").getAsJsonArray().iterator();
                            while (winningNumDatas.hasNext()) {
                                JsonElement winningNumData = winningNumDatas.next();
                                IpoCnExWinningDto ipoCnExWinningDto = new IpoCnExWinningDto();
                                ipoCnExWinningDto.setWinningInfo(winningNumData.getAsJsonObject().get("winningInfo").getAsString());
                                ipoCnExWinningDto.setWinningName(winningNumData.getAsJsonObject().get("winningName").getAsString());
                                ipoCnExWinningDtos.add(ipoCnExWinningDto);
                            }
                            ipoCnDto.setCnExWinningDtos(ipoCnExWinningDtos);
                        }
                        ipoCnDtos.add(ipoCnDto);
                    } else if (jsonElement.getAsJsonObject().has("hkExData")) {
                        //港股IPO
                        JsonObject hkExData = jsonElement.getAsJsonObject().get("hkExData").getAsJsonObject();
                        IpoHkDto ipoHkDto = new IpoHkDto();
                        ipoHkDto.setCode(code);
                        ipoHkDto.setName(name);
                        ipoHkDto.setMarket(market);
                        if (basic.has("listTime")) {
                            ipoHkDto.setListTime(LocalDate.parse(basic.get("listTime").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        }
                        ipoHkDto.setLotSize(hkExData.get("lotSize").getAsInt());
                        ipoHkDto.setIpoPriceMin(hkExData.get("ipoPriceMin").getAsDouble());
                        ipoHkDto.setIpoPriceMax(hkExData.get("ipoPriceMax").getAsDouble());
                        ipoHkDto.setListPrice(hkExData.get("listPrice").getAsDouble());
                        ipoHkDto.setEntrancePrice(hkExData.get("entrancePrice").getAsDouble());
                        ipoHkDto.setIsSubscribeStatus(hkExData.get("isSubscribeStatus").getAsBoolean() ? 1 : 0);
                        if (hkExData.has("applyEndTime")) {
                            ipoHkDto.setApplyEndtime(LocalDate.parse(hkExData.get("applyEndTime").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        }
                        ipoHkDtos.add(ipoHkDto);
                    }
                }
                if (ipoHkDtos.size() > 0) {
                    List<IpoHkDto> oldIpoHks = ipoHkMapper.selectList(null);
                    ipoHkDtos.removeIf(oldIpoHks::contains);
                    if (ipoHkDtos.size() > 0) {
                        int insertRow = ipoHkMapper.insertBatch(ipoHkDtos);
                        LOGGER.info("港股IPO信息插入条数:" + insertRow);
                    }
                }
                if (ipoUsDtos.size() > 0) {
                    List<IpoUsDto> oldIpoUss = ipoUsMapper.selectList(null);
                    ipoUsDtos.removeIf(oldIpoUss::contains);
                    if (ipoUsDtos.size() > 0) {
                        int insertRow = ipoUsMapper.insertBatch(ipoUsDtos);
                        LOGGER.info("美股IPO信息插入条数:" + insertRow);
                    }
                }
                if (ipoCnDtos.size() > 0) {
                    List<IpoCnDto> oldIpoCns = ipoCnMapper.selectList(null);
                    ipoCnDtos.removeIf(oldIpoCns::contains);
                    if (ipoCnDtos.size() > 0) {
                        int insertRow = ipoCnMapper.insertBatch(ipoCnDtos);
                        LOGGER.info("A股IPO信息插入条数:" + insertRow);
                        List<IpoCnDto> hasWinningIpoCns = ipoCnDtos.stream()
                                .filter(ipoCnDto -> ipoCnDto.getCnExWinningDtos() != null)
                                .collect(Collectors.toList());
                        hasWinningIpoCns.forEach(hasWinningIpoDto -> {
                            hasWinningIpoDto.getCnExWinningDtos().forEach(ipoCnExWinningDto -> {
                                ipoCnExWinningDto.setIpoCnId(hasWinningIpoDto.getId());
                            });
                            int insertWinningRow = ipoCnExWinningMapper.insertBatch(hasWinningIpoDto.getCnExWinningDtos());
                            LOGGER.info("A股IPO中签信息插入条数:" + insertWinningRow);
                        });
                    }
                }
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("查询IPO信息解析结果失败!", e);
            }

        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onReply_GetOwnerPlate(FTAPI_Conn client, int nSerialNo, QotGetOwnerPlate.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询股票板块信息失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询股票板块信息失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                Iterator<JsonElement> iterator = ftGrpcReturnResult.getS2c().getAsJsonArray("ownerPlateList").iterator();
                while (iterator.hasNext()) {
                    JsonElement jsonElement = iterator.next();
                    JsonArray plateInfoList = jsonElement.getAsJsonObject().get("plateInfoList").getAsJsonArray();
                    JsonObject security = jsonElement.getAsJsonObject().get("security").getAsJsonObject();
                    List<PlateDto> newPlates = new ArrayList<>();
                    List<PlateStockDto> newPlateStocks = new ArrayList<>();
                    Iterator<JsonElement> plateInfoIterator = plateInfoList.iterator();
                    while (plateInfoIterator.hasNext()) {
                        JsonElement plateInfoJsonElement = plateInfoIterator.next();
                        PlateDto newPlate = new PlateDto();
                        String name = plateInfoJsonElement.getAsJsonObject().get("name").getAsString();
                        Integer plateType = plateInfoJsonElement.getAsJsonObject().get("plateType").getAsInt();
                        String code = plateInfoJsonElement.getAsJsonObject().get("plate").getAsJsonObject().get("code").getAsString();
                        Integer market = plateInfoJsonElement.getAsJsonObject().get("plate").getAsJsonObject().get("market").getAsInt();
                        newPlate.setCode(code);
                        newPlate.setName(name);
                        newPlate.setMarket(market);
                        newPlate.setPlateType(plateType);
                        newPlates.add(newPlate);
                    }
                    List<PlateDto> allPlate = plateMapper.selectList(null);
                    List<Long> existPlateIds = new ArrayList<>();
                    Iterator<PlateDto> newPlatesIterator = newPlates.iterator();
                    while (newPlatesIterator.hasNext()) {
                        PlateDto newPlate = newPlatesIterator.next();
                        if (allPlate.contains(newPlate)) {
                            //存在在库里
                            newPlatesIterator.remove();
                            PlateDto plateDto = allPlate.stream()
                                    .filter(existPlate -> existPlate.getCode().equals(newPlate.getCode()))
                                    .collect(Collectors.toList()).get(0);
                            existPlateIds.add(plateDto.getId());
                        }
                    }
                    //建立关联关系
                    String securityCode = security.get("code").getAsString();
                    //todo 尚有缺陷 code有重复(SearchOne but found 2)
                    StockDto findStock = stockMapper.searchOneByCode(securityCode);
                    if (newPlates.size() > 0) {
                        //板块信息有不在库里,而是新的
                        int insertRow = plateMapper.insertBatch(newPlates);
                        LOGGER.info("板块信息插入条数:" + insertRow);
                        existPlateIds.addAll(newPlates.stream()
                                .map(PlateDto::getId)
                                .collect(Collectors.toList()));
                        for (Long plateId : existPlateIds) {
                            PlateStockDto plateStockDto = new PlateStockDto();
                            plateStockDto.setPlateId(plateId);
                            plateStockDto.setStockId(findStock.getId());
                            newPlateStocks.add(plateStockDto);
                        }
                    } else {
                        //板块信息都在库里,在库里的板块id来建立关联表
                        for (Long plateId : existPlateIds) {
                            PlateStockDto plateStockDto = new PlateStockDto();
                            plateStockDto.setPlateId(plateId);
                            plateStockDto.setStockId(findStock.getId());
                            newPlateStocks.add(plateStockDto);
                        }
                    }
                    try {
                        int insertRow = plateStockMapper.insertBatch(newPlateStocks);
                        LOGGER.info("插入板块-股票关系表条数:" + insertRow);
                    } catch (DuplicateKeyException e) {
                        LOGGER.error("批插入关系表报错", e);
                    }

                }
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("查询股票板塊信息解析结果失败!", e);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onReply_GetStaticInfo(FTAPI_Conn client, int nSerialNo, QotGetStaticInfo.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询静态信息失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询静态信息失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                Iterator<JsonElement> iterator = ftGrpcReturnResult.getS2c().getAsJsonArray("staticInfoList").iterator();
                List<StockDto> newStocks = new ArrayList<>();
                while (iterator.hasNext()) {
                    JsonElement jsonElement = iterator.next();
                    StockDto stockDto = new StockDto();
                    if (jsonElement.getAsJsonObject().has("basic")) {
                        JsonObject basic = jsonElement.getAsJsonObject().get("basic").getAsJsonObject();
                        stockDto.setName(basic.get("name").getAsString());
                        stockDto.setLotSize(basic.get("lotSize").getAsInt());
                        stockDto.setStockType(basic.get("secType").getAsInt());
                        String listTime = basic.get("listTime").getAsString();
                        if (listTime.length() != 0) {
                            stockDto.setListingDate(LocalDate.parse(listTime, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        }
                        stockDto.setDelisting(basic.get("delisting").getAsBoolean() ? 1 : 0);
                        stockDto.setExchangeType(basic.get("exchType").getAsInt());
                        stockDto.setStockId(basic.get("id").getAsString());
                        stockDto.setCode(basic.get("security").getAsJsonObject().get("code").getAsString());
                        stockDto.setMarket(basic.get("security").getAsJsonObject().get("market").getAsInt());
                    } else if (jsonElement.getAsJsonObject().has("futureExData")) {
                        JsonObject futureExData = jsonElement.getAsJsonObject().get("futureExData").getAsJsonObject();
                        String lastTradeTime = futureExData.get("lastTradeTime").getAsString();
                        if (lastTradeTime.length() != 0) {
                            stockDto.setLastTradeTime(LocalDate.parse(lastTradeTime, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        }
                        stockDto.setMainContract(futureExData.get("isMainContract").getAsBoolean() ? 1 : 0);
                    } else if (jsonElement.getAsJsonObject().has("warrantExData")) {
                        JsonObject warrantExData = jsonElement.getAsJsonObject().get("warrantExData").getAsJsonObject();
                        stockDto.setStockChildType(warrantExData.get("type").getAsInt());
                        stockDto.setStockOwner(warrantExData.get("owner").getAsJsonObject().get("code").getAsString());
                    } else if (jsonElement.getAsJsonObject().has("optionExData")) {
                        JsonObject optionExData = jsonElement.getAsJsonObject().get("optionExData").getAsJsonObject();
                        LOGGER.warn("optionExData=" + optionExData.toString());
                        try {
                            Thread.sleep(1000000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
//                        stockDto.setSuspension(optionExData.get("suspend").getAsBoolean() ? 1 : 0);

                    }
                    newStocks.add(stockDto);
                }
                StockDto any = newStocks.get(0);
                QueryWrapper<StockDto> queryWrapper = Wrappers.query();
                if (any.getMarket() == 21 || any.getMarket() == 22) {
                    //沪深有可能有同一只股票
                    queryWrapper = queryWrapper.in("market", 21, 22);
                } else {
                    queryWrapper = queryWrapper.eq("market", any.getMarket());
                }
                List<StockDto> allStock = stockMapper.selectList(queryWrapper);
                newStocks.removeIf(allStock::contains);
                int insertLength = newStocks.size();
                int i = 0;
                int batchLimit = 200;
                while (insertLength > batchLimit) {
                    int insertRow = stockMapper.insertBatch(newStocks.subList(i, i + batchLimit));
                    LOGGER.info("插入条数:" + insertRow);
                    i = i + batchLimit;
                    insertLength = insertLength - batchLimit;
                }
                if (insertLength > 0) {
                    int insertRow = stockMapper.insertBatch(newStocks.subList(i, i + insertLength));
                    LOGGER.info("插入条数:" + insertRow);
                }
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("查询静态信息解析结果失败!", e);
            }

        }
    }

    @Override
    @Transactional
    public void onReply_GetPlateSecurity(FTAPI_Conn client, int nSerialNo, QotGetPlateSecurity.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询股票信息失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询股票信息失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                Iterator<JsonElement> stockInfoIterator = ftGrpcReturnResult.getS2c().getAsJsonArray("staticInfoList").iterator();
                List<StockDto> newStocks = new ArrayList<>();
                String plateCode = (String) CacheManager.get(String.valueOf(nSerialNo));
                LOGGER.info("SeqNo=" + nSerialNo + "缓存的板块代码:" + plateCode);
                while (stockInfoIterator.hasNext()) {
                    JsonElement jsonElement = stockInfoIterator.next();
                    JsonObject basic = jsonElement.getAsJsonObject().get("basic").getAsJsonObject();
                    StockDto stockDto = new StockDto();
                    stockDto.setName(basic.get("name").getAsString());
                    stockDto.setLotSize(basic.get("lotSize").getAsInt());
                    stockDto.setStockType(basic.get("secType").getAsInt());
                    stockDto.setListingDate(LocalDate.parse(basic.get("listTime").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    stockDto.setDelisting(basic.get("delisting").getAsBoolean() ? 1 : 0);
                    stockDto.setExchangeType(basic.get("exchType").getAsInt());
                    stockDto.setStockId(basic.get("id").getAsString());
                    stockDto.setCode(basic.get("security").getAsJsonObject().get("code").getAsString());
                    stockDto.setMarket(basic.get("security").getAsJsonObject().get("market").getAsInt());
                    newStocks.add(stockDto);
                }
                StockDto any = newStocks.get(0);
                QueryWrapper<StockDto> queryWrapper = Wrappers.query();
                if (any.getMarket() == 21 || any.getMarket() == 22) {
                    //沪深有可能有同一只股票
                    queryWrapper = queryWrapper.in("market", 21, 22);
                } else {
                    queryWrapper = queryWrapper.eq("market", any.getMarket());
                }
                List<StockDto> allStock = stockMapper.selectList(queryWrapper);
                newStocks.removeIf(allStock::contains);
                if (newStocks.size() > 0) {
                    int insertRow = stockMapper.insertBatch(newStocks);
                    LOGGER.info("插入条数:" + insertRow);
                }
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("查询股票信息解析结果失败!", e);
            }
        }
    }

    @Override
    @Transactional
    public void onReply_GetPlateSet(FTAPI_Conn client, int nSerialNo, QotGetPlateSet.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询板块信息失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("请求序列号:" + nSerialNo + "查询板块信息失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp),
                        FTGrpcReturnResult.class);
                Iterator<JsonElement> plateInfosIterator = ftGrpcReturnResult.getS2c().getAsJsonArray("plateInfoList").iterator();
                List<PlateDto> newPlates = new ArrayList<>();
                while (plateInfosIterator.hasNext()) {
                    JsonElement jsonElement = plateInfosIterator.next();
                    PlateDto dto = new PlateDto();
                    dto.setName(jsonElement.getAsJsonObject().get("name").getAsString());
                    dto.setCode(jsonElement.getAsJsonObject().get("plate").getAsJsonObject().get("code").getAsString());
                    dto.setMarket(jsonElement.getAsJsonObject().get("plate").getAsJsonObject().get("market").getAsInt());
                    newPlates.add(dto);
                }
                List<PlateDto> allPlates = plateMapper.selectList(null);
                newPlates.removeIf(allPlates::contains);
                if (newPlates.size() > 0) {
                    int insertRow = plateMapper.insertBatch(newPlates);
                    LOGGER.info("插入条数:" + insertRow);
                }
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("查询板块信息解析结果失败!", e);
            }
        }

    }
}
