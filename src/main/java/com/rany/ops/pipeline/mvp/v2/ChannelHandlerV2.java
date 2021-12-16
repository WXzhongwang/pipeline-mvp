package com.rany.ops.pipeline.mvp.v2;

/**
 * @author zhongshengwang
 * @description TODO
 * @date 2021/12/14 10:38 下午
 * @email 18668485565@163.com
 */

public interface ChannelHandlerV2<U, V> {

    /**
     * 业务处理
     *
     * @param input input
     * @return
     */
    V process(U input);

}
