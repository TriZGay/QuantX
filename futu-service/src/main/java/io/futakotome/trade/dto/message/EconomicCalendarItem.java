package io.futakotome.trade.dto.message;

public class EconomicCalendarItem {
    private String title;          // 标题(如"非农就业人数")
    private Double timestamp;      // 发布时间戳(秒)
    private String country;        // 国家名称
    private Integer star;            // 星级(重要性 1-3)
    private String previous;       // 前值
    private String consensus;      // 预测值
    private String actual;         // 实际值(公布值)

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Double timestamp) {
        this.timestamp = timestamp;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getConsensus() {
        return consensus;
    }

    public void setConsensus(String consensus) {
        this.consensus = consensus;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }
}
