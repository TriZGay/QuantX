package io.futakotome.trade.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWebSocketServerHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractWebSocketServerHandler.class);
    public static final String NOTIFY_TAG = "notify";
    public static final String MARKET_STATE_TAG = "market_state";
    public static final String BASIC_QUOTE_TAG = "basic_quote";
    public static final String KLINE_MIN1_TAG = "kline_min1";
    public static final String KLINE_MIN3_TAG = "kline_min3";
    public static final String KLINE_MIN5_TAG = "kline_min5";
    public static final String KLINE_MIN15_TAG = "kline_min15";
    public static final String KLINE_MIN30_TAG = "kline_min30";
    public static final String KLINE_MIN60_TAG = "kline_min60";
    public static final String KLINE_HISTORY_DETAIL_TAG = "kline_history_detail";

    //用于获取url参数
    protected Map<String, String> getQueryMap(String queryStr) {
        LOGGER.info("Query String :" + queryStr);
        Map<String, String> queryMap = new HashMap<>();
        if (!StringUtils.isEmpty(queryStr)) {
            String[] queryParam = queryStr.split("&");
            Arrays.stream(queryParam).forEach(s -> {
                String[] kv = s.split("=", 2);
                String value = kv.length == 2 ? kv[1] : "";
                queryMap.put(kv[0], value);
            });
        }
        return queryMap;
    }

}
