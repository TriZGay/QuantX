package io.futakotome.quantx.key;

import org.apache.flink.api.java.tuple.Tuple3;

public class RTKLineKey extends Tuple3<String, Integer, String> {
    public RTKLineKey() {
    }

    public RTKLineKey(String code, Integer rehabType, String updateTime) {
        super(code, rehabType, updateTime);
    }

}
