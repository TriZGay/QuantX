package io.futakotome.rtck.listener;

import org.apache.kafka.clients.consumer.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerErrorHandler implements KafkaListenerErrorHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerErrorHandler.class);

    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception) {
        LOGGER.error("消费错误={}", exception.getMessage(), exception);
        LOGGER.error("消息详情={}", message);
        return new Object();
    }

    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {
        LOGGER.error("消费者详情={}", consumer.groupMetadata());
        LOGGER.error("主题={}", consumer.listTopics());
        return KafkaListenerErrorHandler.super.handleError(message, exception, consumer);
    }
}
