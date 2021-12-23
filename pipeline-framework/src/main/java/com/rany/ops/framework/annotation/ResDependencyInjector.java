package com.rany.ops.framework.annotation;

import com.rany.ops.framework.core.channel.Channel;
import com.rany.ops.framework.core.sink.Sink;
import com.rany.ops.framework.core.source.Source;
import com.rany.ops.framework.exception.ResourceException;
import com.rany.ops.framework.resource.ResourceManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 资源注入
 *
 * @author zhongshengwang
 * @description 资源注入
 * @date 2021/12/23 9:37 下午
 * @email 18668485565@163.com
 */

public class ResDependencyInjector {

    private ResDependencyInjector() {

    }

    private static final Logger logger = LoggerFactory.getLogger(ResDependencyInjector.class);

    private static final Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");

    public static void inject(Object component) {
        Field[] fields = getAllFields(component);
        for (Field field : fields) {
            Res resource = field.getAnnotation(Res.class);
            if (resource == null) {
                continue;
            }
            if (StringUtils.isEmpty(resource.name())) {
                logger.error("resource name is empty for field [{}]", field.getName());
                throw new ResourceException(String.format("resource name is empty for field [%s] in class [%s]",
                        field.getName(), component.getClass().getSimpleName()));
            }
            String resourceName = resource.name();
            Matcher matcher = pattern.matcher(resourceName);
            if (matcher != null && matcher.matches()) {
                resourceName = matcher.group(1);
                if (StringUtils.isEmpty(resourceName) && !resource.allowNull()) {
                    logger.error("resource name is not configured in [{}] for field [{}]",
                            component.getClass().getSimpleName(), field.getName());
                    throw new ResourceException(String.format("resource name is not configured in [%s] for field [%s]",
                            component.getClass().getSimpleName(), field.getName()));
                }
            }
            Object resourceObj = ResourceManager.getResource(resourceName, field.getType());
            if (resourceObj == null && !resource.allowNull()) {
                logger.error("resource [{}] is not found, or is not expected type [{}]",
                        resourceName, field.getType().getName());
                throw new ResourceException(String.format("resource[%s] is not found, or is not expected type[%s]",
                        resourceName, field.getType().getName()));
            }
            field.setAccessible(true);
            try {
                field.set(component, resourceObj);
            } catch (IllegalAccessException e) {
                logger.error("reflect setting field [{}] failed", field.getName(), e);
                throw new ResourceException(String.format("reflect setting field [%s] failed", field.getName()), e);
            }
        }
    }

    private static Field[] getAllFields(Object object) {
        List<Field> fields = new ArrayList<>();
        Class clazz = object.getClass();
        while (Source.class.isAssignableFrom(clazz) ||
                Channel.class.isAssignableFrom(clazz) ||
                Sink.class.isAssignableFrom(clazz)) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields.toArray(new Field[0]);
    }
}
