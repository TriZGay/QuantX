package io.futakotome.trade.utils.converter;

import com.google.gson.*;
import io.futakotome.trade.domain.code.*;
import io.futakotome.trade.dto.message.OrderContent;

import java.lang.reflect.Type;
import java.util.Objects;

public class OrderContentConverter implements JsonDeserializer<OrderContent> {
    @Override
    public OrderContent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        OrderContent orderContent = new OrderContent();
        if (Objects.nonNull(jsonObject)) {
            if (Objects.nonNull(jsonObject.get("trdSide"))) {
                orderContent.setTrdSide(jsonObject.get("trdSide").getAsInt());
                orderContent.setTrdSideStr(TradeSide.getName(jsonObject.get("trdSide").getAsInt()));
            }
            if (Objects.nonNull(jsonObject.get("orderType"))) {
                orderContent.setOrderType(jsonObject.get("orderType").getAsInt());
                orderContent.setOrderTypeStr(OrderType.getName(jsonObject.get("orderType").getAsInt()));
            }
            if (Objects.nonNull(jsonObject.get("orderStatus"))) {
                orderContent.setOrderStatus(jsonObject.get("orderStatus").getAsInt());
                orderContent.setOrderStatusStr(OrderStatus.getName(jsonObject.get("orderStatus").getAsInt()));
            }
            if (Objects.nonNull(jsonObject.get("orderID"))) {
                orderContent.setOrderID(jsonObject.get("orderID").getAsString());
            }
            if (Objects.nonNull(jsonObject.get("orderIDEx"))) {
                orderContent.setOrderIDEx(jsonObject.get("orderIDEx").getAsString());
            }
            if (Objects.nonNull(jsonObject.get("code"))) {
                orderContent.setCode(jsonObject.get("code").getAsString());
            }
            if (Objects.nonNull(jsonObject.get("name"))) {
                orderContent.setName(jsonObject.get("name").getAsString());
            }
            if (Objects.nonNull(jsonObject.get("qty"))) {
                orderContent.setQty(jsonObject.get("qty").getAsDouble());
            }
            if (Objects.nonNull(jsonObject.get("price"))) {
                orderContent.setPrice(jsonObject.get("price").getAsDouble());
            }
            if (Objects.nonNull(jsonObject.get("createTime"))) {
                orderContent.setCreateTime(jsonObject.get("createTime").getAsString());
            }
            if (Objects.nonNull(jsonObject.get("updateTime"))) {
                orderContent.setUpdateTime(jsonObject.get("updateTime").getAsString());
            }
            if (Objects.nonNull(jsonObject.get("fillQty"))) {
                orderContent.setFillQty(jsonObject.get("fillQty").getAsDouble());
            }
            if (Objects.nonNull(jsonObject.get("fillAvgPrice"))) {
                orderContent.setFillAvgPrice(jsonObject.get("fillAvgPrice").getAsDouble());
            }
            if (Objects.nonNull(jsonObject.get("lastErrMsg"))) {
                orderContent.setLastErrMsg(jsonObject.get("lastErrMsg").getAsString());
            }
            if (Objects.nonNull(jsonObject.get("secMarket"))) {
                orderContent.setSecMarket(jsonObject.get("secMarket").getAsInt());
                orderContent.setSecMarketStr(TradeSecurityMarket.getName(jsonObject.get("secMarket").getAsInt()));
            }
            if (Objects.nonNull(jsonObject.get("createTimestamp"))) {
                orderContent.setCreateTimestamp(jsonObject.get("createTimestamp").getAsDouble());
            }
            if (Objects.nonNull(jsonObject.get("updateTimestamp"))) {
                orderContent.setUpdateTimestamp(jsonObject.get("updateTimestamp").getAsDouble());
            }
            if (Objects.nonNull(jsonObject.get("remark"))) {
                orderContent.setRemark(jsonObject.get("remark").getAsString());
            }
            if (Objects.nonNull(jsonObject.get("auxPrice"))) {
                orderContent.setAuxPrice(jsonObject.get("auxPrice").getAsDouble());
            }
            if (Objects.nonNull(jsonObject.get("trailType"))) {
                orderContent.setTrailType(jsonObject.get("trailType").getAsInt());
                orderContent.setTrailTypeStr(TrailType.getName(jsonObject.get("trailType").getAsInt()));
            }
            if (Objects.nonNull(jsonObject.get("trailValue"))) {
                orderContent.setTrailValue(jsonObject.get("trailValue").getAsDouble());
            }
            if (Objects.nonNull(jsonObject.get("trailSpread"))) {
                orderContent.setTrailSpread(jsonObject.get("trailSpread").getAsDouble());
            }
            if (Objects.nonNull(jsonObject.get("currency"))) {
                orderContent.setCurrency(jsonObject.get("currency").getAsInt());
                orderContent.setCurrencyStr(Currency.getNameByCode(jsonObject.get("currency").getAsInt()));
            }
            if (Objects.nonNull(jsonObject.get("trdMarket"))) {
                orderContent.setTrdMarket(jsonObject.get("trdMarket").getAsInt());
                orderContent.setTrdMarketStr(TradeMarket.getName(jsonObject.get("trdMarket").getAsInt()));
            }
        }
        return orderContent;
    }
}
