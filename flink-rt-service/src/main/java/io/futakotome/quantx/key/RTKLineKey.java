package io.futakotome.quantx.key;

import org.apache.flink.api.java.tuple.Tuple4;

/**
 * f0:market,
 * f1:code,
 * f2:rehab_type
 * f3:update_time
 */
public class RTKLineKey extends Tuple4<Integer, String, Integer, String> {
    public RTKLineKey() {
    }

    public RTKLineKey(Integer market, String code, Integer rehabType, String updateTime) {
        super(market, code, rehabType, updateTime);
    }

}
