package com.rany.ops.common.reflection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * ReflectClass
 *
 * @author zhongshengwang
 * @description ReflectClass
 * @date 2021/12/17 10:09 下午
 * @email 18668485565@163.com
 */
public class ReflectClass {

    private static final Logger logger = LoggerFactory.getLogger(ReflectClass.class);

    private Class clazz;
    private Map<String, Constructor> constructorMap;
    private Map<String, Field> fieldMap;
    private Map<String, Method> methodMap;

    public ReflectClass(Class clazz) {
        this.clazz = clazz;
        constructorMap = new HashMap<>();
        fieldMap = new HashMap<>();
        methodMap = new HashMap<>();
    }

    public Object getFieldValue(Object targetObj, String fieldName) {
        try {
            Field field = getField(fieldName);
            field.setAccessible(true);
            return field.get(targetObj);
        } catch (Exception e) {
            logger.error("get field[{}] failed from class[{}]", fieldName, clazz.getName());
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public void setFieldValue(Object targetObj, String fieldName, Object fieldValue) {
        try {
            Field field = getField(fieldName);
            field.setAccessible(true);
            field.set(targetObj, fieldValue);
        } catch (Exception e) {
            logger.error("set field[{}] failed for class[{}]", fieldName, clazz.getName());
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public Method getMethod(String methodName, Class... parameterTypes) {
        String sign = getMethodSignature(methodName, parameterTypes);
        Method method = null;
        try {
            if (methodMap.containsKey(sign)) {
                method = methodMap.get(sign);
            } else {
                synchronized (methodMap) {
                    if (methodMap.containsKey(sign)) {
                        method = methodMap.get(sign);
                    } else {
                        method = clazz.getMethod(methodName, parameterTypes);
                        method.setAccessible(true);
                        methodMap.put(sign, method);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("invoke method[{}] failed for class[{}]", methodName, clazz.getName());
            logger.error(e.getMessage(), e);
        }
        return method;
    }

    public Object invokeMethod(Object targetObj, Method method, Object... parameters) {
        try {
            if (method == null) {
                logger.error("method is not found");
                throw new RuntimeException("method is not found");
            }
            return method.invoke(targetObj, parameters);
        } catch (Exception e) {
            logger.error("invoke method[{}] failed for class[{}]", method.getName(), clazz.getName());
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public Object invokeMethod(Object targetObj, String methodName, Object... parameters) {
        Class[] parameterTypes = getParameterTypes(parameters);
        Method method = getMethod(methodName, parameterTypes);
        return invokeMethod(targetObj, method, parameters);
    }

    public Constructor getConstructor(Class... parameterTypes) {
        String sign = getMethodSignature("", parameterTypes);
        Constructor constructor = null;
        try {
            if (constructorMap.containsKey(sign)) {
                constructor = constructorMap.get(sign);
            } else {
                synchronized (constructorMap) {
                    if (constructorMap.containsKey(sign)) {
                        constructor = constructorMap.get(sign);
                    } else {
                        constructor = clazz.getConstructor(parameterTypes);
                        constructor.setAccessible(true);
                        constructorMap.put(sign, constructor);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("get constructor failed for class[{}]", clazz.getName());
            logger.error(e.getMessage(), e);
        }
        return constructor;
    }

    public Object createInstance(Constructor constructor, Object... parameters) {
        try {
            if (constructor == null) {
                logger.error("constructor is null");
                return null;
            }
            return constructor.newInstance(parameters);
        } catch (Exception e) {
            logger.error("create instance failed for class[{}]", clazz.getName());
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public Object createInstance(Object... parameters) {
        Class[] parameterTypes = getParameterTypes(parameters);
        Constructor constructor = getConstructor(parameterTypes);
        return createInstance(constructor, parameters);
    }

    private Field getField(String fieldName) throws NoSuchFieldException {
        if (fieldMap.containsKey(fieldName)) {
            return fieldMap.get(fieldName);
        } else {
            synchronized (fieldMap) {
                if (fieldMap.containsKey(fieldName)) {
                    return fieldMap.get(fieldName);
                } else {
                    Field field = clazz.getDeclaredField(fieldName);
                    fieldMap.put(fieldName, field);
                    return field;
                }
            }
        }
    }

    private Class[] getParameterTypes(Object... parameters) {
        Class[] parameterTypes = new Class[parameters == null ? 0 : parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            parameterTypes[i] = parameters[i].getClass();
        }
        return parameterTypes;
    }

    private String getMethodSignature(String methodName, Class... parameterTypes) {
        StringBuffer buffer = new StringBuffer(methodName);
        buffer.append("(");
        for (Class parameterType : parameterTypes) {
            buffer.append(parameterType.getName()).append(",");
        }
        buffer.append(")");
        return buffer.toString();
    }

    public Class getClazz() {
        return clazz;
    }

}
