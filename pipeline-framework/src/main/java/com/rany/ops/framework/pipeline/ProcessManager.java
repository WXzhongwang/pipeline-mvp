package com.rany.ops.framework.pipeline;

import com.rany.ops.common.reflection.ReflectClass;
import com.rany.ops.common.reflection.ReflectUtil;
import com.rany.ops.framework.config.ProcessConfig;
import com.rany.ops.framework.config.ProcessorConfig;
import com.rany.ops.framework.config.SlsConfig;
import com.rany.ops.framework.core.AbstractComponent;
import com.rany.ops.framework.core.channel.Channel;
import com.rany.ops.framework.core.sink.Sink;
import com.rany.ops.framework.core.source.Source;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author dick
 * @description TODO
 * @date 2021/12/21 10:00 下午
 * @email 18668485565@163.com
 */
public class ProcessManager {

    private static final Logger logger = LoggerFactory.getLogger(ProcessManager.class);

    private Map<String, Source> sourceMap;
    private Map<String, Channel> channelMap;
    private Map<String, Sink> sinkMap;

    private SlsConfig slsConfig;

    public ProcessManager() {
        sourceMap = new HashMap<>();
        channelMap = new HashMap<>();
        sinkMap = new HashMap<>();
    }

    public boolean init(final ProcessConfig process, final SlsConfig slsConfig) {
        logger.info("processor manager is init ...");
        if (Objects.nonNull(process)) {
            if (CollectionUtils.isEmpty(process.getSources())) {
                logger.error("no source configured");
                return false;
            }
        }
        if (Objects.isNull(slsConfig)) {
            logger.error("no sls config");
            return false;
        }
        this.slsConfig = slsConfig;
        List<ProcessorConfig> sources = process.getSources();
        List<ProcessorConfig> channels = process.getChannels();
        List<ProcessorConfig> sinks = process.getSinks();

        if (!initSink(sinks)) {
            logger.error("process manager init sinks failed");
            return false;
        }
        if (!initChannel(channels)) {
            logger.error("process manager init channels failed");
            return false;
        }
        if (!initSource(sources)) {
            logger.error("process manager init sources failed");
            return false;
        }
        logger.info("processor manager init success ...");
        return true;
    }

    private boolean initSink(List<ProcessorConfig> sinks) {
        for (ProcessorConfig sinkConfig : sinks) {
            String sinkName = sinkConfig.getName();
            if (!sinkConfig.validate()) {
                logger.error("sink [{}] validate failed", sinkName);
                return false;
            }
            if (sourceMap.containsKey(sinkName)) {
                logger.error("sink name duplicated, exist same sink name[{}]", sinkName);
                return false;
            }
            String clazz = sinkConfig.getClassName();
            logger.info("start to construct sink [{}] instance", sinkName);
            ReflectClass reflectClass = ReflectUtil.loadClass(clazz);
            if (reflectClass == null) {
                logger.error("load class[{}] failed", clazz);
                return false;
            }
            Sink sink = (Sink) reflectClass.createInstance(sinkName);
            if (sink == null) {
                logger.error("create sink [{}] instance failed", sinkName);
                return false;
            }
            if (!sink.init(sinkConfig.getConfig())) {
                logger.error("sink [{}]  init failed", sinkName);
                return false;
            }
            sink.setSlsConfig(slsConfig);

            // TODO: resource inject
            sinkMap.put(sinkName, sink);
            logger.info("sink [{}] has finished init", sinkName);
        }
        return true;
    }

    private boolean initChannel(List<ProcessorConfig> channels) {
        for (ProcessorConfig channelConfig : channels) {
            String channelName = channelConfig.getName();
            if (!channelConfig.validate()) {
                logger.error("channel [{}] validate failed", channelName);
                return false;
            }
            if (channelMap.containsKey(channelName)) {
                logger.error("channel name duplicated, exist same channel name[{}]", channelName);
                return false;
            }
            String clazz = channelConfig.getClassName();
            logger.info("start to construct channel [{}] instance", channelName);
            ReflectClass reflectClass = ReflectUtil.loadClass(clazz);
            if (reflectClass == null) {
                logger.error("load class[{}] failed", clazz);
                return false;
            }
            Channel channel = (Channel) reflectClass.createInstance(channelName);
            if (channel == null) {
                logger.error("create channel [{}] instance failed", channelName);
                return false;
            }
            if (!channel.init(channelConfig.getConfig())) {
                logger.error("channel [{}]  init failed", channelName);
                return false;
            }
            channel.setNextProcessors(channelConfig.getNext());
            channel.setSlsConfig(slsConfig);

            // TODO: resource inject

            channelMap.put(channelName, channel);
            logger.info("channel [{}] has finished init", channelName);
        }
        return true;
    }

