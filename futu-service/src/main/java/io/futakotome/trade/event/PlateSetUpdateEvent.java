package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.PlateInfoContent;

import java.util.List;

public class PlateSetUpdateEvent {
   private  List<PlateInfoContent> plateInfos;

    public PlateSetUpdateEvent(List<PlateInfoContent> plateInfos) {
        this.plateInfos = plateInfos;
    }

    public List<PlateInfoContent> getPlateInfos() {
        return plateInfos;
    }

    public void setPlateInfos(List<PlateInfoContent> plateInfos) {
        this.plateInfos = plateInfos;
    }
}
