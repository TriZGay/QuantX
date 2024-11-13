package io.futakotome.rtck.mapper.dto;

public class RehabDto {
    private Integer market;
    private String code;

    private Long companyActFlag;//公司行动
    private Double fwdFactorA;//前复权因子 A
    private Double fwdFactorB;//B
    private Double bwdFactorA;//后复权因子 A
    private Double bwdFactorB;//B
    private Integer splitBase;//拆股(例如，1拆5，Base 为1，Ert 为5)
    private Integer splitErt;
    private Integer joinBase;//合股(例如，50合1，Base 为50，Ert 为1)
    private Integer joinErt;
    private Integer bonusBase;//送股(例如，10送3, Base 为10,Ert 为3)
    private Integer bonusErt;
    private Integer transferBase;//转赠股(例如，10转3, Base 为10,Ert 为3)
    private Integer transferErt;
    private Integer allotBase;//配股(例如，10送2, 配股价为6.3元, Base 为10, Ert 为2, Price 为6.3)
    private Integer allotErt;
    private Double allotPrice;
    private Integer addBase;//增发股(例如，10送2, 增发股价为6.3元, Base 为10, Ert 为2, Price 为6.3)
    private Integer addErt;
    private Double addPrice;
    private Double dividend;//现金分红(例如，每10股派现0.5元,则该字段值为0.05)
    private Double spDividend;//特别股息(例如，每10股派特别股息0.5元,则该字段值为0.05)
    private String time;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "RehabDto{" +
                "market=" + market +
                ", code='" + code + '\'' +
                ", companyActFlag=" + companyActFlag +
                ", fwdFactorA=" + fwdFactorA +
                ", fwdFactorB=" + fwdFactorB +
                ", bwdFactorA=" + bwdFactorA +
                ", bwdFactorB=" + bwdFactorB +
                ", splitBase=" + splitBase +
                ", splitErt=" + splitErt +
                ", joinBase=" + joinBase +
                ", joinErt=" + joinErt +
                ", bonusBase=" + bonusBase +
                ", bonusErt=" + bonusErt +
                ", transferBase=" + transferBase +
                ", transferErt=" + transferErt +
                ", allotBase=" + allotBase +
                ", allotErt=" + allotErt +
                ", allotPrice=" + allotPrice +
                ", addBase=" + addBase +
                ", addErt=" + addErt +
                ", addPrice=" + addPrice +
                ", dividend=" + dividend +
                ", spDividend=" + spDividend +
                ", time='" + time + '\'' +
                '}';
    }
}
