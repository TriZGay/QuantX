package io.futakotome.analyze.mapper.dto;

public class MacdDto {
    private Integer market;
    private String code;
    private Integer rehabType;
    private Double dif;
    private Double dea;
    private Double macd;
    private String updateTime;

    private String table;
    private String start;
    private String end;

    public MacdDto() {
    }

    public MacdDto(String table, String code, Integer rehabType, String start, String end) {
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

    public Double getDif() {
        return dif;
    }

    public void setDif(Double dif) {
        this.dif = dif;
    }

    public Double getDea() {
        return dea;
    }

    public void setDea(Double dea) {
        this.dea = dea;
    }

    public Double getMacd() {
        return macd;
    }

    public void setMacd(Double macd) {
        this.macd = macd;
    }
}
