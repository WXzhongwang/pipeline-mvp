package com.rany.ops.resource;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 基于Guava Cache的LRU cache实现
 *
 * @author dick
 * @description 限速器
 * @date 2021/12/24 9:32 下午
 * @email 18668485565@163.com
 */
public class LRUCache {

    private static final Logger logger = LoggerFactory.getLogger(LRUCache.class);

    private static final int DEFAULT_CACHE_SIZE = 100;

    private Cache<String, Object> cache;
    private RemovalListener<String, Object> removalListener;

    public LRUCache(int cacheSize, int expiredTimeMinutes) {
        removalListener = removal -> logger.info("cache data[{}->{}] is evicted",
                removal.getKey(), removal.getValue().toString());
        cacheSize = cacheSize <= 0 ? DEFAULT_CACHE_SIZE : cacheSize;
        if (expiredTimeMinutes > 0) {
            cache = CacheBuilder.newBuilder().maximumSize(cacheSize).
                    expireAfterAccess(expiredTimeMinutes, TimeUnit.MINUTES).
                    removalListener(removalListener).build();
        } else {
            cache = CacheBuilder.newBuilder().maximumSize(cacheSize).
                    removalListener(removalListener).build();
        }
    }

    /**
     * 根据key获取数据
     *
     * @param key key
     * @return 如果存在返回数据，否则返回null
     */
    public Object get(String key) {
        if (key == null) {
            logger.warn("key[null] is not allowed");
            return null;
        }
        return cache.getIfPresent(key);
    }

    /**
     * 插入数据
     *
     * @param key   key
     * @param value 数据
     */
    public void put(String key, Object value) {
        if (key == null) {
            logger.error("key[null] is not allowed");
            return;
        }
        cache.put(key, value);
    }

    /**
     * 驱逐数据
     *
     * @param key key
     */
    public void evict(String key) {
        if (key == null) {
            logger.error("key [null] is not allowed");
            return;
        }
        cache.invalidate(key);
    }

    /**
     * 获取缓存大小
     *
     * @return 缓存大小
     */
    public long size() {
        return cache == null ? 0L : cache.size();
    }
}
