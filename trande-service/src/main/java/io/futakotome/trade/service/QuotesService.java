package io.futakotome.trade.service;

import com.futu.openapi.*;
import com.futu.openapi.pb.TrdGetAccList;
import com.google.gson.Gson;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.futakotome.trade.config.FutuConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
public class QuotesService implements FTSPI_Conn, FTSPI_Trd, InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuotesService.class);
    private static final Gson GSON = new Gson();

    private final FutuConfig futuConfig;

    private static final String clientID = "javaclient";

    public static final FTAPI_Conn_Trd trd = new FTAPI_Conn_Trd();

    public QuotesService(FutuConfig futuConfig) {
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
    public void onReply_GetAccList(FTAPI_Conn client, int nSerialNo, TrdGetAccList.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.error("查询交易业务账户列表失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("请求序列号:" + nSerialNo + "查询交易业务账户列表失败,code:" + rsp.getRetType()));
        } else {
            LOGGER.info("SeqNo:" + nSerialNo + ",connID=" + client.getConnectID() + "查询交易业务账户列表...");
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                LOGGER.info(ftGrpcReturnResult.toString());
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("查询交易业务账户列表解析结果失败.", e);
            }
        }
    }
}
