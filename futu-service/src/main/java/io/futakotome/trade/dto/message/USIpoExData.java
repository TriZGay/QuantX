package io.futakotome.trade.dto.message;

public class USIpoExData {
    // 最低发行价
    private Double ipoPriceMin;
    // 最高发行价
    private Double ipoPriceMax;
    // 发行量
    private Double issueSize;

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

    public Double getIssueSize() {
        return issueSize;
    }

    public void setIssueSize(Double issueSize) {
        this.issueSize = issueSize;
    }
}
