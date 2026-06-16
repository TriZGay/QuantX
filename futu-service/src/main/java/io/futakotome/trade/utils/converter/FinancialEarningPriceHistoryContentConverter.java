package io.futakotome.trade.utils.converter;

import com.google.gson.*;
import io.futakotome.trade.domain.code.EarningsPubTimeType;
import io.futakotome.trade.domain.code.FinancialType;
import io.futakotome.trade.dto.message.FinancialEarningPriceHistoryContent;

import java.lang.reflect.Type;

public class FinancialEarningPriceHistoryContentConverter implements JsonDeserializer<FinancialEarningPriceHistoryContent> {
    @Override
    public FinancialEarningPriceHistoryContent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject j = jsonElement.getAsJsonObject().deepCopy();
        String financialTypeStr = FinancialType.getName(j.get("financialType").getAsInt());
        j.addProperty("financialTypeStr", financialTypeStr);
        String pubTypeStr = EarningsPubTimeType.getName(j.get("pubType").getAsInt());
        j.addProperty("pubTypeStr", pubTypeStr);
        return new Gson().fromJson(j, FinancialEarningPriceHistoryContent.class);
    }
}
