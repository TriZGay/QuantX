package io.futakotome.trade.controller.vo;

import java.util.List;

public class TradeMetaResponse {
    private List<AntDesignSelectOptions> tradeSides;
    private List<AntDesignSelectOptions> trailTypes;
    private List<AntDesignSelectOptions> timeInForces;
    private List<AntDesignSelectOptions> tradeSecMarkets;
    private List<AntDesignSelectOptions> orderTypes;
    private List<AntDesignSelectOptions> marketTypes;
    private List<AntDesignSelectOptions> modifyOrderOps;
    private List<AntDesignSelectOptions> setPriceReminderOps;

    public List<AntDesignSelectOptions> getSetPriceReminderOps() {
        return setPriceReminderOps;
    }

    public void setSetPriceReminderOps(List<AntDesignSelectOptions> setPriceReminderOps) {
        this.setPriceReminderOps = setPriceReminderOps;
    }

    public List<AntDesignSelectOptions> getModifyOrderOps() {
        return modifyOrderOps;
    }

    public void setModifyOrderOps(List<AntDesignSelectOptions> modifyOrderOps) {
        this.modifyOrderOps = modifyOrderOps;
    }

    public List<AntDesignSelectOptions> getMarketTypes() {
        return marketTypes;
    }

    public void setMarketTypes(List<AntDesignSelectOptions> marketTypes) {
        this.marketTypes = marketTypes;
    }

    public List<AntDesignSelectOptions> getTradeSides() {
        return tradeSides;
    }

    public void setTradeSides(List<AntDesignSelectOptions> tradeSides) {
        this.tradeSides = tradeSides;
    }

    public List<AntDesignSelectOptions> getTrailTypes() {
        return trailTypes;
    }

    public void setTrailTypes(List<AntDesignSelectOptions> trailTypes) {
        this.trailTypes = trailTypes;
    }

    public List<AntDesignSelectOptions> getTimeInForces() {
        return timeInForces;
    }

    public void setTimeInForces(List<AntDesignSelectOptions> timeInForces) {
        this.timeInForces = timeInForces;
    }

    public List<AntDesignSelectOptions> getTradeSecMarkets() {
        return tradeSecMarkets;
    }

    public void setTradeSecMarkets(List<AntDesignSelectOptions> tradeSecMarkets) {
        this.tradeSecMarkets = tradeSecMarkets;
    }

    public List<AntDesignSelectOptions> getOrderTypes() {
        return orderTypes;
    }

    public void setOrderTypes(List<AntDesignSelectOptions> orderTypes) {
        this.orderTypes = orderTypes;
    }
}
