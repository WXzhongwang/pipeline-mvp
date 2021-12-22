package com.rany.ops.framework.core.channel;

import com.rany.ops.framework.kv.KvRecord;
import com.rany.ops.framework.log.Log;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
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
        kvRecord.put("count", count);
        Random random = new Random();
        int sleepMs = random.nextInt(1000);
        try {

            Log.info(kvRecord, null);
            logger.info(kvRecord.toString());
            TimeUnit.MILLISECONDS.sleep(sleepMs);
        } catch (InterruptedException e) {
        }
        return kvRecord;
    }

    @Override
    public boolean init(Map<String, Object> config) {
        logger.info("dummy channel is init ...");
        logger.info("dummy channel has finished init");
        return true;
    }
}
