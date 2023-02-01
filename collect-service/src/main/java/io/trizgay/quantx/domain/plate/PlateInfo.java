package io.trizgay.quantx.domain.plate;

import com.futu.openapi.pb.QotGetPlateSet;
import io.trizgay.quantx.domain.market.MarketType;
import io.trizgay.quantx.http.pojo.PostPlateSetRequest;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(publicConverter = false, generateConverter = true)
public class PlateInfo {
    private MarketType marketType;
    private PlateSetType plateSetType;

    public PlateInfo(MarketType marketType, PlateSetType plateSetType) {
        this.marketType = marketType;
        this.plateSetType = plateSetType;
    }

    public PlateInfo(JsonObject jsonObject) {
        PlateInfoConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        PlateInfoConverter.toJson(this, jsonObject);
        return jsonObject;
    }

    public static PlateInfo fromRequest(PostPlateSetRequest request) {
        return new PlateInfo(MarketType.getMarket(request.getMarket()),
                PlateSetType.getPlateSet(request.getPlateSetType()));
    }

    public QotGetPlateSet.C2S toFTGrpcRequest() {
        return QotGetPlateSet.C2S.newBuilder()
                .setMarket(this.marketType.getCode())
                .setPlateSetType(this.plateSetType.getCode())
                .build();
    }

    public MarketType getMarketType() {
        return marketType;
    }

    public void setMarketType(MarketType marketType) {
        this.marketType = marketType;
    }

    public PlateSetType getPlateSetType() {
        return plateSetType;
    }

    public void setPlateSetType(PlateSetType plateSetType) {
        this.plateSetType = plateSetType;
    }
}
