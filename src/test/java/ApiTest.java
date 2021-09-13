import com.futu.openapi.*;
import com.futu.openapi.pb.QotCommon;
import com.futu.openapi.pb.QotGetPlateSet;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class ApiTest implements FTSPI_Conn, FTSPI_Qot {
    Vertx vertx;
    FTAPI_Conn_Qot qot = new FTAPI_Conn_Qot();

    @BeforeEach
    void init() {
        vertx = Vertx.vertx();
    }

    public ApiTest() {
        qot.setClientInfo("javaclient", 1);
        qot.setConnSpi(this);//连接回调
        qot.setQotSpi(this);
    }

    public void start() {
        qot.initConnect("127.0.0.1", (short) 11111, false);
    }

    @Override
    public void onInitConnect(FTAPI_Conn client, long errCode, String desc) {
        System.out.printf("Qot onInitConnect: ret=%b desc=%s connID=%d\n", errCode, desc, client.getConnectID());
        if (errCode != 0)
            return;

        QotGetPlateSet.C2S c2s = QotGetPlateSet.C2S.newBuilder()
                .setMarket(QotCommon.QotMarket.QotMarket_HK_Security_VALUE)
                .setPlateSetType(QotCommon.PlateSetType.PlateSetType_Industry_VALUE)
                .build();
        QotGetPlateSet.Request req = QotGetPlateSet.Request.newBuilder().setC2S(c2s).build();
        int seqNo = qot.getPlateSet(req);
        System.out.printf("Send QotGetPlateSet: %d\n", seqNo);
    }

    @Override
    public void onDisconnect(FTAPI_Conn client, long errCode) {
        System.out.printf("Qot onDisConnect: %d\n", errCode);
    }

    @Override
    public void onReply_GetPlateSet(FTAPI_Conn client, int nSerialNo, QotGetPlateSet.Response rsp) {
        if (rsp.getRetType() != 0) {
            System.out.printf("QotGetPlateSet failed: %s\n", rsp.getRetMsg());
        } else {
            try {
                String json = JsonFormat.printer().print(rsp);
                System.out.printf("Receive QotGetPlateSet: %s\n", json);
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    @DisplayName("获取板块列表")
    void testListPlate(VertxTestContext context) {
        FTAPI.init();
        ApiTest qot = new ApiTest();
        qot.start();
        while (true) {
            try {
                Thread.sleep(1000 * 600);
            } catch (InterruptedException exc) {

            }
        }
    }

    @AfterEach
    void close() {
        vertx.close();
    }

}
