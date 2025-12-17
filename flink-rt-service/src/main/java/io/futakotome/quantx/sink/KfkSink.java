package io.futakotome.quantx.sink;

import io.futakotome.common.message.RTMaMessage;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.formats.json.JsonSerializationSchema;

public class KfkSink {
    public static <T> KafkaSink<T> produce(ParameterTool configs, String topic) {
        return KafkaSink.<T>builder()
                .setBootstrapServers(configs.getRequired("kafka.bootstrapServers"))
                .setRecordSerializer(KafkaRecordSerializationSchema.builder()
                        .setTopic(topic)
                        .setValueSerializationSchema(new JsonSerializationSchema<T>())
                        .build())
//                .setProperty(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG, String.valueOf(3 * 60 * 1000))
//                .setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "1")
//                .setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true")
                .setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
                .build();
    }
}
