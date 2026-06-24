package io.futakotome.trade.utils.converter;

import com.google.gson.*;
import io.futakotome.trade.domain.code.BuySellType;
import io.futakotome.trade.dto.message.BrokerItem;

import java.lang.reflect.Type;

public class BrokerItemConverter implements JsonDeserializer<BrokerItem> {
    @Override
    public BrokerItem deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject j = jsonElement.getAsJsonObject().deepCopy();
        String buySellTypeStr = BuySellType.getName(j.get("buySellType").getAsInt());
        j.addProperty("buySellTypeStr", buySellTypeStr);
        return new Gson().fromJson(j, BrokerItem.class);
    }
}
