package io.futakotome.trade.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestCount {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestCount.class);

    private Integer count = 0;
    private final Long sleepMillis;
    private final Integer limit;

    public RequestCount(Long sleepMillis, Integer limit) {
        this.sleepMillis = sleepMillis;
        this.limit = limit;
    }

    public void count() {
        this.count += 1;
        if (this.count != 0 && this.count % (limit - 1) == 0) {
            try {
                LOGGER.info("接口限制每{}(millis)最多请求{}次.sleep....", sleepMillis, limit);
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                LOGGER.error("sleep失败!", e);
            }
        }
    }
}
