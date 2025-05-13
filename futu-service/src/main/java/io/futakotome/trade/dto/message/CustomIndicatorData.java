package io.futakotome.trade.dto.message;

import java.util.List;

public class CustomIndicatorData {
    private Integer fieldName;
    private Double value;
    private Integer klType;
    private List<Integer> fieldParaList;

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

    public Integer getKlType() {
        return klType;
    }

    public void setKlType(Integer klType) {
        this.klType = klType;
    }

    public List<Integer> getFieldParaList() {
        return fieldParaList;
    }

    public void setFieldParaList(List<Integer> fieldParaList) {
        this.fieldParaList = fieldParaList;
    }
}
