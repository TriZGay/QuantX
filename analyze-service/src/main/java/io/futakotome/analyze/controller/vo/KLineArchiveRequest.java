package io.futakotome.analyze.controller.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class KLineArchiveRequest extends RangeRequest {
    @NotNull(message = "表格名必填")
    @NotEmpty(message = "表格名不能为空")
    private String tableName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
