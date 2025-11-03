package io.futakotome.common.indies.ema;

public class Ema60 extends EmaCalculator {
    private Double cache = null;

    @Override
    Double getCache() {
        return cache;
    }

    @Override
    void setCache(Double cache) {
        this.cache = cache;
    }

    @Override
    Integer getPeriod() {
        return 60;
    }
}
