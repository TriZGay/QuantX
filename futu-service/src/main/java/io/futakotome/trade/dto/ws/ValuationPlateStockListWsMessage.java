package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.ValuationPlateStockListContent;

public class ValuationPlateStockListWsMessage implements Message {
    private Integer market;
    private String code;
    private Integer valuationType;
    private String nextKey;
    private Integer num;
    private Integer sortType;
    private Integer sortId;
    private Integer filterMarket;
    private String filterCode;
    private ValuationPlateStockListContent content;

    public ValuationPlateStockListContent getContent() {
        return content;
    }

    public void setContent(ValuationPlateStockListContent content) {
        this.content = content;
    }

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getValuationType() {
        return valuationType;
    }

    public void setValuationType(Integer valuationType) {
        this.valuationType = valuationType;
    }

    public String getNextKey() {
        return nextKey;
    }

    public void setNextKey(String nextKey) {
        this.nextKey = nextKey;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public Integer getFilterMarket() {
        return filterMarket;
    }

    public void setFilterMarket(Integer filterMarket) {
        this.filterMarket = filterMarket;
    }

    public String getFilterCode() {
        return filterCode;
    }

    public void setFilterCode(String filterCode) {
        this.filterCode = filterCode;
    }

    @Override
    public MessageType getType() {
        return MessageType.VALUATION_P_S_LIST;
    }
}
