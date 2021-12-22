package com.rany.ops.framework.core.source;

import com.alibaba.fastjson.JSONArray;
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

    @Override
    public void execute(KvRecord kvRecord) {
        // 记录开始处理时间
        long processStartTime = System.currentTimeMillis();
        kvRecord.put(LoggerKeys.SLS_START_PROCESS_TIME_MS, processStartTime);
        kvRecord.put(LoggerKeys.SLS_CURRENT_PROCESS_TIME_MS, processStartTime);
        if (!kvRecord.has(LoggerKeys.SLS_PROCESS_PLUGINS)) {
            kvRecord.put(LoggerKeys.SLS_PROCESS_PLUGINS, new JSONArray());
        }
        ((JSONArray) kvRecord.get(LoggerKeys.SLS_PROCESS_PLUGINS)).add(this.name);
        super.execute(kvRecord);
    }
}
