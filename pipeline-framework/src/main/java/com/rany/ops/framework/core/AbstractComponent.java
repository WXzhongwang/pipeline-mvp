package com.rany.ops.framework.core;

import com.rany.ops.common.json.CopyUtils;
import com.rany.ops.framework.config.SlsConfig;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 组件抽象
 *
 * @author dick
 * @description 组件抽象
 * @date 2021/12/16 10:33 上午
 * @email 18668485565@163.com
 */
public abstract class AbstractComponent<T, R> implements Component<T, R> {

    protected final static Logger logger = LoggerFactory.getLogger(AbstractComponent.class);

    protected volatile SlsConfig slsConfig;

    private volatile Collection<Component> next = new ArrayList<>();

    private volatile Component prev;

    protected volatile String name;

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

    public void setSlsConfig(SlsConfig slsConfig) {
        this.slsConfig = slsConfig;
    }

    @Override
    public void execute(T input) {
        executeBefore(input);
        // 当前组件执行
        logger.info("[{}] is executing......", this.getName());
        R r = doExecute(input);
        executeAfter(r);
        // 单个component执行完毕

        // 获取下游组件，并执行
        Collection<Component> downStreams = getNext();
        if (!CollectionUtils.isEmpty(downStreams)) {
            for (Component downStream : downStreams) {
                R newR = CopyUtils.deepCopy(r);
                downStream.execute(newR);
            }
        }
        logger.info("[{}] execute success......", this.getName());
    }

    @Override
    public void addNext(Component next) {
        this.next.add(next);
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
