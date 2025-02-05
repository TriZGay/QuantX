package io.futakotome.rtck.listener;

import io.futakotome.common.message.RTBasicQuoteMessage;
import io.futakotome.rtck.mapper.RTBasicQuoteMapper;
import io.futakotome.rtck.mapper.dto.RTBasicQuoteDto;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
//@RocketMQMessageListener(consumerGroup = MessageCommon.RT_BASIC_QUO_CONSUMER_GROUP_PLATE, topic = MessageCommon.RT_BASIC_QUO_TOPIC_PLATE)
public class RTPlateBasicQuoteListener extends AbstractBasicQuoteListener implements RocketMQListener<RTBasicQuoteMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RTPlateBasicQuoteListener.class);
    private final RTBasicQuoteMapper mapper;

    public RTPlateBasicQuoteListener(RTBasicQuoteMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onMessage(RTBasicQuoteMessage rtBasicQuoteMessage) {
        RTBasicQuoteDto dto = message2Dto(rtBasicQuoteMessage);
        if (mapper.insertOnePlateBasicQuote(dto)) {
            LOGGER.info("板块实时报价入库成功.");
        }
    }

}
