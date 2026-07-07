package io.futakotome.trade.dto.message;

public class MacroIndicatorCategory {
    private String categoryName;                  //分类名称(如"全部"/"就业"/"通胀"/"利率")
    private MacroIndicatorInfo indicatorList;     //该分类下的宏观指标列表

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public MacroIndicatorInfo getIndicatorList() {
        return indicatorList;
    }

    public void setIndicatorList(MacroIndicatorInfo indicatorList) {
        this.indicatorList = indicatorList;
    }
}
