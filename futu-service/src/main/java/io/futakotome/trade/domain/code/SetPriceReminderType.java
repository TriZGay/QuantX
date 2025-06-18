package io.futakotome.trade.domain.code;

import java.util.HashMap;
import java.util.Map;

public enum SetPriceReminderType {
    UNKNOWN(0, "未知"),
    PRICE_UP(1, "价格涨到"),
    PRICE_DOWN(2, "价格跌到"),
    CHANGE_RATE_UP(3, "日涨幅超"),
    CHANGE_RATE_DOWN(4, "日跌幅超"),
    FIVE_MIN_CHANGE_RATE_UP(5, "5分钟涨幅超"),
    FIVE_MIN_CHANGE_RATE_DOWN(6, "5分钟跌幅超"),
    VOLUME_UP(7, "成交量超过"),
    TURNOVER_UP(8, "成交额超过"),
    TURNOVER_RATE_UP(9, "换手率超过"),
    BID_PRICE_UP(10, "买一价高于"),
    ASK_PRICE_DOWN(11, "卖一价低于"),
    BID_VOL_UP(12, "买一量高于"),
    ASK_VOL_UP(13, "卖一量高于"),
    THREE_MIN_CHANGE_RATE_UP(14, "3分钟涨幅超"),
    THREE_MIN_CHANGE_RATE_DOWN(15, "3分钟跌幅超");

    private Integer code;
    private String name;
    private static final Map<Integer, String> map = new HashMap<>();

    static {
        for (SetPriceReminderType setPriceReminderType : SetPriceReminderType.values()) {
            map.put(setPriceReminderType.getCode(), setPriceReminderType.getName());
        }
    }

    public static String getName(int code) {
        return map.getOrDefault(code, "未知的设置到价提醒类型码表值");
    }

    SetPriceReminderType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
