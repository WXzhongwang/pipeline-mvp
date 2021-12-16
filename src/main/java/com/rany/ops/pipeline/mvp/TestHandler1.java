package com.rany.ops.pipeline.mvp;

import java.util.Map;

/**
 * @author zhongshengwang
 * @description TODO
 * @date 2021/12/15 5:49 下午
 * @email 18668485565@163.com
 */

public class TestHandler1 implements ChannelHandler<DefaultPipelineContext> {

    public TestHandler1() {

    }

    @Override
    public void process(DefaultPipelineContext context) {
        Map<String, Object> fieldMap = context.getFieldMap();
        System.out.println(fieldMap.size());
    }

    @Override
    public void digest(DefaultPipelineContext context) {
        System.out.println("digest");
        System.out.println(context.getName());
    }
}
