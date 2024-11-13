package io.futakotome.rtck.listener;

import io.futakotome.common.MessageCommon;
import io.futakotome.common.message.RTTickerMessage;
import io.futakotome.rtck.mapper.RTTickerMapper;
import io.futakotome.rtck.mapper.dto.RTTickerDto;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = MessageCommon.RT_TICKER_CONSUMER_GROUP, topic = MessageCommon.RT_TICKER_TOPIC)
public class RTTickerListener implements RocketMQListener<RTTickerMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RTTickerListener.class);
    private final RTTickerMapper mapper;

    public RTTickerListener(RTTickerMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onMessage(RTTickerMessage rtTickerMessage) {
        RTTickerDto dto = new RTTickerDto();
        dto.setMarket(rtTickerMessage.getMarket());
        dto.setCode(rtTickerMessage.getCode());
        dto.setSequence(rtTickerMessage.getSequence());
        dto.setTickerDirection(rtTickerMessage.getTickerDirection());
        dto.setPrice(rtTickerMessage.getPrice());
        dto.setVolume(rtTickerMessage.getVolume());
        dto.setTurnover(rtTickerMessage.getTurnover());
        dto.setTickerType(rtTickerMessage.getTickerType());
        dto.setTypeSign(rtTickerMessage.getTypeSign());
        dto.setUpdateTime(rtTickerMessage.getUpdateTime());
        if (mapper.insertOne(dto)) {
            LOGGER.info("逐笔数据插入成功");
        }
    }
}
