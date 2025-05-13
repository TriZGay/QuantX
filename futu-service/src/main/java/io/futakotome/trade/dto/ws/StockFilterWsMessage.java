package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.CommonSecurity;
import io.futakotome.trade.dto.message.StockFilterContent;

import java.util.List;

public class StockFilterWsMessage implements Message {
    private Integer begin;
    private Integer num;
    private Integer market;
    private CommonSecurity plate;
    private List<BaseFilterWsMessage> baseFilterList;
    private List<AccumulateFilterWsMessage> accumulateFilterList;
    private List<FinancialFilterWsMessage> financialFilterList;
    private List<PatternFilterWsMessage> patternFilterList;
    private List<CustomIndicatorFilterWsMessage> customIndicatorFilterList;

    private StockFilterContent stockFilterContent;

    public List<BaseFilterWsMessage> getBaseFilterList() {
        return baseFilterList;
    }

    public void setBaseFilterList(List<BaseFilterWsMessage> baseFilterList) {
        this.baseFilterList = baseFilterList;
    }

    public List<AccumulateFilterWsMessage> getAccumulateFilterList() {
        return accumulateFilterList;
    }

    public void setAccumulateFilterList(List<AccumulateFilterWsMessage> accumulateFilterList) {
        this.accumulateFilterList = accumulateFilterList;
    }

    public List<FinancialFilterWsMessage> getFinancialFilterList() {
        return financialFilterList;
    }

    public void setFinancialFilterList(List<FinancialFilterWsMessage> financialFilterList) {
        this.financialFilterList = financialFilterList;
    }

    public List<PatternFilterWsMessage> getPatternFilterList() {
        return patternFilterList;
    }

    public void setPatternFilterList(List<PatternFilterWsMessage> patternFilterList) {
        this.patternFilterList = patternFilterList;
    }

    public List<CustomIndicatorFilterWsMessage> getCustomIndicatorFilterList() {
        return customIndicatorFilterList;
    }

    public void setCustomIndicatorFilterList(List<CustomIndicatorFilterWsMessage> customIndicatorFilterList) {
        this.customIndicatorFilterList = customIndicatorFilterList;
    }

    public CommonSecurity getPlate() {
        return plate;
    }

    public void setPlate(CommonSecurity plate) {
        this.plate = plate;
    }

    public StockFilterContent getStockFilterContent() {
        return stockFilterContent;
    }

    public void setStockFilterContent(StockFilterContent stockFilterContent) {
        this.stockFilterContent = stockFilterContent;
    }

    public Integer getBegin() {
        return begin;
    }

