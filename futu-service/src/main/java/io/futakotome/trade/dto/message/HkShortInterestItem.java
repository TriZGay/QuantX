package io.futakotome.trade.dto.message;

public class HkShortInterestItem {
    private Long timestamp;
    private String timestampStr;
    private Double closePrice;
    private Double lastClosePrice;
    private Long aggregatedShort;
    private Double aggregatedShortRatio;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestampStr() {
        return timestampStr;
    }

    public void setTimestampStr(String timestampStr) {
        this.timestampStr = timestampStr;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }

    public Double getLastClosePrice() {
        return lastClosePrice;
    }

    public void setLastClosePrice(Double lastClosePrice) {
        this.lastClosePrice = lastClosePrice;
    }

    public Long getAggregatedShort() {
        return aggregatedShort;
    }

    public void setAggregatedShort(Long aggregatedShort) {
        this.aggregatedShort = aggregatedShort;
    }

    public Double getAggregatedShortRatio() {
        return aggregatedShortRatio;
    }

    public void setAggregatedShortRatio(Double aggregatedShortRatio) {
        this.aggregatedShortRatio = aggregatedShortRatio;
    }
}
