package com.rany.ops.framework.config;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * ProcessorConfig
 *
 * @author dick
 * @description ProcessorConfig
 * @date 2021/12/20 11:18 下午
 * @email 18668485565@163.com
 */

public class ProcessorConfig implements Serializable {

    private String name;
    private String className;
    private Map<String, Object> config;
    private Set<String> next;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public Set<String> getNext() {
        return next;
    }

    public void setNext(Set<String> next) {
        this.next = next;
    }

    public boolean validate() {
        return !StringUtils.isEmpty(name) && !StringUtils.isEmpty(className);
    }
}
