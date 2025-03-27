package io.futakotome.quantx.pojo;

import io.futakotome.quantx.key.RTKLineKey;
import org.apache.flink.api.java.tuple.Tuple3;

public class TradeSignal extends Tuple3<RTKLineKey, Integer, Double> {
    public TradeSignal() {
    }

    public TradeSignal(RTKLineKey f0, Integer f1, Double f2) {
        super(f0, f1, f2);
    }
}
