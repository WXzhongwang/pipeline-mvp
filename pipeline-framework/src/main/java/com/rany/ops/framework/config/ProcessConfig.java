package com.rany.ops.framework.config;

import java.io.Serializable;
import java.util.List;

/**
 * @author dick
 * @description TODO
 * @date 2021/12/20 11:15 下午
 * @email 18668485565@163.com
 */

public class ProcessConfig implements Serializable {

    private List<SourceProcessorConfig> sources;
    private List<ProcessorConfig> channels;
    private List<ProcessorConfig> sinks;

    public List<SourceProcessorConfig> getSources() {
        return sources;
    }

    public void setSources(List<SourceProcessorConfig> sources) {
        this.sources = sources;
    }

    public List<ProcessorConfig> getChannels() {
        return channels;
    }

    public void setChannels(List<ProcessorConfig> channels) {
        this.channels = channels;
    }

    public List<ProcessorConfig> getSinks() {
        return sinks;
    }

    public void setSinks(List<ProcessorConfig> sinks) {
        this.sinks = sinks;
    }
}
