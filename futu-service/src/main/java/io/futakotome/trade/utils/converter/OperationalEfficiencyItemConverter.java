package io.futakotome.trade.utils.converter;

import com.google.gson.*;
import io.futakotome.trade.domain.code.FinancialType;
import io.futakotome.trade.dto.message.OperationalEfficiencyItem;

import java.lang.reflect.Type;

public class OperationalEfficiencyItemConverter implements JsonDeserializer<OperationalEfficiencyItem> {
    @Override
    public OperationalEfficiencyItem deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject j = jsonElement.getAsJsonObject().deepCopy();
        String financialTypeStr = FinancialType.getName(j.get("financialType").getAsInt());
        j.addProperty("financialTypeStr", financialTypeStr);
        return new Gson().fromJson(j, OperationalEfficiencyItem.class);
    }
}
