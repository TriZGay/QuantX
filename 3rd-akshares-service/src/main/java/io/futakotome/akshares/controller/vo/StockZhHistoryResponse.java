package io.futakotome.akshares.controller.vo;

import io.futakotome.akshares.dto.StockZhHistory;

import java.util.List;

public class StockZhHistoryResponse {
    private List<StockZhHistory> histories;

    public List<StockZhHistory> getHistories() {
        return histories;
    }

    public void setHistories(List<StockZhHistory> histories) {
        this.histories = histories;
    }
}
