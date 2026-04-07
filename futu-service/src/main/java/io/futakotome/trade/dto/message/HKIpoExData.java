package io.futakotome.trade.dto.message;

public class HKIpoExData {
    // 最低发售价
    private Double ipoPriceMin;
    // 最高发售价
    private Double ipoPriceMax;
    // 上市价
    private Double listPrice;
    // 每手股数
    private Integer lotSize;
    // 入场费
    private Double entrancePrice;
    // 是否为认购状态，True-认购中，False-待上市
    private Boolean isSubscribeStatus;
    // 截止认购日期字符串（格式：yyyy-MM-dd）
    private String applyEndTime;
    // 截止认购日期时间戳 因需处理认购手续，富途认购截止时间会早于交易所公布的日期。
    private Double applyEndTimestamp;

    public Double getIpoPriceMin() {
        return ipoPriceMin;
    }

    public void setIpoPriceMin(Double ipoPriceMin) {
        this.ipoPriceMin = ipoPriceMin;
    }

    public Double getIpoPriceMax() {
        return ipoPriceMax;
    }

    public void setIpoPriceMax(Double ipoPriceMax) {
        this.ipoPriceMax = ipoPriceMax;
    }

    public Double getListPrice() {
        return listPrice;
    }

    public void setListPrice(Double listPrice) {
        this.listPrice = listPrice;
    }

    public Integer getLotSize() {
        return lotSize;
    }

    public void setLotSize(Integer lotSize) {
        this.lotSize = lotSize;
    }

    public Double getEntrancePrice() {
        return entrancePrice;
    }

    public void setEntrancePrice(Double entrancePrice) {
        this.entrancePrice = entrancePrice;
    }

    public Boolean getSubscribeStatus() {
        return isSubscribeStatus;
    }

    public void setSubscribeStatus(Boolean subscribeStatus) {
        isSubscribeStatus = subscribeStatus;
    }

    public String getApplyEndTime() {
        return applyEndTime;
    }

    public void setApplyEndTime(String applyEndTime) {
        this.applyEndTime = applyEndTime;
    }

    public Double getApplyEndTimestamp() {
        return applyEndTimestamp;
    }

    public void setApplyEndTimestamp(Double applyEndTimestamp) {
        this.applyEndTimestamp = applyEndTimestamp;
    }
}
