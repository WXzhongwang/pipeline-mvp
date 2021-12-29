package com.rany.ops.framework.core.channel;

import com.rany.ops.framework.annotation.Res;
import com.rany.ops.framework.kv.KvRecord;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 累加计数 channel
 *
 * @author dick
 * @description 累加计数
 * @date 2021/12/18 9:43 下午
 * @email 18668485565@163.com
 */

public class DummyChannel extends Channel {


    public DummyChannel(String name) {
        super(name);
    }

    @Res(name = "${counter}", allowNull = false)
    private AtomicLong counter;

    @Override
    protected KvRecord doExecute(KvRecord kvRecord) {
        long count = counter.getAndAdd(1);
        kvRecord.put("count", count);
        return kvRecord;
    }

    @Override
    public boolean init(Map<String, Object> config) {
        logger.info("[{}] is init ...", name);
        logger.info("[{}] has finished init", name);
        return true;
    }
}
