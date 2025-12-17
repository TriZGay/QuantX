package io.futakotome.trade.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.futakotome.common.message.RTMaMessage;
import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static io.futakotome.common.MessageCommon.*;

@Component
public class MaListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(MaListener.class);
    private final QuantxFutuWsService wsService;
    private final ObjectMapper objectMapper;

    public MaListener(QuantxFutuWsService wsService, ObjectMapper objectMapper) {
        this.wsService = wsService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(groupId = RT_MA5_CONSUMER_GROUP_STREAM, topics = {RT_MA5_TOPIC}, errorHandler = "commonErrorHandler")
    public void ma5Listener(String maMessage) throws JsonProcessingException {
        LOGGER.info("实时MA5数据:{}", maMessage);
        RTMaMessage rtMaMessage = objectMapper.readValue(maMessage, RTMaMessage.class);
        wsService.sendRtMa5(rtMaMessage);
    }

    @KafkaListener(groupId = RT_MA10_CONSUMER_GROUP_STREAM, topics = {RT_MA10_TOPIC}, errorHandler = "commonErrorHandler")
    public void ma10Listener(String maMessage) throws JsonProcessingException {
        LOGGER.info("实时MA10数据:{}", maMessage);
        RTMaMessage rtMaMessage = objectMapper.readValue(maMessage, RTMaMessage.class);
        wsService.sendRtMa10(rtMaMessage);
    }

    @KafkaListener(groupId = RT_MA20_CONSUMER_GROUP_STREAM, topics = {RT_MA20_TOPIC}, errorHandler = "commonErrorHandler")
    public void ma20Listener(String maMessage) throws JsonProcessingException {
        LOGGER.info("实时MA20数据:{}", maMessage);
        RTMaMessage rtMaMessage = objectMapper.readValue(maMessage, RTMaMessage.class);
        wsService.sendRtMa20(rtMaMessage);
    }

    @KafkaListener(groupId = RT_MA30_CONSUMER_GROUP_STREAM, topics = {RT_MA30_TOPIC}, errorHandler = "commonErrorHandler")
    public void ma30Listener(String maMessage) throws JsonProcessingException {
        LOGGER.info("实时MA30数据:{}", maMessage);
        RTMaMessage rtMaMessage = objectMapper.readValue(maMessage, RTMaMessage.class);
        wsService.sendRtMa30(rtMaMessage);
    }

    @KafkaListener(groupId = RT_MA60_CONSUMER_GROUP_STREAM, topics = {RT_MA60_TOPIC}, errorHandler = "commonErrorHandler")
    public void ma60Listener(String maMessage) throws JsonProcessingException {
        LOGGER.info("实时MA60数据:{}", maMessage);
        RTMaMessage rtMaMessage = objectMapper.readValue(maMessage, RTMaMessage.class);
        wsService.sendRtMa60(rtMaMessage);
    }

    @KafkaListener(groupId = RT_MA120_CONSUMER_GROUP_STREAM, topics = {RT_MA120_TOPIC}, errorHandler = "commonErrorHandler")
    public void ma120Listener(String maMessage) throws JsonProcessingException {
        LOGGER.info("实时MA120数据:{}", maMessage);
        RTMaMessage rtMaMessage = objectMapper.readValue(maMessage, RTMaMessage.class);
        wsService.sendRtMa120(rtMaMessage);
    }
}
