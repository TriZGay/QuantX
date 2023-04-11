package io.futakotome.stock.domain;

import com.futu.openapi.pb.QotCommon;
import com.futu.openapi.pb.QotGetPlateSet;
import io.futakotome.stock.service.QuotesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SGMarket implements RequestPlateInfo {
    private static final Logger LOGGER = LoggerFactory.getLogger(SGMarket.class);

    @Override
    public void sendPlateInfoRequest() {
        QotGetPlateSet.Request request = QotGetPlateSet.Request.newBuilder()
                .setC2S(QotGetPlateSet.C2S.newBuilder()
                        .setMarket(QotCommon.QotMarket.QotMarket_SG_Security_VALUE)
                        .setPlateSetType(QotCommon.PlateSetType.PlateSetType_All_VALUE)
                        .build()).build();
        int seqNo = QuotesService.qot.getPlateSet(request);
        LOGGER.info("SeqNo:" + seqNo + "新加坡市场请求板块信息:" + request.toString());
    }
}
