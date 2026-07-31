package io.futakotome.trade.utils.converter;

import com.google.gson.*;
import io.futakotome.trade.domain.code.CompanyProfileFieldType;
import io.futakotome.trade.dto.message.CompanyLabItem;

import java.lang.reflect.Type;

public class CompanyLabItemConverter implements JsonDeserializer<CompanyLabItem> {
    @Override
    public CompanyLabItem deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject j = jsonElement.getAsJsonObject();
        String fieldType = j.get("fieldType").getAsString();
        String fieldTypeStr = CompanyProfileFieldType.getName(fieldType);
        String name = j.get("name").getAsString();
        String value = j.get("value").getAsString();
        return new CompanyLabItem(name, value, fieldType, fieldTypeStr);
    }
}
