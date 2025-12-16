package io.futakotome.quantx.pojo;

import io.futakotome.quantx.key.RTKLineKey;
import org.apache.flink.api.java.tuple.Tuple2;

public class MaResult extends Tuple2<RTKLineKey, Double> {
    public MaResult() {
    }

    public MaResult(RTKLineKey f0, Double f1) {
        super(f0, f1);
    }
}
