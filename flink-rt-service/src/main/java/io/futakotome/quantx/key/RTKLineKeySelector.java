package io.futakotome.quantx.key;

import io.futakotome.common.message.RTKLMessage;
import org.apache.flink.api.java.functions.KeySelector;

public class RTKLineKeySelector implements KeySelector<RTKLMessage, RTKLineKey> {

    @Override
    public RTKLineKey getKey(RTKLMessage value) throws Exception {
        return new RTKLineKey(value.getCode(), value.getRehabType(), value.getUpdateTime());
    }
}
