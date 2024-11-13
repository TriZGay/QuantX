package io.futakotome.trade.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@TableName(value = "t_ipo_cn")
public class IpoCnDto implements Serializable {
    @TableId
    private Long id;

    private String name;

    private Integer market;

    private String code;

    private LocalDate listTime;

    private String applyCode;

    private Long issueSize;

    private Long onlineIssueSize;

    private Long applyUpperLimit;

    private Long applyLimitMarketValue;

    private Integer isEstimateIpoPrice;

    private Double ipoPrice;

    private Double industryPeRate;

    private Integer isEstimateWinningRatio;

    private Double winningRatio;

    private Double issuePeRate;

    private LocalDate applyTime;

    private LocalDate winningTime;

    private Integer isHasWon;

    @TableField(exist = false)
    private List<IpoCnExWinningDto> cnExWinningDtos;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getMarket() {
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

    public LocalDate getListTime() {
        return listTime;
    }

    public void setListTime(LocalDate listTime) {
        this.listTime = listTime;
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
    }

    public Long getIssueSize() {
        return issueSize;
    }

    public void setIssueSize(Long issueSize) {
        this.issueSize = issueSize;
    }

    public Long getOnlineIssueSize() {
        return onlineIssueSize;
    }

    public void setOnlineIssueSize(Long onlineIssueSize) {
        this.onlineIssueSize = onlineIssueSize;
    }

    public Long getApplyUpperLimit() {
        return applyUpperLimit;
    }

    public void setApplyUpperLimit(Long applyUpperLimit) {
        this.applyUpperLimit = applyUpperLimit;
    }

    public Long getApplyLimitMarketValue() {
        return applyLimitMarketValue;
    }

    public void setApplyLimitMarketValue(Long applyLimitMarketValue) {
        this.applyLimitMarketValue = applyLimitMarketValue;
    }

    public Integer getIsEstimateIpoPrice() {
        return isEstimateIpoPrice;
    }

    public void setIsEstimateIpoPrice(Integer isEstimateIpoPrice) {
        this.isEstimateIpoPrice = isEstimateIpoPrice;
    }

    public Double getIpoPrice() {
        return ipoPrice;
    }

    public void setIpoPrice(Double ipoPrice) {
        this.ipoPrice = ipoPrice;
    }

    public Double getIndustryPeRate() {
        return industryPeRate;
    }

    public void setIndustryPeRate(Double industryPeRate) {
        this.industryPeRate = industryPeRate;
    }

    public Integer getIsEstimateWinningRatio() {
        return isEstimateWinningRatio;
    }

    public void setIsEstimateWinningRatio(Integer isEstimateWinningRatio) {
        this.isEstimateWinningRatio = isEstimateWinningRatio;
    }

    public Double getWinningRatio() {
        return winningRatio;
    }

    public void setWinningRatio(Double winningRatio) {
        this.winningRatio = winningRatio;
    }

    public Double getIssuePeRate() {
        return issuePeRate;
    }

    public void setIssuePeRate(Double issuePeRate) {
        this.issuePeRate = issuePeRate;
    }

    public LocalDate getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(LocalDate applyTime) {
        this.applyTime = applyTime;
    }

    public LocalDate getWinningTime() {
        return winningTime;
    }

    public void setWinningTime(LocalDate winningTime) {
        this.winningTime = winningTime;
    }

    public Integer getIsHasWon() {
        return isHasWon;
    }

    public void setIsHasWon(Integer isHasWon) {
        this.isHasWon = isHasWon;
    }

    public List<IpoCnExWinningDto> getCnExWinningDtos() {
        return cnExWinningDtos;
    }

    public void setCnExWinningDtos(List<IpoCnExWinningDto> cnExWinningDtos) {
        this.cnExWinningDtos = cnExWinningDtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IpoCnDto ipoCnDto = (IpoCnDto) o;
        return market.equals(ipoCnDto.market) && code.equals(ipoCnDto.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(market, code);
    }
}