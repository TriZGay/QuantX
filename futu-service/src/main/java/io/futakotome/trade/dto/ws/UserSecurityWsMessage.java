package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.StockContent;

import java.util.List;

public class UserSecurityWsMessage implements Message {
    private String groupName;
    private List<StockContent> stocks;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<StockContent> getStocks() {
        return stocks;
    }

    public void setStocks(List<StockContent> stocks) {
        this.stocks = stocks;
    }

    @Override
    public MessageType getType() {
        return MessageType.USER_SECURITY;
    }
}
