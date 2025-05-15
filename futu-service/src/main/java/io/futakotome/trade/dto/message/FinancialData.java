package io.futakotome.trade.dto.message;

import com.google.gson.annotations.JsonAdapter;
import io.futakotome.trade.utils.converter.StockFinancialFieldConverter;

@JsonAdapter(StockFinancialFieldConverter.class)
public class FinancialData {
    private Integer fieldName;
    private String fieldNameStr;
    private Double value;
    private Integer quarter;
    private String quarterStr;

    public FinancialData() {
    }

    public FinancialData(Integer fieldName, String fieldNameStr, Double value, Integer quarter, String quarterStr) {
        this.fieldName = fieldName;
        this.fieldNameStr = fieldNameStr;
        this.value = value;
        this.quarter = quarter;
        this.quarterStr = quarterStr;
    }

    public String getQuarterStr() {
        return quarterStr;
    }

    public void setQuarterStr(String quarterStr) {
        this.quarterStr = quarterStr;
    }

    public String getFieldNameStr() {
        return fieldNameStr;
    }

    public void setFieldNameStr(String fieldNameStr) {
        this.fieldNameStr = fieldNameStr;
    }

    public Integer getFieldName() {
        return fieldName;
    }

    public void setFieldName(Integer fieldName) {
        this.fieldName = fieldName;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer getQuarter() {
        return quarter;
    }

    public void setQuarter(Integer quarter) {
        this.quarter = quarter;
    }
}
