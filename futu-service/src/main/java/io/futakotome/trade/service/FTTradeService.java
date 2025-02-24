package io.futakotome.trade.service;

import com.futu.openapi.*;
import com.futu.openapi.pb.*;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.futakotome.trade.config.FutuConfig;
import io.futakotome.trade.controller.vo.OrderRequest;
import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.domain.code.*;
import io.futakotome.trade.dto.OrderDto;
import io.futakotome.trade.dto.PositionDto;
import io.futakotome.trade.dto.message.AccountItem;
import io.futakotome.trade.dto.ws.AccountsWsMessage;
import io.futakotome.trade.mapper.OrderDtoMapper;
import io.futakotome.trade.mapper.PositionDtoMapper;
import org.bouncycastle.jcajce.provider.digest.MD5;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FTTradeService implements FTSPI_Conn, FTSPI_Trd, InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(FTTradeService.class);
    private static final Gson GSON = new Gson();

    private final FutuConfig futuConfig;

    private static final String clientID = "javaclient";

    private static final FTAPI_Conn_Trd trd = new FTAPI_Conn_Trd();

    private final OrderDtoMapper orderDtoMapper;
    private final PositionDtoMapper positionDtoMapper;
    private final QuantxFutuWsService quantxFutuWsService;

    public FTTradeService(FutuConfig futuConfig, OrderDtoMapper orderDtoMapper, PositionDtoMapper positionDtoMapper, QuantxFutuWsService quantxFutuWsService) {
        trd.setClientInfo(clientID, 1);
        trd.setConnSpi(this);
        trd.setTrdSpi(this);
        this.orderDtoMapper = orderDtoMapper;
        this.positionDtoMapper = positionDtoMapper;
        this.quantxFutuWsService = quantxFutuWsService;
        this.futuConfig = futuConfig;
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
        LOGGER.info("pwd md5 & lowerCase:{}", lowerCasePwdMd5);
        return lowerCasePwdMd5;
    }

    public void requestAccounts() {
        TrdGetAccList.C2S c2S = TrdGetAccList.C2S.newBuilder()
                .setUserID(0)
                .setNeedGeneralSecAccount(true)
                .build();
        TrdGetAccList.Request request = TrdGetAccList.Request.newBuilder()
                .setC2S(c2S)
                .build();
        int seqNo = trd.getAccList(request);
        LOGGER.info("请求交易账户,seqNo={}", seqNo);
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


    @Override
    public void onInitConnect(FTAPI_Conn client, long errCode, String desc) {
        String content = "FUTU API 初始化交易连接 onInitConnect: ret=" + errCode + ",desc=" + desc + ",connID=" + client.getConnectID();
        LOGGER.info(content);
        sendNotifyMessage(content);
    }

    @Override
    public void onDisconnect(FTAPI_Conn client, long errCode) {
        String content = "FUTU API 关闭交易连接 onDisconnect: connID=" + client.getConnectID() + ",ret=" + errCode;
        LOGGER.info(content);
        sendNotifyMessage(content);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        FTAPI.init();
        trd.initConnect(futuConfig.getUrl(), futuConfig.getPort(), futuConfig.isEnableEncrypt());
    }

    @Override
    public void onReply_GetAccList(FTAPI_Conn client, int nSerialNo, TrdGetAccList.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "请求交易账户失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("connID=" + client.getConnectID() + "获取请求交易账户失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                List<AccountItem> accountItems = GSON.fromJson(ftGrpcReturnResult.getS2c().getAsJsonArray("accList"), new TypeToken<List<AccountItem>>() {
                }.getType());
                accountItems = accountItems.stream().peek(accountItem -> {
                    accountItem.setTrdEnvStr(TradeEnv.getNameByCode(accountItem.getTrdEnv()));
                    accountItem.setAccTypeStr(TradeAccType.getNameByCode(accountItem.getAccType()));
                    accountItem.setSecurityFirmStr(SecurityFirm.getNameByCode(accountItem.getSecurityFirm()));
                    accountItem.setSimAccTypeStr(SimAccType.getNameByCode(accountItem.getSimAccType()));
                    accountItem.setAccStatusStr(TradeAccStatus.getNameByCode(accountItem.getAccStatus()));
                }).collect(Collectors.toList());
                AccountsWsMessage accountsWsMessage = new AccountsWsMessage();
                accountsWsMessage.setAccounts(accountItems);
                quantxFutuWsService.sendAccounts(accountsWsMessage);
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("查询交易账户结果解析失败.", e);
            }
        }
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

    private void sendNotifyMessage(String notifyContent) {
        if (Objects.nonNull(notifyContent) && !notifyContent.isEmpty()) {
            quantxFutuWsService.sendNotify(notifyContent);
        }
    }

}
