package com.rany.ops.common.tablestore;

import com.alicloud.openservices.tablestore.model.PrimaryKeyValue;
import com.rany.ops.common.exception.TableStoreOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TableStore主键列
 *
 * @author dick
 * @description TableStore主键列
 * @date 2022/1/4 11:43 下午
 * @email 18668485565@163.com
 */

public class PkColumn {

    private static final Logger logger = LoggerFactory.getLogger(PkColumn.class);
    private String primaryKey;
    private Object primaryValue;

    public PkColumn(String primaryKey, Object primaryValue) {
        if (primaryKey == null) {
            logger.error("primary key is null");
            throw new TableStoreOperationException("primary key is null");
        }
        if (primaryValue == null) {
            logger.error("primary value is null");
            throw new TableStoreOperationException("primary value is null");
        }
        if (!(primaryValue instanceof String) &&
                !(primaryValue instanceof Long) &&
                !(primaryValue instanceof Integer) &&
                !(primaryValue instanceof byte[])) {
            logger.error("invalid table store primary key type[{}]", primaryValue.getClass().getName());
            throw new TableStoreOperationException(String.format("invalid table store primary key type[%s]", primaryValue.getClass().getName()));
        }
        this.primaryKey = primaryKey;
        this.primaryValue = primaryValue instanceof Integer ? ((Integer) primaryValue).longValue() : primaryValue;
    }

    /**
     * 构建主键
     *
     * @return
     */
    public PrimaryKeyValue buildPrimaryKeyValue() {
        if (primaryValue instanceof Long) {
            return PrimaryKeyValue.fromLong((Long) primaryValue);
        } else if (primaryValue instanceof byte[]) {
            return PrimaryKeyValue.fromBinary((byte[]) primaryValue);
        }
        return PrimaryKeyValue.fromString((String) primaryValue);
    }

    @Override
    public String toString() {
        return String.format("pk[%s=%s]", primaryKey, primaryValue.toString());
    }

}
