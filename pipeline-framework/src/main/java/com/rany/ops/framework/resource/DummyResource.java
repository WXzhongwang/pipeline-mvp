package com.rany.ops.framework.resource;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zhongshengwang
 * @description TODO
 * @date 2021/12/22 8:44 下午
 * @email zhongshengwang@shuwen.com
 */

public class DummyResource extends Resource<AtomicLong> {

    private final static String KEY_INIT_VALUE = "initValue";

    protected DummyResource(String name) {
        super(name);
    }

    @Override
    protected AtomicLong create(Map<String, Object> config) {
        return new AtomicLong((Long) config.getOrDefault(KEY_INIT_VALUE, 0L));
    }

    @Override
    public void destroy() {
        
    }
}
