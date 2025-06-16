package io.futakotome.trade.domain.code;

import java.util.HashMap;
import java.util.Map;

public enum ModifyOrderOperation {
    UNKNOWN(0, "未知"),
    //修改订单的价格、数量
    NORMAL(1, "改单"),
    //未成交订单将直接从交易所撮合队列中撤销
    CANCEL(2, "撤单"),
    //对交易所来说，「失效」的效果等同于 「撤单」。订单「失效」后，未成交订单将直接从交易所撮合队列中撤出，但订单信息（如价格和数量）会继续保留在富途服务器，您随时可以重新使它生效。
    DISABLE(3, "失效"),
    //对交易所来说，「生效」等同于下一笔新订单。订单重新「生效」后，将按照原来的价格数量重新提交到交易所，并按照价格优先、时间优先顺序重新排队。
    ENABLE(4, "生效"),
    //指对已撤单/下单失败的订单进行隐藏操作
    DELETE(5, "删除");

    private Integer code;
    private String name;
    private static final Map<Integer, String> MODIFY_ORDER_OPERATION_MAP = new HashMap<>();

    static {
        for (ModifyOrderOperation op : ModifyOrderOperation.values()) {
            MODIFY_ORDER_OPERATION_MAP.put(op.code, op.name);
        }
    }

    public static String getName(Integer code) {
        return MODIFY_ORDER_OPERATION_MAP.getOrDefault(code, "未知的操作码表值");
    }

    ModifyOrderOperation(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
