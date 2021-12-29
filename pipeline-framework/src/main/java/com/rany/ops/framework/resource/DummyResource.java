package com.rany.ops.framework.resource;

import com.rany.ops.common.utils.MapUtil;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * DummyResource
 *
 * @author dick
 * @description DummyResource
 * @date 2021/12/22 8:44 下午
 * @email 18668485565@163.com
 */

public class DummyResource extends Resource<AtomicLong> {

    private static final String KEY_INIT_VALUE = "initValue";

    public DummyResource(String name) {
        super(name);
    }

    @Override
    protected AtomicLong create(Map<String, Object> config) {
        return new AtomicLong(Long.valueOf(MapUtil.getMapValue(config, KEY_INIT_VALUE, Integer.class)));
    }

    @Override
    public void destroy() {
        //nothing to do
    }
}
