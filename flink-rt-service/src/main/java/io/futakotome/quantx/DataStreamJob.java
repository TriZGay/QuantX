package io.futakotome.quantx;

import io.futakotome.quantx.dto.KLineDto;
import io.futakotome.quantx.source.KLineSource;
import org.apache.flink.api.java.utils.MultipleParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class DataStreamJob {

    public static void main(String[] args) throws Exception {
        final MultipleParameterTool params = MultipleParameterTool.fromArgs(args);
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<KLineDto> dataInput = env.addSource(new KLineSource());
        dataInput.print();
        env.execute("KLine data read");
    }
}
