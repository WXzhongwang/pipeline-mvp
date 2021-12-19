package com.rany.ops.framework.resource;

import com.rany.ops.framework.config.ResourceConfig;
import com.rany.ops.framework.exception.ResourceException;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 资源池：负责管理资源对象，负责资源的实例化数量控制和生命周期管理
 * 建议资源池管理对象为线程安全
 *
 * @author zhongshengwang
 * @description 资源工厂
 * @date 2021/12/19 8:54 下午
 * @email 18668485565@163.com
 */
public class ResourcePool extends GenericObjectPool<Resource> {

    private static final Logger logger = LoggerFactory.getLogger(ResourcePool.class);

    public ResourcePool(ResourceConfig config) {
        super(new ResourceFactory(config));
        int instanceNum = config.getInstanceNum();
        if (instanceNum <= 0) {
            logger.error("instance num[{}] is invalid for resource pool[{}]", instanceNum, config.getName());
            throw new ResourceException(String.format(
                    "instance num[%d] is invalid for resource pool[%s]", instanceNum, config.getName()));
        }
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(config.getInstanceNum());
        poolConfig.setMaxIdle(config.getInstanceNum());
        poolConfig.setMinIdle(0);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestOnBorrow(true);
        setConfig(poolConfig);
    }

}
