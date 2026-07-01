package io.futakotome.trade.dto.message;

public class RiseFallRange {
    private Integer type ; //DistributionType,分布类型
    private Integer leftBorder ; //左边界值
    private Integer rightBorder ; //右边界值
    private Integer stockCount ; //区间股票数

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLeftBorder() {
        return leftBorder;
    }

    public void setLeftBorder(Integer leftBorder) {
        this.leftBorder = leftBorder;
    }

    public Integer getRightBorder() {
        return rightBorder;
    }

    public void setRightBorder(Integer rightBorder) {
        this.rightBorder = rightBorder;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }
}
