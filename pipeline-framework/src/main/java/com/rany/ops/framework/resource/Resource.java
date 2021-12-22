package com.rany.ops.framework.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 资源抽象
 *
 * @author dick
 * @description TODO
 * @date 2021/12/19 8:54 下午
 * @email 18668485565@163.com
 */

public abstract class Resource<T> {

    private static final Logger logger = LoggerFactory.getLogger(Resource.class);

    protected String name;
    protected T res;

    protected Resource(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public final T get() {
        return res;
    }

    public final void build(Map<String, Object> config) {
        if (res != null) {
            return;
        }
        try {
            res = create(config);
        } catch (Exception e) {
            logger.error("build resource object failed", e);
        }
    }

    public boolean validate() {
        return true;
    }

    /**
     * 创建资源
     *
     * @param config
     * @return
     */
    protected abstract T create(Map<String, Object> config);

    /**
     * 销毁资源
     */
    public abstract void destroy();
}
