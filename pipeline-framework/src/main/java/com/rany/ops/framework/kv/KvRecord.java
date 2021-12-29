package com.rany.ops.framework.kv;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rany.ops.common.json.CopyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 数据抽象, 用于pipeline中数据扭转
 *
 * @author dick
 * @description 用于pipeline中数据扭转
 * @date 2021/12/17 8:23 下午
 * @email 18668485565@163.com
 */

public class KvRecord implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(KvRecord.class);

    public KvRecord() {
        fieldMap = new HashMap<>();
    }

    private Map<String, Object> fieldMap;

    public Map<String, Object> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(Map<String, Object> fieldMap) {
        this.fieldMap = fieldMap;
    }

    public boolean has(String key) {
        if (key == null) {
            logger.warn("key is null");
            return false;
        }
        return fieldMap.containsKey(key);
    }

    public void put(String key, Object value) {
        if (key == null) {
            logger.warn("key is null");
            return;
        }
        if (value == null) {
            logger.warn("value is null");
            return;
        }
        if (fieldMap.containsKey(key)) {
            logger.debug("key[{}] has existed, overwrite it", key);
        }
        fieldMap.put(key, value);
    }

    public boolean isEmpty() {
        return fieldMap == null || fieldMap.isEmpty();
    }

    public int getFieldCnt() {
        return fieldMap == null ? 0 : fieldMap.size();
    }

    public Object get(String key) {
        if (key == null) {
            logger.warn("key is null");
            return null;
        }
        return fieldMap.getOrDefault(key, null);
    }

    public boolean remove(String key) {
        if (fieldMap.containsKey(key)) {
            fieldMap.remove(key);
            return true;
        }
        return false;
    }

    public void clear() {
        fieldMap.clear();
    }

    public Iterator<Map.Entry<String, Object>> iterator() {
        return fieldMap.entrySet().iterator();
    }

    public List<String> keys() {
        return new ArrayList<>(fieldMap.keySet());
    }

    public void copyFrom(KvRecord other) {
        for (String key : other.fieldMap.keySet()) {
            fieldMap.put(key, other.fieldMap.get(key));
        }
    }

    /**
     * 复制克隆
     * 浅拷贝 针对JSONObject和JSONArray做深拷贝
     *
     * @return 复制KVRecord
     */
    public KvRecord copy() {
        KvRecord replica = new KvRecord();
        for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof JSONObject) {
                replica.put(entry.getKey(), CopyUtils.deepCopy((JSONObject) value));
            } else if (value instanceof JSONArray) {
                replica.put(entry.getKey(), CopyUtils.deepCopy((JSONArray) value));
            } else {
                replica.put(entry.getKey(), value);
            }
        }
        return replica;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        boolean firstFlag = true;
        buffer.append("kv record[");
        for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {
            if (!firstFlag) {
                buffer.append(", ");
            } else {
                firstFlag = false;
            }
            buffer.append(entry.getKey());
            buffer.append("=");
            buffer.append(entry.getValue().toString());
        }
        buffer.append("]");
        return buffer.toString();
    }

}
