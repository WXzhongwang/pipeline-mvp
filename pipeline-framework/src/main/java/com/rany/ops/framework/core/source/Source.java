package com.rany.ops.framework.core.source;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rany.ops.framework.kv.KvRecord;
import com.rany.ops.framework.log.LoggerKeys;

/**
 * 抽象Source
 *
 * @author dick
 * @description 抽象Source
 * @date 2021/12/17 8:26 下午
 * @email 18668485565@163.com
 */
public abstract class Source extends AbstractSource<KvRecord, KvRecord> {

    protected Source(String name) {
        super(name);
    }

    protected static ThreadLocal<Long> processTime = new ThreadLocal<>();
    
    @Override
    public void executeBefore(KvRecord kvRecord) {
        // 记录开始处理时间
        long processStartTime = System.currentTimeMillis();
        kvRecord.put(LoggerKeys.SLS_START_PROCESS_TIME_MS, processStartTime);
        if (!kvRecord.has(LoggerKeys.SLS_PROCESS_PLUGINS)) {
            kvRecord.put(LoggerKeys.SLS_PROCESS_PLUGINS, new JSONArray());
        }
        ((JSONArray) kvRecord.get(LoggerKeys.SLS_PROCESS_PLUGINS)).add(this.name);
        processTime.set(processStartTime);
        super.executeBefore(kvRecord);
    }

    @Override
    public void executeAfter(KvRecord output) {
        long cost = System.currentTimeMillis() - processTime.get();
        if (!output.has(LoggerKeys.SLS_PLUGIN_TIMES)) {
            output.put(LoggerKeys.SLS_PLUGIN_TIMES, new JSONObject());
        }
        Object pluginTimes = output.get(LoggerKeys.SLS_PLUGIN_TIMES);
        if (pluginTimes instanceof JSONObject) {
            ((JSONObject) pluginTimes).put(name, cost);
        }
    }
}
