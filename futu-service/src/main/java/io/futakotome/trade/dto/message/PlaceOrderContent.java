package io.futakotome.trade.dto.message;

public class PlaceOrderContent {
    private CommonTrdHeader header;
    private String orderID;

    public CommonTrdHeader getHeader() {
        return header;
    }

    public void setHeader(CommonTrdHeader header) {
        this.header = header;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
}
