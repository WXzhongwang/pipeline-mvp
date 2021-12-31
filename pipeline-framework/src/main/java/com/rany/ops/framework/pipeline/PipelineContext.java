package com.rany.ops.framework.pipeline;

import com.rany.ops.framework.config.ProcessConfig;
import com.rany.ops.framework.config.SlsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * pipeline管理
 *
 * @author dick
 * @description pipeline管理
 * @date 2021/12/20 10:52 下午
 * @email 18668485565@163.com
 */

public class PipelineContext implements IPipelineLifeCycle {

    private static final Logger logger = LoggerFactory.getLogger(PipelineContext.class);

    /**
     * 应用程序名称
     */
    private String appName;
    /**
     * 支持多渠道并行
     */
    private List<Pipeline> pipelines;

    /**
     * 处理流程配置
     */
    private ProcessConfig process;

    /**
     * 日志跟踪配置
     */
    private SlsConfig slsConfig;

    public PipelineContext(String appName, ProcessConfig process, SlsConfig slsConfig) {
        this.appName = appName;
        this.process = process;
        this.slsConfig = slsConfig;

    }

    @Override
    public boolean prepare() {
        logger.info("multiple pipe line context is ready to prepare...");
        ProcessManager processManager = new ProcessManager();
        if (!processManager.init(appName, process, slsConfig)) {
            logger.info("process manager init failed...");
            return false;
        }
        pipelines = processManager.buildPipes();
        logger.info("multiple pipe line context prepare success...");
        return true;
    }

    @Override
    public boolean open() {
        if (null == pipelines || pipelines.isEmpty()) {
            logger.error("no pipeline...");
            return false;
        }
        for (Pipeline pipeline : pipelines) {
            // 开启管道
            if (!pipeline.open()) {
                logger.error("source [{}] open failed...", pipeline.source.getName());
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean close() {
        for (Pipeline pipeline : pipelines) {
            // 关闭管道
            if (!pipeline.close()) {
                logger.error("source [{}] stop failed...", pipeline.source.getName());
                return false;
            }
        }
        return true;
    }
}
