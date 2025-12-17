package io.futakotome.quantx.sink;

import io.futakotome.common.message.RTMaMessage;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.formats.json.JsonSerializationSchema;

public class RTMaSink {
    public static KafkaSink<RTMaMessage> toKafka(ParameterTool configs, String topic) {
        return KfkSink.produce(configs, topic);
    }
}
