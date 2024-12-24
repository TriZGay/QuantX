package io.futakotome.rtck.listener;

import io.futakotome.common.MessageCommon;
import io.futakotome.common.message.RTBasicQuoteMessage;
import io.futakotome.rtck.mapper.RTBasicQuoteMapper;
import io.futakotome.rtck.mapper.dto.RTBasicQuoteDto;
import io.futakotome.rtck.message.AbstractWebSocketServerHandler;
import io.futakotome.rtck.message.core.WebSocketSender;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
//@RocketMQMessageListener(consumerGroup = MessageCommon.RT_BASIC_QUO_CONSUMER_GROUP_STOCK, topic = MessageCommon.RT_BASIC_QUO_TOPIC_STOCK)
public class RTStockBasicQuoteListener extends AbstractBasicQuoteListener implements RocketMQListener<RTBasicQuoteMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RTStockBasicQuoteListener.class);
    private final RTBasicQuoteMapper mapper;
    private final ConcurrentHashMap<String, WebSocketSender> senderMap;

    public RTStockBasicQuoteListener(RTBasicQuoteMapper mapper, ConcurrentHashMap<String, WebSocketSender> senderMap) {
        this.mapper = mapper;
        this.senderMap = senderMap;
    }

    @Override
    public void onMessage(RTBasicQuoteMessage rtBasicQuoteMessage) {
        RTBasicQuoteDto dto = message2Dto(rtBasicQuoteMessage);
        if (mapper.insertOneStockBasicQuote(dto)) {
            LOGGER.info("正股实时报价入库成功.");
            sendBasicQuoteWsMessage(rtBasicQuoteMessage,
                    senderMap.get(AbstractWebSocketServerHandler.BASIC_QUOTE_TAG));
        }
    }
}
