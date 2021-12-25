package com.rany.ops.framework.core;

import com.rany.ops.framework.kv.KvRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    protected static final Logger logger = LoggerFactory.getLogger(MessageConvertor.class);

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
     * @param object
     * @return
     */
    public abstract KvRecords convert(Object object);
}
