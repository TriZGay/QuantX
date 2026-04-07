package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.IpoData;

import java.util.List;

public class GetIpoWsMessage implements Message {
    private List<IpoData> ipoList;
    private Integer market;

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public List<IpoData> getIpoList() {
        return ipoList;
    }

    public void setIpoList(List<IpoData> ipoList) {
        this.ipoList = ipoList;
    }

    @Override
    public MessageType getType() {
        return MessageType.IPO;
    }
}
