package io.futakotome.trade.dto.message;

public class RehabMessageContent {
    private Integer market;
    private String code;

    private String time;
    private Long companyActFlag;
    private Double fwdFactorA;
    private Double fwdFactorB;
    private Double bwdFactorA;
    private Double bwdFactorB;
    private Integer splitBase;
    private Integer splitErt;
    private Integer joinBase;
    private Integer joinErt;
    private Integer bonusBase;
    private Integer bonusErt;
    private Integer transferBase;
    private Integer transferErt;
    private Integer allotBase;
    private Integer allotErt;
    private Double allotPrice;
    private Integer addBase;
    private Integer addErt;
    private Double addPrice;
    private Double dividend;
    private Double spDividend;
    private Double timestamp;

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getCompanyActFlag() {
        return companyActFlag;
    }

    public void setCompanyActFlag(Long companyActFlag) {
        this.companyActFlag = companyActFlag;
    }

    public Double getFwdFactorA() {
        return fwdFactorA;
    }

    public void setFwdFactorA(Double fwdFactorA) {
        this.fwdFactorA = fwdFactorA;
    }

    public Double getFwdFactorB() {
        return fwdFactorB;
    }

    public void setFwdFactorB(Double fwdFactorB) {
        this.fwdFactorB = fwdFactorB;
    }

    public Double getBwdFactorA() {
        return bwdFactorA;
    }

    public void setBwdFactorA(Double bwdFactorA) {
        this.bwdFactorA = bwdFactorA;
    }

    public Double getBwdFactorB() {
        return bwdFactorB;
    }

    public void setBwdFactorB(Double bwdFactorB) {
        this.bwdFactorB = bwdFactorB;
    }

    public Integer getSplitBase() {
        return splitBase;
    }

    public void setSplitBase(Integer splitBase) {
        this.splitBase = splitBase;
    }

    public Integer getSplitErt() {
        return splitErt;
    }

    public void setSplitErt(Integer splitErt) {
        this.splitErt = splitErt;
    }

    public Integer getJoinBase() {
        return joinBase;
    }

    public void setJoinBase(Integer joinBase) {
        this.joinBase = joinBase;
    }

    public Integer getJoinErt() {
        return joinErt;
    }

    public void setJoinErt(Integer joinErt) {
        this.joinErt = joinErt;
    }

    public Integer getBonusBase() {
        return bonusBase;
    }

    public void setBonusBase(Integer bonusBase) {
        this.bonusBase = bonusBase;
    }

    public Integer getBonusErt() {
        return bonusErt;
    }

    public void setBonusErt(Integer bonusErt) {
        this.bonusErt = bonusErt;
    }

    public Integer getTransferBase() {
        return transferBase;
    }

    public void setTransferBase(Integer transferBase) {
        this.transferBase = transferBase;
    }

    public Integer getTransferErt() {
        return transferErt;
    }

    public void setTransferErt(Integer transferErt) {
        this.transferErt = transferErt;
    }

    public Integer getAllotBase() {
        return allotBase;
    }

    public void setAllotBase(Integer allotBase) {
        this.allotBase = allotBase;
    }

    public Integer getAllotErt() {
        return allotErt;
    }

    public void setAllotErt(Integer allotErt) {
        this.allotErt = allotErt;
    }

    public Double getAllotPrice() {
        return allotPrice;
    }

    public void setAllotPrice(Double allotPrice) {
        this.allotPrice = allotPrice;
    }

    public Integer getAddBase() {
        return addBase;
    }

    public void setAddBase(Integer addBase) {
        this.addBase = addBase;
    }

    public Integer getAddErt() {
        return addErt;
    }

    public void setAddErt(Integer addErt) {
        this.addErt = addErt;
    }

    public Double getAddPrice() {
        return addPrice;
    }

    public void setAddPrice(Double addPrice) {
        this.addPrice = addPrice;
    }

    public Double getDividend() {
        return dividend;
    }

    public void setDividend(Double dividend) {
        this.dividend = dividend;
    }

    public Double getSpDividend() {
        return spDividend;
    }

    public void setSpDividend(Double spDividend) {
        this.spDividend = spDividend;
    }

    public Double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Double timestamp) {
        this.timestamp = timestamp;
    }
}
