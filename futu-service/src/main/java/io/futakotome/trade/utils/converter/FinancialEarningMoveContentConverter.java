package io.futakotome.trade.utils.converter;

import com.google.gson.*;
import io.futakotome.trade.domain.code.EarningsPubTimeType;
import io.futakotome.trade.domain.code.FinancialType;
import io.futakotome.trade.dto.message.FinancialEarningMoveContent;

import java.lang.reflect.Type;

public class FinancialEarningMoveContentConverter implements JsonDeserializer<FinancialEarningMoveContent> {
    @Override
    public FinancialEarningMoveContent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject j = jsonElement.getAsJsonObject().deepCopy();
        String financialTypeStr = FinancialType.getName(j.get("financialType").getAsString());
        j.addProperty("financialTypeStr", financialTypeStr);
        String pubTypeStr = EarningsPubTimeType.getName(j.get("pubType").getAsInt());
        j.addProperty("pubTypeStr", pubTypeStr);
        return new Gson().fromJson(j, FinancialEarningMoveContent.class);
    }
}
