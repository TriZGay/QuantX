package io.futakotome.rtck.listener;

import io.futakotome.common.MessageCommon;
import io.futakotome.common.message.akshres.UsRtPriceMessage;
import io.futakotome.rtck.mapper.AkSharesMapper;
import io.futakotome.rtck.mapper.dto.StockUsRTDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AkSharesListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(AkSharesListener.class);
    private final AkSharesMapper mapper;

    public AkSharesListener(AkSharesMapper mapper) {
        this.mapper = mapper;
    }


    @KafkaListener(groupId = MessageCommon.US_RT_CONSUMER_GROUP, topics = {MessageCommon.US_RT_TOPIC},
            errorHandler = "rtKLineErrorHandler")
    public void usRealTimeListener(List<UsRtPriceMessage> usRtPriceMessages) {
        List<StockUsRTDto> toAdd = usRtPriceMessages.stream()
                .map(this::messageToDto).collect(Collectors.toList());
        if (mapper.insertUsRealTimeBatch(toAdd)) {
            LOGGER.info("美股实时行情入库成功");
        }
    }

    private StockUsRTDto messageToDto(UsRtPriceMessage message) {
        StockUsRTDto stockUsRTDto = new StockUsRTDto();
        stockUsRTDto.setId(message.getId());
        stockUsRTDto.setCode(message.getCode());
        stockUsRTDto.setName(message.getName());
        stockUsRTDto.setPrice(Objects.isNull(message.getPrice()) ? -1 : message.getPrice());
        stockUsRTDto.setRatio(Objects.isNull(message.getRatio()) ? -1 : message.getRatio());
        stockUsRTDto.setRatioVal(Objects.isNull(message.getRatioVal()) ? -1 : message.getRatioVal());
        stockUsRTDto.setTurnover(Objects.isNull(message.getTurnover()) ? -1 : message.getTurnover());
        stockUsRTDto.setVolume(Objects.isNull(message.getVolume()) ? -1 : message.getVolume());
        stockUsRTDto.setAmplitude(Objects.isNull(message.getAmplitude()) ? -1 : message.getAmplitude());
        stockUsRTDto.setHigh(Objects.isNull(message.getHigh()) ? -1 : message.getHigh());
        stockUsRTDto.setLow(Objects.isNull(message.getLow()) ? -1 : message.getLow());
        stockUsRTDto.setOpen(Objects.isNull(message.getOpen()) ? -1 : message.getOpen());
        stockUsRTDto.setClose(Objects.isNull(message.getClose()) ? -1 : message.getClose());
        stockUsRTDto.setTurnoverRatio(Objects.isNull(message.getTurnoverRatio()) ? -1 : message.getTurnoverRatio());
        stockUsRTDto.setMarketCap(Objects.isNull(message.getMarketCap()) ? -1 : message.getMarketCap());
        stockUsRTDto.setPeRatio(Objects.isNull(message.getPeRatio()) ? -1 : message.getPeRatio());
        stockUsRTDto.setAddTime(message.getAddTime());
        return stockUsRTDto;

    }
}
