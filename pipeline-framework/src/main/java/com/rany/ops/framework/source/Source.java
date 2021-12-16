package com.rany.ops.framework.source;

import com.rany.ops.framework.core.AbstractComponent;

/**
 * @author zhongshengwang
 * @description TODO
 * @date 2021/12/16 10:51 上午
 * @email 18668485565@163.com
 */

public abstract class Source<T, R> extends AbstractComponent<T, R> implements Bootable {

    protected Source(String name) {
        super(name);
    }
}
