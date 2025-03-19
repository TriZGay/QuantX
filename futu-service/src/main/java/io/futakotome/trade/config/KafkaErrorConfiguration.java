package io.futakotome.trade.config;

import org.apache.kafka.clients.consumer.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;

@Configuration
public class KafkaErrorConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaErrorConfiguration.class);

    private void loggingCommon(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {
        LOGGER.error("消费出现异常={},exception={},topics={}", message, exception.getMessage(), consumer.listTopics(), exception);
    }

    @Bean
    public ConsumerAwareListenerErrorHandler commonErrorHandler() {
        return (message, exception, consumer) -> {
            loggingCommon(message, exception, consumer);
            return null;
        };
    }
}
