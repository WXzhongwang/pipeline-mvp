package com.rany.ops.framework.config;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * ConvertConfig
 *
 * @author dick
 * @description ConvertConfig
 * @date 2021/12/24 6:12 下午
 * @email 18668485565@163.com
 */

public class ConvertConfig implements Serializable {

    private String className;
    private Map<String, Object> config;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Map<String, Object> getConfig() {
        return config;
    }

    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }

    public boolean validate() {
        return !StringUtils.isEmpty(className);
    }
}
