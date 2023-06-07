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
import io.futakotome.trade.config.FutuConfig;
import io.futakotome.trade.controller.vo.SubscribeRequest;
import io.futakotome.trade.controller.vo.SubscribeSecurity;
import io.futakotome.trade.domain.MarketAggregator;
import io.futakotome.trade.domain.MarketState;
import io.futakotome.trade.dto.*;
import io.futakotome.trade.listener.NotifyMessage;
import io.futakotome.trade.mapper.*;
import io.futakotome.trade.utils.CacheManager;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Scheduled;
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

    private final MessageService messageService;

    private final PlateDtoMapper plateMapper;
    private final StockDtoMapper stockMapper;
    private final PlateStockDtoMapper plateStockMapper;
    private final IpoHkDtoMapper ipoHkMapper;
    private final IpoUsDtoMapper ipoUsMapper;
    private final IpoCnDtoMapper ipoCnMapper;
    private final IpoCnExWinningDtoMapper ipoCnExWinningMapper;
    private final SubDtoMapper subDtoMapper;
    private final FutuConfig futuConfig;

    private String sessionId;

    private static final String clientID = "javaclient";

    public static final FTAPI_Conn_Qot qot = new FTAPI_Conn_Qot();

    public FTQotService(MessageService messageService, PlateDtoMapper plateMapper, StockDtoMapper stockMapper, PlateStockDtoMapper plateStockMapper,
                        IpoHkDtoMapper ipoHkMapper, IpoUsDtoMapper ipoUsMapper, IpoCnDtoMapper ipoCnMapper, IpoCnExWinningDtoMapper ipoCnExWinningMapper,
                        SubDtoMapper subDtoMapper, FutuConfig futuConfig) {
        qot.setClientInfo(clientID, 1);
        qot.setConnSpi(this);
        qot.setQotSpi(this);
        this.messageService = messageService;
        this.subDtoMapper = subDtoMapper;
        this.futuConfig = futuConfig;
        this.plateMapper = plateMapper;
        this.stockMapper = stockMapper;
        this.plateStockMapper = plateStockMapper;
        this.ipoHkMapper = ipoHkMapper;
        this.ipoUsMapper = ipoUsMapper;
        this.ipoCnMapper = ipoCnMapper;
        this.ipoCnExWinningMapper = ipoCnExWinningMapper;
    }

    @Deprecated
    public void syncPlateInfo() {
        market.sendPlateInfoRequest();
    }

    @Deprecated
    public void syncStockInfo() {
        market.sendStockInfoRequest(plateMapper);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void syncStaticInfo() {
        market.sendStaticInfoRequest();
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void syncStockOwnerPlateInfo() {
        market.sendPlateInfoRequest(stockMapper);
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void syncIpoInfo() {
        market.sendIpoInfoRequest();
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

    public void subscribeRequest(SubscribeRequest subscribeRequest) {
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
                        .setIsRegOrUnRegPush(true)
                        .setIsSubOrUnSub(true)
                        .build())
                .build();
        int seqNo = qot.sub(request);
        //下面订阅成功之后再拿出来插入订阅信息
        CacheManager.put(String.valueOf(seqNo), subscribeRequest);
        LOGGER.info("发起订阅.seqNo=" + seqNo);
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
        LOGGER.info("FUTU API 初始化连接 onInitConnect: ret=" + errCode + ",desc=" + desc + ",connID=" + client.getConnectID());
        messageService.onNext(new NotifyMessage(Long.toString(errCode), "FUTU API 连接成功"), this.sessionId);
    }

    @Override
    public void onDisconnect(FTAPI_Conn client, long errCode) {
        LOGGER.info("FUTU API 关闭连接 onDisconnect: connID=" + client.getConnectID() + ",ret=" + errCode);
        messageService.onNext(new NotifyMessage(Long.toString(errCode), "FUTU API 关闭连接成功"), this.sessionId);
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public void onPush_Notify(FTAPI_Conn client, Notify.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.error("获取FutuD通知推送失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("connID=" + client.getConnectID() + "获取FutuD通知推送失败,code:" + rsp.getRetType()));
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                LOGGER.info("FutuD通知推送" + ftGrpcReturnResult.toString());
                messageService.onNext(new NotifyMessage(String.valueOf(client.getConnectID()), "ConnectId:" + client.getConnectID() + "收到通知" + ftGrpcReturnResult.toString()), this.sessionId);
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("FutuD通知推送结果解析失败.", e);
            }
        }
    }

    @Override
    public void onReply_GetGlobalState(FTAPI_Conn client, int nSerialNo, GetGlobalState.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.error("获取全局市场状态失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("connID=" + client.getConnectID() + "获取全局市场状态失败,code:" + rsp.getRetType()));
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                JsonObject state = ftGrpcReturnResult.getS2c();
                state.addProperty("marketHK", MarketState.mapFrom(state.get("marketHK").getAsInt()));
                state.addProperty("marketUS", MarketState.mapFrom(state.get("marketUS").getAsInt()));
                state.addProperty("marketSH", MarketState.mapFrom(state.get("marketSH").getAsInt()));
                state.addProperty("marketSZ", MarketState.mapFrom(state.get("marketSZ").getAsInt()));
                state.addProperty("marketHKFuture", MarketState.mapFrom(state.get("marketHKFuture").getAsInt()));
                state.addProperty("time", LocalDateTime.ofInstant(Instant.ofEpochSecond(state.get("time").getAsLong()), ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                state.addProperty("localTime", LocalDateTime.ofInstant(Instant.ofEpochSecond(state.get("localTime").getAsLong()), ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                state.addProperty("marketUSFuture", MarketState.mapFrom(state.get("marketUSFuture").getAsInt()));
                state.addProperty("marketSGFuture", MarketState.mapFrom(state.get("marketSGFuture").getAsInt()));
                state.addProperty("marketJPFuture", MarketState.mapFrom(state.get("marketJPFuture").getAsInt()));
                messageService.onNext(new NotifyMessage(ftGrpcReturnResult.getRetType().toString(), state.toString()), this.sessionId);
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("解析全局市场状态结果失败.", e);
            }
        }
    }

    @Override
    public void onPush_UpdateOrderBook(FTAPI_Conn client, QotUpdateOrderBook.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.error("获取摆盘推送失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("connID=" + client.getConnectID() + "获取摆盘失败,code:" + rsp.getRetType()));
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                LOGGER.info(ftGrpcReturnResult.toString());
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("摆盘结果解析失败.", e);
            }
        }
    }

    @Override
    public void onPush_UpdateBasicQuote(FTAPI_Conn client, QotUpdateBasicQot.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.error("获取报价推送失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("connID=" + client.getConnectID() + "获取报价失败,code:" + rsp.getRetType()));
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                LOGGER.info(ftGrpcReturnResult.toString());
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("报价推送结果解析失败.", e);
            }
        }
    }

    @Override
    public void onPush_UpdateKL(FTAPI_Conn client, QotUpdateKL.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.error("获取K线推送失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("connID=" + client.getConnectID() + "获取K线数据失败,code:" + rsp.getRetType()));
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                LOGGER.info(ftGrpcReturnResult.toString());
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("K线推送结果解析失败.", e);
            }
        }
    }

    @Override
    public void onPush_UpdateRT(FTAPI_Conn client, QotUpdateRT.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.error("获取分时推送失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("connID=" + client.getConnectID() + "获取分时数据失败,code:" + rsp.getRetType()));
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                LOGGER.info(ftGrpcReturnResult.toString());
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("分时推送结果解析失败.", e);
            }
        }
    }

    @Override
    public void onPush_UpdateTicker(FTAPI_Conn client, QotUpdateTicker.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.error("获取逐笔推送失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("connID=" + client.getConnectID() + "获取逐笔数据失败,code:" + rsp.getRetType()));
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                LOGGER.info(ftGrpcReturnResult.toString());
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("逐笔推送结果解析失败.", e);
            }
        }
    }

    @Override
    public void onPush_UpdateBroker(FTAPI_Conn client, QotUpdateBroker.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.error("获取经纪队列推送失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("connID=" + client.getConnectID() + "获取经纪队列数据失败,code:" + rsp.getRetType()));
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                LOGGER.info(ftGrpcReturnResult.toString());
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("经纪队列推送结果解析失败.", e);
            }
        }
    }

    @Override
    public void onReply_Sub(FTAPI_Conn client, int nSerialNo, QotSub.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.error("订阅失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("请求序列号:" + nSerialNo + "订阅失败,code:" + rsp.getRetType()));
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                Object cacheValue = CacheManager.get(String.valueOf(nSerialNo));
                if (cacheValue instanceof SubscribeRequest) {
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
                            messageService.onNext(new NotifyMessage(ftGrpcReturnResult.getRetType().toString(), "订阅成功"), this.sessionId);
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
            LOGGER.error("查询订阅信息失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("请求序列号:" + nSerialNo + "查询订阅信息失败,code:" + rsp.getRetType()));
        } else {
            LOGGER.info("SeqNo:" + nSerialNo + ",connID=" + client.getConnectID() + "查询订阅信息...");
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
                        LOGGER.info("订阅信息表插入条数." + insertRow);
                    }
                    //差集,删除数据
                    Collection<SubDto> subtractForDel = CollectionUtils.subtract(existSubscribeInfo, subDtoList);
                    if (subtractForDel.size() > 0) {
                        int delRow = subDtoMapper.deleteBatchIds(subtractForDel
                                .stream().map(SubDto::getId).collect(Collectors.toList()));
                        LOGGER.info("订阅信息表删除条数." + delRow);
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
            LOGGER.error("查询IPO信息失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("请求序列号:" + nSerialNo + "查询IPO信息失败,code:" + rsp.getRetType()));
        } else {
            LOGGER.info("SeqNo:" + nSerialNo + ",connID=" + client.getConnectID() + "查询IPO信息...");
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
            LOGGER.error("查询股票板块信息失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("请求序列号:" + nSerialNo + "查询股票板块信息失败,code:" + rsp.getRetType()));
        } else {
            LOGGER.info("SeqNo:" + nSerialNo + ",connID=" + client.getConnectID() + "查询股票板块信息...");
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
            LOGGER.error("查询静态信息失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("请求序列号:" + nSerialNo + "查询静态信息失败,code:" + rsp.getRetType()));
        } else {
            LOGGER.info("SeqNo:" + nSerialNo + ",connID=" + client.getConnectID() + "查询静态信息...");
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
                messageService.onNext(new NotifyMessage(String.valueOf(nSerialNo), "nSerialNo=" + nSerialNo + "同步成功"), this.sessionId);
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("查询静态信息解析结果失败!", e);
            }

        }
    }

    @Override
    @Transactional
    public void onReply_GetPlateSecurity(FTAPI_Conn client, int nSerialNo, QotGetPlateSecurity.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.error("查询股票信息失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("请求序列号:" + nSerialNo + "查询股票信息失败,code:" + rsp.getRetType()));
        } else {
            LOGGER.info("connID=" + client.getConnectID() + "查询股票信息...");
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
            LOGGER.error("查询板块信息失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("请求序列号:" + nSerialNo + "查询板块信息失败,code:" + rsp.getRetType()));
        } else {
            LOGGER.info("connID=" + client.getConnectID() + "查询板块信息...");
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
