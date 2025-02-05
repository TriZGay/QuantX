package io.futakotome.rtck.listener;

import io.futakotome.common.message.RTKLMessage;
import io.futakotome.rtck.mapper.RTKLMapper;
import io.futakotome.rtck.mapper.dto.RTKLDto;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
//@RocketMQMessageListener(consumerGroup = MessageCommon.RT_KL_YEAR_CONSUMER_GROUP, topic = MessageCommon.RT_KL_YEAR_TOPIC)
public class RTKLYearListener extends AbstractKLineListener implements RocketMQListener<RTKLMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RTKLYearListener.class);
    private final RTKLMapper mapper;

    public RTKLYearListener(RTKLMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onMessage(RTKLMessage rtklMessage) {
        RTKLDto dto = message2Dto(rtklMessage);
        if (mapper.insertOne(dto, RTKLMapper.KL_YEAR_TABLE_NAME)) {
            LOGGER.info("年K数据,[代码={},复权={},K线时间={},入库时间={}]入库成功", dto.getCode(), dto.getRehabType(),
                    dto.getUpdateTime(), dto.getAddTime());
        }
    }

}
