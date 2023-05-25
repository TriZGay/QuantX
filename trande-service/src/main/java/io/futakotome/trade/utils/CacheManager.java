package io.futakotome.trade.utils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public final class CacheManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheManager.class);

    private static final Long GUAVA_CACHE_SIZE = 100000L;

    private static final Long GUAVA_CACHE_DAY = 10L;

    private static LoadingCache<String, Object> GLOBAL_CACHE = null;

    static {
        try {
            GLOBAL_CACHE = loadCache(new CacheLoader<>() {
                @Override
                public Object load(String s) throws Exception {
                    return "";
                }
            });
        } catch (Exception e) {
            LOGGER.error("初始化缓存失败", e);
        }
    }

    private static LoadingCache<String, Object> loadCache(CacheLoader<String, Object> cacheLoader) {
        return CacheBuilder.newBuilder()
                .maximumSize(GUAVA_CACHE_SIZE)
                .expireAfterAccess(GUAVA_CACHE_DAY, TimeUnit.DAYS)
                .removalListener(removalNotification -> LOGGER.info("缓存被清除:" + removalNotification.getCause().name()))
                .build(cacheLoader);
    }

    public static void put(String key, Object value) {
        try {
            GLOBAL_CACHE.put(key, value);
        } catch (Exception e) {
            LOGGER.error("设置缓存出错", e);
        }
    }

    public static Object get(String key) {
        Object token = "";
        try {
            token = GLOBAL_CACHE.get(key);
        } catch (Exception e) {
            LOGGER.error("获取缓存出错", e);
        }
        return token;
    }
}
