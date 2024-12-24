package io.futakotome.rtck.listener;

import io.futakotome.common.MessageCommon;
import io.futakotome.common.message.NotifyMessage;
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
//@RocketMQMessageListener(consumerGroup = MessageCommon.NOTIFY_CONSUMER_GROUP, topic = MessageCommon.NOTIFY_TOPIC)
public class NotifyListener implements RocketMQListener<NotifyMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyListener.class);
    private final ConcurrentHashMap<String, WebSocketSender> senderMap;

    public NotifyListener(ConcurrentHashMap<String, WebSocketSender> senderMap) {
        this.senderMap = senderMap;
    }

    @Override
    public void onMessage(NotifyMessage notifyMessage) {
        String notifyContent = notifyMessage.getContent();
        if (notifyContent != null && !notifyContent.isEmpty()) {

            WebSocketSender sender = senderMap.get(AbstractWebSocketServerHandler.NOTIFY_TAG);
            if (sender != null) {
                LOGGER.info("WebSocket消息:{}", notifyContent);
                sender.sendData(new NotifyWsMessage(notifyContent));
            }
        }
    }
}
