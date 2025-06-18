package io.futakotome.trade.domain.code;

import java.util.HashMap;
import java.util.Map;

public enum SetPriceReminderOperation {
    UNKNOWN(0, "未知"),
    ADD(1, "新增"),
    DEL(2, "删除"),
    ENABLE(3, "启用"),
    DISABLE(4, "禁用"),
    MODIFY(5, "修改"),
    DEL_ALL(6, "删除该支股票下所有到价提醒");

    private Integer code;
    private String name;
    private static final Map<Integer, String> PRICE_REMINDER_MAP = new HashMap<>();

    static {
        for (SetPriceReminderOperation op : SetPriceReminderOperation.values()) {
            PRICE_REMINDER_MAP.put(op.getCode(), op.getName());
        }
    }

    SetPriceReminderOperation(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getName(Integer code) {
        return PRICE_REMINDER_MAP.getOrDefault(code, "未知设置到价码表值");
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
