package io.futakotome.akshares.task;

import io.futakotome.akshares.dto.StockUsRTPrice;
import io.futakotome.akshares.dto.StockZhRTPrice;
import io.futakotome.akshares.service.AkSharesService;
import io.futakotome.akshares.service.KafkaService;
import io.futakotome.common.message.akshres.BigARtPriceMessage;
import io.futakotome.common.message.akshres.UsRtPriceMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class KLineFetchTask {
    private final AkSharesService akSharesService;
    private final KafkaService kafkaService;

    public KLineFetchTask(AkSharesService akSharesService, KafkaService kafkaService) {
        this.akSharesService = akSharesService;
        this.kafkaService = kafkaService;
    }

    //    @Scheduled(cron = "*/10 * * * * ?")
    public void fetchBigARealTime() {
        List<StockZhRTPrice> shRtPrices = akSharesService.fetchShStockRtPrice();
        shRtPrices.forEach(stockZhRTPrice -> kafkaService.sendBigRtPrices(this.dtoToMessage(stockZhRTPrice)));
    }

    @Scheduled(cron = "0 0/3 * * * ?")
    public void fetchUsRealTime() {
        List<StockUsRTPrice> usRTPrices = akSharesService.fetchStockUsRealTime();
        usRTPrices.forEach(stockUsRTPrice -> kafkaService.sendUsRtPrices(this.dtoToMessage(stockUsRTPrice)));
    }

    private UsRtPriceMessage dtoToMessage(StockUsRTPrice dto) {
        UsRtPriceMessage usRtPriceMessage = new UsRtPriceMessage();
        usRtPriceMessage.setId(dto.getId());
        usRtPriceMessage.setCode(dto.getCode());
        usRtPriceMessage.setName(dto.getName());
        usRtPriceMessage.setPrice(dto.getPrice());
        usRtPriceMessage.setRatio(dto.getRatio());
        usRtPriceMessage.setRatioVal(dto.getRatioVal());
        usRtPriceMessage.setTurnover(dto.getTurnover());
        usRtPriceMessage.setVolume(dto.getVolume());
        usRtPriceMessage.setAmplitude(dto.getAmplitude());
        usRtPriceMessage.setHigh(dto.getHigh());
        usRtPriceMessage.setLow(dto.getLow());
        usRtPriceMessage.setOpen(dto.getOpen());
        usRtPriceMessage.setClose(dto.getClose());
        usRtPriceMessage.setTurnoverRatio(dto.getTurnoverRatio());
        usRtPriceMessage.setMarketCap(dto.getMarketCap());
        usRtPriceMessage.setPeRatio(dto.getPeRatio());
        usRtPriceMessage.setAddTime(LocalDateTime.now(ZoneId.of("Asia/Shanghai"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
        return usRtPriceMessage;

    }

    private BigARtPriceMessage dtoToMessage(StockZhRTPrice dto) {
        BigARtPriceMessage kLineRtPriceMessage = new BigARtPriceMessage();
        kLineRtPriceMessage.setId(dto.getId());
        kLineRtPriceMessage.setCode(dto.getCode());
        kLineRtPriceMessage.setName(dto.getName());
        kLineRtPriceMessage.setPrice(dto.getPrice());
        kLineRtPriceMessage.setRatio(dto.getRatio());
        kLineRtPriceMessage.setRatioVal(dto.getRatioVal());
        kLineRtPriceMessage.setTurnover(dto.getTurnover());
        kLineRtPriceMessage.setVolume(dto.getVolume());
        kLineRtPriceMessage.setAmplitude(dto.getAmplitude());
        kLineRtPriceMessage.setHigh(dto.getHigh());
        kLineRtPriceMessage.setLow(dto.getLow());
        kLineRtPriceMessage.setOpen(dto.getOpen());
        kLineRtPriceMessage.setClose(dto.getClose());
        kLineRtPriceMessage.setEquivalentRatio(dto.getEquivalentRatio());
        kLineRtPriceMessage.setTurnoverRatio(dto.getTurnoverRatio());
        kLineRtPriceMessage.setPeRatio(dto.getPeRatio());
        kLineRtPriceMessage.setPbRatio(dto.getPbRatio());
        kLineRtPriceMessage.setMarketCap(dto.getMarketCap());
        kLineRtPriceMessage.setCircularRatio(dto.getCircularRatio());
        kLineRtPriceMessage.setGrowthRatio(dto.getGrowthRatio());
        kLineRtPriceMessage.setFiveMRatio(dto.getFiveMRatio());
        kLineRtPriceMessage.setSixtyDRatio(dto.getSixtyDRatio());
        kLineRtPriceMessage.setYtdPercentRatio(dto.getYtdPercentRatio());
        return kLineRtPriceMessage;

    }
}
