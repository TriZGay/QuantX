package io.futakotome.trade.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.futu.openapi.*;
import com.futu.openapi.pb.*;
import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.futakotome.trade.config.FutuConfig;
import io.futakotome.trade.controller.vo.OrderRequest;
import io.futakotome.trade.controller.vo.UnlockRequest;
import io.futakotome.trade.domain.Currency;
import io.futakotome.trade.dto.AccDto;
import io.futakotome.trade.dto.AccInfoDto;
import io.futakotome.trade.dto.OrderDto;
import io.futakotome.trade.dto.PositionDto;
import io.futakotome.trade.mapper.AccDtoMapper;
import io.futakotome.trade.mapper.AccInfoDtoMapper;
import io.futakotome.trade.mapper.OrderDtoMapper;
import io.futakotome.trade.mapper.PositionDtoMapper;
import io.futakotome.trade.utils.RequestCount;
import org.bouncycastle.jcajce.provider.digest.MD5;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FTTradeService implements FTSPI_Conn, FTSPI_Trd, InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(FTTradeService.class);
    private static final Gson GSON = new Gson();

    private final FutuConfig futuConfig;

    private static final String clientID = "javaclient";

    public static final FTAPI_Conn_Trd trd = new FTAPI_Conn_Trd();

    private final AccDtoMapper accDtoMapper;
    private final AccInfoDtoMapper accInfoDtoMapper;
    private final OrderDtoMapper orderDtoMapper;
    private final PositionDtoMapper positionDtoMapper;

    public FTTradeService(FutuConfig futuConfig, AccDtoMapper accDtoMapper, AccInfoDtoMapper accInfoDtoMapper, OrderDtoMapper orderDtoMapper, PositionDtoMapper positionDtoMapper) {
        this.accDtoMapper = accDtoMapper;
        this.accInfoDtoMapper = accInfoDtoMapper;
        this.orderDtoMapper = orderDtoMapper;
        this.positionDtoMapper = positionDtoMapper;
        trd.setClientInfo(clientID, 1);
        trd.setConnSpi(this);
        trd.setTrdSpi(this);
        this.futuConfig = futuConfig;
    }

    public void sendGetAccListRequest() {
        TrdGetAccList.Request request = TrdGetAccList.Request
                .newBuilder()
                .setC2S(TrdGetAccList.C2S.newBuilder()
                        .setUserID(0)
                        .build())
                .build();
        int seqNo = trd.getAccList(request);
        LOGGER.info("查询交易业务账户列表.seqNo=" + seqNo);
    }

    private TrdCommon.TrdHeader trdHeader(String accId, Integer tradeEnv, Integer tradeMarket) {
        return TrdCommon.TrdHeader.newBuilder()
                .setAccID(Long.parseLong(accId))
                .setTrdEnv(tradeEnv)
                .setTrdMarket(tradeMarket)
                .build();
    }

    private String encodePwd2Md5(String pwd) {
        MD5.Digest digest = new MD5.Digest();
        digest.update(pwd.getBytes(), 0, pwd.getBytes().length);
        byte[] md5Bytes = new byte[digest.getDigestLength()];
        String lowerCasePwdMd5 = Hex.toHexString(md5Bytes).toLowerCase();
        LOGGER.info("pwd md5 & lowerCase:" + lowerCasePwdMd5);
        return lowerCasePwdMd5;
    }

    public void sendOrderRequest(OrderRequest orderRequest) {
        TrdPlaceOrder.C2S.Builder builder = TrdPlaceOrder.C2S.newBuilder();
        builder.setPacketID(trd.nextPacketID());
        builder.setHeader(this.trdHeader(orderRequest.getAccId(), orderRequest.getTradeEnv(), orderRequest.getTradeMarket()));
        builder.setTrdSide(orderRequest.getTradeSide());
        builder.setOrderType(orderRequest.getOrderType());
        if (orderRequest.getSecurityMarket() != null) {
            builder.setSecMarket(orderRequest.getSecurityMarket());
        }
        builder.setCode(orderRequest.getCode());
        builder.setQty(orderRequest.getQty());
        if (orderRequest.getPrice() != null) {
            builder.setPrice(orderRequest.getPrice());
        }
        TrdPlaceOrder.Request request = TrdPlaceOrder.Request
                .newBuilder()
                .setC2S(builder.build())
                .build();
        int seqNo = trd.placeOrder(request);
        LOGGER.info("下单!!!.seqNo=" + seqNo);
    }

    @Async
    public void sendUnLockRequest(UnlockRequest unlockRequest) {
        TrdUnlockTrade.C2S.Builder c2s = TrdUnlockTrade.C2S.newBuilder();
        c2s.setUnlock(unlockRequest.getUnlock());
        c2s.setPwdMD5(this.encodePwd2Md5(futuConfig.getPwd()));
        if (unlockRequest.getFirm() != null) {
            c2s.setSecurityFirm(unlockRequest.getFirm());
        }
        TrdUnlockTrade.Request request = TrdUnlockTrade.Request
                .newBuilder()
                .setC2S(c2s.build())
                .build();
        int seqNo = trd.unlockTrade(request);
        LOGGER.info("账号解锁.seqNo=" + seqNo);
    }

    @Async
    public void sendGetPositionRequest() {
        List<AccDto> allAcc = accDtoMapper.selectList(null);
        RequestCount requestCount = new RequestCount();
        allAcc.forEach(accDto -> {
            TrdGetPositionList.Request request = TrdGetPositionList.Request
                    .newBuilder()
                    .setC2S(TrdGetPositionList.C2S.newBuilder()
                            .setHeader(this.trdHeader(accDto.getAccId(), accDto.getTradeEnv(),
                                    Integer.valueOf(accDto.getTradeMarketAuthList())))
                            .setRefreshCache(true)
                            .build())
                    .build();
            int seqNo = trd.getPositionList(request);
            LOGGER.info("查询账户持仓.seqNo=" + seqNo);
            requestCount.count();
        });
    }

    @Async
    public void sendGetFundsRequest() {
        List<AccDto> allAcc = accDtoMapper.selectList(null);
        RequestCount requestCount = new RequestCount();
        allAcc.forEach(accDto ->
                Arrays.stream(Currency.values()).forEach(currency -> {
                    if (!currency.getCode().equals(0)) {
                        TrdGetFunds.Request request = TrdGetFunds.Request.newBuilder()
                                .setC2S(TrdGetFunds.C2S.newBuilder()
                                        .setHeader(this.trdHeader(accDto.getAccId(), accDto.getTradeEnv(),
                                                //todo trade_market_auth_list 原则上是存 ','拼接的字符串
                                                Integer.valueOf(accDto.getTradeMarketAuthList())))
                                        .setCurrency(currency.getCode())
                                        .setRefreshCache(true)
                                        .build())
                                .build();
                        int seqNo = trd.getFunds(request);
                        LOGGER.info("查询账户资金.seqNo=" + seqNo);
                        requestCount.count();
                    }
                }));
    }

