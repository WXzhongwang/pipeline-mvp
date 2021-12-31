package com.rany.ops.framework.core.sink;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rany.ops.framework.kv.KvRecord;
import com.rany.ops.framework.log.Log;
import com.rany.ops.framework.log.LoggerKeys;
import com.rany.ops.framework.monitor.Alarm;

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
        // sink执行结束，日志收口
        if (slsConfig.isEnable()) {
            Log.info(output, slsConfig.getLoggerKeys());
        }
    }

    @Override
    public void execute(KvRecord input) {
        try {
            super.execute(input);
        } catch (Exception ex) {
            monitor.sendAlarm(ex.getMessage(), Alarm.ALERT_TYPE_ERROR, ex);
            logger.warn("sink [{}] occur an exception......", this.name, ex);
            JSONArray array = (JSONArray) input.get(LoggerKeys.SLS_PROCESS_PLUGINS);
            String exceptionOne = (String) array.get(array.size() - 1);
            input.put(LoggerKeys.SLS_EXCEPTION_PLUGIN, exceptionOne);
            if (slsConfig.isEnable()) {
                Log.info(input, slsConfig.getLoggerKeys());
            }
        }
    }
}
