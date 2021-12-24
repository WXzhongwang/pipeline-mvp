package com.rany.ops.framework.core.source;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rany.ops.framework.kv.KvRecord;
import com.rany.ops.framework.log.Log;
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

    protected MessageConvertor convertor;

    public void setConvertor(MessageConvertor convertor) {
        this.convertor = convertor;
    }

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
    public void execute(KvRecord input) {
        try {
            // 失败异常捕捉
            super.execute(input);
        } catch (Exception ex) {
            logger.warn("source [{}] occur an exception......", this.getName(), ex);
            if (!input.has(LoggerKeys.SLS_PROCESS_PLUGINS)) {
                input.put(LoggerKeys.SLS_PROCESS_PLUGINS, new JSONArray());
            }
            JSONArray array = (JSONArray) input.get(LoggerKeys.SLS_PROCESS_PLUGINS);
            String exceptionOne = (String) array.get(array.size() - 1);
            input.put(LoggerKeys.SLS_EXCEPTION_PLUGIN, exceptionOne);

            if (slsConfig.isEnable()) {
                Log.info(input, slsConfig.getLoggerKeys());
            }
        }
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