//    public void sendTradeAccPushSubscribe() {
//        List<AccDto> accounts = accDtoMapper.selectList(null);
//        TrdSubAccPush.Request request = TrdSubAccPush.Request
//                .newBuilder()
//                .setC2S(TrdSubAccPush
//                        .C2S.newBuilder()
//                        .addAllAccIDList(accounts.stream()
//                                .map(accDto -> Long.parseLong(accDto.getAccId()))
//                                .collect(Collectors.toList()))
//                        .build())
//                .build();
//        int seqNo = trd.subAccPush(request);
//        LOGGER.info("交易账号发起订阅.seqNo=" + seqNo);
//    }

    public void sendGetHistoryOrderListRequest() {
        List<AccDto> accounts = accDtoMapper.selectList(null);
        accounts.forEach(accDto -> {
            String beginTime = LocalDateTime.of(2023, 5, 15, 0, 0, 0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.MS"));
            String endTime = LocalTime.MAX.atDate(LocalDate.now().minusDays(1L)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.MS"));
            LOGGER.info("查询历史订单:" + beginTime + "-" + endTime);
            TrdGetHistoryOrderList.Request request = TrdGetHistoryOrderList.Request
                    .newBuilder()
                    .setC2S(TrdGetHistoryOrderList.C2S.newBuilder()
                            .setHeader(this.trdHeader(accDto.getAccId(), accDto.getTradeEnv(),
                                    //todo trade_market_auth_list 原则上是存 ','拼接的字符串
                                    Integer.valueOf(accDto.getTradeMarketAuthList())))
                            .setFilterConditions(TrdCommon.TrdFilterConditions.newBuilder()
                                    //2023/05/15 开始算起
                                    .setBeginTime(beginTime)
                                    // 到每一天的前一天为止
                                    .setEndTime(endTime)
                                    .build())
                            .build())
                    .build();
            int seqNo = trd.getHistoryOrderList(request);
            LOGGER.info("查询历史订单.seqNo=" + seqNo);
        });
    }

    public void sendGetTodayOrderListRequest() {
        List<AccDto> accounts = accDtoMapper.selectList(null);
        accounts.forEach(accDto -> {
            TrdGetOrderList.Request request = TrdGetOrderList.Request.newBuilder()
                    .setC2S(TrdGetOrderList.C2S.newBuilder()
                            .setHeader(this.trdHeader(accDto.getAccId(), accDto.getTradeEnv(),
                                    //todo trade_market_auth_list 原则上是存 ','拼接的字符串
                                    Integer.valueOf(accDto.getTradeMarketAuthList())))
                            .setRefreshCache(true)
                            .build())
                    .build();
            int seqNo = trd.getOrderList(request);
            LOGGER.info("查询今日订单.seqNo=" + seqNo);
        });
    }

    @Override
    public void onInitConnect(FTAPI_Conn client, long errCode, String desc) {
        LOGGER.info("FUTU API 初始化连接 onInitConnect: ret=" + errCode + ",desc=" + desc + ",connID=" + client.getConnectID());
//        webSocketService.onNext(new NotifyMessage(Long.toString(errCode), "FUTU API交易连接初始化."), this.sessionId);
    }

    @Override
    public void onDisconnect(FTAPI_Conn client, long errCode) {
        LOGGER.info("FUTU API 关闭连接连接 onDisconnect: connID=" + client.getConnectID() + ",ret=" + errCode);
//        webSocketService.onNext(new NotifyMessage(Long.toString(errCode), "FUTU API交易连接关闭."), this.sessionId);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        FTAPI.init();
        trd.initConnect(futuConfig.getUrl(), futuConfig.getPort(), futuConfig.isEnableEncrypt());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onReply_GetOrderList(FTAPI_Conn client, int nSerialNo, TrdGetOrderList.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.error("查询今日订单失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("请求序列号:" + nSerialNo + "查询今日订单失败,code:" + rsp.getRetType()));
        } else {
            LOGGER.info("SeqNo:" + nSerialNo + ",connID=" + client.getConnectID() + "查询今日订单...");
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                LOGGER.info(ftGrpcReturnResult.toString());
                List<OrderDto> existOrders = orderDtoMapper.selectList(null);
                JsonObject header = ftGrpcReturnResult.getS2c().get("header").getAsJsonObject();
                OrderDto order = new OrderDto();
                order.setTradeEnv(header.get("trdEnv").getAsInt());
                order.setAccId(header.get("accID").getAsString());
                order.setTradeMarket(header.get("trdMarket").getAsInt());
                if (ftGrpcReturnResult.getS2c().has("orderList")) {
                    Iterator<JsonElement> orderListIterator = ftGrpcReturnResult.getS2c().getAsJsonArray("orderList").iterator();
                    while (orderListIterator.hasNext()) {
                        JsonObject oneOrder = orderListIterator.next().getAsJsonObject();

                        if (existOrders.contains(order)) {
                            //在库里update
                        } else {
                            //不在库里新增
                        }

                    }
                }
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("查询今日订单结果解析失败.", e);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onReply_GetHistoryOrderList(FTAPI_Conn client, int nSerialNo, TrdGetHistoryOrderList.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.error("查询历史订单失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("请求序列号:" + nSerialNo + "查询历史订单失败,code:" + rsp.getRetType()));
        } else {
            LOGGER.info("SeqNo:" + nSerialNo + ",connID=" + client.getConnectID() + "查询历史订单...");
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                LOGGER.info(ftGrpcReturnResult.toString());
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("解析历史订单结果失败.", e);
            }
        }
    }

    @Override
    public void onReply_SubAccPush(FTAPI_Conn client, int nSerialNo, TrdSubAccPush.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.error("下单失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("请求序列号:" + nSerialNo + "订阅失败,code:" + rsp.getRetType()));
        } else {
            LOGGER.info("SeqNo:" + nSerialNo + ",connID=" + client.getConnectID() + "订阅...");
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                LOGGER.info(ftGrpcReturnResult.toString());
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("订阅结果解析失败.", e);
            }

        }
    }

    @Override
    public void onPush_UpdateOrder(FTAPI_Conn client, TrdUpdateOrder.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.error("下单失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("接收订单推送失败,code:" + rsp.getRetType()));
        } else {
            LOGGER.info("connID=" + client.getConnectID() + "接收订单推送...");
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                LOGGER.info(ftGrpcReturnResult.toString());
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("接收订单推送结果解析失败.", e);
            }

        }
    }

    @Override
    public void onPush_UpdateOrderFill(FTAPI_Conn client, TrdUpdateOrderFill.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.error("下单失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("接收成交推送失败,code:" + rsp.getRetType()));
        } else {
            LOGGER.info("connID=" + client.getConnectID() + "接收成交推送...");
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                LOGGER.info(ftGrpcReturnResult.toString());
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("接收成交推送结果解析失败.", e);
            }
        }
    }

    @Override
    public void onReply_PlaceOrder(FTAPI_Conn client, int nSerialNo, TrdPlaceOrder.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.error("下单失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("请求序列号:" + nSerialNo + "账号解锁失败,code:" + rsp.getRetType()));
        } else {
            LOGGER.info("SeqNo:" + nSerialNo + ",connID=" + client.getConnectID() + "下单...");
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                if (ftGrpcReturnResult.getRetType() == 0) {
                    LOGGER.info("SeqNo:" + nSerialNo + ",connID=" + client.getConnectID() + "下单成功!!");
                } else {
                    LOGGER.info("SeqNo:" + nSerialNo + ",connID=" + client.getConnectID() + "下单失败:" + ftGrpcReturnResult.getRetType());
                    LOGGER.error("失败原因:" + ftGrpcReturnResult.getRetMsg());
                }
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("下单结果解析失败.", e);
            }

        }
    }

    @Override
    public void onReply_UnlockTrade(FTAPI_Conn client, int nSerialNo, TrdUnlockTrade.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.error("账号解锁失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("请求序列号:" + nSerialNo + "账号解锁失败,code:" + rsp.getRetType()));
        } else {
            LOGGER.info("SeqNo:" + nSerialNo + ",connID=" + client.getConnectID() + "账号解锁...");
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                Integer returnType = ftGrpcReturnResult.getRetType();
                if (returnType == 0) {
                    LOGGER.info("解锁成功.");
                } else {
                    LOGGER.info("解锁失败.");
                    LOGGER.info("失败原因:" + ftGrpcReturnResult.getRetMsg());
                }
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("账号解锁结果解析失败.", e);
            }

        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onReply_GetPositionList(FTAPI_Conn client, int nSerialNo, TrdGetPositionList.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.error("查询账户持仓失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("请求序列号:" + nSerialNo + "查询账户持仓失败,code:" + rsp.getRetType()));
//            webSocketService.onNext(new NotifyMessage(String.valueOf(nSerialNo), "查询账户持仓失败" + rsp.getRetMsg()), this.sessionId);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                List<PositionDto> existPositions = positionDtoMapper.selectList(null);
                JsonObject header = ftGrpcReturnResult.getS2c().get("header").getAsJsonObject();
                PositionDto position = new PositionDto();
                position.setAccId(header.get("accID").getAsString());
                position.setTradeEnv(header.get("trdEnv").getAsInt());
                position.setAccTradeMarket(header.get("trdMarket").getAsInt());
                if (ftGrpcReturnResult.getS2c().has("positionList")) {
                    JsonArray positionList = ftGrpcReturnResult.getS2c().get("positionList").getAsJsonArray();
                    Iterator<JsonElement> positionIterator = positionList.iterator();
                    while (positionIterator.hasNext()) {
                        JsonObject onePosition = positionIterator.next().getAsJsonObject();
                        if (onePosition.has("positionID")) {
                            position.setPositionId(onePosition.get("positionID").getAsLong());
                        }
                        if (onePosition.has("positionSide")) {
                            position.setPositionSide(onePosition.get("positionSide").getAsInt());
                        }
                        if (onePosition.has("code")) {
                            position.setCode(onePosition.get("code").getAsString());
                        }
                        if (onePosition.has("name")) {
                            position.setName(onePosition.get("name").getAsString());
                        }
                        if (onePosition.has("qty")) {
                            position.setQty(onePosition.get("qty").getAsDouble());
                        }
                        if (onePosition.has("canSellQty")) {
                            position.setCanSellQty(onePosition.get("canSellQty").getAsDouble());
                        }
                        if (onePosition.has("price")) {
                            position.setPrice(onePosition.get("price").getAsDouble());
                        }
                        if (onePosition.has("costPrice")) {
                            position.setCostPrice(onePosition.get("costPrice").getAsDouble());
                        }
                        if (onePosition.has("val")) {
                            position.setVal(onePosition.get("val").getAsDouble());
                        }
                        if (onePosition.has("plVal")) {
                            position.setPlVal(onePosition.get("plVal").getAsDouble());
                        }
                        if (onePosition.has("plRatio")) {
                            position.setPlRatio(onePosition.get("plRatio").getAsDouble());
                        }
                        if (onePosition.has("secMarket")) {
                            position.setSecurityMarket(onePosition.get("secMarket").getAsInt());
                        }
                        if (onePosition.has("td_plVal")) {
                            position.setTdPlVal(onePosition.get("td_plVal").getAsDouble());
                        }
                        if (onePosition.has("td_trdVal")) {
                            position.setTdTrdVal(onePosition.get("td_trdVal").getAsDouble());
                        }
                        if (onePosition.has("td_buyVal")) {
                            position.setTdBuyVal(onePosition.get("td_buyVal").getAsDouble());
                        }
                        if (onePosition.has("td_buyQty")) {
                            position.setTdBuyQty(onePosition.get("td_buyQty").getAsDouble());
                        }
                        if (onePosition.has("td_sellVal")) {
                            position.setTdSellVal(onePosition.get("td_sellVal").getAsDouble());
                        }
                        if (onePosition.has("td_sellQty")) {
                            position.setTdSellQty(onePosition.get("td_sellQty").getAsDouble());
                        }
                        if (onePosition.has("unrealizedPL")) {
                            position.setUnrealizedPl(onePosition.get("unrealizedPL").getAsDouble());
                        }
                        if (onePosition.has("realizedPL")) {
                            position.setRealizedPl(onePosition.get("realizedPL").getAsDouble());
                        }
                        if (onePosition.has("currency")) {
                            position.setCurrency(onePosition.get("currency").getAsInt());
                        }
                        if (onePosition.has("trdMarket")) {
                            position.setTradeMarket(onePosition.get("trdMarket").getAsInt());
                        }
                        if (existPositions.contains(position)) {
                            //库里有 update
                            PositionDto findOne = existPositions.stream()
                                    .filter(exist -> exist.getPositionId().equals(position.getPositionId()))
                                    .collect(Collectors.toList()).get(0);
                            findOne.setTradeEnv(position.getTradeEnv());
                            findOne.setAccId(position.getAccId());
                            findOne.setAccTradeMarket(position.getAccTradeMarket());
                            findOne.setPositionId(position.getPositionId());
                            findOne.setPositionSide(position.getPositionSide());
                            findOne.setCode(position.getCode());
                            findOne.setName(position.getName());
                            findOne.setQty(position.getQty());
                            findOne.setCanSellQty(position.getCanSellQty());
                            findOne.setPrice(position.getPrice());
                            findOne.setCostPrice(position.getCostPrice());
                            findOne.setVal(position.getVal());
                            findOne.setPlVal(position.getPlVal());
                            findOne.setPlRatio(position.getPlRatio());
                            findOne.setSecurityMarket(position.getSecurityMarket());
                            findOne.setTdPlVal(position.getTdPlVal());
                            findOne.setTdTrdVal(position.getTdTrdVal());
                            findOne.setTdBuyVal(position.getTdBuyVal());
                            findOne.setTdBuyQty(position.getTdBuyQty());
                            findOne.setTdSellVal(position.getTdSellVal());
                            findOne.setTdSellQty(position.getTdSellQty());
                            findOne.setUnrealizedPl(position.getUnrealizedPl());
                            findOne.setRealizedPl(position.getRealizedPl());
                            findOne.setCurrency(position.getCurrency());
                            findOne.setTradeMarket(position.getTradeMarket());
                            int updateRow = positionDtoMapper.updateById(findOne);
                            if (updateRow > 0) {
                                LOGGER.info("持仓信息修改成功.条数:" + updateRow);
//                                webSocketService.onNext(new NotifyMessage(String.valueOf(updateRow), "持仓信息修改成功.条数:" + updateRow), this.sessionId);
                            } else {
                                LOGGER.info("持仓信息已最新");
//                                webSocketService.onNext(new NotifyMessage(String.valueOf(0), "持仓信息已最新"), this.sessionId);
                            }
                        } else {
                            //库里没有新增
                            int insertRow = positionDtoMapper.insertSelective(position);
                            if (insertRow > 0) {
                                LOGGER.info("持仓信息插入成功.条数:" + insertRow);
//                                webSocketService.onNext(new NotifyMessage(String.valueOf(insertRow), "持仓信息插入成功.条数:" + insertRow), this.sessionId);
                            } else {
                                LOGGER.info("持仓信息插入失败");
//                                webSocketService.onNext(new NotifyMessage(String.valueOf(0), "持仓信息插入失败"), this.sessionId);
                            }
                        }
                    }
                }
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("解析账户持仓结果失败.", e);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onReply_GetFunds(FTAPI_Conn client, int nSerialNo, TrdGetFunds.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.error("查询账户资金失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("请求序列号:" + nSerialNo + "查询账户资金失败,code:" + rsp.getRetType()));
        } else {
            LOGGER.info("SeqNo:" + nSerialNo + ",connID=" + client.getConnectID() + "查询账户资金...");
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                LOGGER.info(ftGrpcReturnResult.toString());
                AccInfoDto accInfoDto = new AccInfoDto();
                JsonObject header = ftGrpcReturnResult.getS2c().get("header").getAsJsonObject();
                String accId = header.get("accID").getAsString();
                accInfoDto.setAccId(accId);
                JsonObject funds = ftGrpcReturnResult.getS2c().get("funds").getAsJsonObject();
                if (funds.has("power")) {
                    accInfoDto.setPower(funds.get("power").getAsDouble());
                }
                if (funds.has("totalAssets")) {
                    accInfoDto.setTotalAssets(funds.get("totalAssets").getAsDouble());
                }
                if (funds.has("cash")) {
                    accInfoDto.setCash(funds.get("cash").getAsDouble());
                }
                if (funds.has("marketVal")) {
                    accInfoDto.setMarketVal(funds.get("marketVal").getAsDouble());
                }
                if (funds.has("frozenCash")) {
                    accInfoDto.setFrozenCash(funds.get("frozenCash").getAsDouble());
                }
                if (funds.has("debtCash")) {
                    accInfoDto.setDebtCash(funds.get("debtCash").getAsDouble());
                }
                if (funds.has("avlWithdrawalCash")) {
                    accInfoDto.setAvlWithdrawalCash(funds.get("avlWithdrawalCash").getAsDouble());
                }
                if (funds.has("currency")) {
                    accInfoDto.setCurrency(funds.get("currency").getAsInt());
                }
                if (funds.has("availableFunds")) {
                    accInfoDto.setAvailableFunds(funds.get("availableFunds").getAsDouble());
                }
                if (funds.has("unrealizedPL")) {
                    accInfoDto.setUnrealizedPl(funds.get("unrealizedPL").getAsDouble());
                }
                if (funds.has("realizedPL")) {
                    accInfoDto.setRealizedPl(funds.get("realizedPL").getAsDouble());
                }
                if (funds.has("riskLevel")) {
                    accInfoDto.setRiskLevel(funds.get("riskLevel").getAsInt());
                }
                if (funds.has("initialMargin")) {
                    accInfoDto.setInitialMargin(funds.get("initialMargin").getAsDouble());
                }
                if (funds.has("maintenanceMargin")) {
                    accInfoDto.setMaintenanceMargin(funds.get("maintenanceMargin").getAsDouble());
                }
                if (funds.has("cashInfoList")) {
                    accInfoDto.setCashInfoList(funds.get("cashInfoList").getAsJsonArray().toString());
                }
                if (funds.has("maxPowerShort")) {
                    accInfoDto.setMaxPowerShort(funds.get("maxPowerShort").getAsDouble());
                }
                if (funds.has("netCashPower")) {
                    accInfoDto.setNetCashPower(funds.get("netCashPower").getAsDouble());
                }
                if (funds.has("longMv")) {
                    accInfoDto.setLongMv(funds.get("longMv").getAsDouble());
                }
                if (funds.has("shortMv")) {
                    accInfoDto.setShortMv(funds.get("shortMv").getAsDouble());
                }
                if (funds.has("pendingAsset")) {
                    accInfoDto.setPendingAsset(funds.get("pendingAsset").getAsDouble());
                }
                if (funds.has("maxWithdrawal")) {
                    accInfoDto.setMaxWithdrawal(funds.get("maxWithdrawal").getAsDouble());
                }
                if (funds.has("riskStatus")) {
                    accInfoDto.setRiskStatus(funds.get("riskStatus").getAsInt());
                }
                if (funds.has("marginCallMargin")) {
                    accInfoDto.setMarginCallMargin(funds.get("marginCallMargin").getAsDouble());
                }
                if (funds.has("isPdt")) {
                    accInfoDto.setIsPdt(funds.get("isPdt").getAsBoolean() ? 1 : 0);
                }
                if (funds.has("pdtSeq")) {
                    accInfoDto.setPdtSeq(funds.get("pdtSeq").getAsString());
                }
                if (funds.has("beginningDTBP")) {
                    accInfoDto.setBeginningDtbp(funds.get("beginningDTBP").getAsDouble());
                }
                if (funds.has("remainingDTBP")) {
                    accInfoDto.setRemainingDtbp(funds.get("remainingDTBP").getAsDouble());
                }
                if (funds.has("dtCallAmount")) {
                    accInfoDto.setDtCallAmount(funds.get("dtCallAmount").getAsDouble());
                }
                if (funds.has("dtStatus")) {
                    accInfoDto.setDtStatus(funds.get("dtStatus").getAsInt());
                }
                QueryWrapper<AccInfoDto> wrapper = Wrappers.query();
                wrapper.eq("acc_id", accInfoDto.getAccId());
                //todo 发起请求是按币种遍历,这样会造成覆盖,但是现在初期没有所谓币种,之后再改造
                AccInfoDto exist = accInfoDtoMapper.selectOne(wrapper);
                if (exist != null) {
                    exist.setAccId(accInfoDto.getAccId());
                    exist.setPower(accInfoDto.getPower());
                    exist.setTotalAssets(accInfoDto.getTotalAssets());
                    exist.setCash(accInfoDto.getCash());
                    exist.setMarketVal(accInfoDto.getMarketVal());
                    exist.setFrozenCash(accInfoDto.getFrozenCash());
                    exist.setDebtCash(accInfoDto.getDebtCash());
                    exist.setAvlWithdrawalCash(accInfoDto.getAvlWithdrawalCash());
                    exist.setCurrency(accInfoDto.getCurrency());
                    exist.setAvailableFunds(accInfoDto.getAvailableFunds());
                    exist.setUnrealizedPl(accInfoDto.getUnrealizedPl());
                    exist.setRealizedPl(accInfoDto.getRealizedPl());
                    exist.setRiskLevel(accInfoDto.getRiskLevel());
                    exist.setInitialMargin(accInfoDto.getInitialMargin());
                    exist.setMaintenanceMargin(accInfoDto.getMaintenanceMargin());
                    exist.setCashInfoList(accInfoDto.getCashInfoList());
                    exist.setMaxPowerShort(accInfoDto.getMaxPowerShort());
                    exist.setNetCashPower(accInfoDto.getNetCashPower());
                    exist.setLongMv(accInfoDto.getLongMv());
                    exist.setShortMv(accInfoDto.getShortMv());
                    exist.setPendingAsset(accInfoDto.getPendingAsset());
                    exist.setMaxWithdrawal(accInfoDto.getMaxWithdrawal());
                    exist.setRiskStatus(accInfoDto.getRiskStatus());
                    exist.setMarginCallMargin(accInfoDto.getMarginCallMargin());
                    exist.setIsPdt(accInfoDto.getIsPdt());
                    exist.setPdtSeq(accInfoDto.getPdtSeq());
                    exist.setBeginningDtbp(accInfoDto.getBeginningDtbp());
                    exist.setRemainingDtbp(accInfoDto.getRemainingDtbp());
                    exist.setDtCallAmount(accInfoDto.getDtCallAmount());
                    exist.setDtStatus(accInfoDto.getDtStatus());
                    int updateRow = accInfoDtoMapper.updateById(exist);
                    if (updateRow > 0) {
                        LOGGER.info("修改账户详细信息成功.条数:" + updateRow);
                    }
                } else {
                    int insertRow = accInfoDtoMapper.insertSelective(accInfoDto);
                    if (insertRow > 0) {
                        LOGGER.info("插入账户详细信息成功.条数:" + insertRow);
                    }
                }
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("解析账户资金结果失败.", e);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onReply_GetAccList(FTAPI_Conn client, int nSerialNo, TrdGetAccList.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.error("查询交易业务账户列表失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("请求序列号:" + nSerialNo + "查询交易业务账户列表失败,code:" + rsp.getRetType()));
        } else {
            LOGGER.info("SeqNo:" + nSerialNo + ",connID=" + client.getConnectID() + "查询交易业务账户列表...");
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                Iterator<JsonElement> accListIterator = ftGrpcReturnResult.getS2c().getAsJsonArray("accList").iterator();
                List<AccDto> insertdDtos = new ArrayList<>();
                while (accListIterator.hasNext()) {
                    JsonObject accInfo = accListIterator.next().getAsJsonObject();
                    AccDto accDto = new AccDto();
                    if (accInfo.has("trdEnv")) {
                        accDto.setTradeEnv(accInfo.get("trdEnv").getAsInt());
                    }
                    if (accInfo.has("accID")) {
                        accDto.setAccId(accInfo.get("accID").getAsString());
                    }
                    if (accInfo.has("trdMarketAuthList")) {
                        List<Integer> tempTrdMarketAuthList = new ArrayList<>();
                        accInfo.getAsJsonArray("trdMarketAuthList").forEach(jsonElement -> tempTrdMarketAuthList.add(jsonElement.getAsInt()));
                        accDto.setTradeMarketAuthList(Joiner.on(",").join(tempTrdMarketAuthList));
                    }
                    if (accInfo.has("accType")) {
                        accDto.setAccType(accInfo.get("accType").getAsInt());
                    }
                    if (accInfo.has("cardNum")) {
                        accDto.setCardNum(accInfo.get("cardNum").getAsString());
                    }
                    if (accInfo.has("securityFirm")) {
                        accDto.setFirm(accInfo.get("securityFirm").getAsInt());
                    }
                    if (accInfo.has("simAccType")) {
                        accDto.setSimAccType(accInfo.get("simAccType").getAsInt());
                    }
                    insertdDtos.add(accDto);
                }
                List<AccDto> allAcc = accDtoMapper.selectList(null);
                List<AccDto> updateList = new ArrayList<>();
                Iterator<AccDto> beInsertedIterator = insertdDtos.iterator();
                while (beInsertedIterator.hasNext()) {
                    AccDto beInserted = beInsertedIterator.next();
                    for (AccDto existed : allAcc) {
                        if (existed.equals(beInserted)) {
                            //在库里
                            existed.setTradeEnv(beInserted.getTradeEnv());
                            existed.setAccId(beInserted.getAccId());
                            existed.setTradeMarketAuthList(beInserted.getTradeMarketAuthList());
                            existed.setAccType(beInserted.getAccType());
                            existed.setCardNum(beInserted.getCardNum());
                            existed.setFirm(beInserted.getFirm());
                            existed.setSimAccType(beInserted.getSimAccType());
                            updateList.add(existed);
                            beInsertedIterator.remove();
                        }
                    }
                }
                if (insertdDtos.size() > 0) {
                    int insertedRow = accDtoMapper.insertBatch(insertdDtos);
                    LOGGER.info("交易业务账户插入条数:" + insertedRow);
                }
                if (updateList.size() > 0) {
                    int updateRows = accDtoMapper.updateBatch(updateList);
                    LOGGER.info("交易业务账户修改条数:" + updateRows);
                }
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("查询交易业务账户列表解析结果失败.", e);
            }
        }
    }

}
