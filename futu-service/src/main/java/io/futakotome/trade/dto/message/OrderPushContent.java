package io.futakotome.trade.dto.message;

import java.util.List;

public class OrderPushContent {
    private CommonTrdHeader header;
    private OrderContent order;

    public CommonTrdHeader getHeader() {
        return header;
    }

    public void setHeader(CommonTrdHeader header) {
        this.header = header;
    }

    public OrderContent getOrder() {
        return order;
    }

    public void setOrder(OrderContent order) {
        this.order = order;
    }
}
