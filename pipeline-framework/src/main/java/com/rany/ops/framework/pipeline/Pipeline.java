package com.rany.ops.framework.pipeline;

import com.rany.ops.framework.core.LifeCycle;
import com.rany.ops.framework.core.source.Source;

import java.util.Map;

/**
 * pipeline 抽象
 * @author zhongshengwang
 * @description pipeline
 * @date 2021/12/16 11:10 上午
 * @email 18668485565@163.com
 */

public class Pipeline implements LifeCycle {

    private Source source;

    public Pipeline(Source source) {
        this.source = source;
    }

    @Override
    public boolean init(Map<String, Object> config) {
        return source.init(config);
    }

    @Override
    public boolean startUp() {
        boolean startUp = source.startUp();
        if (startUp) {
            source.start();
        }
        return true;
    }

    @Override
    public boolean shutdown() {
        return source.shutdown();
    }
}
