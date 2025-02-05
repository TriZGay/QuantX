package io.futakotome.rtck.listener;

import io.futakotome.common.MessageCommon;
import io.futakotome.common.message.RTKLMessage;
import io.futakotome.rtck.event.KLineReceivedEventProducer;
import io.futakotome.rtck.mapper.RTKLMapper;
import io.futakotome.rtck.mapper.dto.RTKLDto;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = MessageCommon.RT_KL_MIN_1_CONSUMER_GROUP, topic = MessageCommon.RT_KL_MIN_1_TOPIC)
public class RTKLMin1Listener extends AbstractKLineListener implements RocketMQListener<RTKLMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RTKLMin1Listener.class);
    private final RTKLMapper mapper;
    private final KLineReceivedEventProducer kLineReceivedEventProducer;

    public RTKLMin1Listener(RTKLMapper mapper, KLineReceivedEventProducer kLineReceivedEventProducer) {
        this.mapper = mapper;
        this.kLineReceivedEventProducer = kLineReceivedEventProducer;
    }

    @Override
    public void onMessage(RTKLMessage rtklMessage) {
        RTKLDto dto = message2Dto(rtklMessage);
        kLineReceivedEventProducer.onData(RTKLMapper.KL_MIN_1_TABLE_NAME, dto);
        if (mapper.insertOne(dto, RTKLMapper.KL_MIN_1_TABLE_NAME)) {
            LOGGER.info("1分K数据,[代码={},复权={},K线时间={},入库时间={}]入库成功", dto.getCode(), dto.getRehabType(),
                    dto.getUpdateTime(), dto.getAddTime());
        }
    }
}
