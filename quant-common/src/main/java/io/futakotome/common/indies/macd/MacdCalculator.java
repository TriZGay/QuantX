package io.futakotome.common.indies.macd;

import io.futakotome.common.indies.ema.Ema12;
import io.futakotome.common.indies.ema.Ema26;
import io.futakotome.common.indies.ema.Ema9;

public class MacdCalculator {
    private final Ema9 ema9 = new Ema9();
    private final Ema12 ema12 = new Ema12();
    private final Ema26 ema26 = new Ema26();

    public Macd compute(Double price) {
        Double ema12Value = ema12.compute(price);
        Double ema26Value = ema26.compute(price);
        Double dif = ema12Value - ema26Value;
        Double dea = ema9.compute(dif);
        Double macd = 2 * (dif - dea);
        return new Macd(dif, dea, macd);
    }
}
