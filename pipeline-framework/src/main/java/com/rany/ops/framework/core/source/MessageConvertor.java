package com.rany.ops.framework.core.source;

import com.rany.ops.framework.kv.KvRecords;

import java.util.Map;

/**
 * 消息转换器
 *
 * @author dick
 * @description 消息转换器
 * @date 2021/12/24 5:57 下午
 * @email 18668485565@163.com
 */

public abstract class MessageConvertor {

    /**
     * 消息转换器初始化
     *
     * @param configMap
     * @return
     */
    public abstract boolean init(Map<String, Object> configMap);

    /**
     * 消息转换
     *
     * @param Message
     * @return
     */
    public abstract KvRecords convert(Object Message);
}
