package io.futakotome.trade.dto.message;

import java.util.List;

public class FinancialStatementsContent {
    private List<FinancialFieldInfo> structureList;
    private List<FinancialReport> reportList;
    private String nextKey;

    public List<FinancialFieldInfo> getStructureList() {
        return structureList;
    }

    public void setStructureList(List<FinancialFieldInfo> structureList) {
        this.structureList = structureList;
    }

    public List<FinancialReport> getReportList() {
        return reportList;
    }

    public void setReportList(List<FinancialReport> reportList) {
        this.reportList = reportList;
    }

    public String getNextKey() {
        return nextKey;
    }

    public void setNextKey(String nextKey) {
        this.nextKey = nextKey;
    }
}
