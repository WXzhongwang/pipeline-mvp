package com.rany.ops.framework.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 日志配置
 * @author zhongshengwang
 * @description 日志配置
 * @date 2021/12/18 10:01 下午
 * @email 18668485565@163.com
 */
public class SlsConfig implements Serializable {

    private boolean enable = false;
    /**
     * SLS日志记录字段
     */
    private List<String> loggerKeys = new ArrayList<>();

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<String> getLoggerKeys() {
        return loggerKeys;
    }

    public void setLoggerKeys(List<String> loggerKeys) {
        this.loggerKeys = loggerKeys;
    }
}