    public void setBegin(Integer begin) {
        this.begin = begin;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public static class CustomIndicatorFilterWsMessage {
        private Integer firstFieldName;
        private Integer secondFieldName;
        private Integer relativePosition;
        private Double fieldValue;
        private Integer klType;
        private boolean isNoFilter;
        private List<Integer> firstFieldParaList;
        private List<Integer> secondFieldParaList;
        private Integer consecutivePeriod;

        public Integer getFirstFieldName() {
            return firstFieldName;
        }

        public void setFirstFieldName(Integer firstFieldName) {
            this.firstFieldName = firstFieldName;
        }

        public Integer getSecondFieldName() {
            return secondFieldName;
        }

        public void setSecondFieldName(Integer secondFieldName) {
            this.secondFieldName = secondFieldName;
        }

        public Integer getRelativePosition() {
            return relativePosition;
        }

        public void setRelativePosition(Integer relativePosition) {
            this.relativePosition = relativePosition;
        }

        public Double getFieldValue() {
            return fieldValue;
        }

        public void setFieldValue(Double fieldValue) {
            this.fieldValue = fieldValue;
        }

        public Integer getKlType() {
            return klType;
        }

        public void setKlType(Integer klType) {
            this.klType = klType;
        }

        public boolean isNoFilter() {
            return isNoFilter;
        }

        public void setNoFilter(boolean noFilter) {
            isNoFilter = noFilter;
        }

        public List<Integer> getFirstFieldParaList() {
            return firstFieldParaList;
        }

        public void setFirstFieldParaList(List<Integer> firstFieldParaList) {
            this.firstFieldParaList = firstFieldParaList;
        }

        public List<Integer> getSecondFieldParaList() {
            return secondFieldParaList;
        }

        public void setSecondFieldParaList(List<Integer> secondFieldParaList) {
            this.secondFieldParaList = secondFieldParaList;
        }

        public Integer getConsecutivePeriod() {
            return consecutivePeriod;
        }

        public void setConsecutivePeriod(Integer consecutivePeriod) {
            this.consecutivePeriod = consecutivePeriod;
        }
    }

    public static class PatternFilterWsMessage {
        private Integer fieldName;
        private Integer klType;
        private boolean isNoFilter;
        private Integer consecutivePeriod;

        public Integer getFieldName() {
            return fieldName;
        }

        public void setFieldName(Integer fieldName) {
            this.fieldName = fieldName;
        }

        public Integer getKlType() {
            return klType;
        }

        public void setKlType(Integer klType) {
            this.klType = klType;
        }

        public boolean isNoFilter() {
            return isNoFilter;
        }

        public void setNoFilter(boolean noFilter) {
            isNoFilter = noFilter;
        }

        public Integer getConsecutivePeriod() {
            return consecutivePeriod;
        }

        public void setConsecutivePeriod(Integer consecutivePeriod) {
            this.consecutivePeriod = consecutivePeriod;
        }
    }

    public static class FinancialFilterWsMessage {
        private Integer fieldName;
        private Double filterMin;
        private Double filterMax;
        private boolean isNoFilter;
        private Integer sortDir;
        private Integer quarter;

        public Integer getFieldName() {
            return fieldName;
        }

        public void setFieldName(Integer fieldName) {
            this.fieldName = fieldName;
        }

        public Double getFilterMin() {
            return filterMin;
        }

        public void setFilterMin(Double filterMin) {
            this.filterMin = filterMin;
        }

        public Double getFilterMax() {
            return filterMax;
        }

        public void setFilterMax(Double filterMax) {
            this.filterMax = filterMax;
        }

        public boolean isNoFilter() {
            return isNoFilter;
        }

        public void setNoFilter(boolean noFilter) {
            isNoFilter = noFilter;
        }

        public Integer getSortDir() {
            return sortDir;
        }

        public void setSortDir(Integer sortDir) {
            this.sortDir = sortDir;
        }

        public Integer getQuarter() {
            return quarter;
        }

        public void setQuarter(Integer quarter) {
            this.quarter = quarter;
        }
    }

    public static class AccumulateFilterWsMessage {
        private Integer fieldName;
        private Double filterMin;
        private Double filterMax;
        private boolean isNoFilter;
        private Integer sortDir;
        private Integer days;

        public Integer getFieldName() {
            return fieldName;
        }

        public void setFieldName(Integer fieldName) {
            this.fieldName = fieldName;
        }

        public Double getFilterMin() {
            return filterMin;
        }

        public void setFilterMin(Double filterMin) {
            this.filterMin = filterMin;
        }

        public Double getFilterMax() {
            return filterMax;
        }

        public void setFilterMax(Double filterMax) {
            this.filterMax = filterMax;
        }

        public boolean isNoFilter() {
            return isNoFilter;
        }

        public void setNoFilter(boolean noFilter) {
            isNoFilter = noFilter;
        }

        public Integer getSortDir() {
            return sortDir;
        }

        public void setSortDir(Integer sortDir) {
            this.sortDir = sortDir;
        }

        public Integer getDays() {
            return days;
        }

        public void setDays(Integer days) {
            this.days = days;
        }
    }

    public static class BaseFilterWsMessage {
        private Integer fieldName;
        private Double filterMin;
        private Double filterMax;
        private boolean isNoFilter;
        private Integer sortDir;

        public Integer getFieldName() {
            return fieldName;
        }

        public void setFieldName(Integer fieldName) {
            this.fieldName = fieldName;
        }

        public Double getFilterMin() {
            return filterMin;
        }

        public void setFilterMin(Double filterMin) {
            this.filterMin = filterMin;
        }

        public Double getFilterMax() {
            return filterMax;
        }

        public void setFilterMax(Double filterMax) {
            this.filterMax = filterMax;
        }

        public boolean isNoFilter() {
            return isNoFilter;
        }

        public void setNoFilter(boolean noFilter) {
            isNoFilter = noFilter;
        }

        public Integer getSortDir() {
            return sortDir;
        }

        public void setSortDir(Integer sortDir) {
            this.sortDir = sortDir;
        }
    }


    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }


    @Override
    public MessageType getType() {
        return MessageType.STOCK_FILTER;
    }
}
