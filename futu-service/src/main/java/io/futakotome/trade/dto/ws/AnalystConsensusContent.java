package io.futakotome.trade.dto.ws;

import com.google.gson.annotations.JsonAdapter;
import io.futakotome.trade.utils.converter.AnalystConsensusContentConverter;

@JsonAdapter(AnalystConsensusContentConverter.class)
public class AnalystConsensusContent {
    private Double highest;
    private Double average;
    private Double lowest;
    private Integer rating;
    private String ratingStr;
    private Integer total;
    private Long updateTime;
    private String updateTimeStr;
    private Double buy;
    private Double hold;
    private Double sell;
    private Double strongBuy;
    private Double underperform;

    public String getRatingStr() {
        return ratingStr;
    }

    public void setRatingStr(String ratingStr) {
        this.ratingStr = ratingStr;
    }

    public Double getHighest() {
        return highest;
    }

    public void setHighest(Double highest) {
        this.highest = highest;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public Double getLowest() {
        return lowest;
    }

    public void setLowest(Double lowest) {
        this.lowest = lowest;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public Double getBuy() {
        return buy;
    }

    public void setBuy(Double buy) {
        this.buy = buy;
    }

    public Double getHold() {
        return hold;
    }

    public void setHold(Double hold) {
        this.hold = hold;
    }

    public Double getSell() {
        return sell;
    }

    public void setSell(Double sell) {
        this.sell = sell;
    }

    public Double getStrongBuy() {
        return strongBuy;
    }

    public void setStrongBuy(Double strongBuy) {
        this.strongBuy = strongBuy;
    }

    public Double getUnderperform() {
        return underperform;
    }

    public void setUnderperform(Double underperform) {
        this.underperform = underperform;
    }
}
