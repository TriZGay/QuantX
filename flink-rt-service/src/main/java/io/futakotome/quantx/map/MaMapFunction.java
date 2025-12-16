package io.futakotome.quantx.map;

import io.futakotome.common.message.RTMaMessage;
import io.futakotome.quantx.pojo.MaResult;
import org.apache.flink.api.common.functions.MapFunction;

public class MaMapFunction implements MapFunction<MaResult, RTMaMessage> {
    @Override
    public RTMaMessage map(MaResult value) throws Exception {
        RTMaMessage rtMaMessage = new RTMaMessage();
        rtMaMessage.setCode(value.f0.f1);
        rtMaMessage.setRehabType(value.f0.f2);
        rtMaMessage.setUpdateTime(value.f0.f3);
        rtMaMessage.setValue(value.f1);
        return rtMaMessage;
    }
}
