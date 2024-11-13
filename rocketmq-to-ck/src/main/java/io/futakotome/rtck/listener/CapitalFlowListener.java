package io.futakotome.rtck.listener;

import io.futakotome.common.MessageCommon;
import io.futakotome.common.message.CapitalFlowMessage;
import io.futakotome.rtck.mapper.CapitalFlowMapper;
import io.futakotome.rtck.mapper.dto.CapitalFlowDto;
import io.futakotome.rtck.message.AbstractWebSocketServerHandler;
import io.futakotome.rtck.message.core.WebSocketSender;
import io.futakotome.rtck.message.dto.NotifyWsMessage;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
@RocketMQMessageListener(consumerGroup = MessageCommon.CAPITAL_FLOW_CONSUMER_GROUP, topic = MessageCommon.CAPITAL_FLOW_TOPIC)
public class CapitalFlowListener implements RocketMQListener<CapitalFlowMessage> {
    public static final Logger LOGGER = LoggerFactory.getLogger(CapitalFlowListener.class);
    private final CapitalFlowMapper mapper;
    private final ConcurrentHashMap<String, WebSocketSender> senderMap;

    public CapitalFlowListener(CapitalFlowMapper mapper, ConcurrentHashMap<String, WebSocketSender> senderMap) {
        this.mapper = mapper;
        this.senderMap = senderMap;
    }

    @Override
    public void onMessage(CapitalFlowMessage message) {
        CapitalFlowDto dto = new CapitalFlowDto();
        dto.setMarket(message.getMarket());
        dto.setCode(message.getCode());
        dto.setInFlow(message.getInFlow() == null ? -1 : message.getInFlow());
        dto.setMainInFlow(message.getMainInFlow() == null ? -1 : message.getMainInFlow());
        dto.setSuperInFlow(message.getSuperInFlow() == null ? -1 : message.getSuperInFlow());
        dto.setBigInFlow(message.getBigInFlow() == null ? -1 : message.getBigInFlow());
        dto.setMidInFlow(message.getMidInFlow() == null ? -1 : message.getMidInFlow());
        dto.setSmlInFlow(message.getSmlInFlow() == null ? -1 : message.getSmlInFlow());
        dto.setTime(message.getTime());
        dto.setLastValidTime(message.getLastValidTime());
        if (mapper.insetOneCapitalFlow(dto)) {
            LOGGER.info("资金流向入库成功");
            WebSocketSender sender = senderMap.get(AbstractWebSocketServerHandler.NOTIFY_TAG);
            if (sender != null) {
                sender.sendData(new NotifyWsMessage("资金流向入库成功"));
            }
        }
    }
}
