package com.rany.ops.resource;

import com.rany.ops.common.utils.MapUtil;
import com.rany.ops.framework.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * LRUCache资源封装
 *
 * @author dick
 * @description LRUCache资源封装
 * @date 2021/12/24 9:32 下午
 * @email 18668485565@163.com
 */
public class LRUCacheResource extends Resource<LRUCache> {

    private final static Logger logger = LoggerFactory.getLogger(LRUCacheResource.class);

    private final static String KEY_CACHE_SIZE = "cacheSize";
    private final static String KEY_EXPIRED_TIME_MINUTES = "expiredTimeMinutes";

    public LRUCacheResource(String name) {
        super(name);
    }

    @Override
    protected LRUCache create(Map<String, Object> configMap) {
        if (!configMap.containsKey(KEY_CACHE_SIZE)) {
            logger.error("missing param [{}]", KEY_CACHE_SIZE);
            return null;
        }
        int cacheSize = MapUtil.getMapValue(configMap, KEY_CACHE_SIZE, Integer.class);
        int expiredTimeMinutes = configMap.containsKey(KEY_EXPIRED_TIME_MINUTES) ?
                (Integer) configMap.get(KEY_EXPIRED_TIME_MINUTES) : -1;
        return new LRUCache(cacheSize, expiredTimeMinutes);
    }

    @Override
    public void destroy() {
        res = null;
    }
}
