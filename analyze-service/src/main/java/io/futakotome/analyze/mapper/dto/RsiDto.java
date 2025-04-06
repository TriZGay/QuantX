package io.futakotome.analyze.mapper.dto;

public class RsiDto {
    private Integer market;
    private String code;
    private Integer rehabType;
    private Double rsi_6;
    private Double rsi_12;
    private Double rsi_24;
    private String updateTime;

    private String table;
    private String start;
    private String end;

    public RsiDto() {
    }

    public RsiDto(String table, String code, Integer rehabType, String start, String end) {
        this.code = code;
        this.rehabType = rehabType;
        this.table = table;
        this.start = start;
        this.end = end;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getRehabType() {
        return rehabType;
    }

    public void setRehabType(Integer rehabType) {
        this.rehabType = rehabType;
    }

    public Double getRsi_6() {
        return rsi_6;
    }

    public void setRsi_6(Double rsi_6) {
        this.rsi_6 = rsi_6;
    }

    public Double getRsi_12() {
        return rsi_12;
    }

    public void setRsi_12(Double rsi_12) {
        this.rsi_12 = rsi_12;
    }

    public Double getRsi_24() {
        return rsi_24;
    }

    public void setRsi_24(Double rsi_24) {
        this.rsi_24 = rsi_24;
    }
}
