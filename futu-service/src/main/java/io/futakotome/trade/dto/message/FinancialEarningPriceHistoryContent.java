package io.futakotome.trade.dto.message;

import com.google.gson.annotations.JsonAdapter;
import io.futakotome.trade.utils.converter.FinancialEarningPriceHistoryContentConverter;

import java.util.List;

@JsonAdapter(FinancialEarningPriceHistoryContentConverter.class)
public class FinancialEarningPriceHistoryContent {
    private Integer fiscalYear;
    private Integer financialType;
    private String financialTypeStr;
    private String periodText;
    private Boolean isCurrent;
    private Long pubTradingDay;
    private String pubTradingDayStr;
    private Long pubTime;
    private String pubTimeStr;
    private Integer pubType;
    private String pubTypeStr;
    private Double predictVolaRatioNewest;
    private Double predictVolaRatioHighest;
    private Double predictVolaValNewest;
    private Double predictVolaValHighest;
    private Double optionIVCrush;
    private Double optionStrikeDateIVCrush;
    private FinancialEarningPriceHistoryPriceInfo priceInfo;
    private List<FinancialEarningPriceHistoryFinScheduleInfo> scheduleInfoList;

    public String getPubTypeStr() {
        return pubTypeStr;
    }

    public void setPubTypeStr(String pubTypeStr) {
        this.pubTypeStr = pubTypeStr;
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

    public Boolean getCurrent() {
        return isCurrent;
    }

    public void setCurrent(Boolean current) {
        isCurrent = current;
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

    public Long getPubTime() {
        return pubTime;
    }

    public void setPubTime(Long pubTime) {
        this.pubTime = pubTime;
    }

    public String getPubTimeStr() {
        return pubTimeStr;
    }

    public void setPubTimeStr(String pubTimeStr) {
        this.pubTimeStr = pubTimeStr;
    }

    public Integer getPubType() {
        return pubType;
    }

    public void setPubType(Integer pubType) {
        this.pubType = pubType;
    }

    public Double getPredictVolaRatioNewest() {
        return predictVolaRatioNewest;
    }

    public void setPredictVolaRatioNewest(Double predictVolaRatioNewest) {
        this.predictVolaRatioNewest = predictVolaRatioNewest;
    }

    public Double getPredictVolaRatioHighest() {
        return predictVolaRatioHighest;
    }

    public void setPredictVolaRatioHighest(Double predictVolaRatioHighest) {
        this.predictVolaRatioHighest = predictVolaRatioHighest;
    }

    public Double getPredictVolaValNewest() {
        return predictVolaValNewest;
    }

    public void setPredictVolaValNewest(Double predictVolaValNewest) {
        this.predictVolaValNewest = predictVolaValNewest;
    }

    public Double getPredictVolaValHighest() {
        return predictVolaValHighest;
    }

    public void setPredictVolaValHighest(Double predictVolaValHighest) {
        this.predictVolaValHighest = predictVolaValHighest;
    }

    public Double getOptionIVCrush() {
        return optionIVCrush;
    }

    public void setOptionIVCrush(Double optionIVCrush) {
        this.optionIVCrush = optionIVCrush;
    }

    public Double getOptionStrikeDateIVCrush() {
        return optionStrikeDateIVCrush;
    }

    public void setOptionStrikeDateIVCrush(Double optionStrikeDateIVCrush) {
        this.optionStrikeDateIVCrush = optionStrikeDateIVCrush;
    }

    public FinancialEarningPriceHistoryPriceInfo getPriceInfo() {
        return priceInfo;
    }

    public void setPriceInfo(FinancialEarningPriceHistoryPriceInfo priceInfo) {
        this.priceInfo = priceInfo;
    }

    public List<FinancialEarningPriceHistoryFinScheduleInfo> getScheduleInfoList() {
        return scheduleInfoList;
    }

    public void setScheduleInfoList(List<FinancialEarningPriceHistoryFinScheduleInfo> scheduleInfoList) {
        this.scheduleInfoList = scheduleInfoList;
    }
}
