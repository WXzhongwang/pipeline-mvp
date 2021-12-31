package com.rany.ops.framework.core;

import com.rany.ops.common.json.CopyUtils;
import com.rany.ops.framework.config.SlsConfig;
import com.rany.ops.framework.monitor.Monitor;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * 组件抽象
 *
 * @author dick
 * @description 组件抽象
 * @date 2021/12/16 10:33 上午
 * @email 18668485565@163.com
 */
public abstract class AbstractComponent<T, R> implements Component<T, R> {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractComponent.class);

    protected AbstractComponent(String name) {
        this.name = name;
    }

    protected ThreadLocal<Long> processTime = new ThreadLocal<>();
    protected ThreadLocal<Long> errorCount = new ThreadLocal<>();

    protected volatile SlsConfig slsConfig;
    protected volatile Collection<Component> next = new ArrayList<>();
    protected volatile Collection<Component> prev = new ArrayList<>();
    protected volatile String name;
    protected volatile Set<String> nextProcessors;
    protected Monitor monitor;

    public Set<String> getNextProcessors() {
        return nextProcessors;
    }

    public void setNextProcessors(Set<String> nextProcessors) {
        this.nextProcessors = nextProcessors;
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<Component> getNext() {
        return next;
    }

    @Override
    public Collection<Component> getPrev() {
        return prev;
    }

    public void setSlsConfig(SlsConfig slsConfig) {
        this.slsConfig = slsConfig;
    }

    @Override
    public void execute(T input) {
        before(input);
        // 当前组件执行
        logger.info("[{}] is executing......", this.name);
        R r = doExecute(input);
        after(r);
        // 单个component执行完毕

        // 获取下游组件，并执行
        Collection<Component> downStreams = getNext();
        if (!CollectionUtils.isEmpty(downStreams)) {
            for (Component downStream : downStreams) {
                R newR = CopyUtils.deepCopy(r);
                downStream.execute(newR);
            }
        }
        logger.info("[{}] execute success......", this.name);
    }

    @Override
    public void addNext(Component next) {
        this.next.add(next);
    }

    @Override
    public void addPrev(Component prev) {
        this.prev.add(prev);
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
    public boolean start() {
        // 下游 -> 上游 依次启动
        logger.info("[{}] is ready to start......", this.name);
        Collection<Component> downStreams = getNext();
        if (!CollectionUtils.isEmpty(downStreams)) {
            downStreams.forEach(Component::start);
        }
        this.monitor.start();
        logger.info("[{}] start success......", this.name);
        return true;
    }

    /**
     * 停止
     *
     * @return
     */
    @Override
    public boolean stop() {
        // 上游 -> 下游 依次关闭
        logger.info("[{}] is ready to shutdown......", this.name);
        Collection<Component> downStreams = getNext();
        if (!CollectionUtils.isEmpty(downStreams)) {
            downStreams.forEach(Component::stop);
        }
        this.monitor.stop();
        logger.info("[{}] shutdown success......", this.name);
        return true;
    }
}
