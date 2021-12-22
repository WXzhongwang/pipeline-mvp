package com.rany.ops.framework.core.sink;


import com.rany.ops.framework.kv.KvRecord;

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
}
