package io.trizgay.quantx.domain.security;

import com.futu.openapi.pb.QotCommon;
import com.futu.openapi.pb.QotGetPlateSecurity;
import io.trizgay.quantx.domain.market.MarketType;
import io.trizgay.quantx.http.pojo.PostSecurityListRequest;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(publicConverter = false, generateConverter = true)
public class SecurityInfo {
    private MarketType market;
    private String code;

    public SecurityInfo(JsonObject jsonObject) {
        SecurityInfoConverter.fromJson(jsonObject, this);
    }

    public SecurityInfo(MarketType market, String code) {
        this.market = market;
        this.code = code;
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        SecurityInfoConverter.toJson(this, jsonObject);
        return jsonObject;
    }

    public static SecurityInfo fromRequest(PostSecurityListRequest body) {
        return new SecurityInfo(MarketType.getMarket(body.getMarket()), body.getCode());
    }

    public QotGetPlateSecurity.C2S toFTGrpcRequest() {
        return QotGetPlateSecurity.C2S.newBuilder()
                .setPlate(QotCommon.Security.newBuilder()
                        .setMarket(this.market.getCode())
                        .setCode(this.code).build())
                .build();
    }

    public MarketType getMarket() {
        return market;
    }

    public void setMarket(MarketType market) {
        this.market = market;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "SecurityInfo{" +
                "market=" + market +
                ", code='" + code + '\'' +
                '}';
    }

}
