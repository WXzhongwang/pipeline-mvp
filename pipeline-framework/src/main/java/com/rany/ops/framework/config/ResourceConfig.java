package com.rany.ops.framework.config;

import java.io.Serializable;
import java.util.Map;

/**
 * 资源配置
 *
 * @author zhongshengwang
 * @description TODO
 * @date 2021/12/19 8:38 下午
 * @email 18668485565@163.com
 */

public class ResourceConfig implements Serializable {

    private String name;

    private String className;

    private String instanceNum;

    private Map<String, Object> configMap;

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

    public String getInstanceNum() {
        return instanceNum;
    }

    public void setInstanceNum(String instanceNum) {
        this.instanceNum = instanceNum;
    }

    public Map<String, Object> getConfigMap() {
        return configMap;
    }

    public void setConfigMap(Map<String, Object> configMap) {
        this.configMap = configMap;
    }
}
