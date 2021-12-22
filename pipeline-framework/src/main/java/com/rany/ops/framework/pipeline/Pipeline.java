package com.rany.ops.framework.pipeline;

import com.rany.ops.framework.core.source.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * pipeline 抽象
 *
 * @author dick
 * @description pipeline
 * @date 2021/12/16 11:10 上午
 * @email 18668485565@163.com
 */

public abstract class Pipeline implements IPipelineLifeCycle {

    protected static final Logger logger = LoggerFactory.getLogger(Pipeline.class);

    protected Source source;

    Pipeline(Source source) {
        this.source = source;
    }

    @Override
    public boolean prepare() {
        return true;
    }

    @Override
    public boolean start() {
        boolean startUp = source.startUp();
        if (startUp) {
            if (!source.start()) {
                logger.error("source [{}] start failed", source.getName());
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean stop() {
        return source.shutdown();
    }
}
