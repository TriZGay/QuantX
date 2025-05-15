package io.futakotome.trade.dto.message;

import com.google.gson.annotations.JsonAdapter;
import io.futakotome.trade.utils.converter.StockBasicFieldConverter;

@JsonAdapter(StockBasicFieldConverter.class)
public class BaseData {
    private Integer fieldName;
    private String fieldNameStr;
    private Double value;

    public BaseData() {
    }

    public BaseData(Integer fieldName, String fieldNameStr, Double value) {
        this.fieldName = fieldName;
        this.fieldNameStr = fieldNameStr;
        this.value = value;
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
}
