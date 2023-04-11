package io.futakotome.stock.domain;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.futu.openapi.pb.QotCommon;
import com.futu.openapi.pb.QotGetPlateSecurity;
import com.futu.openapi.pb.QotGetPlateSet;
import io.futakotome.stock.dto.PlateDto;
import io.futakotome.stock.mapper.PlateDtoMapper;
import io.futakotome.stock.service.QuotesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class HKMarket implements RequestPlateInfo, RequestStockInfo {
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

    @Override
    public void sendStockInfoRequest(PlateDtoMapper plateDtoMapper) {
        List<PlateDto> hkAllPlates = plateDtoMapper.searchByMarketEquals(QotCommon.QotMarket.QotMarket_HK_Security_VALUE);
        for (PlateDto hkPlate : hkAllPlates) {
            String plateCode = hkPlate.getCode();
            QotGetPlateSecurity.Request request = QotGetPlateSecurity.Request.newBuilder()
                    .setC2S(QotGetPlateSecurity.C2S.newBuilder()
                            .setPlate(QotCommon.Security.newBuilder()
                                    .setMarket(QotCommon.QotMarket.QotMarket_HK_Security_VALUE)
                                    .setCode(plateCode)
                                    .build())
                            .build())
                    .build();
            int seqNo = QuotesService.qot.getPlateSecurity(request);
            LOGGER.info("SeqNo:" + seqNo + "香港市场请求股票信息:" + request.toString());
        }

    }
}
