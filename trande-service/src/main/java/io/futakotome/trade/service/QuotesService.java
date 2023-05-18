package io.futakotome.trade.service;

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
import io.futakotome.trade.controller.OrderRequest;
import io.futakotome.trade.controller.UnlockRequest;
import io.futakotome.trade.domain.Currency;
import io.futakotome.trade.dto.AccDto;
import io.futakotome.trade.dto.AccInfoDto;
import io.futakotome.trade.dto.OrderDto;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuotesService implements FTSPI_Conn, FTSPI_Trd, InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuotesService.class);
    private static final Gson GSON = new Gson();

    private final FutuConfig futuConfig;

    private static final String clientID = "javaclient";

    public static final FTAPI_Conn_Trd trd = new FTAPI_Conn_Trd();

    private final AccDtoMapper accDtoMapper;
    private final AccInfoDtoMapper accInfoDtoMapper;
    private final OrderDtoMapper orderDtoMapper;
    private final PositionDtoMapper positionDtoMapper;

    public QuotesService(FutuConfig futuConfig, AccDtoMapper accDtoMapper, AccInfoDtoMapper accInfoDtoMapper, OrderDtoMapper orderDtoMapper, PositionDtoMapper positionDtoMapper) {
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

    public void sendTradeAccPushSubscribe() {
        List<AccDto> accounts = accDtoMapper.selectList(null);
        TrdSubAccPush.Request request = TrdSubAccPush.Request
                .newBuilder()
                .setC2S(TrdSubAccPush
                        .C2S.newBuilder()
                        .addAllAccIDList(accounts.stream()
                                .map(accDto -> Long.parseLong(accDto.getAccId()))
                                .collect(Collectors.toList()))
                        .build())
                .build();
        int seqNo = trd.subAccPush(request);
        LOGGER.info("交易账号发起订阅.seqNo=" + seqNo);
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
        this.sendGetAccListRequest();
        this.sendTradeAccPushSubscribe();
    }

    @Override
    public void onDisconnect(FTAPI_Conn client, long errCode) {
        LOGGER.info("FUTU API 关闭连接连接 onDisconnect: connID=" + client.getConnectID() + ",ret=" + errCode);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        FTAPI.init();
        trd.initConnect(futuConfig.getUrl(), futuConfig.getPort(), futuConfig.isEnableEncrypt());
    }

    @Override
    public void onReply_GetOrderList(FTAPI_Conn client, int nSerialNo, TrdGetOrderList.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.error("查询今日订单失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("请求序列号:" + nSerialNo + "查询今日订单失败,code:" + rsp.getRetType()));
        } else {
            LOGGER.info("SeqNo:" + nSerialNo + ",connID=" + client.getConnectID() + "查询今日订单...");
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                LOGGER.info(ftGrpcReturnResult.toString());
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("查询今日订单结果解析失败.", e);
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
                LOGGER.info(ftGrpcReturnResult.toString());
                JsonObject s2c = ftGrpcReturnResult.getS2c();
                OrderDto order = new OrderDto();
                order.setAccId(s2c.get("header").getAsJsonObject().get("accID").getAsString());
                order.setTradeMarket(s2c.get("header").getAsJsonObject().get("trdMarket").getAsInt());
                order.setTradeEnv(s2c.get("header").getAsJsonObject().get("trdEnv").getAsInt());
                order.setOrderId(s2c.get("orderID").getAsString());
                int insertRow = orderDtoMapper.insertSelective(order);
                if (insertRow > 0) {
                    LOGGER.info("下单数据插入成功.条数:" + insertRow);
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
        } else {
            LOGGER.info("SeqNo:" + nSerialNo + ",connID=" + client.getConnectID() + "查询账户持仓...");
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                LOGGER.info(ftGrpcReturnResult.toString());
                JsonObject header = ftGrpcReturnResult.getS2c().get("header").getAsJsonObject();
                if (ftGrpcReturnResult.getS2c().has("positionList")) {
                    JsonArray positionList = ftGrpcReturnResult.getS2c().get("positionList").getAsJsonArray();
                    Iterator<JsonElement> positionIterator = positionList.iterator();
                    while (positionIterator.hasNext()) {
                        JsonElement onePosition = positionIterator.next();

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
                int insertRow = accInfoDtoMapper.insertSelective(accInfoDto);
                if (insertRow > 0) {
                    LOGGER.info("插入账户详细信息成功.条数:" + insertRow);
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
