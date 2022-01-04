package com.rany.ops.common.exception;

/**
 * TableStoreOperationException
 *
 * @author dick
 * @description TableStoreOperationException 操作统一封装
 * @date 2022/1/4 11:46 下午
 * @email 18668485565@163.com
 */

public class TableStoreOperationException extends RuntimeException {

    public TableStoreOperationException(String message) {
        super(message);
    }
}
