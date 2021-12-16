package com.rany.ops.pipeline.mvp;

import java.util.Map;

/**
 * @author zhongshengwang
 * @description TODO
 * @date 2021/12/15 10:55 下午
 * @email zhongshengwang@shuwen.com
 */

public abstract class KvRecord {

    protected Map<String, Object> fieldMap;

    public KvRecord() {
    }

    public Map<String, Object> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(Map<String, Object> fieldMap) {
        this.fieldMap = fieldMap;
    }
}
