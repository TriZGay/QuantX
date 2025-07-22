package io.futakotome.akshares.service;

import io.futakotome.common.MessageCommon;
import io.futakotome.common.message.akshres.BigARtPriceMessage;
import io.futakotome.common.message.akshres.UsRtPriceMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaService.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private ListenableFutureCallback<SendResult<String, Object>> logging() {
        return new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, Object> result) {
                LOGGER.info("发送消息成功:{},{}", result.getRecordMetadata(), result.getProducerRecord());
            }

            @Override
            public void onFailure(Throwable ex) {
                LOGGER.error("发送消息失败:{}", ex.getMessage(), ex);
            }
        };
    }

    public void sendBigRtPrices(BigARtPriceMessage message) {
        ListenableFuture<SendResult<String, Object>> sendFuture = kafkaTemplate.send(MessageCommon.BIG_A_RT_TOPIC, message);
        sendFuture.addCallback(this.logging());
    }

    public void sendUsRtPrices(UsRtPriceMessage message) {
        ListenableFuture<SendResult<String, Object>> sendFuture = kafkaTemplate.send(MessageCommon.US_RT_TOPIC, message);
        sendFuture.addCallback(this.logging());
    }
}
