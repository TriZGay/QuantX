package io.futakotome.trade.dto.message;

import com.google.gson.annotations.JsonAdapter;
import io.futakotome.trade.utils.converter.FinancialEarningMoveContentConverter;

import java.util.List;

@JsonAdapter(FinancialEarningMoveContentConverter.class)
public class FinancialEarningMoveContent {
    private Integer fiscalYear;
    private Integer financialType;
    private String financialTypeStr;
    private String periodText;
    private Long pubTradingDay;
    private String pubTradingDayStr;
    private Integer pubType;
    private String pubTypeStr;
    private Integer priceInfoIndex;
    private List<FinancialEarningMovePricePerformance> itemList;

    public String getFinancialTypeStr() {
        return financialTypeStr;
    }

    public void setFinancialTypeStr(String financialTypeStr) {
        this.financialTypeStr = financialTypeStr;
    }

    public String getPubTypeStr() {
        return pubTypeStr;
    }

    public void setPubTypeStr(String pubTypeStr) {
        this.pubTypeStr = pubTypeStr;
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

    public Long getPubTradingDay() {
        return pubTradingDay;
    }

    public void setPubTradingDay(Long pubTradingDay) {
        this.pubTradingDay = pubTradingDay;
    }

    public String getPubTradingDayStr() {
        return pubTradingDayStr;
    }

    public void setPubTradingDayStr(String pubTradingDayStr) {
        this.pubTradingDayStr = pubTradingDayStr;
    }

    public Integer getPubType() {
        return pubType;
    }

    public void setPubType(Integer pubType) {
        this.pubType = pubType;
    }

    public Integer getPriceInfoIndex() {
        return priceInfoIndex;
    }

    public void setPriceInfoIndex(Integer priceInfoIndex) {
        this.priceInfoIndex = priceInfoIndex;
    }

    public List<FinancialEarningMovePricePerformance> getItemList() {
        return itemList;
    }

    public void setItemList(List<FinancialEarningMovePricePerformance> itemList) {
        this.itemList = itemList;
    }
}
