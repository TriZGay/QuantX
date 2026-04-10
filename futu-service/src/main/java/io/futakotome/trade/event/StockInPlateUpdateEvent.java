package io.futakotome.trade.event;

import io.futakotome.trade.dto.PlateDto;
import io.futakotome.trade.dto.StockDto;

import java.util.List;

public class StockInPlateUpdateEvent {
    private PlateDto plateDto;
    private List<StockDto> stockDtos;

    public StockInPlateUpdateEvent(PlateDto plateDto, List<StockDto> stockDtos) {
        this.plateDto = plateDto;
        this.stockDtos = stockDtos;
    }

    public PlateDto getPlateDto() {
        return plateDto;
    }

    public void setPlateDto(PlateDto plateDto) {
        this.plateDto = plateDto;
    }

    public List<StockDto> getStockDtos() {
        return stockDtos;
    }

    public void setStockDtos(List<StockDto> stockDtos) {
        this.stockDtos = stockDtos;
    }
}
