package io.futakotome.trade.dto.message;

public class FinancialItem {
    private Long fieldId; // 财务字段 ID，与 structureList 中 fieldId 对应
    private Double data; // 财务数据
    private Double yoy; // 同比（%）
    private Double qoq;// 环比（%）

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public Double getData() {
        return data;
    }

    public void setData(Double data) {
        this.data = data;
    }

    public Double getYoy() {
        return yoy;
    }

    public void setYoy(Double yoy) {
        this.yoy = yoy;
    }

    public Double getQoq() {
        return qoq;
    }

    public void setQoq(Double qoq) {
        this.qoq = qoq;
    }
}
