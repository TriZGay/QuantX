package io.futakotome.rtck.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.futakotome.common.MessageCommon;
import io.futakotome.common.message.RTKLMessage;
import io.futakotome.rtck.mapper.KLMapper;
import io.futakotome.rtck.mapper.dto.RTKLDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.futakotome.rtck.mapper.KLMapper.*;

@Component
public class RTKLineListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(RTKLineListener.class);
    private final KLMapper mapper;
    private final ObjectMapper objectMapper;

    public RTKLineListener(KLMapper mapper, ObjectMapper objectMapper) {
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(groupId = MessageCommon.RT_KL_MIN_1_CONSUMER_GROUP, topics = {MessageCommon.RT_KL_MIN_1_TOPIC},
            errorHandler = "rtKLineErrorHandler")
    public void min1Listener(List<ConsumerRecord<?, ?>> records) throws JsonProcessingException {
        List<RTKLMessage> rtklMessages = new ArrayList<>();
        for (ConsumerRecord<?, ?> record : records) {
            RTKLMessage message = objectMapper.readValue(record.value().toString(), new TypeReference<>() {
            });
            rtklMessages.add(message);
        }
        List<RTKLDto> toAddKLines = rtklMessages.stream()
                .map(this::message2Dto).collect(Collectors.toList());
        if (mapper.insertBatch(toAddKLines, KL_MIN_1_RAW_TABLE_NAME)) {
            LOGGER.info("1分K线入库成功");
        }
    }

    private RTKLDto message2Dto(RTKLMessage rtklMessage) {
        RTKLDto dto = new RTKLDto();
        dto.setMarket(rtklMessage.getMarket());
        dto.setCode(rtklMessage.getCode());
        dto.setRehabType(rtklMessage.getRehabType());
        dto.setHighPrice(rtklMessage.getHighPrice());
        dto.setOpenPrice(rtklMessage.getOpenPrice());
        dto.setLowPrice(rtklMessage.getLowPrice());
        dto.setClosePrice(rtklMessage.getClosePrice());
        dto.setLastClosePrice(rtklMessage.getLastClosePrice());
        dto.setVolume(rtklMessage.getVolume());
        dto.setTurnover(rtklMessage.getTurnover());
        dto.setTurnoverRate(rtklMessage.getTurnoverRate());
        dto.setPe(rtklMessage.getPe());
        dto.setChangeRate(rtklMessage.getChangeRate() == null ? -1 : rtklMessage.getChangeRate());
        dto.setUpdateTime(rtklMessage.getUpdateTime());
        dto.setAddTime(rtklMessage.getAddTime());
        return dto;
    }

    //todo K线其他类型
    @KafkaListener(groupId = "test_ck_kafka_group", topics = {"test_ck_kafka"},
            errorHandler = "rtKLineErrorHandler")
    public void testCkKafka(String message) {
        System.out.println(message);
    }

}
