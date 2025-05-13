package io.futakotome.trade.controller.vo;

import java.util.List;

public class StockFilterMetaResponse {
    private List<AntDesignSelectOptions> sortDirs;
    private List<AntDesignSelectOptions> klTypes;
    private List<AntDesignSelectOptions> relativePositions;
    private List<AntDesignSelectOptions> baseFiltersFields;
    private List<AntDesignSelectOptions> financialFiltersFields;
    private List<AntDesignSelectOptions> financialQuarters;
    private List<AntDesignSelectOptions> accumulateFiltersFields;
    private List<AntDesignSelectOptions> indicatorFiltersFields;
    private List<AntDesignSelectOptions> patternFiltersFields;

    public List<AntDesignSelectOptions> getSortDirs() {
        return sortDirs;
    }

    public void setSortDirs(List<AntDesignSelectOptions> sortDirs) {
        this.sortDirs = sortDirs;
    }

    public List<AntDesignSelectOptions> getKlTypes() {
        return klTypes;
    }

    public void setKlTypes(List<AntDesignSelectOptions> klTypes) {
        this.klTypes = klTypes;
    }

    public List<AntDesignSelectOptions> getRelativePositions() {
        return relativePositions;
    }

    public void setRelativePositions(List<AntDesignSelectOptions> relativePositions) {
        this.relativePositions = relativePositions;
    }

    public List<AntDesignSelectOptions> getBaseFiltersFields() {
        return baseFiltersFields;
    }

    public void setBaseFiltersFields(List<AntDesignSelectOptions> baseFiltersFields) {
        this.baseFiltersFields = baseFiltersFields;
    }

    public List<AntDesignSelectOptions> getFinancialFiltersFields() {
        return financialFiltersFields;
    }

    public void setFinancialFiltersFields(List<AntDesignSelectOptions> financialFiltersFields) {
        this.financialFiltersFields = financialFiltersFields;
    }

    public List<AntDesignSelectOptions> getFinancialQuarters() {
        return financialQuarters;
    }

    public void setFinancialQuarters(List<AntDesignSelectOptions> financialQuarters) {
        this.financialQuarters = financialQuarters;
    }

    public List<AntDesignSelectOptions> getAccumulateFiltersFields() {
        return accumulateFiltersFields;
    }

    public void setAccumulateFiltersFields(List<AntDesignSelectOptions> accumulateFiltersFields) {
        this.accumulateFiltersFields = accumulateFiltersFields;
    }

    public List<AntDesignSelectOptions> getIndicatorFiltersFields() {
        return indicatorFiltersFields;
    }

    public void setIndicatorFiltersFields(List<AntDesignSelectOptions> indicatorFiltersFields) {
        this.indicatorFiltersFields = indicatorFiltersFields;
    }

    public List<AntDesignSelectOptions> getPatternFiltersFields() {
        return patternFiltersFields;
    }

    public void setPatternFiltersFields(List<AntDesignSelectOptions> patternFiltersFields) {
        this.patternFiltersFields = patternFiltersFields;
    }

    public static class AntDesignSelectOptions {
        private String label;
        private Integer value;

        public AntDesignSelectOptions(String label, Integer value) {
            this.label = label;
            this.value = value;
        }

        public AntDesignSelectOptions() {
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }
}
