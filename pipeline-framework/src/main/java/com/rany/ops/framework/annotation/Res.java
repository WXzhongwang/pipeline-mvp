package com.rany.ops.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 资源注解
 *
 * @author zhongshengwang
 * @description 资源注解，字段级别
 * @date 2021/12/23 9:33 下午
 * @email 18668485565@163.com
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Res {

    /**
     * 资源名称
     *
     * @return
     */
    String name() default "";

    /**
     * 是否允许为空，默认不允许
     *
     * @return
     */
    boolean allowNull() default false;
}