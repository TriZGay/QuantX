package io.futakotome.rtck.listener;

import io.futakotome.common.message.RTBasicQuoteMessage;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import static io.futakotome.common.MessageCommon.RT_BASIC_QUO_CONSUMER_GROUP;
import static io.futakotome.common.MessageCommon.RT_BASIC_QUO_TOPIC;

@Component
@RocketMQMessageListener(consumerGroup = RT_BASIC_QUO_CONSUMER_GROUP, topic = RT_BASIC_QUO_TOPIC)
public class RTBasicQuoteListener implements RocketMQListener<RTBasicQuoteMessage> {
    @Override
    public void onMessage(RTBasicQuoteMessage rtBasicQuoteMessage) {

    }
}
