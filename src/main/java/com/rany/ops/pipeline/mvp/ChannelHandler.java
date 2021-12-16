package com.rany.ops.pipeline.mvp;

/**
 * 注册在ChannelPipeline上每一个handler都实现这个接口
 * <p>
 * <p>
 * eg 其实还可以拓展其他方法，比如记录摘要、写日志等
 *
 * @author zhongshengwang
 * @description TODO
 * @date 2021/12/14 10:06 下午
 * @email 18668485565@163.com
 */

public interface ChannelHandler<T extends AbstractContext> {

    /**
     * 业务处理
     *
     * @param context context
     */
    void process(T context);

    /**
     * 打印摘要
     *
     * @param context
     */
    void digest(T context);

}
