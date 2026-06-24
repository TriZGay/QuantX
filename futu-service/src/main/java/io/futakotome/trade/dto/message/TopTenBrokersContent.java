package io.futakotome.trade.dto.message;

import java.util.List;

public class TopTenBrokersContent {
    private Boolean isRealTime; // true=实时数据，false=历史数据
    private Long dataTime; // 数据更新时间戳（秒）
    private String dataTimeStr; // 数据更新时间字符串，格式 YYYY-MM-DD HH:MM:SS
    private List<BrokerItem> brokerList; // 经纪商列表，按净买/卖量从高到低排列

    public Boolean getRealTime() {
        return isRealTime;
    }

    public void setRealTime(Boolean realTime) {
        isRealTime = realTime;
    }

    public Long getDataTime() {
        return dataTime;
    }

    public void setDataTime(Long dataTime) {
        this.dataTime = dataTime;
    }

    public String getDataTimeStr() {
        return dataTimeStr;
    }

    public void setDataTimeStr(String dataTimeStr) {
        this.dataTimeStr = dataTimeStr;
    }

    public List<BrokerItem> getBrokerList() {
        return brokerList;
    }

    public void setBrokerList(List<BrokerItem> brokerList) {
        this.brokerList = brokerList;
    }
}
