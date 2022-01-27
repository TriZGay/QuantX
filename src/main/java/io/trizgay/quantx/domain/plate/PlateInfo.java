package io.trizgay.quantx.domain.plate;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(publicConverter = false, generateConverter = true)
public class PlateInfo {
    public PlateInfo() {
    }

    public PlateInfo(JsonObject jsonObject) {
        PlateInfoConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        PlateInfoConverter.toJson(this, jsonObject);
        return jsonObject;
    }
}
