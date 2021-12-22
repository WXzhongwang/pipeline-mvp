package com.rany.ops.framework.core.channel;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rany.ops.framework.kv.KvRecord;
import com.rany.ops.framework.log.LoggerKeys;

import java.util.Set;

/**
 * @author dick
 * @description TODO
 * @date 2021/12/16 10:51 上午
 * @email 18668485565@163.com
 */

public abstract class Channel extends AbstractChannel<KvRecord, KvRecord> {

    protected Channel(String name) {
        super(name);
    }

    protected Set<String> nextProcessors;

    public Set<String> getNextProcessors() {
        return nextProcessors;
    }

    public void setNextProcessors(Set<String> nextProcessors) {
        this.nextProcessors = nextProcessors;
    }

    @Override
    public void execute(KvRecord kvRecord) {
        String name = this.getPrev().getName();
        long processTime = System.currentTimeMillis();
        long prevProcessTime = (long) kvRecord.get(LoggerKeys.SLS_CURRENT_PROCESS_TIME_MS);
        long cost = processTime - prevProcessTime;
        // 记录上一流程处理的耗时
        if (!kvRecord.has(LoggerKeys.SLS_PLUGIN_TIMES)) {
            kvRecord.put(LoggerKeys.SLS_PLUGIN_TIMES, new JSONObject());
        }
        Object pluginTimes = kvRecord.get(LoggerKeys.SLS_PLUGIN_TIMES);
        if (pluginTimes instanceof JSONObject) {
            ((JSONObject) pluginTimes).put(name, cost);
        }
        ((JSONArray) kvRecord.get(LoggerKeys.SLS_PROCESS_PLUGINS)).add(this.name);
        kvRecord.put(LoggerKeys.SLS_CURRENT_PROCESS_TIME_MS, processTime);
        super.execute(kvRecord);
    }
}
