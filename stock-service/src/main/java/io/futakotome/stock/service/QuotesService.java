package io.futakotome.stock.service;

import com.futu.openapi.FTAPI_Conn;
import com.futu.openapi.FTAPI_Conn_Qot;
import com.futu.openapi.FTSPI_Conn;
import com.futu.openapi.FTSPI_Qot;
import com.futu.openapi.pb.QotCommon;
import com.futu.openapi.pb.QotGetPlateSet;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.futakotome.stock.config.FutuConfig;
import io.futakotome.stock.dto.PlateDto;
import io.futakotome.stock.mapper.PlateDtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class QuotesService implements FTSPI_Conn, FTSPI_Qot, InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuotesService.class);
    private static final Gson GSON = new Gson();

    private final PlateDtoMapper plateMapper;
    private final FutuConfig futuConfig;

    private static final String clientID = "javaclient";
    private final FTAPI_Conn_Qot qot = new FTAPI_Conn_Qot();


    public QuotesService(PlateDtoMapper plateMapper, FutuConfig futuConfig) {
        qot.setClientInfo(clientID, 1);
        qot.setConnSpi(this);
        qot.setQotSpi(this);
        this.futuConfig = futuConfig;
        this.plateMapper = plateMapper;
    }

    @Scheduled(fixedRate = 10000L)
    public void sync() {
        this.sendPlateInfoRequest(QotCommon.QotMarket.QotMarket_HK_Security_VALUE,
                QotCommon.PlateSetType.PlateSetType_Industry_VALUE);
    }

    public void sendPlateInfoRequest(Integer market, Integer plateSetType) {
        QotGetPlateSet.Request request = QotGetPlateSet.Request.newBuilder()
                .setC2S(QotGetPlateSet.C2S.newBuilder()
                        .setMarket(market)
                        .setPlateSetType(plateSetType)
                        .build()).build();
        int seqNo = qot.getPlateSet(request);
        LOGGER.info("SeqNo:" + seqNo + "请求板块信息:" + request.toString());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        qot.initConnect(futuConfig.getUrl(), futuConfig.getPort(), futuConfig.isEnableEncrypt());
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
    @Transactional
    public void onReply_GetPlateSet(FTAPI_Conn client, int nSerialNo, QotGetPlateSet.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.error("查询板块信息失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("请求序列号:" + nSerialNo + "查询板块信息失败,code:" + rsp.getRetType()));
        } else {
            LOGGER.info("connID=" + client.getConnectID() + "查询板块信息...");
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp),
                        FTGrpcReturnResult.class);
                Iterator<JsonElement> plateInfosIterator = ftGrpcReturnResult.getS2c().getAsJsonArray("plateInfoList").iterator();
                List<PlateDto> newPlates = new ArrayList<>();
                while (plateInfosIterator.hasNext()) {
                    JsonElement jsonElement = plateInfosIterator.next();
                    PlateDto dto = new PlateDto();
                    dto.setName(jsonElement.getAsJsonObject().get("name").getAsString());
                    dto.setCode(jsonElement.getAsJsonObject().get("plate").getAsJsonObject().get("code").getAsString());
                    dto.setMarket(jsonElement.getAsJsonObject().get("plate").getAsJsonObject().get("market").getAsInt());
                    newPlates.add(dto);
                }
                List<PlateDto> allPlates = plateMapper.selectList(null);
                newPlates.removeIf(allPlates::contains);
                if (newPlates.size() > 0) {
                    int insertRow = plateMapper.insertBatch(newPlates);
                    LOGGER.info("插入条数:" + insertRow);
                }
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("查询板块信息解析结果失败!", e);
            }
        }

    }
}
