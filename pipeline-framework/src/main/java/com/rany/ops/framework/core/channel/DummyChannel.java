package com.rany.ops.framework.core.channel;

import com.rany.ops.framework.kv.KvRecord;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 计数 channel
 *
 * @author zhongshengwang
 * @description TODO
 * @date 2021/12/18 9:43 下午
 * @email 18668485565@163.com
 */

public class DummyChannel extends Channel {

    private AtomicLong counter = new AtomicLong(0L);

    public DummyChannel(String name) {
        super(name);
    }

    @Override
    protected KvRecord doExecute(KvRecord kvRecord) {
        long count = counter.getAndAdd(1);
        kvRecord.put("key", count);
        return kvRecord;
    }

    @Override
    public boolean init(Map<String, Object> config) {
        logger.info("dummy channel is init ...");
        logger.info("dummy channel has finished init");
        return true;
    }
}
