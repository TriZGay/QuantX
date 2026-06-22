package io.futakotome.trade.dto.message;

public class ABuyBackItem {
    private Long changeRegDate;  // 工商变更登记日时间戳（秒）
    private String changeRegDateStr;  // 工商变更登记日字符串，格式 YYYY-MM-DD，对应市场时区
    private Long changeDate;  // 股份变动日时间戳（秒）
    private String changeDateStr;  // 股份变动日字符串，格式 YYYY-MM-DD，对应市场时区
    private String eventProceDesc;  // 事件进程描述
    private Long advanceDate;  // 预案公告日时间戳（秒）
    private String advanceDateStr;  // 预案公告日字符串，格式 YYYY-MM-DD，对应市场时区
    private Long meetPassDate;  // 股东大会通过日时间戳（秒）
    private String meetPassDateStr;  // 股东大会通过日字符串，格式 YYYY-MM-DD，对应市场时区
    private Long startDate; // 回购开始日时间戳（秒）
    private String startDateStr; // 回购开始日字符串，格式 YYYY-MM-DD，对应市场时区
    private Long endDate; // 回购截止日时间戳（秒）
    private String endDateStr; // 回购截止日字符串，格式 YYYY-MM-DD，对应市场时区
    private Long payDate; // 支付日时间戳（秒）
    private String payDateStr; // 支付日字符串，格式 YYYY-MM-DD，对应市场时区
    private String seller; // 出售方（股份被回购方）
    private String buyBackMode; // 回购方式
    private String shareType; // 股份类别
    private Long buyBackSum; // 回购股数（股）
    private Double buyBackMoney; // 回购金额
    private Double percentage; // 占已发行股份百分比，百分号前的值，如 12.34 表示 12.34%
    private Double valueFloor; // 拟回购资金总额下限
    private Double valueCeiling; // 拟回购资金总额上限
    private Double priceFloor; // 回购价格下限
    private Double priceCeiling; // 回购价格上限
    private Double volumeFloor; // 拟回购股数下限
    private Double volumeCeiling; // 拟回购股数上限

    public Long getChangeRegDate() {
        return changeRegDate;
    }

    public void setChangeRegDate(Long changeRegDate) {
        this.changeRegDate = changeRegDate;
    }

    public String getChangeRegDateStr() {
        return changeRegDateStr;
    }

    public void setChangeRegDateStr(String changeRegDateStr) {
        this.changeRegDateStr = changeRegDateStr;
    }

    public Long getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Long changeDate) {
        this.changeDate = changeDate;
    }

    public String getChangeDateStr() {
        return changeDateStr;
    }

    public void setChangeDateStr(String changeDateStr) {
        this.changeDateStr = changeDateStr;
    }

    public String getEventProceDesc() {
        return eventProceDesc;
    }

    public void setEventProceDesc(String eventProceDesc) {
        this.eventProceDesc = eventProceDesc;
    }

    public Long getAdvanceDate() {
        return advanceDate;
    }

    public void setAdvanceDate(Long advanceDate) {
        this.advanceDate = advanceDate;
    }

    public String getAdvanceDateStr() {
        return advanceDateStr;
    }

    public void setAdvanceDateStr(String advanceDateStr) {
        this.advanceDateStr = advanceDateStr;
    }

    public Long getMeetPassDate() {
        return meetPassDate;
    }

    public void setMeetPassDate(Long meetPassDate) {
        this.meetPassDate = meetPassDate;
    }

    public String getMeetPassDateStr() {
        return meetPassDateStr;
    }

    public void setMeetPassDateStr(String meetPassDateStr) {
        this.meetPassDateStr = meetPassDateStr;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public Long getPayDate() {
        return payDate;
    }

    public void setPayDate(Long payDate) {
        this.payDate = payDate;
    }

    public String getPayDateStr() {
        return payDateStr;
    }

    public void setPayDateStr(String payDateStr) {
        this.payDateStr = payDateStr;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getBuyBackMode() {
        return buyBackMode;
    }

    public void setBuyBackMode(String buyBackMode) {
        this.buyBackMode = buyBackMode;
    }

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public Long getBuyBackSum() {
        return buyBackSum;
    }

    public void setBuyBackSum(Long buyBackSum) {
        this.buyBackSum = buyBackSum;
    }

    public Double getBuyBackMoney() {
        return buyBackMoney;
    }

    public void setBuyBackMoney(Double buyBackMoney) {
        this.buyBackMoney = buyBackMoney;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Double getValueFloor() {
        return valueFloor;
    }

    public void setValueFloor(Double valueFloor) {
        this.valueFloor = valueFloor;
    }

    public Double getValueCeiling() {
        return valueCeiling;
    }

    public void setValueCeiling(Double valueCeiling) {
        this.valueCeiling = valueCeiling;
    }

    public Double getPriceFloor() {
        return priceFloor;
    }

    public void setPriceFloor(Double priceFloor) {
        this.priceFloor = priceFloor;
    }

    public Double getPriceCeiling() {
        return priceCeiling;
    }

    public void setPriceCeiling(Double priceCeiling) {
        this.priceCeiling = priceCeiling;
    }

    public Double getVolumeFloor() {
        return volumeFloor;
    }

    public void setVolumeFloor(Double volumeFloor) {
        this.volumeFloor = volumeFloor;
    }

    public Double getVolumeCeiling() {
        return volumeCeiling;
    }

    public void setVolumeCeiling(Double volumeCeiling) {
        this.volumeCeiling = volumeCeiling;
    }
}
