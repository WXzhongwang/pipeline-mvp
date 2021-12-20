package com.rany.ops.framework.core;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 组件抽象
 *
 * @author zhongshengwang
 * @description 组件抽象
 * @date 2021/12/16 10:33 上午
 * @email 18668485565@163.com
 */
public abstract class AbstractComponent<T, R> implements Component<T> {

    protected final static Logger logger = LoggerFactory.getLogger(AbstractComponent.class);

    private volatile Collection<Component> next = new ArrayList<>();

    private volatile Component prev;

    private volatile String name;

    @Override
    public String getName() {
        return name;
    }

    protected AbstractComponent(String name) {
        this.name = name;
    }

    @Override
    public Collection<Component> getNext() {
        return next;
    }

    @Override
    public Component getPrev() {
        return prev;
    }

    @Override
    public void setPrev(Component prev) {
        this.prev = prev;
    }

    @Override
    public void execute(T o) {
        // 当前组件执行
        logger.info("[{}] is executing......", this.getName());
        R r = doExecute(o);
        // 获取下游组件，并执行
        Collection<Component> downStreams = getNext();
        if (!CollectionUtils.isEmpty(downStreams)) {
            downStreams.forEach(c -> c.execute(r));
        }
        logger.info("[{}] execute success......", this.getName());
    }


    @Override
    public void addNext(Component next) {
        next.addNext(next);
    }

    /**
     * 具体组件执行处理
     *
     * @param o 传入的数据
     * @return
     */
    protected abstract R doExecute(T o);

    /**
     * 启动
     *
     * @return
     */
    @Override
    public boolean startUp() {
        // 下游 -> 上游 依次启动
        logger.info("[{}] is ready to start......", this.getName());
        Collection<Component> downStreams = getNext();
        if (!CollectionUtils.isEmpty(downStreams)) {
            downStreams.forEach(Component::startUp);
        }
        logger.info("[{}] start success......", this.getName());
        return true;
    }

    /**
     * 停止
     *
     * @return
     */
    @Override
    public boolean shutdown() {
        // 上游 -> 下游 依次关闭
        logger.info("[{}] is ready to shutdown......", this.getName());
        Collection<Component> downStreams = getNext();
        if (!CollectionUtils.isEmpty(downStreams)) {
            downStreams.forEach(Component::shutdown);
        }
        logger.info("[{}] shutdown success......", this.getName());
        return true;
    }
}