    private boolean initSource(List<ProcessorConfig> sources) {
        for (ProcessorConfig sourceConfig : sources) {
            String sourceName = sourceConfig.getName();
            if (!sourceConfig.validate()) {
                logger.error("source [{}] validate failed", sourceName);
                return false;
            }
            if (sourceMap.containsKey(sourceName)) {
                logger.error("source name duplicated, exist same source name[{}]", sourceName);
                return false;
            }
            String clazz = sourceConfig.getClassName();
            logger.info("start to construct source [{}] instance", sourceName);
            ReflectClass reflectClass = ReflectUtil.loadClass(clazz);
            if (reflectClass == null) {
                logger.error("load class[{}] failed", clazz);
                return false;
            }
            Source source = (Source) reflectClass.createInstance(sourceName);

            if (source == null) {
                logger.error("create source [{}] instance failed", sourceName);
                return false;
            }
            if (!source.init(sourceConfig.getConfig())) {
                logger.error("source [{}]  init failed", sourceName);
                return false;
            }
            source.setSlsConfig(slsConfig);

            // TODO: resource inject

            logger.info("source [{}] has finished init", sourceName);
            sourceMap.put(sourceName, source);

            // 构建执行链引用关系
            if (!setSourceDownStream(source, sourceConfig.getNext())) {
                logger.info("source [{}] chain reference chain build failed", sourceName);
                return false;
            }
        }
        return true;
    }

    private boolean setSourceDownStream(Source source, Set<String> next) {
        if (CollectionUtils.isEmpty(next)) {
            logger.error("source [{}] have no downstream channel or sink", source.getName());
            return false;
        }
        for (String nextProcessor : next) {
            // 不能依赖自己
            if (nextProcessor.equals(source.getName())) {
                logger.error("can not set the downstream as yourself, component name [{}]", nextProcessor);
                return false;
            }
            Set<String> processSet = new HashSet<>();
            processSet.add(source.getName());
            if (!setDownStream(source, next, processSet)) {
                logger.error("set downstream failed, source name [{}]", source.getName());
                return false;
            }
        }
        return true;
    }

    private boolean setDownStream(AbstractComponent component, Set<String> next, Set<String> processSet) {
        for (String nextProcessor : next) {
            // 不能依赖自己
            if (nextProcessor.equals(component.getName())) {
                logger.error("can not set the downstream as yourself, component name [{}]", nextProcessor);
                return false;
            }
            if (channelMap.containsKey(nextProcessor)) {
                Channel channel = channelMap.get(nextProcessor);
                if (!checkLoopReference(channel, processSet)) {
                    logger.error("check loop failed, current component [{}]", component.getName());
                    return false;
                }
                component.addNext(channel);
                channel.setPrev(component);
                if (!setDownStream(channel, channel.getNextProcessors(), processSet)) {
                    logger.error("set downstream failed, component name [{}]", channel.getName());
                    return false;
                }
            } else if (sinkMap.containsKey(nextProcessor)) {
                // sink 是结束，不会再有下一个
                Sink sink = sinkMap.get(nextProcessor);
                component.addNext(sink);
                sink.setPrev(component);
            } else {
                logger.error("not found downstream component, component name [{}]", nextProcessor);
                return false;
            }
        }
        return true;
    }

    public boolean checkLoopReference(AbstractComponent component, Set<String> components) {
        Collection<AbstractComponent> next = component.getNext();
        Iterator<AbstractComponent> iterator = next.iterator();
        while (iterator.hasNext()) {
            AbstractComponent nextComponent = iterator.next();
            if (components.contains(component)) {
                logger.error("exist loop reference, component name [{}]", component.getName());
                return false;
            }
            components.add(nextComponent.getName());
            if (!checkLoopReference(nextComponent, components)) {
                return false;
            }
        }
        return true;
    }

    public List<Pipeline> buildChain() {
        List<Pipeline> lines = new ArrayList<>();
        for (Map.Entry<String, Source> sourceEntry : sourceMap.entrySet()) {
            Source source = sourceEntry.getValue();
            DefaultPipeline pipeline = new DefaultPipeline(source);
            lines.add(pipeline);
        }
        return lines;
    }
}
