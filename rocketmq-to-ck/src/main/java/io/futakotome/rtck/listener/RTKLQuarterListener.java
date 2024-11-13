package io.futakotome.rtck.listener;

import io.futakotome.common.MessageCommon;
import io.futakotome.common.message.RTKLMessage;
import io.futakotome.rtck.mapper.RTKLMapper;
import io.futakotome.rtck.mapper.dto.RTKLDto;
import io.futakotome.rtck.message.AbstractWebSocketServerHandler;
import io.futakotome.rtck.message.core.WebSocketSender;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
@RocketMQMessageListener(consumerGroup = MessageCommon.RT_KL_QUARTER_CONSUMER_GROUP, topic = MessageCommon.RT_KL_QUARTER_TOPIC, consumeMode = ConsumeMode.ORDERLY)
public class RTKLQuarterListener extends AbstractKLineListener implements RocketMQListener<RTKLMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RTKLQuarterListener.class);
    private final RTKLMapper mapper;
    private final ConcurrentHashMap<String, WebSocketSender> senderMap;

    public RTKLQuarterListener(RTKLMapper mapper, ConcurrentHashMap<String, WebSocketSender> senderMap) {
        this.mapper = mapper;
        this.senderMap = senderMap;
    }

    @Override
    public void onMessage(RTKLMessage rtklMessage) {
        RTKLDto dto = message2Dto(rtklMessage);
        if (mapper.insertOne(dto, RTKLMapper.KL_QUARTER_TABLE_NAME)) {
            LOGGER.info("季K数据入库成功");
        }

    }

}
