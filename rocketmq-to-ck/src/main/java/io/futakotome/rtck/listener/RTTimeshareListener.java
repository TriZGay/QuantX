package io.futakotome.rtck.listener;

import io.futakotome.common.MessageCommon;
import io.futakotome.common.message.RTTimeShareMessage;
import io.futakotome.rtck.mapper.RTTimeShareMapper;
import io.futakotome.rtck.mapper.dto.RTTimeShareDto;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = MessageCommon.RT_TIMESHARE_CONSUMER_GROUP, topic = MessageCommon.RT_TIMESHARE_TOPIC)
public class RTTimeshareListener implements RocketMQListener<RTTimeShareMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RTTimeshareListener.class);
    private final RTTimeShareMapper mapper;

    public RTTimeshareListener(RTTimeShareMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onMessage(RTTimeShareMessage rtTimeShareMessage) {
        RTTimeShareDto dto = new RTTimeShareDto();
        dto.setMarket(rtTimeShareMessage.getMarket());
        dto.setCode(rtTimeShareMessage.getCode());
        dto.setMinute(rtTimeShareMessage.getMinute());
        dto.setPrice(rtTimeShareMessage.getPrice());
        dto.setLastClosePrice(rtTimeShareMessage.getLastClosePrice());
        dto.setAvgPrice(rtTimeShareMessage.getAvgPrice());
        dto.setVolume(rtTimeShareMessage.getVolume());
        dto.setTurnover(rtTimeShareMessage.getTurnover());
        dto.setUpdateTime(rtTimeShareMessage.getUpdateTime());

        if (mapper.insertOne(dto)) {
            LOGGER.info("分时数据入库成功");
        }
    }
}
