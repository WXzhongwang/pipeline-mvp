package com.rany.ops.framework.admin;

import com.rany.ops.framework.config.BootstrapConfig;
import com.rany.ops.framework.config.ResourceConfig;
import com.rany.ops.framework.pipeline.PipelineContext;
import com.rany.ops.framework.resource.ResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 启动引导
 *
 * @author dick
 * @description 启动引导
 * @date 2021/12/19 8:16 下午
 * @email 18668485565@163.com
 */

public class Bootstrap {

    private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    private BootstrapConfig config;

    private PipelineContext context;

    public Bootstrap(BootstrapConfig config) {
        this.config = config;
    }

    public boolean init() {
        logger.info("pipeline app [{}] bootstrap start to init...", config.getApp().getName());
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
        // 串流程
        context = new PipelineContext(config.getProcess(), config.getSls());
        if (!context.prepare()) {
            logger.info("context prepare failed...");
            return false;
        }
        logger.info("pipeline app [{}] bootstrap init success...", config.getApp().getName());
        return true;
    }

    public boolean startUp() {
        return context.open();
    }

    public boolean shutdown() {
        return context.close();
    }

}
