package com.rany.ops.framework.core;

import java.util.Collection;

/**
 * 组件接口
 *
 * @author dick
 * @description 组件接口
 * @date 2021/12/16 10:30 上午
 * @email 18668485565@163.com
 */

public interface Component<T, R> extends LifeCycle {

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
    Collection<Component> getPrev();


    /**
     * 添加下游节点
     *
     * @param next
     */
    void addNext(Component next);

    /**
     * 添加设置前置节点
     *
     * @param prev
     */
    void addPrev(Component prev);

    /**
     * 执行
     *
     * @param input
     */
    void execute(T input);

    /**
     * 执行完成后
     *
     * @param output 输入参数
     */
    default void after(R output) {
    }

    /**
     * 执行完成后
     *
     * @param input 输入
     */
    default void before(T input) {
    }
}
