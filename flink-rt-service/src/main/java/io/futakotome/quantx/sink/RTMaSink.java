package io.futakotome.quantx.sink;

import io.futakotome.common.message.RTEmaMessage;
import io.futakotome.common.message.RTMaMessage;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.formats.json.JsonSerializationSchema;

public class RTMaSink {
    public static KafkaSink<RTMaMessage> toKafka(ParameterTool configs, String topic) {
        return KafkaSink.<RTMaMessage>builder()
                .setBootstrapServers(configs.getRequired("kafka.bootstrapServers"))
                .setRecordSerializer(KafkaRecordSerializationSchema.builder()
                        .setTopic(topic)
                        .setValueSerializationSchema(new JsonSerializationSchema<RTMaMessage>())
                        .build())
                .setProperty("transaction.timeout.ms", String.valueOf(15 * 60 * 1000))
                .setDeliveryGuarantee(DeliveryGuarantee.EXACTLY_ONCE)
                .build();
    }
}
