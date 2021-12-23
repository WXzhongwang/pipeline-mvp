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
    public void before(KvRecord input) {
        // 记录开始处理时间
        long processStartTime = System.currentTimeMillis();
        input.put(LoggerKeys.SLS_START_PROCESS_TIME_MS, processStartTime);
        if (!input.has(LoggerKeys.SLS_PROCESS_PLUGINS)) {
            input.put(LoggerKeys.SLS_PROCESS_PLUGINS, new JSONArray());
        }
        ((JSONArray) input.get(LoggerKeys.SLS_PROCESS_PLUGINS)).add(this.name);
        processTime.set(processStartTime);
        super.before(input);
    }

    @Override
    public void after(KvRecord output) {
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
