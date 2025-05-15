package io.futakotome.trade.utils.converter;

import com.google.gson.*;
import io.futakotome.trade.domain.code.StockFinancialField;
import io.futakotome.trade.domain.code.StockFinancialQuarter;
import io.futakotome.trade.dto.message.FinancialData;

import java.lang.reflect.Type;

public class StockFinancialFieldConverter implements JsonDeserializer<FinancialData> {
    @Override
    public FinancialData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Integer fieldName = jsonObject.get("fieldName").getAsInt();
        Double value = jsonObject.get("value").getAsDouble();
        Integer quarter = jsonObject.get("quarter").getAsInt();
        String fieldNameStr = StockFinancialField.getName(fieldName);
        String quarterStr = StockFinancialQuarter.getDesc(quarter);
        return new FinancialData(fieldName, fieldNameStr, value, quarter, quarterStr);
    }
}
