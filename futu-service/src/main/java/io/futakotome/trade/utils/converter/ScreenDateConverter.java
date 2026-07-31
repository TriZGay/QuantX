package io.futakotome.trade.utils.converter;

import com.google.gson.*;
import io.futakotome.trade.domain.code.FinancialType;
import io.futakotome.trade.dto.message.ScreenDateContent;

import java.lang.reflect.Type;

public class ScreenDateConverter implements JsonDeserializer<ScreenDateContent> {
    @Override
    public ScreenDateContent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Integer date = jsonObject.get("date").getAsInt();
        String periodText = jsonObject.get("periodText").getAsString();
        String financialType = jsonObject.get("financialType").getAsString();
        String finTypeStr = FinancialType.getName(financialType);
        return null;
    }
}
