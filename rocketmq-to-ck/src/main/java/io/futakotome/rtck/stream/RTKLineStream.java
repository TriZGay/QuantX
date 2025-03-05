package io.futakotome.rtck.stream;

import io.futakotome.common.MessageCommon;
import io.futakotome.common.message.RTKLMessage;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

@Component
public class RTKLineStream {
    private static final Logger LOGGER = LoggerFactory.getLogger(RTKLineStream.class);

    @Bean
    public KStream<String, RTKLMessage> rtKLineMin1Stream(StreamsBuilder streamsBuilder) {
        KStream<String, RTKLMessage> stream = streamsBuilder.stream(MessageCommon.RT_KL_MIN_1_TOPIC);
        stream.print(Printed.toSysOut());

        return stream;
    }
}
