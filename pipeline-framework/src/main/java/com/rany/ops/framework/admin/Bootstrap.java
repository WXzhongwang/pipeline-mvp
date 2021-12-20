package com.rany.ops.framework.admin;

import com.rany.ops.framework.config.BootstrapConfig;
import com.rany.ops.framework.config.ResourceConfig;
import com.rany.ops.framework.resource.ResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 启动引导
 *
 * @author zhongshengwang
 * @description 启动引导
 * @date 2021/12/19 8:16 下午
 * @email 18668485565@163.com
 */

public class Bootstrap {

    private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    private BootstrapConfig config;

    public Bootstrap(BootstrapConfig config) {
        this.config = config;
    }

    public boolean init() {
        logger.info("pipeline app [{}] admin center start to init...", config.getApp().getName());
        // 资源注册
        for (ResourceConfig resourceConfig : config.getResourceConfigList()) {
            if (ResourceManager.hasResource(resourceConfig.getName())) {
                logger.error("resource[{}] has been registered", resourceConfig.getName());
                return false;
            }
            try {
                ResourceManager.registerResource(resourceConfig);
            } catch (Exception e) {
                logger.error("register resource[{}] failed", resourceConfig.getName());
                logger.error(e.getMessage(), e);
                return false;
            }
        }
        // TODO: 串流程

        logger.info("pipeline app [{}] admin center start success...", config.getApp().getName());
        return true;
    }

    public boolean startUp() {
        return true;
    }

    public boolean shutdown() {
        return true;
    }

}
