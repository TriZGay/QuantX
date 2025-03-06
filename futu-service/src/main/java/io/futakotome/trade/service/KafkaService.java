package io.futakotome.trade.service;

import io.futakotome.common.MessageCommon;
import io.futakotome.common.message.RTKLMessage;
import io.futakotome.trade.domain.code.KLType;
import io.futakotome.trade.dto.message.KLMessageContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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

    public void sendRTKLineMessage(KLMessageContent klMessageContent) {
        if (!klMessageContent.getBlank()) {
            //内容不为空才发
            RTKLMessage message = new RTKLMessage();
            message.setMarket(klMessageContent.getMarket());
            message.setCode(klMessageContent.getCode());
            message.setRehabType(klMessageContent.getRehabType());
            message.setHighPrice(klMessageContent.getHighPrice());
            message.setOpenPrice(klMessageContent.getOpenPrice());
            message.setLowPrice(klMessageContent.getLowPrice());
            message.setClosePrice(klMessageContent.getClosePrice());
            message.setLastClosePrice(klMessageContent.getLastClosePrice());
            message.setVolume(klMessageContent.getVolume());
            message.setTurnover(klMessageContent.getTurnover());
            message.setTurnoverRate(klMessageContent.getTurnoverRate());
            message.setPe(klMessageContent.getPe());
            message.setChangeRate(klMessageContent.getChangeRate());
            message.setUpdateTime(klMessageContent.getTime());
            message.setAddTime(LocalDateTime.now(ZoneId.of("Asia/Shanghai"))
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));

            if (klMessageContent.getKlType().equals(KLType.DAY.getCode())) {
                //日K
                ListenableFuture<SendResult<String, Object>> sendFuture = kafkaTemplate.send(MessageCommon.RT_KL_DAY_TOPIC, message);
                sendFuture.addCallback(this.logging());
            } else if (klMessageContent.getKlType().equals(KLType.WEEK.getCode())) {
                //周K
                ListenableFuture<SendResult<String, Object>> sendFuture = kafkaTemplate.send(MessageCommon.RT_KL_WEEK_TOPIC, message);
                sendFuture.addCallback(this.logging());
            } else if (klMessageContent.getKlType().equals(KLType.MONTH.getCode())) {
                //月K
                ListenableFuture<SendResult<String, Object>> sendFuture = kafkaTemplate.send(MessageCommon.RT_KL_MONTH_TOPIC, message);
                sendFuture.addCallback(this.logging());
            } else if (klMessageContent.getKlType().equals(KLType.QUARTER.getCode())) {
                //季K
                ListenableFuture<SendResult<String, Object>> sendFuture = kafkaTemplate.send(MessageCommon.RT_KL_QUARTER_TOPIC, message);
                sendFuture.addCallback(this.logging());
            } else if (klMessageContent.getKlType().equals(KLType.YEAR.getCode())) {
                //年K
                ListenableFuture<SendResult<String, Object>> sendFuture = kafkaTemplate.send(MessageCommon.RT_KL_YEAR_TOPIC, message);
                sendFuture.addCallback(this.logging());
            } else if (klMessageContent.getKlType().equals(KLType.MIN_1.getCode())) {
                //1
                ListenableFuture<SendResult<String, Object>> sendFuture = kafkaTemplate.send(MessageCommon.RT_KL_MIN_1_TOPIC, message);
                sendFuture.addCallback(this.logging());
            } else if (klMessageContent.getKlType().equals(KLType.MIN_3.getCode())) {
                //3
                ListenableFuture<SendResult<String, Object>> sendFuture = kafkaTemplate.send(MessageCommon.RT_KL_MIN_3_TOPIC, message);
                sendFuture.addCallback(this.logging());
            } else if (klMessageContent.getKlType().equals(KLType.MIN_5.getCode())) {
                //5分K
                ListenableFuture<SendResult<String, Object>> sendFuture = kafkaTemplate.send(MessageCommon.RT_KL_MIN_5_TOPIC, message);
                sendFuture.addCallback(this.logging());
            } else if (klMessageContent.getKlType().equals(KLType.MIN_15.getCode())) {
                //15
                ListenableFuture<SendResult<String, Object>> sendFuture = kafkaTemplate.send(MessageCommon.RT_KL_MIN_15_TOPIC, message);
                sendFuture.addCallback(this.logging());
            } else if (klMessageContent.getKlType().equals(KLType.MIN_30.getCode())) {
                //30
                ListenableFuture<SendResult<String, Object>> sendFuture = kafkaTemplate.send(MessageCommon.RT_KL_MIN_30_TOPIC, message);
                sendFuture.addCallback(this.logging());
            } else if (klMessageContent.getKlType().equals(KLType.MIN_60.getCode())) {
                //60
                ListenableFuture<SendResult<String, Object>> sendFuture = kafkaTemplate.send(MessageCommon.RT_KL_MIN_60_TOPIC, message);
                sendFuture.addCallback(this.logging());
            }
        }
    }
}
