package com.rany.ops.framework.core.sink;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rany.ops.framework.kv.KvRecord;
import com.rany.ops.framework.log.Log;
import com.rany.ops.framework.log.LoggerKeys;

/**
 * 抽象Sink
 *
 * @author dick
 * @description 抽象Sink
 * @date 2021/12/16 10:51 上午
 * @email 18668485565@163.com
 */

public abstract class Sink extends AbstractSink<KvRecord, KvRecord> {

    protected Sink(String name) {
        super(name);
    }

    protected static ThreadLocal<Long> processTime = new ThreadLocal<>();

    @Override
    public void executeBefore(KvRecord kvRecord) {
        long processStartTime = System.currentTimeMillis();
        ((JSONArray) kvRecord.get(LoggerKeys.SLS_PROCESS_PLUGINS)).add(this.name);
        processTime.set(processStartTime);
        super.executeBefore(kvRecord);
    }

    @Override
    public void executeAfter(KvRecord output) {
        long cost = System.currentTimeMillis() - processTime.get();
        Object pluginTimes = output.get(LoggerKeys.SLS_PLUGIN_TIMES);
        if (pluginTimes instanceof JSONObject) {
            ((JSONObject) pluginTimes).put(name, cost);
        }
        // sink执行结束，日志收口
        if (slsConfig.isEnable()) {
            Log.info(output, slsConfig.getLoggerKeys());
        }
    }
}
