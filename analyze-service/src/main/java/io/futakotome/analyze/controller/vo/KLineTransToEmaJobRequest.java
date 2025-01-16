package io.futakotome.analyze.controller.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class KLineTransToEmaJobRequest extends JobRequest {
    @NotNull(message = "表格必填")
    @NotEmpty(message = "表格必填")
    private String toTableName;
    @NotNull(message = "K表格必填")
    @NotEmpty(message = "K表格必填")
    private String kTableName;
    @NotNull(message = "MA表格必填")
    @NotEmpty(message = "MA表格必填")
    private String maTableName;

    private String startDateTime;
    private String endDateTime;

    public String getToTableName() {
        return toTableName;
    }

    public void setToTableName(String toTableName) {
        this.toTableName = toTableName;
    }

    public String getkTableName() {
        return kTableName;
    }

    public void setkTableName(String kTableName) {
        this.kTableName = kTableName;
    }

    public String getMaTableName() {
        return maTableName;
    }

    public void setMaTableName(String maTableName) {
        this.maTableName = maTableName;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }
}
