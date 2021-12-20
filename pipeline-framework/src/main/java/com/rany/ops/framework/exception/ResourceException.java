package com.rany.ops.framework.exception;

/**
 * 资源管理统一异常
 *
 * @author zhongshengwang
 * @description TODO
 * @date 2021/12/19 9:21 下午
 * @email 18668485565@163.com
 */

public class ResourceException extends RuntimeException {

    public ResourceException(String message) {
        super(message);
    }
}
