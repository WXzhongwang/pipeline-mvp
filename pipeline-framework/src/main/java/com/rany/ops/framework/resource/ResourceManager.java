package com.rany.ops.framework.resource;

import com.rany.ops.framework.config.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dick
 * @description TODO
 * @date 2021/12/19 9:38 下午
 * @email 18668485565@163.com
 */

public class ResourceManager {

    private static final Logger logger = LoggerFactory.getLogger(ResourceManager.class);

    private static Map<String, ResourcePool> resourcePoolMap = new ConcurrentHashMap<>();

    /**
     * 获取资源实例
     *
     * @param resourceName 资源名
     * @param clazz        资源实例class
     * @param <T>          资源实例类型
     * @return 获取成功返回资源实例，否则返回null
     */
    public static <T> T getResource(String resourceName, Class<T> clazz) {
        Resource resource = borrowResource(resourceName);
        if (resource == null) {
            logger.warn("borrow resource[{}] failed", resourceName);
            return null;
        }
        T object = null;
        try {
            object = clazz.cast(resource.get());
            if (object == null) {
                returnResource(resource);
                logger.warn("resource object[{}] is not expected class[{}]",
                        resource.get().getClass().getName(), clazz.getName());
                return null;
            }
        } catch (Exception e) {
            logger.error("cast resource object failed");
            logger.error(e.getMessage(), e);
        }
        returnResource(resource);
        return object;
    }

    /**
     * 归还资源
     * 支持多线程并发
     *
     * @param resource 待归还资源对象
     * @return 归还成功返回true，否则返回false
     */
    public static boolean returnResource(Resource resource) {
        String resourceName = resource.getName();
        if (!resourcePoolMap.containsKey(resourceName)) {
            logger.warn("resource[{}] is not found, ignore request", resourceName);
            return false;
        }
        ResourcePool pool;
        synchronized (resourcePoolMap) {
            pool = resourcePoolMap.get(resourceName);
            if (pool == null) {
                logger.warn("resource[{}] has been unregistered by other", resourceName);
                return false;
            }
        }
        pool.returnObject(resource);
        return true;
    }

    /**
     * 借出资源
     * 支持多线程并发
     *
     * @param resourceName 资源名
     * @return 如果资源不存在返回null，否则返回资源对象
     */
    public static Resource borrowResource(String resourceName) {
        if (!resourcePoolMap.containsKey(resourceName)) {
            logger.warn("resource[{}] is not found", resourceName);
            return null;
        }
        ResourcePool pool;
        synchronized (resourcePoolMap) {
            pool = resourcePoolMap.get(resourceName);
            if (pool == null) {
                logger.warn("resource[{}] has been unregistered by other", resourceName);
                return null;
            }
        }
        try {
            return pool.borrowObject();
        } catch (Exception e) {
            logger.error("borrow resource[{}] failed", resourceName);
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 注册资源
     * 支持多线程并发
     *
     * @param config 资源配置
     */
    public static void registerResource(ResourceConfig config) {
        synchronized (resourcePoolMap) {
            if (resourcePoolMap.containsKey(config.getName())) {
                logger.info("resource[{}] has existed, unregister it", config.getName());
                unregisterResource(config.getName());
            }
            ResourcePool pool = new ResourcePool(config);
            resourcePoolMap.put(config.getName(), pool);
            logger.info("register resource[{}] success", config.getName());
        }
    }

    /**
     * 注销资源
     * 支持多线程并发
     *
     * @param resourceName 资源名
     */
    public static void unregisterResource(String resourceName) {
        if (!resourcePoolMap.containsKey(resourceName)) {
            logger.warn("resource[{}] is not found, ignore unregister request", resourceName);
            return;
        }
        synchronized (resourcePoolMap) {
            ResourcePool resourcePool = resourcePoolMap.remove(resourceName);
            if (resourcePool == null) {
                logger.info("resource[{}] has been unregistered by other", resourceName);
                return;
            }
            resourcePool.close();
            logger.info("unregister resource[{}] success", resourceName);
        }
    }

    /**
     * 注销所有资源
     */
    public static void unregisterResources() {
        synchronized (resourcePoolMap) {
            for (ResourcePool pool : resourcePoolMap.values()) {
                pool.close();
            }
            resourcePoolMap.clear();
            logger.info("unregister all resources");
        }
    }

    /**
     * 是否注册资源
     *
     * @param resourceName 资源名
     * @return 已注册返回true，否则返回false
     */
    public static boolean hasResource(String resourceName) {
        return resourcePoolMap.containsKey(resourceName);
    }

}
