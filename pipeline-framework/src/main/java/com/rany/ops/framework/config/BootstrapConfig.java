package com.rany.ops.framework.config;

import java.io.Serializable;
import java.util.List;

/**
 * 应用程序配置
 *
 * @author zhongshengwang
 * @description 应用程序配置
 * @date 2021/12/18 9:56 下午
 * @email 18668485565@163.com
 */

public class BootstrapConfig implements Serializable {

    /**
     * 应用基础配置
     */
    private AppConfig app;

    /**
     * 日志配置
     */
    private SlsConfig sls;

    /**
     * 告警配置
     */
    private MonitorConfig monitor;

    /**
     * 资源配置
     */
    private List<ResourceConfig> resourceConfigList;

    /**
     * 处理流程
     */
    private ProcessConfig process;

    public AppConfig getApp() {
        return app;
    }

    public void setApp(AppConfig app) {
        this.app = app;
    }

    public SlsConfig getSls() {
        return sls;
    }

    public void setSls(SlsConfig sls) {
        this.sls = sls;
    }

    public MonitorConfig getMonitor() {
        return monitor;
    }

    public void setMonitor(MonitorConfig monitor) {
        this.monitor = monitor;
    }

    public List<ResourceConfig> getResourceConfigList() {
        return resourceConfigList;
    }

    public void setResourceConfigList(List<ResourceConfig> resourceConfigList) {
        this.resourceConfigList = resourceConfigList;
    }

    public ProcessConfig getProcess() {
        return process;
    }

    public void setProcess(ProcessConfig process) {
        this.process = process;
    }
}
