package com.rany.ops.framework.core.channel;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rany.ops.framework.kv.KvRecord;
import com.rany.ops.framework.log.Log;
import com.rany.ops.framework.log.LoggerKeys;
import org.apache.commons.collections.CollectionUtils;

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

    protected static ThreadLocal<Long> processTime = new ThreadLocal<>();

    protected Set<String> nextProcessors;

    public Set<String> getNextProcessors() {
        return nextProcessors;
    }

    public void setNextProcessors(Set<String> nextProcessors) {
        this.nextProcessors = nextProcessors;
    }

    @Override
    public void before(KvRecord input) {
        long processStartTime = System.currentTimeMillis();
        ((JSONArray) input.get(LoggerKeys.SLS_PROCESS_PLUGINS)).add(this.name);
        processTime.set(processStartTime);
    }

    @Override
    public void after(KvRecord output) {
        long cost = System.currentTimeMillis() - processTime.get();
        Object pluginTimes = output.get(LoggerKeys.SLS_PLUGIN_TIMES);
        if (pluginTimes instanceof JSONObject) {
            ((JSONObject) pluginTimes).put(name, cost);
        }
        // channel 无后续channel 或 sink 执行结束，日志收口
        if (CollectionUtils.isEmpty(this.getNext())) {
            if (slsConfig.isEnable()) {
                Log.info(output, slsConfig.getLoggerKeys());
            }
        }
    }
}
