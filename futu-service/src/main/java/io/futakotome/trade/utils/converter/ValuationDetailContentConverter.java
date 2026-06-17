package io.futakotome.trade.utils.converter;

import com.futu.openapi.pb.QotCommon;
import com.google.gson.*;
import io.futakotome.trade.domain.code.ValuationType;
import io.futakotome.trade.dto.message.ValuationDetailContent;

import java.lang.reflect.Type;

public class ValuationDetailContentConverter implements JsonDeserializer<ValuationDetailContent> {
    @Override
    public ValuationDetailContent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject j = jsonElement.getAsJsonObject().deepCopy();
        String valuationTypeStr = ValuationType.getName(j.get("valuationType").getAsInt());
        j.addProperty("valuationTypeStr", valuationTypeStr);
        return new Gson().fromJson(j, ValuationDetailContent.class);
    }
}
