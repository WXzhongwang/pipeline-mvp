package com.rany.ops.framework.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 资源配置
 *
 * @author dick
 * @description 资源配置
 * @date 2021/12/19 8:38 下午
 * @email 18668485565@163.com
 */

public class ResourceConfig implements Serializable {

    private String name;

    private String className;

    private Integer instanceNum;

    private Map<String, Object> configMap = new HashMap<>();

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

    public Integer getInstanceNum() {
        return instanceNum;
    }

    public void setInstanceNum(Integer instanceNum) {
        this.instanceNum = instanceNum;
    }

    public Map<String, Object> getConfigMap() {
        return configMap;
    }

    public void setConfigMap(Map<String, Object> configMap) {
        this.configMap = configMap;
    }
}
