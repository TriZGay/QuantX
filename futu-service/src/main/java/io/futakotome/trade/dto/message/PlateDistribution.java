package io.futakotome.trade.dto.message;

import java.util.List;

public class PlateDistribution {
    private CommonSecurity plate; // 所属板块
    private String plateName; // 所属板块名称
    private Double plateAverageValue; // 板块估值均值
    private Integer plateRanking; // 该股票估值在板块中的排名
    private Integer plateStockItemCount; // 板块个股总数
    private List<PlateStockItem> stockItems; // 板块成分股估值明细

    public CommonSecurity getPlate() {
        return plate;
    }

    public void setPlate(CommonSecurity plate) {
        this.plate = plate;
    }

    public String getPlateName() {
        return plateName;
    }

    public void setPlateName(String plateName) {
        this.plateName = plateName;
    }

    public Double getPlateAverageValue() {
        return plateAverageValue;
    }

    public void setPlateAverageValue(Double plateAverageValue) {
        this.plateAverageValue = plateAverageValue;
    }

    public Integer getPlateRanking() {
        return plateRanking;
    }

    public void setPlateRanking(Integer plateRanking) {
        this.plateRanking = plateRanking;
    }

    public Integer getPlateStockItemCount() {
        return plateStockItemCount;
    }

    public void setPlateStockItemCount(Integer plateStockItemCount) {
        this.plateStockItemCount = plateStockItemCount;
    }

    public List<PlateStockItem> getStockItems() {
        return stockItems;
    }

    public void setStockItems(List<PlateStockItem> stockItems) {
        this.stockItems = stockItems;
    }
}
