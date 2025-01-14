package io.futakotome.analyze.mapper.dto;

public class BollDto {
    private Integer market;
    private String code;
    private Integer rehabType;
    private Double ma20_mid;
    private Double double_upper;
    private Double double_lower;
    private Double one_upper;
    private Double one_lower;
    private Double triple_upper;
    private Double triple_lower;
    private String updateTime;

    private String tableName;
    private String start;
    private String end;

    public BollDto() {
    }

    public BollDto(String tableName, String code, Integer rehabType, String start, String end) {
        this.tableName = tableName;
        this.code = code;
        this.rehabType = rehabType;
        this.start = start;
        this.end = end;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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

    public Double getMa20_mid() {
        return ma20_mid;
    }

    public void setMa20_mid(Double ma20_mid) {
        this.ma20_mid = ma20_mid;
    }

    public Double getDouble_upper() {
        return double_upper;
    }

    public void setDouble_upper(Double double_upper) {
        this.double_upper = double_upper;
    }

    public Double getDouble_lower() {
        return double_lower;
    }

    public void setDouble_lower(Double double_lower) {
        this.double_lower = double_lower;
    }

    public Double getOne_upper() {
        return one_upper;
    }

    public void setOne_upper(Double one_upper) {
        this.one_upper = one_upper;
    }

    public Double getOne_lower() {
        return one_lower;
    }

    public void setOne_lower(Double one_lower) {
        this.one_lower = one_lower;
    }

    public Double getTriple_upper() {
        return triple_upper;
    }

    public void setTriple_upper(Double triple_upper) {
        this.triple_upper = triple_upper;
    }

    public Double getTriple_lower() {
        return triple_lower;
    }

    public void setTriple_lower(Double triple_lower) {
        this.triple_lower = triple_lower;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
