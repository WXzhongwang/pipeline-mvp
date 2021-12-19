package com.rany.ops.framework.admin;

import com.rany.ops.framework.config.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhongshengwang
 * @description TODO
 * @date 2021/12/19 8:16 下午
 * @email 18668485565@163.com
 */

public class Admin {

    private static final Logger logger = LoggerFactory.getLogger(Admin.class);

    private ApplicationConfig config;

    public Admin(ApplicationConfig config) {
        this.config = config;
    }

    public boolean init() {
        logger.info("pipeline app [{}] admin center start to init...", config.getApp().getName());

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
