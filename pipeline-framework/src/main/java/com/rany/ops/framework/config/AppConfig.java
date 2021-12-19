package com.rany.ops.framework.config;

import java.io.Serializable;

/**
 * @author zhongshengwang
 * @description TODO
 * @date 2021/12/18 9:57 下午
 * @email 18668485565@163.com
 */

public class AppConfig implements Serializable {

    /**
     * 应用名称
     */
    private String name;

    /**
     * 紧急联系人
     */
    private String emergency;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmergency() {
        return emergency;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }
}
