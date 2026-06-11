package io.futakotome.trade.utils.converter;


import com.google.gson.*;
import io.futakotome.trade.domain.code.ResearchRatingType;
import io.futakotome.trade.dto.ws.AnalystConsensusContent;

import java.lang.reflect.Type;

public class AnalystConsensusContentConverter implements JsonDeserializer<AnalystConsensusContent> {
    @Override
    public AnalystConsensusContent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject j = jsonElement.getAsJsonObject().deepCopy();
        String ratingStr = ResearchRatingType.getName(j.get("rating").getAsInt());
        j.addProperty("ratingStr", ratingStr);
        return new Gson().fromJson(j, AnalystConsensusContent.class);
    }
}
