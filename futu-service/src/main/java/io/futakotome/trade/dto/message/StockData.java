package io.futakotome.trade.dto.message;

import java.util.List;

public class StockData {
    private CommonSecurity security;
    private String name;
    private List<BaseData> baseDataList;
    private List<AccumulateData> accumulateDataList;
    private List<FinancialData> financialDataList;
    private List<CustomIndicatorData> customIndicatorDataList;

    public CommonSecurity getSecurity() {
        return security;
    }

    public void setSecurity(CommonSecurity security) {
        this.security = security;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BaseData> getBaseDataList() {
        return baseDataList;
    }

    public void setBaseDataList(List<BaseData> baseDataList) {
        this.baseDataList = baseDataList;
    }

    public List<AccumulateData> getAccumulateDataList() {
        return accumulateDataList;
    }

    public void setAccumulateDataList(List<AccumulateData> accumulateDataList) {
        this.accumulateDataList = accumulateDataList;
    }

    public List<FinancialData> getFinancialDataList() {
        return financialDataList;
    }

    public void setFinancialDataList(List<FinancialData> financialDataList) {
        this.financialDataList = financialDataList;
    }

    public List<CustomIndicatorData> getCustomIndicatorDataList() {
        return customIndicatorDataList;
    }

    public void setCustomIndicatorDataList(List<CustomIndicatorData> customIndicatorDataList) {
        this.customIndicatorDataList = customIndicatorDataList;
    }
}
