package io.futakotome.trade.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Objects;

@TableName(value = "t_trade_date")
public class TradeDateDto implements Serializable {
    @TableId
    private Long id;

    private String marketOrSecurity;

    private String time;

    private Integer tradeDateType;

    private static final long serialVersionUID = 1L;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradeDateDto that = (TradeDateDto) o;
        return marketOrSecurity.equals(that.marketOrSecurity) && time.equals(that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marketOrSecurity, time);
    }
}