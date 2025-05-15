package io.futakotome.trade.utils.converter;

import com.google.gson.*;
import io.futakotome.trade.domain.code.MarketType;
import io.futakotome.trade.dto.message.CommonSecurity;

import java.lang.reflect.Type;

public class MarketTypeConverter implements JsonDeserializer<CommonSecurity> {
    @Override
    public CommonSecurity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject marketTypeJson = json.getAsJsonObject();
        Integer market = marketTypeJson.get("market").getAsInt();
        String code = marketTypeJson.get("code").getAsString();
        String marketStr = MarketType.getName(market);
        return new CommonSecurity(market, marketStr, code);
    }
}
