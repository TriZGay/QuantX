package io.futakotome.trade.utils.converter;

import com.google.gson.*;
import io.futakotome.trade.domain.code.CompanyProfileFieldType;
import io.futakotome.trade.dto.message.CompanyLabItem;

import java.lang.reflect.Type;

public class CompanyLabItemConverter implements JsonDeserializer<CompanyLabItem> {
    @Override
    public CompanyLabItem deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject j = jsonElement.getAsJsonObject().deepCopy();
        String fieldTypeStr = CompanyProfileFieldType.getName(j.get("fieldType").getAsInt());
        j.addProperty("fieldTypeStr", fieldTypeStr);
        return new Gson().fromJson(j, CompanyLabItem.class);
    }
}
