package com.rany.ops.framework.core.source;

import com.rany.ops.framework.core.AbstractComponent;

/**
 * 抽象Source
 * @author zhongshengwang
 * @description 抽象Source
 * @date 2021/12/16 10:51 上午
 * @email 18668485565@163.com
 */

public abstract class AbstractSource<T, R> extends AbstractComponent<T, R> implements Bootable {

    protected AbstractSource(String name) {
        super(name);
    }
}
