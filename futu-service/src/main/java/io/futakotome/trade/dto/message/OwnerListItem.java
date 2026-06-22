package io.futakotome.trade.dto.message;

public class OwnerListItem {
    private String periodText;  // 报告期，如 "2025/Q3"
    private String name;  // 股东名称
    private Integer holderId;  // 股东 ID，用于请求历史变动明细
    private Long shareChangeNum;  // 持股变动数（单位：股）
    private Long sharesChangePrice;  // 参考变动金额
    private Double holderPct;  // 持股比例，百分号前的值，如 12.34 表示 12.34%
    private String holderType;  // 持股性质（文本，如"传统投资经理"）
    private Integer holderTypeId;  // 持股性质 ID，用于请求历史变动明细
    private Long holdingDate;  // 报告日期时间戳（秒）
    private String holdingDateStr; // 报告日期字符串，格式 YYYY-MM-DD，香港时区
    private Double holderPctChange; // 持股变动比例，百分号前的值，如 12.34 表示变动 12.34%
    private Long holderQuantity; // 持股数（单位：股）

    public String getPeriodText() {
        return periodText;
    }

    public void setPeriodText(String periodText) {
        this.periodText = periodText;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHolderId() {
        return holderId;
    }

    public void setHolderId(Integer holderId) {
        this.holderId = holderId;
    }

    public Long getShareChangeNum() {
        return shareChangeNum;
    }

    public void setShareChangeNum(Long shareChangeNum) {
        this.shareChangeNum = shareChangeNum;
    }

    public Long getSharesChangePrice() {
        return sharesChangePrice;
    }

    public void setSharesChangePrice(Long sharesChangePrice) {
        this.sharesChangePrice = sharesChangePrice;
    }

    public Double getHolderPct() {
        return holderPct;
    }

    public void setHolderPct(Double holderPct) {
        this.holderPct = holderPct;
    }

    public String getHolderType() {
        return holderType;
    }

    public void setHolderType(String holderType) {
        this.holderType = holderType;
    }

    public Integer getHolderTypeId() {
        return holderTypeId;
    }

    public void setHolderTypeId(Integer holderTypeId) {
        this.holderTypeId = holderTypeId;
    }

    public Long getHoldingDate() {
        return holdingDate;
    }

    public void setHoldingDate(Long holdingDate) {
        this.holdingDate = holdingDate;
    }

    public String getHoldingDateStr() {
        return holdingDateStr;
    }

    public void setHoldingDateStr(String holdingDateStr) {
        this.holdingDateStr = holdingDateStr;
    }

    public Double getHolderPctChange() {
        return holderPctChange;
    }

    public void setHolderPctChange(Double holderPctChange) {
        this.holderPctChange = holderPctChange;
    }

    public Long getHolderQuantity() {
        return holderQuantity;
    }

    public void setHolderQuantity(Long holderQuantity) {
        this.holderQuantity = holderQuantity;
    }
}
