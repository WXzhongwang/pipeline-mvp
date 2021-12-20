package com.rany.ops.framework.pipeline;

import com.rany.ops.framework.resource.ResourceManager;

import java.util.List;

/**
 * pipeline管理
 *
 * @author zhongshengwang
 * @description TODO
 * @date 2021/12/20 10:52 下午
 * @email 18668485565@163.com
 */

public class PipelineContext {

    /**
     * 支持多渠道并行
     */
    private List<Pipeline> pipelines;

    /**
     * 资源管理
     */
    private ResourceManager resourceManager;

    public PipelineContext() {

    }

}
