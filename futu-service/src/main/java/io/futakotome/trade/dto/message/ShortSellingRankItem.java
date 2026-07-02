package io.futakotome.trade.dto.message;

public class ShortSellingRankItem {
    private CommonSecurity security;  // 股票
    private String name;                    // 名称

    private Double closePrice;               // 收盘价
    private Double changeRatio;              // 涨跌幅(%)
    private Double changeRatio5D;            // 5日涨跌幅(%)
    private Double changeRatio10D;           // 10日涨跌幅(%)
    private Long volume;                    // 成交量

    private Long shortNumber;               // 卖空数量
    private Long shortNumberChange;         // 卖空变化量
    private Double shortRatio;               // 卖空比例(%)
    private Double shortRatioChange;         // 卖空变化比例(%)
    private Long shortPositionVolume;       // 空头持仓数量
    private Double shortPositionRatio;       // 空头持仓比例(%)
    private Double daysToCover;              // 回补天数
    private Long weekAvgShortNumber;        // 近一周日均卖空
    private Double weekAvgShortRatio;        // 近一周日均卖空比例(%)
    private Long monthAvgShortNumber;       // 近一月日均卖空
    private Double monthAvgShortRatio;       // 近一月日均卖空比例(%)

    public CommonSecurity getSecurity() {
        return security;
    }

    public void setSecurity(CommonSecurity security) {
        this.security = security;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }

    public Double getChangeRatio() {
        return changeRatio;
    }

    public void setChangeRatio(Double changeRatio) {
        this.changeRatio = changeRatio;
    }

    public Double getChangeRatio5D() {
        return changeRatio5D;
    }

    public void setChangeRatio5D(Double changeRatio5D) {
        this.changeRatio5D = changeRatio5D;
    }

    public Double getChangeRatio10D() {
        return changeRatio10D;
    }

    public void setChangeRatio10D(Double changeRatio10D) {
        this.changeRatio10D = changeRatio10D;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Long getShortNumber() {
        return shortNumber;
    }

    public void setShortNumber(Long shortNumber) {
        this.shortNumber = shortNumber;
    }

    public Long getShortNumberChange() {
        return shortNumberChange;
    }

    public void setShortNumberChange(Long shortNumberChange) {
        this.shortNumberChange = shortNumberChange;
    }

    public Double getShortRatio() {
        return shortRatio;
    }

    public void setShortRatio(Double shortRatio) {
        this.shortRatio = shortRatio;
    }

    public Double getShortRatioChange() {
        return shortRatioChange;
    }

    public void setShortRatioChange(Double shortRatioChange) {
        this.shortRatioChange = shortRatioChange;
    }

    public Long getShortPositionVolume() {
        return shortPositionVolume;
    }

    public void setShortPositionVolume(Long shortPositionVolume) {
        this.shortPositionVolume = shortPositionVolume;
    }

    public Double getShortPositionRatio() {
        return shortPositionRatio;
    }

    public void setShortPositionRatio(Double shortPositionRatio) {
        this.shortPositionRatio = shortPositionRatio;
    }

    public Double getDaysToCover() {
        return daysToCover;
    }

    public void setDaysToCover(Double daysToCover) {
        this.daysToCover = daysToCover;
    }

    public Long getWeekAvgShortNumber() {
        return weekAvgShortNumber;
    }

    public void setWeekAvgShortNumber(Long weekAvgShortNumber) {
        this.weekAvgShortNumber = weekAvgShortNumber;
    }

    public Double getWeekAvgShortRatio() {
        return weekAvgShortRatio;
    }

    public void setWeekAvgShortRatio(Double weekAvgShortRatio) {
        this.weekAvgShortRatio = weekAvgShortRatio;
    }

    public Long getMonthAvgShortNumber() {
        return monthAvgShortNumber;
    }

    public void setMonthAvgShortNumber(Long monthAvgShortNumber) {
        this.monthAvgShortNumber = monthAvgShortNumber;
    }

    public Double getMonthAvgShortRatio() {
        return monthAvgShortRatio;
    }

    public void setMonthAvgShortRatio(Double monthAvgShortRatio) {
        this.monthAvgShortRatio = monthAvgShortRatio;
    }
}
