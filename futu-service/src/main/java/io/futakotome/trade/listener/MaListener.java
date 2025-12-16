package io.futakotome.trade.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.futakotome.common.message.RTMaMessage;
import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static io.futakotome.common.MessageCommon.RT_MA5_CONSUMER_GROUP_STREAM;
import static io.futakotome.common.MessageCommon.RT_MA5_TOPIC;

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
}
