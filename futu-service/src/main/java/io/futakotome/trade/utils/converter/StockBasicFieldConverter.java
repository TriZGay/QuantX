package io.futakotome.trade.utils.converter;

import com.google.gson.*;
import io.futakotome.trade.domain.code.StockBaseField;
import io.futakotome.trade.dto.message.BaseData;

import java.lang.reflect.Type;

public class StockBasicFieldConverter implements JsonDeserializer<BaseData> {
    @Override
    public BaseData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject baseDataJson = json.getAsJsonObject();
        Integer fieldName = baseDataJson.get("fieldName").getAsInt();
        Double value = baseDataJson.get("value").getAsDouble();
        String fieldNameStr = StockBaseField.getName(fieldName);
        return new BaseData(fieldName, fieldNameStr, value);
    }
}
