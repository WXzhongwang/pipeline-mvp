package com.rany.ops.framework.core.sink;


import com.rany.ops.framework.kv.KvRecord;

/**
 * StreamSink
 * @author zhongshengwang
 * @description TODO
 * @date 2021/12/16 10:51 上午
 * @email 18668485565@163.com
 */

public abstract class StreamSink extends Sink<KvRecord, KvRecord> {

    protected StreamSink(String name) {
        super(name);
    }
}
