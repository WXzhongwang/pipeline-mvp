package com.rany.ops.framework.core.channel;


import com.rany.ops.framework.kv.KvRecord;

/**
 * @author zhongshengwang
 * @description TODO
 * @date 2021/12/16 10:51 上午
 * @email 18668485565@163.com
 */

public abstract class StreamChannel extends Channel<KvRecord, KvRecord> {

    protected StreamChannel(String name) {
        super(name);
    }
}