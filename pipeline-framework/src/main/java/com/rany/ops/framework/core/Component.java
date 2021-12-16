package com.rany.ops.framework.core;

import java.util.Collection;

/**
 * 组件接口
 *
 * @author zhongshengwang
 * @description 组件接口
 * @date 2021/12/16 10:30 上午
 * @email 18668485565@163.com
 */

public interface Component<T> extends LifeCycle {

    /**
     * 获取组件名称
     *
     * @return
     */
    String getName();

    /**
     * 获取下游组件
     *
     * @return
     */
    Collection<Component> getNext();


    /**
     * 获取父节点
     *
     * @return
     */
    Component getPrev();


    /**
     * 设置前置节点
     *
     * @param prev
     */
    void setPrev(Component prev);


    /**
     * 执行
     *
     * @param input
     */
    void execute(T input);
}