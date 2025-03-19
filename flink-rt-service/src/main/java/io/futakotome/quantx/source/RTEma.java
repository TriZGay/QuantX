package io.futakotome.quantx.source;

import io.futakotome.common.message.RTEmaMessage;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.formats.json.JsonSerializationSchema;

public class RTEma {
    public static KafkaSink<RTEmaMessage> toKafka(ParameterTool configs, String topic) {
        return KafkaSink.<RTEmaMessage>builder()
                .setBootstrapServers(configs.getRequired("kafka.bootstrapServers"))
                .setRecordSerializer(KafkaRecordSerializationSchema.builder()
                        .setTopic(topic)
                        .setValueSerializationSchema(new JsonSerializationSchema<RTEmaMessage>())
                        .build())
                .setProperty("transaction.timeout.ms", String.valueOf(15 * 60 * 1000))
                .setDeliveryGuarantee(DeliveryGuarantee.EXACTLY_ONCE)
                .build();
    }
}
