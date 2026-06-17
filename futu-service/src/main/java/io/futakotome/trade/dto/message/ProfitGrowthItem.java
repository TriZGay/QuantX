package io.futakotome.trade.dto.message;

public class ProfitGrowthItem {
    private Integer financialYear; // 财报年度
    private Integer financialQuarter; // 财报季度（1=Q1, 2=Q2, 3=Q3, 4=FY）
    private String periodStr; // 财报周期，如 "2024/Q3"、"2024/FY"
    private Long reportDate; // 报告日时间戳（秒）
    private String reportDateStr; // 报告日字符串，格式 YYYY-MM-DD，对应市场时区
    private Double marketCapMultiple; // 报告日市值倍数（基准期 = 1）
    private Double financeDataMultiple; // 盈利 / 营收倍数（基准期 = 1，依 valuationType 而定）

    public Integer getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(Integer financialYear) {
        this.financialYear = financialYear;
    }

    public Integer getFinancialQuarter() {
        return financialQuarter;
    }

    public void setFinancialQuarter(Integer financialQuarter) {
        this.financialQuarter = financialQuarter;
    }

    public String getPeriodStr() {
        return periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public Long getReportDate() {
        return reportDate;
    }

    public void setReportDate(Long reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportDateStr() {
        return reportDateStr;
    }

    public void setReportDateStr(String reportDateStr) {
        this.reportDateStr = reportDateStr;
    }

    public Double getMarketCapMultiple() {
        return marketCapMultiple;
    }

    public void setMarketCapMultiple(Double marketCapMultiple) {
        this.marketCapMultiple = marketCapMultiple;
    }

    public Double getFinanceDataMultiple() {
        return financeDataMultiple;
    }

    public void setFinanceDataMultiple(Double financeDataMultiple) {
        this.financeDataMultiple = financeDataMultiple;
    }
}
