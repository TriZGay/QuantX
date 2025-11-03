package io.futakotome.common.indies.ema;

import java.util.Objects;

public abstract class EmaCalculator {

    public Double compute(Double price) {
        Double previousEma;
        Double cache = getCache();
        if (Objects.isNull(cache)) {
            previousEma = price;
        } else {
            previousEma = cache;
        }
        Double emaRes = calculate(price, previousEma);
        setCache(emaRes);
        return emaRes;
    }

    Double calculate(Double price, Double previousEma) {
        double multiplier = 2.0 / (getPeriod() + 1);
        return ((price - previousEma) * multiplier) + previousEma;
    }

    abstract Double getCache();

    abstract void setCache(Double cache);

    abstract Integer getPeriod();
}
