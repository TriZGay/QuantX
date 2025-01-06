package io.futakotome.analyze.mapper.dto;

import java.time.LocalDate;

public class TradeDateDto {
    private Long id;
    private String marketOrSecurity;
    private LocalDate time;
    private Integer tradeDateType;

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

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public Integer getTradeDateType() {
        return tradeDateType;
    }

    public void setTradeDateType(Integer tradeDateType) {
        this.tradeDateType = tradeDateType;
    }
}
