package io.futakotome.rtck.listener;

import io.futakotome.common.MessageCommon;
import io.futakotome.common.message.RTKLMessage;
import io.futakotome.rtck.mapper.KLMapper;
import io.futakotome.rtck.mapper.dto.RTKLDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static io.futakotome.rtck.mapper.KLMapper.*;

@Component
public class RTKLineListener extends AbstractKLineListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(RTKLineListener.class);
    private final KLMapper mapper;

    public RTKLineListener(KLMapper mapper) {
        this.mapper = mapper;
    }

    @KafkaListener(groupId = MessageCommon.RT_KL_MIN_1_CONSUMER_GROUP, topics = {MessageCommon.RT_KL_MIN_1_TOPIC},
            errorHandler = "rtKLineErrorHandler")
    public void min1Listener(List<RTKLMessage> rtklMessages) {
        List<RTKLDto> toAddKLines = rtklMessages.stream()
                .map(this::message2Dto).collect(Collectors.toList());
        if (mapper.insertBatch(toAddKLines, KL_MIN_1_RAW_TABLE_NAME)) {
            LOGGER.info("1分K线入库成功");
        }
    }
    //todo K线其他类型
}
