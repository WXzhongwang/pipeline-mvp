package com.rany.ops.pipeline.mvp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhongshengwang
 * @description TODO
 * @date 2021/12/15 10:43 下午
 * @email 18668485565@163.com
 */

public class PipelineTests {

    private final static Logger logger = LoggerFactory.getLogger(PipelineTests.class);

    public static void main(String[] args) {
        logger.info("start pipeline");

        ChannelPipeline channelPipeline = new ChannelPipeline<DefaultPipelineContext>("pipe");
        TestHandler1 handler1 = new TestHandler1();
        channelPipeline.addLast("handler1", handler1);
        channelPipeline.addLast("handler2", new TestHandler1());
        DefaultPipelineContext context = new DefaultPipelineContext("context");
        context.put("k1", "v1");
        channelPipeline.invokePipeline(context);
    }
}
