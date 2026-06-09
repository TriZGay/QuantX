package io.futakotome.trade.dto.message;

import com.google.gson.annotations.JsonAdapter;
import io.futakotome.trade.utils.converter.ScreenDateConverter;

@JsonAdapter(ScreenDateConverter.class)
public class ScreenDateContent {
    private Integer date;
    private String periodText;
    private Integer financialType;
    private String financialTypeStr;

    public ScreenDateContent(Integer date, String periodText, Integer financialType, String financialTypeStr) {
        this.date = date;
        this.periodText = periodText;
        this.financialType = financialType;
        this.financialTypeStr = financialTypeStr;
    }

    public ScreenDateContent() {
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getPeriodText() {
        return periodText;
    }

    public void setPeriodText(String periodText) {
        this.periodText = periodText;
    }

    public Integer getFinancialType() {
        return financialType;
    }

    public void setFinancialType(Integer financialType) {
        this.financialType = financialType;
    }

    public String getFinancialTypeStr() {
        return financialTypeStr;
    }

    public void setFinancialTypeStr(String financialTypeStr) {
        this.financialTypeStr = financialTypeStr;
    }
}
