package io.futakotome.trade.event;

import io.futakotome.trade.dto.PlateDto;
import io.futakotome.trade.dto.message.StockContent;

import java.util.List;

public class StockInPlateUpdateEvent {
    private PlateDto plateDto;
    private   List<StockContent> stockContents;

    public StockInPlateUpdateEvent(PlateDto plateDto, List<StockContent> stockContents) {
        this.plateDto = plateDto;
        this.stockContents = stockContents;
    }

    public PlateDto getPlateDto() {
        return plateDto;
    }

    public void setPlateDto(PlateDto plateDto) {
        this.plateDto = plateDto;
    }

    public List<StockContent> getStockContents() {
        return stockContents;
    }

    public void setStockContents(List<StockContent> stockContents) {
        this.stockContents = stockContents;
    }
}
