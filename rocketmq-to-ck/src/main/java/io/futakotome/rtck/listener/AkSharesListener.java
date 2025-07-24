package io.futakotome.rtck.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.futakotome.common.MessageCommon;
import io.futakotome.common.message.akshres.UsRtPriceMessage;
import io.futakotome.rtck.mapper.AkSharesMapper;
import io.futakotome.rtck.mapper.dto.StockUsRTDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AkSharesListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(AkSharesListener.class);
    private final AkSharesMapper mapper;
    private final ObjectMapper objectMapper;

    public AkSharesListener(AkSharesMapper mapper, ObjectMapper objectMapper) {
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(groupId = MessageCommon.US_RT_CONSUMER_GROUP, topics = {MessageCommon.US_RT_TOPIC},
            errorHandler = "rtKLineErrorHandler")
    public void usRealTimeListener(List<ConsumerRecord<?, ?>> records) throws JsonProcessingException {
        List<UsRtPriceMessage> messages = new ArrayList<>();
        for (ConsumerRecord<?, ?> record : records) {
            LOGGER.info(record.value().toString());
            UsRtPriceMessage message = objectMapper.readValue(record.value().toString(), new TypeReference<>() {
            });
            messages.add(message);
        }
        List<StockUsRTDto> toAdd = messages.stream().map(this::messageToDto).collect(Collectors.toList());
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
