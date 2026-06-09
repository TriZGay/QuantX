package io.futakotome.trade.dto.message;

public class MainIncomeItemContent {
    private String name;
    private Double mainOperIncome;
    private Double ratio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMainOperIncome() {
        return mainOperIncome;
    }

    public void setMainOperIncome(Double mainOperIncome) {
        this.mainOperIncome = mainOperIncome;
    }

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }
}
