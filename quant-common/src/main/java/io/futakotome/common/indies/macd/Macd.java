package io.futakotome.common.indies.macd;

public class Macd {
    private Double dif;
    private Double dea;
    private Double macd;

    public Macd() {
    }

    public Macd(Double dif, Double dea, Double macd) {
        this.dif = dif;
        this.dea = dea;
        this.macd = macd;
    }

    public Double getDif() {
        return dif;
    }

    public void setDif(Double dif) {
        this.dif = dif;
    }

    public Double getDea() {
        return dea;
    }

    public void setDea(Double dea) {
        this.dea = dea;
    }

    public Double getMacd() {
        return macd;
    }

    public void setMacd(Double macd) {
        this.macd = macd;
    }
}
