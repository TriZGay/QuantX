package io.futakotome.rtck.listener;

import io.futakotome.common.MessageCommon;
import io.futakotome.common.message.HistoryKLDetailMessage;
import io.futakotome.rtck.message.AbstractWebSocketServerHandler;
import io.futakotome.rtck.message.core.WebSocketSender;
import io.futakotome.rtck.message.dto.HistoryKLDetailWsMessage;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
//@RocketMQMessageListener(consumerGroup = MessageCommon.HISTORY_KL_DETAIL_CONSUMER_GROUP, topic = MessageCommon.HISTORY_KL_DETAIL_TOPIC)
public class HistoryKLDetailListener implements RocketMQListener<HistoryKLDetailMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HistoryKLDetailListener.class);
    private final ConcurrentHashMap<String, WebSocketSender> senderMap;

    public HistoryKLDetailListener(ConcurrentHashMap<String, WebSocketSender> senderMap) {
        this.senderMap = senderMap;
    }

    @Override
    public void onMessage(HistoryKLDetailMessage historyKLDetailMessage) {
        WebSocketSender sender = senderMap.get(AbstractWebSocketServerHandler.KLINE_HISTORY_DETAIL_TAG);
        if (sender != null) {
            HistoryKLDetailWsMessage wsMessage = new HistoryKLDetailWsMessage();
            wsMessage.setUsedQuota(historyKLDetailMessage.getUsedQuota());
            wsMessage.setRemainQuota(historyKLDetailMessage.getRemainQuota());
            wsMessage.setItemList(historyKLDetailMessage.getDetailList()
                    .stream().map(item -> {
                        HistoryKLDetailWsMessage.HistoryKLDetailItemWsMessage itemWsMessage = new HistoryKLDetailWsMessage.HistoryKLDetailItemWsMessage();
                        itemWsMessage.setCode(item.getCode());
                        itemWsMessage.setRequestTime(item.getRequestTime());
                        itemWsMessage.setMarket(item.getMarket());
                        itemWsMessage.setName(item.getName());
                        itemWsMessage.setRequestTimeStamp(item.getRequestTimeStamp());
                        return itemWsMessage;
                    }).collect(Collectors.toList()));
            LOGGER.info("WebSocket消息:{}", wsMessage.toString());
            sender.sendData(wsMessage);
        }
    }
}
