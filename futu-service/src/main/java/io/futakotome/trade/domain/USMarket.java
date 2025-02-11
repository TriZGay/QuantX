package io.futakotome.trade.domain;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.futu.openapi.pb.*;
import io.futakotome.trade.domain.code.StockType;
import io.futakotome.trade.dto.PlateDto;
import io.futakotome.trade.dto.StockDto;
import io.futakotome.trade.mapper.PlateDtoMapper;
import io.futakotome.trade.mapper.StockDtoMapper;
import io.futakotome.trade.service.FTQotService;
import io.futakotome.trade.utils.CacheManager;
import io.futakotome.trade.utils.RequestCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

public class USMarket implements
        RequestStaticInfo, RequestPlateInfo, RequestStockInfo, RequestTradeDate {
    private static final Logger LOGGER = LoggerFactory.getLogger(USMarket.class);

    @Override
    public void sendPlateInfoRequest() {
        QotGetPlateSet.Request request = QotGetPlateSet.Request.newBuilder()
                .setC2S(QotGetPlateSet.C2S.newBuilder()
                        .setMarket(QotCommon.QotMarket.QotMarket_US_Security_VALUE)
                        .setPlateSetType(QotCommon.PlateSetType.PlateSetType_All_VALUE)
                        .build()).build();
        int seqNo = FTQotService.qot.getPlateSet(request);
        LOGGER.info("SeqNo:" + seqNo + "美国市场请求板块信息:" + request.toString());
    }

    @Override
    public void sendPlateInfoRequest(StockDtoMapper stockMapper) {
        List<StockDto> hkStocks = stockMapper.selectList(Wrappers.<StockDto>query()
                .in("stock_type", StockType.Eqty.getCode(), StockType.Index.getCode())
                .and(stockDtoQueryWrapper -> stockDtoQueryWrapper.eq("market", QotCommon.QotMarket.QotMarket_US_Security_VALUE)));
        int stockSize = hkStocks.size();
        int i = 0;
        //一次最多请求200个股票
        //每30秒最多10次请求,也就是30秒最多请求2000个股票
        int requestStockLimit = 200;
        RequestCount requestCount = new RequestCount();
        while (stockSize > requestStockLimit) {
            QotGetOwnerPlate.Request request = QotGetOwnerPlate.Request.newBuilder()
                    .setC2S(QotGetOwnerPlate.C2S.newBuilder()
                            .addAllSecurityList(hkStocks
                                    .subList(i, i + requestStockLimit)
                                    .stream().map(stockDto -> QotCommon.Security.newBuilder()
                                            .setMarket(stockDto.getMarket())
                                            .setCode(stockDto.getCode())
                                            .build())
                                    .collect(Collectors.toList()))
                            .build())
                    .build();
            i = i + requestStockLimit;
            stockSize = stockSize - requestStockLimit;
            int seqNo = FTQotService.qot.getOwnerPlate(request);
            LOGGER.info("SeqNo:" + seqNo + "美国市场请求板块信息:" + request.toString() + "count:" + request);
            requestCount.count();
        }
        if (stockSize > 0) {
            QotGetOwnerPlate.Request request = QotGetOwnerPlate.Request.newBuilder()
                    .setC2S(QotGetOwnerPlate.C2S.newBuilder()
                            .addAllSecurityList(hkStocks
                                    .subList(i, i + stockSize)
                                    .stream().map(stockDto -> QotCommon.Security.newBuilder()
                                            .setMarket(stockDto.getMarket())
                                            .setCode(stockDto.getCode())
                                            .build())
                                    .collect(Collectors.toList()))
                            .build())
                    .build();
            int seqNo = 0;
            LOGGER.info("SeqNo:" + seqNo + "美国市场请求板块信息:" + request.toString());
        }
    }

    @Override
    public void sendStockInfoRequest(PlateDtoMapper plateDtoMapper) {
        List<PlateDto> hkAllPlates = plateDtoMapper.searchByMarketEquals(QotCommon.QotMarket.QotMarket_US_Security_VALUE);
        RequestCount requestCount = new RequestCount();
        for (int i = 0; i < hkAllPlates.size(); i++) {
            String plateCode = hkAllPlates.get(i).getCode();
            QotGetPlateSecurity.Request request = QotGetPlateSecurity.Request.newBuilder()
                    .setC2S(QotGetPlateSecurity.C2S.newBuilder()
                            .setPlate(QotCommon.Security.newBuilder()
                                    .setMarket(QotCommon.QotMarket.QotMarket_US_Security_VALUE)
                                    .setCode(plateCode)
                                    .build())
                            .build())
                    .build();
            int seqNo = FTQotService.qot.getPlateSecurity(request);
            LOGGER.info("SeqNo:" + seqNo + "美国市场请求股票信息:" + request.toString());
            CacheManager.put(String.valueOf(seqNo), plateCode);
            requestCount.count();
        }
        try {
            LOGGER.info("美国市场请求股票信息结束.sleep....");
            Thread.sleep(30000L);
        } catch (InterruptedException e) {
            LOGGER.error("sleep失败!", e);
        }
    }

    @Override
    public void sendStaticInfoRequest() {
        QotGetStaticInfo.Request request = QotGetStaticInfo.Request.newBuilder()
                .setC2S(QotGetStaticInfo.C2S.newBuilder()
                        .setMarket(QotCommon.QotMarket.QotMarket_US_Security_VALUE)
                        .build()).build();
        int seqNo = FTQotService.qot.getStaticInfo(request);
        LOGGER.info("SeqNo:" + seqNo + "美国市场请求静态信息:" + request.toString());
    }

    @Override
    public void sendIpoInfoRequest() {
        QotGetIpoList.Request request = QotGetIpoList.Request.newBuilder()
                .setC2S(QotGetIpoList.C2S.newBuilder()
                        .setMarket(QotCommon.QotMarket.QotMarket_US_Security_VALUE)
                        .build())
                .build();
        int seqNo = FTQotService.qot.getIpoList(request);
        LOGGER.info("SeqNo:" + seqNo + "美国市场请求IPO信息:" + request.toString());
    }

    @Override
    public void sendTradeDateRequest() {
        LocalDate now = LocalDate.now();
        String firstDayOfYear = now.with(TemporalAdjusters.firstDayOfYear()).toString();
        String endDayOfYear = now.with(TemporalAdjusters.lastDayOfYear()).toString();
        QotRequestTradeDate.Request request = QotRequestTradeDate.Request.newBuilder()
                .setC2S(QotRequestTradeDate.C2S.newBuilder()
                        .setMarket(QotCommon.TradeDateMarket.TradeDateMarket_US_VALUE)
                        .setBeginTime(firstDayOfYear)
                        .setEndTime(endDayOfYear)
                        .build()
                ).build();
        int seqNo = FTQotService.qot.requestTradeDate(request);
        CacheManager.put(String.valueOf(seqNo), QotCommon.TradeDateMarket.TradeDateMarket_US_VALUE);
        LOGGER.info("SeqNo:" + seqNo + "美国市场请求交易日信息:" + request.toString());
    }
}
