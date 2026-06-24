package io.futakotome.trade.dto.message;

import com.google.gson.annotations.JsonAdapter;
import io.futakotome.trade.utils.converter.BrokerItemConverter;

@JsonAdapter(BrokerItemConverter.class)
public class BrokerItem {
    private Long netVol;
    private String brokerName;
    private Integer buySellType;
    private String buySellTypeStr;
    private Double avgPrice;
    private Double totalVol;
    private Double totalTurnover;

    public String getBuySellTypeStr() {
        return buySellTypeStr;
    }

    public void setBuySellTypeStr(String buySellTypeStr) {
        this.buySellTypeStr = buySellTypeStr;
    }

    public Long getNetVol() {
        return netVol;
    }

    public void setNetVol(Long netVol) {
        this.netVol = netVol;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public Integer getBuySellType() {
        return buySellType;
    }

    public void setBuySellType(Integer buySellType) {
        this.buySellType = buySellType;
    }

    public Double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(Double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public Double getTotalVol() {
        return totalVol;
    }

    public void setTotalVol(Double totalVol) {
        this.totalVol = totalVol;
    }

    public Double getTotalTurnover() {
        return totalTurnover;
    }

    public void setTotalTurnover(Double totalTurnover) {
        this.totalTurnover = totalTurnover;
    }
}
