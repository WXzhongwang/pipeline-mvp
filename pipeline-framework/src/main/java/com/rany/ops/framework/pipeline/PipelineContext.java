package com.rany.ops.framework.pipeline;

import com.rany.ops.framework.config.ProcessConfig;
import com.rany.ops.framework.resource.ResourceManager;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

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
     * 资源管理
     */
    private ResourceManager resourceManager;

    /**
     * 处理流程配置
     */
    private ProcessConfig process;

    public PipelineContext(ProcessConfig process) {
        this.process = process;
    }

    @Override
    public boolean prepare() {
        logger.info("multiple pipe line context start to prepare...");
        if (Objects.nonNull(process)) {
            if (CollectionUtils.isEmpty(process.getSources())) {
                logger.warn("no source configured");
                return false;
            }
        }
        logger.info("multiple pipe line context prepare success...");
        return true;
    }

    @Override
    public boolean start() {
        return true;
    }

    @Override
    public boolean stop() {
        return true;
    }
}
