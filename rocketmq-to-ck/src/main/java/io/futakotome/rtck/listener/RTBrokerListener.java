package io.futakotome.rtck.listener;

import io.futakotome.common.MessageCommon;
import io.futakotome.common.message.RTBrokerMessage;
import io.futakotome.rtck.mapper.RTBrokerMapper;
import io.futakotome.rtck.mapper.dto.RTBrokerDto;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = MessageCommon.RT_BROKER_CONSUMER_GROUP, topic = MessageCommon.RT_BROKER_TOPIC)
public class RTBrokerListener implements RocketMQListener<RTBrokerMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RTBrokerListener.class);
    private final RTBrokerMapper mapper;

    public RTBrokerListener(RTBrokerMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onMessage(RTBrokerMessage rtBrokerMessage) {
        RTBrokerDto dto = new RTBrokerDto();
        dto.setMarket(rtBrokerMessage.getMarket());
        dto.setCode(rtBrokerMessage.getCode());
        dto.setBrokerId(rtBrokerMessage.getBrokerId());
        dto.setBrokerName(rtBrokerMessage.getBrokerName());
        dto.setBrokerPos(rtBrokerMessage.getBrokerPos());
        dto.setAskOrBid(rtBrokerMessage.getAskOrBid());
        dto.setOrderId(rtBrokerMessage.getOrderId());
        dto.setVolume(rtBrokerMessage.getVolume());

        if (mapper.insertOne(dto)) {
            LOGGER.info("经纪人队列数据入库成功");
        }
    }
}
