package io.futakotome.quantx;

import io.futakotome.common.MessageCommon;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.connector.rocketmq.legacy.RocketMQConfig;
import org.apache.flink.connector.rocketmq.legacy.RocketMQSourceFunction;
import org.apache.flink.connector.rocketmq.legacy.common.config.OffsetResetStrategy;
import org.apache.flink.connector.rocketmq.legacy.common.serialization.SimpleKeyValueDeserializationSchema;
import org.apache.flink.connector.rocketmq.source.RocketMQSource;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class QuantXMainJob {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        env.enableCheckpointing(3000);
        ParameterTool configs = ParameterTool.fromPropertiesFile(QuantXMainJob.class.getResourceAsStream("/base_config.properties"));
        Properties consumerProps = new Properties();
        consumerProps.setProperty(RocketMQConfig.NAME_SERVER_ADDR, configs.getRequired("rocketmq.nameserver"));
        consumerProps.setProperty(RocketMQConfig.CONSUMER_GROUP, MessageCommon.RT_KL_MIN_1_CONSUMER_GROUP_STREAM);
        consumerProps.setProperty(RocketMQConfig.CONSUMER_TOPIC, MessageCommon.RT_KL_MIN_1_TOPIC);

        RocketMQSourceFunction<Map<String, String>> source = new RocketMQSourceFunction<>(
                new SimpleKeyValueDeserializationSchema("id", "address"), consumerProps
        );
        source.setStartFromGroupOffsets(OffsetResetStrategy.LATEST);
        env.addSource(source)
                .name("rocketmq-source")
                .setParallelism(2)
                .process(new ProcessFunction<Map<String, String>, Map<String, String>>() {
                    @Override
                    public void processElement(Map<String, String> in, ProcessFunction<Map<String, String>, Map<String, String>>.Context context, Collector<Map<String, String>> out) throws Exception {
                        System.out.println(in);
                        HashMap result = new HashMap();
                        result.put("id", in.get("id"));
                        String[] arr = in.get("address").toString().split("\\s+");
                        result.put("province", arr[arr.length - 1]);
                        out.collect(result);
                    }
                });
        try {
            env.execute("rocket-flink-example");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        env.setParallelism(1);
//        DataStream<String> text = env.fromElements("hello", "apache", "flink");
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
