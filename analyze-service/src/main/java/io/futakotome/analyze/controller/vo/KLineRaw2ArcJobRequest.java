package io.futakotome.analyze.controller.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class KLineRaw2ArcJobRequest extends JobRequest {
    @NotNull(message = "源表必填")
    @NotEmpty(message = "源表必填")
    private String fromTable;
    @NotEmpty(message = "目的表必填")
    @NotNull(message = "目的表必填")
    private String toTable;
    private String updateTimeStart;
    private String updateTimeEnd;

    public String getFromTable() {
        return fromTable;
    }

    public void setFromTable(String fromTable) {
        this.fromTable = fromTable;
    }

    public String getToTable() {
        return toTable;
    }

    public void setToTable(String toTable) {
        this.toTable = toTable;
    }

    public String getUpdateTimeStart() {
        return updateTimeStart;
    }

    public void setUpdateTimeStart(String updateTimeStart) {
        this.updateTimeStart = updateTimeStart;
    }

    public String getUpdateTimeEnd() {
        return updateTimeEnd;
    }

    public void setUpdateTimeEnd(String updateTimeEnd) {
        this.updateTimeEnd = updateTimeEnd;
    }
}
