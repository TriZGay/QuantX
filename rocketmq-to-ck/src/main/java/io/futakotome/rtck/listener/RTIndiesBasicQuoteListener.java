package io.futakotome.rtck.listener;

import io.futakotome.common.message.RTBasicQuoteMessage;
import io.futakotome.rtck.mapper.RTBasicQuoteMapper;
import io.futakotome.rtck.mapper.dto.RTBasicQuoteDto;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
//@RocketMQMessageListener(consumerGroup = RT_BASIC_QUO_CONSUMER_GROUP_INDIES, topic = RT_BASIC_QUO_TOPIC_INDEX)
public class RTIndiesBasicQuoteListener extends AbstractBasicQuoteListener implements RocketMQListener<RTBasicQuoteMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RTIndiesBasicQuoteListener.class);
    private final RTBasicQuoteMapper mapper;

    public RTIndiesBasicQuoteListener(RTBasicQuoteMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onMessage(RTBasicQuoteMessage rtBasicQuoteMessage) {
        RTBasicQuoteDto dto = message2Dto(rtBasicQuoteMessage);
        if (mapper.insertOneIndexBasicQuote(dto)) {
            LOGGER.info("指数实时报价入库成功.");
        }
    }
}
