package io.futakotome.analyze.mapper.dto;

public class KdjDto {
    private Integer market;
    private String code;
    private Integer rehabType;
    private Double highest_9;
    private Double lowest_9;
    private Double rsv;
    private Double k;
    private Double d;
    private Double j;
    private String updateTime;

    private String table;
    private String start;
    private String end;

    @Override
    public String toString() {
        return "KdjDto{" +
                "market=" + market +
                ", code='" + code + '\'' +
                ", rehabType=" + rehabType +
                ", highest_9=" + highest_9 +
                ", lowest_9=" + lowest_9 +
                ", rsv=" + rsv +
                ", k=" + k +
                ", d=" + d +
                ", j=" + j +
                ", updateTime='" + updateTime + '\'' +
                ", table='" + table + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }

    public KdjDto() {
    }

    public KdjDto(String table, String code, Integer rehabType, String start, String end) {
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

    public Double getHighest_9() {
        return highest_9;
    }

    public void setHighest_9(Double highest_9) {
        this.highest_9 = highest_9;
    }

    public Double getLowest_9() {
        return lowest_9;
    }

    public void setLowest_9(Double lowest_9) {
        this.lowest_9 = lowest_9;
    }

    public Double getRsv() {
        return rsv;
    }

    public void setRsv(Double rsv) {
        this.rsv = rsv;
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

    public Double getK() {
        return k;
    }

    public void setK(Double k) {
        this.k = k;
    }

    public Double getD() {
        return d;
    }

    public void setD(Double d) {
        this.d = d;
    }

    public Double getJ() {
        return j;
    }

    public void setJ(Double j) {
        this.j = j;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
