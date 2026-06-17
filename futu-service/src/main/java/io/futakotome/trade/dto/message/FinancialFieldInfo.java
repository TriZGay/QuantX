package io.futakotome.trade.dto.message;

public class FinancialFieldInfo {
    private Long fieldId; // 财务字段 ID
    private String displayName; // 字段展示名（当前语言，如"营业收入"）

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
