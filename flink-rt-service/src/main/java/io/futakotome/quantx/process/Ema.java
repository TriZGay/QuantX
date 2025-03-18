package io.futakotome.quantx.process;

public final class Ema {

    public static Double calculate(Double today, Double previousEma, Integer period) {
        double multiplier = 2.0 / (period + 1);
        if (previousEma == null) {
            return today;
        } else {
            return ((today - previousEma) * multiplier) + previousEma;
        }
    }
}
