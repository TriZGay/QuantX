package io.futakotome.quantx.pojo;

import io.futakotome.quantx.key.RTKLineKey;
import org.apache.flink.api.java.tuple.Tuple4;

public class Macd extends Tuple4<RTKLineKey, Double, Double, Double> {
    public Macd() {
    }

    public Macd(RTKLineKey f0, Double f1, Double f2, Double f3) {
        super(f0, f1, f2, f3);
    }
}
