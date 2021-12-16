package com.rany.ops.pipeline.mvp;

/**
 * @author zhongshengwang
 * @description TODO
 * @date 2021/12/14 10:31 下午
 * @email 18668485565@163.com
 */

public class DeliveryException extends RuntimeException {

    private DeliveryErrorCode errorCode;

    private String message;

    public DeliveryException(DeliveryErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public DeliveryException(DeliveryErrorCode errorCode, String message, Throwable e) {
        super(message, e);
        this.errorCode = errorCode;
        this.message = message;
    }
}
