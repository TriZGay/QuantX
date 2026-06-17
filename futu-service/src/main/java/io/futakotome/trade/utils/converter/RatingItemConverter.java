package io.futakotome.trade.utils.converter;

import com.futu.openapi.pb.QotCommon;
import com.google.gson.*;
import io.futakotome.trade.domain.code.ResearchRatingType;
import io.futakotome.trade.dto.message.RatingItem;

import java.lang.reflect.Type;

public class RatingItemConverter implements JsonDeserializer<RatingItem> {
    @Override
    public RatingItem deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject j = jsonElement.getAsJsonObject().deepCopy();
        String ratingStr = ResearchRatingType.getName(j.get("rating").getAsInt());
        j.addProperty("ratingStr", ratingStr);
        return new Gson().fromJson(j, RatingItem.class);
    }
}
