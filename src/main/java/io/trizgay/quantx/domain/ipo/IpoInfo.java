package io.trizgay.quantx.domain.ipo;

import io.trizgay.quantx.domain.market.MarketType;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(publicConverter = false, generateConverter = true)
public class IpoInfo {
    private MarketType marketType;

    public IpoInfo(MarketType marketType) {
        this.marketType = marketType;
    }

    public IpoInfo(JsonObject jsonObject) {
        IpoInfoConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        IpoInfoConverter.toJson(this, jsonObject);
        return jsonObject;
    }

    public MarketType getMarketType() {
        return marketType;
    }

    public void setMarketType(MarketType marketType) {
        this.marketType = marketType;
    }

    @Override
    public String toString() {
        return "IpoInfo{" +
                "marketType=" + marketType +
                '}';
    }
}
