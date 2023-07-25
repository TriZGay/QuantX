package io.futakotome.rtck.listener;

import io.futakotome.common.MessageCommon;
import io.futakotome.common.message.RTKLMessage;
import io.futakotome.rtck.mapper.RTKLMapper;
import io.futakotome.rtck.mapper.dto.RTKLDto;
import io.futakotome.rtck.message.MessageService;
import io.futakotome.rtck.message.ReactiveWebSocketHandlerMapping;
import io.futakotome.rtck.message.RealTimeKLMessage;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = MessageCommon.RT_KL_DAY_CONSUMER_GROUP, topic = MessageCommon.RT_KL_DAY_TOPIC)
public class RTKLDayListener implements RocketMQListener<RTKLMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RTKLDayListener.class);
    private final RTKLMapper mapper;
    private final MessageService messageService;

    public RTKLDayListener(RTKLMapper mapper, MessageService messageService) {
        this.mapper = mapper;
        this.messageService = messageService;
    }

    @Override
    public void onMessage(RTKLMessage rtklMessage) {
        RTKLDto dto = new RTKLDto();
        dto.setMarket(rtklMessage.getMarket());
        dto.setCode(rtklMessage.getCode());
        dto.setRehabType(rtklMessage.getRehabType());
        dto.setHighPrice(rtklMessage.getHighPrice());
        dto.setOpenPrice(rtklMessage.getOpenPrice());
        dto.setLowPrice(rtklMessage.getLowPrice());
        dto.setClosePrice(rtklMessage.getClosePrice());
        dto.setLastClosePrice(rtklMessage.getLastClosePrice());
        dto.setVolume(rtklMessage.getVolume());
        dto.setTurnover(rtklMessage.getTurnover());
        dto.setTurnoverRate(rtklMessage.getTurnoverRate());
        dto.setPe(rtklMessage.getPe());
        dto.setChangeRate(rtklMessage.getChangeRate());
        dto.setUpdateTime(rtklMessage.getUpdateTime());
        if (mapper.insertOne(dto, RTKLMapper.KL_DAY_TABLE_NAME)) {
            LOGGER.info("日K数据入库成功");
            sendKLineWsMessage(rtklMessage);
        }
    }

    private void sendKLineWsMessage(RTKLMessage rtklMessage) {
        RealTimeKLMessage realTimeKLMessage = new RealTimeKLMessage();
        realTimeKLMessage.setMarket(rtklMessage.getMarket());
        realTimeKLMessage.setCode(rtklMessage.getCode());
        realTimeKLMessage.setRehabType(rtklMessage.getRehabType());
        realTimeKLMessage.setHighPrice(rtklMessage.getHighPrice());
        realTimeKLMessage.setOpenPrice(rtklMessage.getOpenPrice());
        realTimeKLMessage.setLowPrice(rtklMessage.getLowPrice());
        realTimeKLMessage.setClosePrice(rtklMessage.getClosePrice());
        realTimeKLMessage.setLastClosePrice(rtklMessage.getLastClosePrice());
        realTimeKLMessage.setVolume(rtklMessage.getVolume());
        realTimeKLMessage.setTurnover(rtklMessage.getTurnover());
        realTimeKLMessage.setTurnoverRate(rtklMessage.getTurnoverRate());
        realTimeKLMessage.setPe(rtklMessage.getPe());
        realTimeKLMessage.setChangeRate(rtklMessage.getChangeRate());
        realTimeKLMessage.setUpdateTime(rtklMessage.getUpdateTime());
        LOGGER.info("WebSocket消息:{}", realTimeKLMessage.toString());
        messageService.onNext(realTimeKLMessage, ReactiveWebSocketHandlerMapping.KLINE_PATH);
    }
}
