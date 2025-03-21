package io.futakotome.analyze.mapper.dto;

public class MaNDto {
    private Integer market;
    private String code;
    private Integer rehabType;
    private Double ma_5;
    private Double ma_10;
    private Double ma_20;
    private Double ma_30;
    private Double ma_60;
    private Double ma_120;

    private Double ma_12;
    private Double ma_26;

    private String updateTime;

    private String tableName;
    private String start;
    private String end;

    public MaNDto() {
    }

    public MaNDto(String tableName, String code, Integer rehabType, String start, String end) {
        this.tableName = tableName;
        this.code = code;
        this.rehabType = rehabType;
        this.start = start;
        this.end = end;
    }

    public Double getMa_12() {
        return ma_12;
    }

    public void setMa_12(Double ma_12) {
        this.ma_12 = ma_12;
    }

    public Double getMa_26() {
        return ma_26;
    }

    public void setMa_26(Double ma_26) {
        this.ma_26 = ma_26;
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

    public Double getMa_5() {
        return ma_5;
    }

    public void setMa_5(Double ma_5) {
        this.ma_5 = ma_5;
    }

    public Double getMa_10() {
        return ma_10;
    }

    public void setMa_10(Double ma_10) {
        this.ma_10 = ma_10;
    }

    public Double getMa_20() {
        return ma_20;
    }

    public void setMa_20(Double ma_20) {
        this.ma_20 = ma_20;
    }

    public Double getMa_30() {
        return ma_30;
    }

    public void setMa_30(Double ma_30) {
        this.ma_30 = ma_30;
    }

    public Double getMa_60() {
        return ma_60;
    }

    public void setMa_60(Double ma_60) {
        this.ma_60 = ma_60;
    }

    public Double getMa_120() {
        return ma_120;
    }

    public void setMa_120(Double ma_120) {
        this.ma_120 = ma_120;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
