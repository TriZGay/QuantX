package io.futakotome.trade.service;

import com.futu.openapi.*;
import com.futu.openapi.pb.*;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.futakotome.trade.config.FutuConfig;
import io.futakotome.trade.controller.vo.OrderRequest;
import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.domain.code.*;
import io.futakotome.trade.dto.AccSubDto;
import io.futakotome.trade.dto.OrderDto;
import io.futakotome.trade.dto.message.AccountItem;
import io.futakotome.trade.dto.message.PositionMessageContent;
import io.futakotome.trade.dto.ws.AccPositionWsMessage;
import io.futakotome.trade.dto.ws.AccSubscribeWsMessage;
import io.futakotome.trade.dto.ws.AccountsWsMessage;
import io.futakotome.trade.mapper.OrderDtoMapper;
import io.futakotome.trade.utils.CacheManager;
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

    private final AccSubDtoService accSubService;
    private final QuantxFutuWsService quantxFutuWsService;

    public FTTradeService(FutuConfig futuConfig, OrderDtoMapper orderDtoMapper, AccSubDtoService accSubService, QuantxFutuWsService quantxFutuWsService) {
        trd.setClientInfo(clientID, 1);
        trd.setConnSpi(this);
        trd.setTrdSpi(this);
        this.orderDtoMapper = orderDtoMapper;
        this.quantxFutuWsService = quantxFutuWsService;
        this.accSubService = accSubService;
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

    public void accSubscribe(AccSubscribeWsMessage accSubscribeWsMessage) {
        TrdSubAccPush.C2S c2S = TrdSubAccPush.C2S
                .newBuilder().addAllAccIDList(accSubscribeWsMessage
                        .getAccSubscribeItems().stream()
                        .map(item -> Long.parseLong(item.getAccId()))
                        .collect(Collectors.toList()))
                .build();
        TrdSubAccPush.Request request = TrdSubAccPush.Request.newBuilder()
                .setC2S(c2S).build();
        int seqNo = trd.subAccPush(request);
        CacheManager.put(String.valueOf(seqNo), accSubscribeWsMessage);
        LOGGER.info("账户订阅交易推送,seqNo={}", seqNo);
    }

    public void requestAccPosition(AccPositionWsMessage accPositionWsMessage) {
        TrdCommon.TrdHeader header = TrdCommon.TrdHeader.newBuilder()
                .setAccID(Long.parseLong(accPositionWsMessage.getAccId()))
                .setTrdEnv(accPositionWsMessage.getTradeEnv())
                .setTrdMarket(accPositionWsMessage.getTradeMarket())
                .build();
        TrdGetPositionList.C2S c2S = TrdGetPositionList.C2S
                .newBuilder().setHeader(header).build();
        TrdGetPositionList.Request request = TrdGetPositionList.Request
                .newBuilder()
                .setC2S(c2S).build();
        int seqNo = trd.getPositionList(request);
        LOGGER.info("请求账户持仓,seqNo={}", seqNo);
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
        this.connect();

    }

    public void connect() {
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
                    accountItem.setTrdMarketAuthStrList(
                            accountItem.getTrdMarketAuthList().stream().map(TradeMarket::getNameByCode)
                                    .collect(Collectors.toList())
                    );
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
    public void onReply_GetPositionList(FTAPI_Conn client, int nSerialNo, TrdGetPositionList.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "查询账户持仓失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("connID=" + client.getConnectID() + "查询账户持仓失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                List<PositionMessageContent> positionMessageContents = GSON.fromJson(ftGrpcReturnResult.getS2c().getAsJsonArray("positionList"), new TypeToken<List<PositionMessageContent>>() {
                }.getType());
                AccPositionWsMessage accPositionWsMessage = new AccPositionWsMessage();
                accPositionWsMessage.setPositions(positionMessageContents
                        .stream().peek(positionMessageContent -> {
                            positionMessageContent.setPositionSideStr(PositionSide.getNameByCode(positionMessageContent.getPositionSide()));
                            positionMessageContent.setSecMarketStr(TradeSecurityMarket.getNameByCode(positionMessageContent.getSecMarket()));
                            positionMessageContent.setTrdMarketStr(TradeMarket.getNameByCode(positionMessageContent.getTrdMarket()));
                            positionMessageContent.setCurrencyStr(Currency.getNameByCode(positionMessageContent.getCurrency()));
                        }).collect(Collectors.toList()));
                quantxFutuWsService.sendAccPosition(accPositionWsMessage);
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("解析账户持仓结果失败.", e);
            }
        }
    }

    @Override
    public void onReply_SubAccPush(FTAPI_Conn client, int nSerialNo, TrdSubAccPush.Response rsp) {
        if (rsp.getRetType() != 0) {
            String notify = "交易账号订阅失败:" + rsp.getRetMsg();
            LOGGER.error(notify, new IllegalArgumentException("connID=" + client.getConnectID() + "交易账号订阅失败,code:" + rsp.getRetType()));
            sendNotifyMessage(notify);
        } else {
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                AccSubscribeWsMessage accSubscribeWsMessage = (AccSubscribeWsMessage) CacheManager.get(String.valueOf(nSerialNo));
                List<AccSubDto> accSubDtos = accSubscribeWsMessage.getAccSubscribeItems()
                        .stream().map(accSubscribeItem -> {
                            AccSubDto dto = new AccSubDto();
                            dto.setAccId(accSubscribeItem.getAccId());
                            dto.setCardNum(accSubscribeItem.getCardNum());
                            dto.setUniCardNum(accSubscribeItem.getUniCardNum());
                            return dto;
                        }).collect(Collectors.toList());
                int insertRow = accSubService.insertBatch(accSubDtos);
                if (insertRow > 0) {
                    LOGGER.info("账号订阅插入条数:{}", insertRow);
                    sendNotifyMessage("订阅成功");
                }
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

    private void sendNotifyMessage(String notifyContent) {
        if (Objects.nonNull(notifyContent) && !notifyContent.isEmpty()) {
            quantxFutuWsService.sendNotify(notifyContent);
        }
    }

}
