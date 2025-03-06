package io.futakotome.quantx;

import io.futakotome.common.MessageCommon;
import io.futakotome.common.message.RTKLMessage;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.formats.json.JsonDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class QuantXMainJob {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        ParameterTool configs = ParameterTool.fromPropertiesFile(QuantXMainJob.class.getResourceAsStream("/base_config.properties"));

        KafkaSource<RTKLMessage> source = KafkaSource.<RTKLMessage>builder()
                .setBootstrapServers(configs.getRequired("kafka.bootstrapServers"))
                .setTopics(MessageCommon.RT_KL_MIN_1_TOPIC)
                .setGroupId(MessageCommon.RT_KL_MIN_1_CONSUMER_GROUP_STREAM)
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(new JsonDeserializationSchema<>(RTKLMessage.class))
                .build();
        DataStream<RTKLMessage> rtklMin1Source = env.fromSource(source, WatermarkStrategy.forMonotonousTimestamps(), "rtk_min1_source")
                .setParallelism(1);
        KeyedStream<RTKLMessage, Tuple3<Integer, String, Integer>> keyedStream = rtklMin1Source.keyBy(new KeySelector<RTKLMessage, Tuple3<Integer, String, Integer>>() {
            @Override
            public Tuple3<Integer, String, Integer> getKey(RTKLMessage value) throws Exception {
                return Tuple3.of(value.getMarket(), value.getCode(), value.getRehabType());
            }
        });
        keyedStream.print();
        env.execute(QuantXMainJob.class.getName());
//        env.setParallelism(1);
//        DataStream<String> text = env.fromElements("hello", "apache", "flink")
//        DataStream<String> upperCaseText = text.map(String::toUpperCase);
//        upperCaseText.print();
//        env.execute("hello flink");
//        ParameterTool configs = ParameterTool.fromPropertiesFile(QuantXMainJob.class.getResourceAsStream("/base_config.properties"));
//        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
//        ParameterTool commandLieParameter = ParameterTool.fromArgs(args);
//        String type = commandLieParameter.get("type", Common.JOB_TYPE_DAYK_MA30);
//        switch (type) {
//            case Common.JOB_TYPE_DAYK_MA5: {
//                new BatchDayKMA5Job(env, configs, args).run();
//                return;
//            }
//            case Common.JOB_TYPE_DAYK_MA10: {
//                new BatchDayKMA10Job(env, configs, args).run();
//                return;
//            }
//            case Common.JOB_TYPE_DAYK_MA20: {
//                new BatchDayKMA20Job(env, configs, args).run();
//                return;
//            }
//            case Common.JOB_TYPE_DAYK_MA30: {
//                new BatchDayKMA30Job(env,configs,args).run();
//                return;
//            }
//        }
    }
}
