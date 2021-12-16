package com.rany.ops.pipeline.mvp;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhongshengwang
 * @description TODO
 * @date 2021/12/15 5:58 下午
 * @email 18668485565@163.com
 */

public class DefaultPipelineContext extends AbstractContext {

    private Map<String, Object> fieldMap;

    public void put(String key, Object value) {
        if (fieldMap == null) {
            fieldMap = new HashMap<>();
        }
        fieldMap.put(key, value);
    }


    public Map<String, Object> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(Map<String, Object> fieldMap) {
        this.fieldMap = fieldMap;
    }

    public DefaultPipelineContext(String name) {
        super(name, false, null);
    }

    public DefaultPipelineContext(String name, boolean skip, Map<String, Object> extendInfo) {
        super(name, skip, extendInfo);
    }
}
