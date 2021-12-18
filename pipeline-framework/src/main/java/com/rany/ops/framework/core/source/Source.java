package com.rany.ops.framework.core.source;

import com.rany.ops.framework.kv.KvRecord;

/**
 * 抽象Source
 * @author zhongshengwang
 * @description 抽象Source
 * @date 2021/12/17 8:26 下午
 * @email 18668485565@163.com
 */
public abstract class Source extends AbstractSource<KvRecord, KvRecord> {

    protected Source(String name) {
        super(name);
    }
}
