package io.futakotome.sub.service;

import com.futu.openapi.*;
import com.futu.openapi.pb.*;
import com.google.gson.Gson;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.futakotome.sub.config.FutuConfig;
import io.futakotome.sub.controller.SubscribeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class QuotesService implements FTSPI_Conn, FTSPI_Qot, InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuotesService.class);
    private static final Gson GSON = new Gson();

    private final FutuConfig futuConfig;

    private static final String clientID = "javaclient";

    public static final FTAPI_Conn_Qot qot = new FTAPI_Conn_Qot();


    public QuotesService(FutuConfig futuConfig) {
        qot.setClientInfo(clientID, 1);
        qot.setConnSpi(this);
        qot.setQotSpi(this);
        this.futuConfig = futuConfig;
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
        LOGGER.info("发起订阅.seqNo=" + seqNo);
    }

    @Override
    public void onInitConnect(FTAPI_Conn client, long errCode, String desc) {
        LOGGER.info("FUTU API 初始化连接 onInitConnect: ret=" + errCode + ",desc=" + desc + ",connID=" + client.getConnectID());
    }

    @Override
    public void onDisconnect(FTAPI_Conn client, long errCode) {
        LOGGER.info("FUTU API 关闭连接连接 onDisconnect: connID=" + client.getConnectID() + ",ret=" + errCode);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        FTAPI.init();
        qot.initConnect(futuConfig.getUrl(), futuConfig.getPort(), futuConfig.isEnableEncrypt());
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
                LOGGER.info(ftGrpcReturnResult.toString());
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("订阅解结果解析失败.", e);
            }

        }
    }

    @Override
    public void onReply_GetSubInfo(FTAPI_Conn client, int nSerialNo, QotGetSubInfo.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.error("查询订阅信息失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("请求序列号:" + nSerialNo + "查询订阅信息失败,code:" + rsp.getRetType()));
        } else {
            LOGGER.info("SeqNo:" + nSerialNo + ",connID=" + client.getConnectID() + "查询订阅信息...");
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                LOGGER.info(ftGrpcReturnResult.toString());
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("查询订阅信息解析结果失败.", e);
            }
        }
    }
}
