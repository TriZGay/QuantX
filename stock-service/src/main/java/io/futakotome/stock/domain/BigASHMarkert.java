package io.futakotome.stock.domain;

import com.futu.openapi.pb.QotCommon;
import com.futu.openapi.pb.QotGetPlateSecurity;
import com.futu.openapi.pb.QotGetPlateSet;
import com.futu.openapi.pb.QotGetStaticInfo;
import io.futakotome.stock.dto.PlateDto;
import io.futakotome.stock.mapper.PlateDtoMapper;
import io.futakotome.stock.mapper.StockDtoMapper;
import io.futakotome.stock.service.QuotesService;
import io.futakotome.stock.utils.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BigASHMarkert implements RequestStaticInfo, RequestPlateInfo, RequestStockInfo {
    private static final Logger LOGGER = LoggerFactory.getLogger(BigASHMarkert.class);

    @Override
    public void sendPlateInfoRequest() {
        QotGetPlateSet.Request request = QotGetPlateSet.Request.newBuilder()
                .setC2S(QotGetPlateSet.C2S.newBuilder()
                        .setMarket(QotCommon.QotMarket.QotMarket_CNSH_Security_VALUE)
                        .setPlateSetType(QotCommon.PlateSetType.PlateSetType_All_VALUE)
                        .build()).build();
        int seqNo = QuotesService.qot.getPlateSet(request);
        LOGGER.info("SeqNo:" + seqNo + "大A上海市场请求板块信息:" + request.toString());
    }

    @Override
    public void sendPlateInfoRequest(StockDtoMapper stockMapper) {

    }

    @Override
    public void sendStockInfoRequest(PlateDtoMapper plateDtoMapper) {
        List<PlateDto> hkAllPlates = plateDtoMapper.searchByMarketEquals(QotCommon.QotMarket.QotMarket_CNSH_Security_VALUE);
        for (int i = 0; i < hkAllPlates.size(); i++) {
            String plateCode = hkAllPlates.get(i).getCode();
            QotGetPlateSecurity.Request request = QotGetPlateSecurity.Request.newBuilder()
                    .setC2S(QotGetPlateSecurity.C2S.newBuilder()
                            .setPlate(QotCommon.Security.newBuilder()
                                    .setMarket(QotCommon.QotMarket.QotMarket_CNSH_Security_VALUE)
                                    .setCode(plateCode)
                                    .build())
                            .build())
                    .build();
            int seqNo = QuotesService.qot.getPlateSecurity(request);
            LOGGER.info("SeqNo:" + seqNo + "大A上海市场请求股票信息:" + request.toString());
            CacheManager.put(String.valueOf(seqNo), plateCode);
            if (i != 0 && i % 9 == 0) {
                try {
                    LOGGER.info("接口限制每30秒最多请求10次.sleep....");
                    Thread.sleep(30000L);
                } catch (InterruptedException e) {
                    LOGGER.error("sleep失败!", e);
                }
            }
        }
        try {
            LOGGER.info("大A上海市场请求股票信息结束.sleep....");
            Thread.sleep(30000L);
        } catch (InterruptedException e) {
            LOGGER.error("sleep失败!", e);
        }
    }

    @Override
    public void sendStaticInfoRequest() {
        QotGetStaticInfo.Request request = QotGetStaticInfo.Request.newBuilder()
                .setC2S(QotGetStaticInfo.C2S.newBuilder()
                        .setMarket(QotCommon.QotMarket.QotMarket_CNSH_Security_VALUE)
                        .build()).build();
        int seqNo = QuotesService.qot.getStaticInfo(request);
        LOGGER.info("SeqNo:" + seqNo + "大A上海市场请求静态信息:" + request.toString());
    }
}
