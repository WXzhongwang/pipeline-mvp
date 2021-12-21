package com.rany.ops.framework.core.channel;


import com.rany.ops.framework.kv.KvRecord;

import java.util.Set;

/**
 * @author zhongshengwang
 * @description TODO
 * @date 2021/12/16 10:51 上午
 * @email 18668485565@163.com
 */

public abstract class Channel extends AbstractChannel<KvRecord, KvRecord> {

    protected Channel(String name) {
        super(name);
    }

    protected Set<String> nextProcessors;

    public Set<String> getNextProcessors() {
        return nextProcessors;
    }

    public void setNextProcessors(Set<String> nextProcessors) {
        this.nextProcessors = nextProcessors;
    }
}
