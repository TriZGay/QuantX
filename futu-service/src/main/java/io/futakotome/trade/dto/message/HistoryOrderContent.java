package io.futakotome.trade.dto.message;

import java.util.List;

public class HistoryOrderContent {
    private CommonTrdHeader header;
    private List<OrderContent> orderList;

    public CommonTrdHeader getHeader() {
        return header;
    }

    public void setHeader(CommonTrdHeader header) {
        this.header = header;
    }

    public List<OrderContent> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderContent> orderList) {
        this.orderList = orderList;
    }
}
