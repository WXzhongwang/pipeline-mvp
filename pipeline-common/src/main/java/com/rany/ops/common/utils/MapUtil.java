package com.rany.ops.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * MapUtil
 *
 * @author dick
 */
public class MapUtil {

    private MapUtil() {
    }

    private static final Logger logger = LoggerFactory.getLogger(MapUtil.class);

    /**
     * 用replaceMap内容替换targetMap
     *
     * @param targetMap
     * @param replaceMap
     */
    public static <K, V> void safeReplace(Map<K, V> targetMap, Map<K, V> replaceMap) {
        if (targetMap == null || replaceMap == null) {
            return;
        }
        targetMap.putAll(replaceMap);
        List<K> keys = new ArrayList<>(targetMap.keySet());
        for (K key : keys) {
            if (replaceMap.containsKey(key)) {
                continue;
            }
            targetMap.remove(key);
        }
    }

    /**
     * 获取map指定key对应值，值必须符合指定class类型
     *
     * @param mapObject     map对象
     * @param key           获取key
     * @param expectedClass 期望class类型
     * @return 如果成功值对象，否则返回null
     */
    public static <T> T getMapValue(Map<String, Object> mapObject, String key, Class<T> expectedClass) {
        if (!mapObject.containsKey(key)) {
            logger.error("map key[{}] is not found", key);
            return null;
        }
        Object v = null;
        try {
            v = mapObject.get(key);
            return expectedClass.cast(v);
        } catch (Exception t) {
            logger.error("invalid value[{}] for map key[{}], expected type[{}]",
                    v == null ? "null" : v.getClass().getName(), key, expectedClass.getName());
            return null;
        }
    }
}
