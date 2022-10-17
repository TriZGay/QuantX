package io.futakotome.quantx.collect.onboot;

import com.futu.openapi.FTAPI_Conn;
import com.futu.openapi.FTAPI_Conn_Qot;
import com.futu.openapi.FTSPI_Conn;
import com.futu.openapi.FTSPI_Qot;
import com.futu.openapi.pb.QotGetPlateSet;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class FutuQotService implements FTSPI_Qot, FTSPI_Conn {
    FTAPI_Conn_Qot qot = new FTAPI_Conn_Qot();
    private static final Logger LOGGER = Logger.getLogger(FutuQotService.class.getName());

    @ConfigProperty(name = "futu.gateway.address")
    String ftIp;

    @ConfigProperty(name = "futu.gateway.pwd")
    Short ftPwd;

    @ConfigProperty(name = "futu.gateway.encrypt")
    boolean enabledEncrypt;

    public FutuQotService() {
        qot.setClientInfo("javaclient", 1);
        qot.setConnSpi(this);
        qot.setQotSpi(this);
    }

    public FTAPI_Conn_Qot getQot() {
        return this.qot;
    }

    public void runFutuApiStartup(@Observes StartupEvent startupEvent) {
        this.start();
    }

    public void start() {
        qot.initConnect(ftIp, ftPwd, enabledEncrypt);
    }

    @Override
    public void onInitConnect(FTAPI_Conn client, long errCode, String desc) {
        LOGGER.infov("Qot onInitConnect: ret={0} desc={1} connID={2}", errCode, desc, client.getConnectID());
    }

    @Override
    public void onDisconnect(FTAPI_Conn client, long errCode) {
        LOGGER.infov("Qot onDisconnect: {0}", errCode);
    }

    @Override
    public void onReply_GetPlateSet(FTAPI_Conn client, int nSerialNo, QotGetPlateSet.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.errorv("GetPlateSetFailed: {0}", rsp.getRetMsg());
        } else {
            try {
                String json = JsonFormat.printer().print(rsp);
                System.out.println("获取板块列表数据: " + json);
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
        }
    }
}
