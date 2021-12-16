package com.rany.ops.framework.core;

import java.util.Map;

/**
 * 组件生命周期
 *
 * @author zhongshengwang
 * @description 组件生命周期
 * @date 2021/12/16 10:24 上午
 * @email 18668485565@163.com
 */

public interface LifeCycle {


    /**
     * 初始化
     *
     * @param config
     * @return
     */
    boolean init(Map<String, Object> config);


    /**
     * 启动
     *
     * @return
     */
    boolean startUp();


    /**
     * 停止
     *
     * @return
     */
    boolean shutdown();
}
