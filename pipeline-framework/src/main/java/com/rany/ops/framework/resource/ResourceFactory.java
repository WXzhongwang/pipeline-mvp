package com.rany.ops.framework.resource;

import com.rany.ops.common.reflection.ReflectClass;
import com.rany.ops.common.reflection.ReflectUtil;
import com.rany.ops.framework.config.ResourceConfig;
import com.rany.ops.framework.exception.ResourceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 资源工厂
 *
 * @author zhongshengwang
 * @description 资源工厂
 * @date 2021/12/19 8:54 下午
 * @email 18668485565@163.com
 */
public class ResourceFactory implements PooledObjectFactory<Resource> {

    private static final Logger logger = LoggerFactory.getLogger(ResourceFactory.class);

    private ResourceConfig config;

    public ResourceFactory(ResourceConfig config) {
        this.config = config;
    }

    @Override
    public PooledObject<Resource> makeObject() throws Exception {
        String name = config.getName();
        String className = config.getClassName();
        if (StringUtils.isEmpty(className)) {
            logger.error("resource[{}] class name is null or empty", name);
            return null;
        }
        ReflectClass reflectClass = ReflectUtil.loadClass(className);
        if (reflectClass == null) {
            logger.error("class[{}] is not found for resource[{}]", className, name);
            return null;
        }
        Resource resource = (Resource) reflectClass.createInstance(name);
        if (resource == null) {
            logger.error("create resource[{}] object failed for class[{}]", name, className);
            return null;
        }
        resource.build(config.getConfigMap());
        if (resource.get() == null) {
            logger.error("build resource[{}] object failed", name);
            return null;
        }
        logger.info("create resource pooled object success for name[{}]", name);
        return new DefaultPooledObject<>(resource);
    }

    @Override
    public void destroyObject(PooledObject<Resource> pooledObject) throws Exception {
        Resource resource = pooledObject.getObject();
        if (resource == null) {
            logger.warn("resource[{}] object is not found, ignore destroying", config.getName());
            return;
        }
        resource.destroy();
        logger.info("finish destroying resource pooled object for name[{}]", config.getName());
    }

    @Override
    public boolean validateObject(PooledObject<Resource> pooledObject) {
        Resource resource = pooledObject.getObject();
        if (resource == null) {
            logger.warn("resource object is null, validate failed");
            return false;
        }
        return resource.validate();
    }

    @Override
    public void activateObject(PooledObject<Resource> pooledObject) throws Exception {
        throw new ResourceException("unsupported operation");
    }

    @Override
    public void passivateObject(PooledObject<Resource> pooledObject) throws Exception {
        throw new ResourceException("unsupported operation");
    }
}
