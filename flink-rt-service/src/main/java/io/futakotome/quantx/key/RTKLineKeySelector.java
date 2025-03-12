package io.futakotome.quantx.key;

import io.futakotome.common.message.RTKLMessage;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;

public class RTKLineKeySelector implements KeySelector<RTKLMessage, Tuple3<Integer, String, Integer>> {
    @Override
    public Tuple3<Integer, String, Integer> getKey(RTKLMessage value) throws Exception {
        return Tuple3.of(value.getMarket(), value.getCode(), value.getRehabType());
    }
}
