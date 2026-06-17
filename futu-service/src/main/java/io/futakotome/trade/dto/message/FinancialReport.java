package io.futakotome.trade.dto.message;

import com.google.gson.annotations.JsonAdapter;
import io.futakotome.trade.utils.converter.FinancialReportConverter;

import java.util.List;

@JsonAdapter(FinancialReportConverter.class)
public class FinancialReport {
    private Long dateTime; // 财报截止日时间戳（秒）
    private String dateTimeStr; // 财报截止日时间字符串，格式 YYYY-MM-DD，对应市场时区
    private Integer fiscalYear; // 财务年度，如 2024；0 表示无数据
    private Integer financialType; // 财报类型，详见 Qot_Common.F10Type 定义；0 表示未知，下次请求可原样传入
    private String financialTypeStr;
    private String periodText; // 财报周期，如 "2024/Q3"、"2024/FY"
    private List<FinancialItem> itemList; // 财务数据项列表
    private String currencyInfo; // 货币单位（展示型），如 "人民币"、"美元"
    private String accountingStandards; // 会计准则，如 "国际会计准则"
    private String auditorReport; // 审计意见，如 "无保留意见"
    private String currencyCode; // 币种代码（ISO 4217），如 "CNY"、"USD"

    public String getFinancialTypeStr() {
        return financialTypeStr;
    }

    public void setFinancialTypeStr(String financialTypeStr) {
        this.financialTypeStr = financialTypeStr;
    }

    public Long getDateTime() {
        return dateTime;
    }

    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTimeStr() {
        return dateTimeStr;
    }

    public void setDateTimeStr(String dateTimeStr) {
        this.dateTimeStr = dateTimeStr;
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

    public List<FinancialItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<FinancialItem> itemList) {
        this.itemList = itemList;
    }

    public String getCurrencyInfo() {
        return currencyInfo;
    }

    public void setCurrencyInfo(String currencyInfo) {
        this.currencyInfo = currencyInfo;
    }

    public String getAccountingStandards() {
        return accountingStandards;
    }

    public void setAccountingStandards(String accountingStandards) {
        this.accountingStandards = accountingStandards;
    }

    public String getAuditorReport() {
        return auditorReport;
    }

    public void setAuditorReport(String auditorReport) {
        this.auditorReport = auditorReport;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
