package io.futakotome.quantx.key;

import org.apache.flink.api.java.tuple.Tuple4;

public class RTKLineKey extends Tuple4<Integer, String, Integer, String> {
    public RTKLineKey() {
    }

    public RTKLineKey(Integer market, String code, Integer rehabType, String updateTime) {
        super(market, code, rehabType, updateTime);
    }

}
