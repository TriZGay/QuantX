package io.futakotome.stock.domain;

import com.futu.openapi.pb.QotCommon;
import com.futu.openapi.pb.QotGetPlateSet;
import io.futakotome.stock.service.QuotesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HKMarket implements RequestPlateInfo {
    private static final Logger LOGGER = LoggerFactory.getLogger(HKMarket.class);

    @Override
    public void sendPlateInfoRequest() {
        QotGetPlateSet.Request request = QotGetPlateSet.Request.newBuilder()
                .setC2S(QotGetPlateSet.C2S.newBuilder()
                        .setMarket(QotCommon.QotMarket.QotMarket_HK_Security_VALUE)
                        .setPlateSetType(QotCommon.PlateSetType.PlateSetType_All_VALUE)
                        .build()).build();
        int seqNo = QuotesService.qot.getPlateSet(request);
        LOGGER.info("SeqNo:" + seqNo + "香港市场请求板块信息:" + request.toString());
    }
}
