package io.futakotome.quantx.map;

import io.futakotome.common.message.RTEmaMessage;
import io.futakotome.quantx.pojo.EmaResult;
import org.apache.flink.api.common.functions.MapFunction;

public class EmaMapFunction implements MapFunction<EmaResult, RTEmaMessage> {
    @Override
    public RTEmaMessage map(EmaResult value) throws Exception {
        RTEmaMessage rtEmaMessage = new RTEmaMessage();
        rtEmaMessage.setCode(value.f0.f0);
        rtEmaMessage.setRehabType(value.f0.f1);
        rtEmaMessage.setUpdateTime(value.f0.f2);
        rtEmaMessage.setValue(value.f1);
        return rtEmaMessage;
    }
}
