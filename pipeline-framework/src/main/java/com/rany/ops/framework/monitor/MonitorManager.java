package com.rany.ops.framework.monitor;

import com.rany.ops.framework.config.MonitorConfig;

/**
 * 告警
 *
 * @author dick
 * @description TODO
 * @date 2021/12/25 11:47 下午
 * @email 18668485565@163.com
 */

public class MonitorManager {

    /**
     * 告警配置
     */
    public MonitorConfig monitorConfig;


    public MonitorManager(MonitorConfig monitorConfig) {
        this.monitorConfig = monitorConfig;
    }
    

}
