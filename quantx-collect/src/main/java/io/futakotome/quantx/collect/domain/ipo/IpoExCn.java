package io.futakotome.quantx.collect.domain.ipo;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_ipo_ex_cn")
public class IpoExCn {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "")
    @Column(name = "id")
    private Long id;
    //申购代码
    private String applyCode;
    //发行总数
    private Long issueSize;
    //网上发行量
    private Long onlineIssueSize;
    //申购上限
    private Long applyUpperLimit;
    //顶格申购需配市值
    private Long applyLimitMarketValue;
    //是否预估发行价
    private Integer isEstimateIpoPrice;
    //发行价 预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新。
    private Double ipoPrice;
    //行业市盈率
    private Double industryPeRate;
    //是否预估中签率
    private Integer isEstimateWinningRatio;
    //中签率 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新
    private Double winningRatio;
    //发行市盈率
    private Double issuePeRate;
    //申购日期字符串
    private String applyTime;
    //申购日期时间戳
    private LocalDateTime applyTimestamp;
    //公布中签日期字符串
    private String winningTime;
    //公布中签日期时间戳
    private LocalDateTime winningTimestamp;
    //是否已经公布中签号
    private Integer isHasWon;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public LocalDateTime getApplyTimestamp() {
        return applyTimestamp;
    }

    public void setApplyTimestamp(LocalDateTime applyTimestamp) {
        this.applyTimestamp = applyTimestamp;
    }

    public String getWinningTime() {
        return winningTime;
    }

    public void setWinningTime(String winningTime) {
        this.winningTime = winningTime;
    }

    public LocalDateTime getWinningTimestamp() {
        return winningTimestamp;
    }

    public void setWinningTimestamp(LocalDateTime winningTimestamp) {
        this.winningTimestamp = winningTimestamp;
    }

    public Integer getIsHasWon() {
        return isHasWon;
    }

    public void setIsHasWon(Integer isHasWon) {
        this.isHasWon = isHasWon;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }
}
