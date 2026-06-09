package io.futakotome.trade.utils.converter;

import com.google.gson.*;
import io.futakotome.trade.domain.code.RevenueBreakdownType;
import io.futakotome.trade.dto.message.RevenueBreakdownGroupContent;

import java.lang.reflect.Type;

public class RevenueBreakdownGroupConverter implements JsonDeserializer<RevenueBreakdownGroupContent> {
    @Override
    public RevenueBreakdownGroupContent deserialize(JsonElement jsonElement, Type t, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject().deepCopy();
        String typeStr = RevenueBreakdownType.getName(jsonObject.get("type").getAsInt());
        jsonObject.addProperty("typeStr", typeStr);

        return new Gson().fromJson(jsonObject, RevenueBreakdownGroupContent.class);
    }
}
