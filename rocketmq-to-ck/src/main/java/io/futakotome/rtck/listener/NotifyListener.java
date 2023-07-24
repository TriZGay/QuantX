package io.futakotome.rtck.listener;

import io.futakotome.common.MessageCommon;
import io.futakotome.common.message.NotifyMessage;
import io.futakotome.rtck.message.MessageService;
import io.futakotome.rtck.message.NotifyWsMessage;
import io.futakotome.rtck.message.ReactiveWebSocketHandlerMapping;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = MessageCommon.NOTIFY_CONSUMER_GROUP, topic = MessageCommon.NOTIFY_TOPIC)
public class NotifyListener implements RocketMQListener<NotifyMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyListener.class);
    private final MessageService messageService;

    public NotifyListener(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void onMessage(NotifyMessage notifyMessage) {
        String notifyContent = notifyMessage.getContent();
        if (notifyContent != null && !notifyContent.isEmpty()) {
            LOGGER.info("WebSocket消息:{}", notifyContent);
            messageService.onNext(new NotifyWsMessage(notifyContent), ReactiveWebSocketHandlerMapping.NOTIFY_PATH);
        }
    }
}
