package io.futakotome.quantx.dto;

public class TradeDateDto {
    private Long id;
    private String marketOrSecurity;
    private String time;
    private Integer tradeDateType;

    public TradeDateDto(Long id, String marketOrSecurity, String time, Integer tradeDateType) {
        this.id = id;
        this.marketOrSecurity = marketOrSecurity;
        this.time = time;
        this.tradeDateType = tradeDateType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarketOrSecurity() {
        return marketOrSecurity;
    }

    public void setMarketOrSecurity(String marketOrSecurity) {
        this.marketOrSecurity = marketOrSecurity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getTradeDateType() {
        return tradeDateType;
    }

    public void setTradeDateType(Integer tradeDateType) {
        this.tradeDateType = tradeDateType;
    }

    @Override
    public String toString() {
        return "TradeDateDto{" +
                "id=" + id +
                ", marketOrSecurity='" + marketOrSecurity + '\'' +
                ", time='" + time + '\'' +
                ", tradeDateType=" + tradeDateType +
                '}';
    }
}
