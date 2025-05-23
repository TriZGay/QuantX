package io.futakotome.analyze.mapper.dto;

public class ArbrDto {
    private Integer market;
    private String code;
    private Integer rehabType;
    private Double ar;
    private Double br;
    private String updateTime;

    private String table;
    private String start;
    private String end;

    public ArbrDto() {
    }

    public ArbrDto(String table, String code, Integer rehabType, String start, String end) {
        this.table = table;
        this.code = code;
        this.rehabType = rehabType;
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

    public Double getAr() {
        return ar;
    }

    public void setAr(Double ar) {
        this.ar = ar;
    }

    public Double getBr() {
        return br;
    }

    public void setBr(Double br) {
        this.br = br;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
