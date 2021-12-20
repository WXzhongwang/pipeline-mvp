package com.rany.ops.framework.config;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhongshengwang
 * @description TODO
 * @date 2021/12/20 11:15 下午
 * @email 18668485565@163.com
 */

public class ProcessConfig implements Serializable {

    private List<ProcessorConfig> source;
    private List<ProcessorConfig> channel;
    private List<ProcessorConfig> sink;

    public List<ProcessorConfig> getSource() {
        return source;
    }

    public void setSource(List<ProcessorConfig> source) {
        this.source = source;
    }

    public List<ProcessorConfig> getChannel() {
        return channel;
    }

    public void setChannel(List<ProcessorConfig> channel) {
        this.channel = channel;
    }

    public List<ProcessorConfig> getSink() {
        return sink;
    }

    public void setSink(List<ProcessorConfig> sink) {
        this.sink = sink;
    }
}
