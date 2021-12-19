package com.rany.ops.common.reflection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * ReflectUtil
 *
 * @author zhongshengwang
 * @description ReflectClass
 * @date 2021/12/17 10:09 下午
 * @email 18668485565@163.com
 */
public class ReflectUtil {

    private ReflectUtil() {
    }

    private static final Logger logger = LoggerFactory.getLogger(ReflectUtil.class);

    private static Map<String, ReflectClass> reflectClassMap = new HashMap<>();

    public static ReflectClass loadClass(String className) {
        if (reflectClassMap.containsKey(className)) {
            return reflectClassMap.get(className);
        }
        synchronized (reflectClassMap) {
            if (reflectClassMap.containsKey(className)) {
                return reflectClassMap.get(className);
            }
            try {
                Class clazz = Class.forName(className);
                ReflectClass reflectClass = new ReflectClass(clazz);
                reflectClassMap.put(className, reflectClass);
                return reflectClass;
            } catch (Exception e) {
                logger.error("class[{}] is not found", className);
                logger.error(e.getMessage(), e);
                return null;
            }
        }
    }

}
