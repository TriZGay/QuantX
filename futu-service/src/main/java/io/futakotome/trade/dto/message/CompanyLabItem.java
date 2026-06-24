package io.futakotome.trade.dto.message;

import com.google.gson.annotations.JsonAdapter;
import io.futakotome.trade.utils.converter.CompanyLabItemConverter;

@JsonAdapter(CompanyLabItemConverter.class)
public class CompanyLabItem {
    private String name; // 标签名
    private String value; // 标签对应信息
    private Integer fieldType; // 标签类型
    private String fieldTypeStr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getFieldType() {
        return fieldType;
    }

    public void setFieldType(Integer fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldTypeStr() {
        return fieldTypeStr;
    }

    public void setFieldTypeStr(String fieldTypeStr) {
        this.fieldTypeStr = fieldTypeStr;
    }
}
