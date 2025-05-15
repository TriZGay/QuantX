package io.futakotome.trade.dto.message;

import com.google.gson.annotations.JsonAdapter;
import io.futakotome.trade.utils.converter.StockAccumulateFieldConverter;

@JsonAdapter(StockAccumulateFieldConverter.class)
public class AccumulateData {
    private Integer fieldName;
    private String fieldNameStr;
    private Double value;
    private Integer days;

    public AccumulateData() {
    }

    public AccumulateData(Integer fieldName, String fieldNameStr, Double value, Integer days) {
        this.fieldName = fieldName;
        this.fieldNameStr = fieldNameStr;
        this.value = value;
        this.days = days;
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

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
