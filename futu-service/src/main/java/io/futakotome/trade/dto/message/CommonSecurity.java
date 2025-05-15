package io.futakotome.trade.dto.message;

import com.google.gson.annotations.JsonAdapter;
import io.futakotome.trade.utils.converter.MarketTypeConverter;

@JsonAdapter(MarketTypeConverter.class)
public class CommonSecurity {
    private Integer market;
    private String marketStr;
    private String code;

    public CommonSecurity() {
    }

    public CommonSecurity(Integer market, String code) {
        this.market = market;
        this.code = code;
    }

    public CommonSecurity(Integer market, String marketStr, String code) {
        this.market = market;
        this.marketStr = marketStr;
        this.code = code;
    }

    public String getMarketStr() {
        return marketStr;
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
}
