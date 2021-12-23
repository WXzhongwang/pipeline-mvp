package com.rany.ops.framework.pipeline;

/**
 * @author dick
 * @description TODO
 * @date 2021/12/20 11:30 下午
 * @email 18668485565@163.com
 */

public interface IPipelineLifeCycle {

    /**
     * 准备
     *
     * @return
     */
    boolean prepare();

    /**
     * 启动
     *
     * @return
     */
    boolean open();

    /**
     * 停止
     *
     * @return
     */
    boolean close();
}
