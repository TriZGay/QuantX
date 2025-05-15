package io.futakotome.trade.utils.converter;

import com.google.gson.*;
import io.futakotome.trade.domain.code.StockAccumulateField;
import io.futakotome.trade.dto.message.AccumulateData;

import java.lang.reflect.Type;

public class StockAccumulateFieldConverter implements JsonDeserializer<AccumulateData> {
    @Override
    public AccumulateData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Integer fieldName = jsonObject.get("fieldName").getAsInt();
        Double value = jsonObject.get("value").getAsDouble();
        Integer days = jsonObject.get("days").getAsInt();
        String fieldNameStr = StockAccumulateField.getName(fieldName);
        return new AccumulateData(fieldName, fieldNameStr, value, days);
    }
}
