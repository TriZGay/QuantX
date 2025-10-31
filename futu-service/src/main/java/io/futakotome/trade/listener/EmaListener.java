package io.futakotome.trade.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.futakotome.common.message.RTEmaMessage;
import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EmaListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmaListener.class);
    private final QuantxFutuWsService wsService;
    private final ObjectMapper objectMapper;

    public EmaListener(QuantxFutuWsService wsService, ObjectMapper objectMapper) {
        this.wsService = wsService;
        this.objectMapper = objectMapper;
    }

//    @KafkaListener(groupId = RT_EMA5_CONSUMER_GROUP_STREAM, topics = {RT_EMA5_TOPIC}, errorHandler = "commonErrorHandler")
    public void ema5Listener(String emaMessage) throws JsonProcessingException {
        RTEmaMessage rtEmaMessage = objectMapper.readValue(emaMessage, RTEmaMessage.class);
        wsService.sendRtEma5(rtEmaMessage);
    }
}
