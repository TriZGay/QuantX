package io.futakotome.trade.dto.message;

import com.google.gson.annotations.JsonAdapter;
import io.futakotome.trade.utils.converter.OperationalEfficiencyItemConverter;

@JsonAdapter(OperationalEfficiencyItemConverter.class)
public class OperationalEfficiencyItem {
    private Integer fiscalYear;
    private Integer financialType;
    private String financialTypeStr;
    private String periodText;
    private Long endDate;
    private String endDateStr;
    private Long employeeNum;
    private Double employeeNumYoy;
    private Double incomePerCapita;
    private Double incomePerCapitaYoy;
    private Double profitPerCapita;
    private Double profitPerCapitaYoy;
    private Double netProfitPerCapita;
    private Double netProfitPerCapitaYoy;

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

    public Integer getFinancialType() {
        return financialType;
    }

    public void setFinancialType(Integer financialType) {
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
