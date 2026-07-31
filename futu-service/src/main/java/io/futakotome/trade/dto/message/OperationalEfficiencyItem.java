package io.futakotome.trade.dto.message;

import com.google.gson.annotations.JsonAdapter;
import io.futakotome.trade.utils.converter.OperationalEfficiencyItemConverter;

@JsonAdapter(OperationalEfficiencyItemConverter.class)
public class OperationalEfficiencyItem {
    private Integer fiscalYear;//财务年度
    private String financialType;//财报类型
    private String financialTypeStr;
    private String periodText;//财报周期
    private Long endDate;//截止日时间戳
    private String endDateStr;
    private Long employeeNum;//员工人数
    private Double employeeNumYoy;//员工人数同比增长率
    private Double incomePerCapita;//人均营收
    private Double incomePerCapitaYoy;//人均营收同比增长率
    private Double profitPerCapita;//人均营业利润
    private Double profitPerCapitaYoy;//人均营业利润同比增长率
    private Double netProfitPerCapita;//人均净利润
    private Double netProfitPerCapitaYoy;//人均净利润同比增长率

    public OperationalEfficiencyItem(Integer fiscalYear, String financialType, String financialTypeStr, String periodText, Long endDate, String endDateStr, Long employeeNum, Double employeeNumYoy, Double incomePerCapita, Double incomePerCapitaYoy, Double profitPerCapita, Double profitPerCapitaYoy, Double netProfitPerCapita, Double netProfitPerCapitaYoy) {
        this.fiscalYear = fiscalYear;
        this.financialType = financialType;
        this.financialTypeStr = financialTypeStr;
        this.periodText = periodText;
        this.endDate = endDate;
        this.endDateStr = endDateStr;
        this.employeeNum = employeeNum;
        this.employeeNumYoy = employeeNumYoy;
        this.incomePerCapita = incomePerCapita;
        this.incomePerCapitaYoy = incomePerCapitaYoy;
        this.profitPerCapita = profitPerCapita;
        this.profitPerCapitaYoy = profitPerCapitaYoy;
        this.netProfitPerCapita = netProfitPerCapita;
        this.netProfitPerCapitaYoy = netProfitPerCapitaYoy;
    }

    public String getFinancialTypeStr() {
        return financialTypeStr;
    }

    public void setFinancialTypeStr(String financialTypeStr) {
        this.financialTypeStr = financialTypeStr;
    }

    public Integer getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(Integer fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public String getFinancialType() {
        return financialType;
    }

    public void setFinancialType(String financialType) {
        this.financialType = financialType;
    }

    public String getPeriodText() {
        return periodText;
    }

    public void setPeriodText(String periodText) {
        this.periodText = periodText;
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

    public Long getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(Long employeeNum) {
        this.employeeNum = employeeNum;
    }

    public Double getEmployeeNumYoy() {
        return employeeNumYoy;
    }

    public void setEmployeeNumYoy(Double employeeNumYoy) {
        this.employeeNumYoy = employeeNumYoy;
    }

    public Double getIncomePerCapita() {
        return incomePerCapita;
    }

    public void setIncomePerCapita(Double incomePerCapita) {
        this.incomePerCapita = incomePerCapita;
    }

    public Double getIncomePerCapitaYoy() {
        return incomePerCapitaYoy;
    }

    public void setIncomePerCapitaYoy(Double incomePerCapitaYoy) {
        this.incomePerCapitaYoy = incomePerCapitaYoy;
    }

    public Double getProfitPerCapita() {
        return profitPerCapita;
    }

    public void setProfitPerCapita(Double profitPerCapita) {
        this.profitPerCapita = profitPerCapita;
    }

    public Double getProfitPerCapitaYoy() {
        return profitPerCapitaYoy;
    }

    public void setProfitPerCapitaYoy(Double profitPerCapitaYoy) {
        this.profitPerCapitaYoy = profitPerCapitaYoy;
    }

    public Double getNetProfitPerCapita() {
        return netProfitPerCapita;
    }

    public void setNetProfitPerCapita(Double netProfitPerCapita) {
        this.netProfitPerCapita = netProfitPerCapita;
    }

    public Double getNetProfitPerCapitaYoy() {
        return netProfitPerCapitaYoy;
    }

    public void setNetProfitPerCapitaYoy(Double netProfitPerCapitaYoy) {
        this.netProfitPerCapitaYoy = netProfitPerCapitaYoy;
    }
}
