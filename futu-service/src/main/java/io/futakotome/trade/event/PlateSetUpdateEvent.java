package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.PlateInfoContent;

import java.util.List;

public class PlateSetUpdateEvent {
    private Integer market;
    private List<PlateInfoContent> plateInfos;

    public PlateSetUpdateEvent(Integer market, List<PlateInfoContent> plateInfos) {
        this.market = market;
        this.plateInfos = plateInfos;
    }

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public List<PlateInfoContent> getPlateInfos() {
        return plateInfos;
    }

    public void setPlateInfos(List<PlateInfoContent> plateInfos) {
        this.plateInfos = plateInfos;
    }
}
