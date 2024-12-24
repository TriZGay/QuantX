package io.futakotome.rtck.listener;

import io.futakotome.common.message.RTBasicQuoteMessage;
import io.futakotome.rtck.mapper.RTBasicQuoteMapper;
import io.futakotome.rtck.mapper.dto.RTBasicQuoteDto;
import io.futakotome.rtck.message.AbstractWebSocketServerHandler;
import io.futakotome.rtck.message.core.WebSocketSender;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

import static io.futakotome.common.MessageCommon.RT_BASIC_QUO_CONSUMER_GROUP_INDIES;
import static io.futakotome.common.MessageCommon.RT_BASIC_QUO_TOPIC_INDEX;

@Component
//@RocketMQMessageListener(consumerGroup = RT_BASIC_QUO_CONSUMER_GROUP_INDIES, topic = RT_BASIC_QUO_TOPIC_INDEX, consumeMode = ConsumeMode.ORDERLY)
public class RTIndiesBasicQuoteListener extends AbstractBasicQuoteListener implements RocketMQListener<RTBasicQuoteMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RTIndiesBasicQuoteListener.class);
    private final RTBasicQuoteMapper mapper;
    private final ConcurrentHashMap<String, WebSocketSender> senderMap;

    public RTIndiesBasicQuoteListener(RTBasicQuoteMapper mapper, ConcurrentHashMap<String, WebSocketSender> senderMap) {
        this.mapper = mapper;
        this.senderMap = senderMap;
    }

    @Override
    public void onMessage(RTBasicQuoteMessage rtBasicQuoteMessage) {
        RTBasicQuoteDto dto = message2Dto(rtBasicQuoteMessage);
        if (mapper.insertOneIndexBasicQuote(dto)) {
            LOGGER.info("指数实时报价入库成功.");
            sendBasicQuoteWsMessage(rtBasicQuoteMessage,
                    senderMap.get(AbstractWebSocketServerHandler.BASIC_QUOTE_TAG));
        }
    }
}
