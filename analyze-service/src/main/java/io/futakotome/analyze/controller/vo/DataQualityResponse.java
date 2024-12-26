package io.futakotome.analyze.controller.vo;

public class DataQualityResponse {
    private String checkDate;
    private boolean klineHasRepeat;

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public boolean isKlineHasRepeat() {
        return klineHasRepeat;
    }

    public void setKlineHasRepeat(boolean klineHasRepeat) {
        this.klineHasRepeat = klineHasRepeat;
    }
}
