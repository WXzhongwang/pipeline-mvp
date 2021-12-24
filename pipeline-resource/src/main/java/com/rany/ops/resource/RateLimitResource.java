package com.rany.ops.resource;


import com.google.common.util.concurrent.RateLimiter;
import com.rany.ops.common.utils.MapUtil;
import com.rany.ops.framework.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 限速器
 * 单机限速
 *
 * @author dick
 * @description 限速器
 * @date 2021/12/24 9:32 下午
 * @email 18668485565@163.com
 */
public class RateLimitResource extends Resource<RateLimiter> {

    private static final Logger logger = LoggerFactory.getLogger(RateLimitResource.class);

    private static final String KEY_PERMITS_PER_SECOND = "permitsPerSecond";

    public RateLimitResource(String name) {
        super(name);
    }

    @Override
    protected RateLimiter create(Map<String, Object> configMap) {
        if (!configMap.containsKey(KEY_PERMITS_PER_SECOND)) {
            logger.error("missing param[{}]", KEY_PERMITS_PER_SECOND);
            return null;
        }
        Double permitsPerSecond = MapUtil.getMapValue(configMap, KEY_PERMITS_PER_SECOND, Double.class);
        if (permitsPerSecond == null) {
            return null;
        }
        return RateLimiter.create(permitsPerSecond);
    }

    @Override
    public void destroy() {
        res = null;
    }
}
