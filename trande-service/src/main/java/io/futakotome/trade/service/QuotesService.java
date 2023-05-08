package io.futakotome.trade.service;

import com.futu.openapi.*;
import com.google.gson.Gson;
import io.futakotome.trade.config.FutuConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

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

}
