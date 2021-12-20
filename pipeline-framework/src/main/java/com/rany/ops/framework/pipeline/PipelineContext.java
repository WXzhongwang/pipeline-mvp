package com.rany.ops.framework.pipeline;

import com.rany.ops.framework.config.ProcessConfig;
import com.rany.ops.framework.core.LifeCycle;
import com.rany.ops.framework.resource.ResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * pipeline管理
 *
 * @author zhongshengwang
 * @description TODO
 * @date 2021/12/20 10:52 下午
 * @email 18668485565@163.com
 */

public class PipelineContext implements LifeCycle {

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
    private List<ProcessConfig> processConfigs;

    
    public PipelineContext(List<ProcessConfig> processConfigs) {
        this.processConfigs = processConfigs;
    }

    @Override
    public boolean init(Map<String, Object> config) {
        return false;
    }

    @Override
    public boolean startUp() {
        return false;
    }

    @Override
    public boolean shutdown() {
        return false;
    }
}
