package io.futakotome.quantx.source;

import io.futakotome.common.message.RTKLMessage;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.formats.json.JsonDeserializationSchema;

public class RTKLine {

    public static KafkaSource<RTKLMessage> fromKafka(ParameterTool configs, String groupId, String... topics) {
        return KafkaSource.<RTKLMessage>builder()
                .setBootstrapServers(configs.getRequired("kafka.bootstrapServers"))
                .setTopics(topics)
                .setGroupId(groupId)
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(new JsonDeserializationSchema<>(RTKLMessage.class))
                .build();
    }

}
