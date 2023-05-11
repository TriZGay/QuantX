package io.futakotome.trade.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestCount {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestCount.class);

    private Integer count = 0;

    public void count() {
        this.count += 1;
        if (this.count != 0 && this.count % 9 == 0) {
            try {
                LOGGER.info("接口限制每30秒最多请求10次.sleep....");
                Thread.sleep(30000L);
            } catch (InterruptedException e) {
                LOGGER.error("sleep失败!", e);
            }
        }
    }
}
