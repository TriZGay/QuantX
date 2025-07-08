package io.futakotome.akshares.controller.vo;

import io.futakotome.akshares.dto.SHStockSummary;
import io.futakotome.akshares.dto.SZSummary;

import java.util.List;

public class SHSZTodaySummary {
    private List<SHStockSummary> shStockSummaries;
    private List<SZSummary> szSummaries;

    public List<SHStockSummary> getShStockSummaries() {
        return shStockSummaries;
    }

    public void setShStockSummaries(List<SHStockSummary> shStockSummaries) {
        this.shStockSummaries = shStockSummaries;
    }

    public List<SZSummary> getSzSummaries() {
        return szSummaries;
    }

    public void setSzSummaries(List<SZSummary> szSummaries) {
        this.szSummaries = szSummaries;
    }
}
