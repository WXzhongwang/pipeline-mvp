package com.rany.ops.framework.core.channel;

import com.rany.ops.framework.core.AbstractComponent;

/**
 * 抽象channel
 *
 * @author dick
 * @description 抽象channel
 * @date 2021/12/16 10:51 上午
 * @email 18668485565@163.com
 */

public abstract class AbstractChannel<T, R> extends AbstractComponent<T, R> {

    protected AbstractChannel(String name) {
        super(name);
    }
}
