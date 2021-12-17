package com.rany.ops.framework.core.source;

import com.rany.ops.framework.kv.KvRecord;

/**
 * StreamSource
 * @author zhongshengwang
 * @description TODO
 * @date 2021/12/17 8:26 下午
 * @email 18668485565@163.com
 */

public abstract class StreamSource extends Source<KvRecord, KvRecord> {

    protected StreamSource(String name) {
        super(name);
    }
}
