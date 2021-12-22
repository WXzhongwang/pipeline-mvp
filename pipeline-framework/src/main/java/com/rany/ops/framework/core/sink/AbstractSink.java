package com.rany.ops.framework.core.sink;


import com.rany.ops.framework.core.AbstractComponent;

/**
 * 抽象Sink
 *
 * @author dick
 * @description 抽象Sink
 * @date 2021/12/16 10:51 上午
 * @email 18668485565@163.com
 */

public abstract class AbstractSink<T, R> extends AbstractComponent<T, R> {

    protected AbstractSink(String name) {
        super(name);
    }
}
