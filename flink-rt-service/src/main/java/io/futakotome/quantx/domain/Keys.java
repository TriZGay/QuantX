package io.futakotome.quantx.domain;

import org.apache.flink.api.java.tuple.Tuple2;

public class Keys {
    public static class CodeRehabTypeKey extends Tuple2<String, Integer> {
        public CodeRehabTypeKey() {
        }

        public CodeRehabTypeKey(String f0, Integer f1) {
            super(f0, f1);
        }
    }
}
