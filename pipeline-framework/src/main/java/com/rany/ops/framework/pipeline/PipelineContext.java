package com.rany.ops.framework.pipeline;

import com.rany.ops.framework.config.ProcessConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * pipeline管理
 *
 * @author zhongshengwang
 * @description TODO
 * @date 2021/12/20 10:52 下午
 * @email 18668485565@163.com
 */

public class PipelineContext implements IPipelineLifeCycle {

    private static final Logger logger = LoggerFactory.getLogger(PipelineContext.class);

    /**
     * 支持多渠道并行
     */
    private List<Pipeline> pipelines;

    /**
     * 处理流程配置
     */
    private ProcessConfig process;

    private ProcessManager processManager;

    public PipelineContext(ProcessConfig process) {
        this.process = process;

    }

    @Override
    public boolean prepare() {
        logger.info("multiple pipe line context start to prepare...");
        processManager = new ProcessManager();
        if (!processManager.init(process)) {
            logger.info("process manager init failed...");
            return false;
        }
        pipelines = processManager.buildChain();
        logger.info("multiple pipe line context prepare success...");
        return true;
    }

    @Override
    public boolean start() {
        if (null == pipelines || pipelines.isEmpty()) {
            logger.error("no pipeline...");
            return false;
        }
        for (Pipeline pipeline : pipelines) {
            if (!pipeline.start()) {
                logger.error("source [{}] start failed...", pipeline.source.getName());
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean stop() {
        for (Pipeline pipeline : pipelines) {
            if (!pipeline.stop()) {
                logger.error("source [{}] stop failed...", pipeline.source.getName());
                return false;
            }
        }
        return true;
    }
}
