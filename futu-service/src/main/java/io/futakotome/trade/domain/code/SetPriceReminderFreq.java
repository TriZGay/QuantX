package io.futakotome.trade.domain.code;

import java.util.HashMap;
import java.util.Map;

public enum SetPriceReminderFreq {
    UNKNOWN(0, "未知"),
    ALWAYS(1, "持续提醒"),
    ONCE_A_DAY(2, "每日一次"),
    ONLY_ONCE(3, "仅提醒一次");

    private Integer code;
    private String name;
    private static final Map<Integer, String> MAP = new HashMap<>();

    static {
        for (SetPriceReminderFreq e : SetPriceReminderFreq.values()) {
            MAP.put(e.code, e.name);
        }
    }

    SetPriceReminderFreq(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getName(Integer code) {
        return MAP.getOrDefault(code, "未知设置到价提醒频率码表值");
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
