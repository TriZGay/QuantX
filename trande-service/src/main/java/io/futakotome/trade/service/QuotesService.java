package io.futakotome.trade.service;

import com.futu.openapi.*;
import com.futu.openapi.pb.TrdCommon;
import com.futu.openapi.pb.TrdGetAccList;
import com.futu.openapi.pb.TrdGetFunds;
import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.futakotome.trade.config.FutuConfig;
import io.futakotome.trade.domain.Currency;
import io.futakotome.trade.dto.AccDto;
import io.futakotome.trade.mapper.AccDtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
public class QuotesService implements FTSPI_Conn, FTSPI_Trd, InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuotesService.class);
    private static final Gson GSON = new Gson();

    private final FutuConfig futuConfig;

    private static final String clientID = "javaclient";

    public static final FTAPI_Conn_Trd trd = new FTAPI_Conn_Trd();

    private final AccDtoMapper accDtoMapper;

    public QuotesService(FutuConfig futuConfig, AccDtoMapper accDtoMapper) {
        this.accDtoMapper = accDtoMapper;
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

    public void sendGetFundsRequest() {
        List<AccDto> allAcc = accDtoMapper.selectList(null);
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
                    }
                }));
    }

    @Override
    public void onInitConnect(FTAPI_Conn client, long errCode, String desc) {
        LOGGER.info("FUTU API 初始化连接 onInitConnect: ret=" + errCode + ",desc=" + desc + ",connID=" + client.getConnectID());
        this.sendGetAccListRequest();
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
    public void onReply_GetFunds(FTAPI_Conn client, int nSerialNo, TrdGetFunds.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.error("查询账户资金失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("请求序列号:" + nSerialNo + "查询账户资金失败,code:" + rsp.getRetType()));
        } else {
            LOGGER.info("SeqNo:" + nSerialNo + ",connID=" + client.getConnectID() + "查询账户资金...");
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                LOGGER.info(ftGrpcReturnResult.toString());
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
