package com.rany.ops.common.tablestore;

import com.alicloud.openservices.tablestore.model.ColumnValue;
import com.rany.ops.common.exception.TableStoreOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TableStore属性列
 *
 * @author dick
 * @description TableStore属性列
 * @date 2022/1/4 11:43 下午
 * @email 18668485565@163.com
 */

public class PropertyColumn {

    private static final Logger logger = LoggerFactory.getLogger(PropertyColumn.class);
    
    private String propertyName;
    private Object propertyValue;

    public PropertyColumn(String propertyName, Object propertyValue) {
        if (propertyName == null) {
            logger.error("property name is null");
            throw new TableStoreOperationException("property name is null");
        }
        if (propertyValue == null) {
            logger.error("property value is null");
            throw new TableStoreOperationException("property value is null");
        }
        if (!(propertyValue instanceof String) &&
                !(propertyValue instanceof Long) &&
                !(propertyValue instanceof Integer) &&
                !(propertyValue instanceof Double) &&
                !(propertyValue instanceof Boolean) &&
                !(propertyValue instanceof byte[])) {
            logger.debug("invalid table store property column type[{}]", propertyValue.getClass().getName());
            throw new TableStoreOperationException(String.format("invalid table store property column type[%s]", propertyValue.getClass().getName()));
        }
        this.propertyName = propertyName;
        this.propertyValue = propertyValue instanceof Integer ? ((Integer) propertyValue).longValue() : propertyValue;
    }

    /**
     * 构建属性列
     *
     * @return
     */
    public ColumnValue buildColumnValue() {
        if (propertyValue instanceof Long) {
            return ColumnValue.fromLong((Long) propertyValue);
        } else if (propertyValue instanceof byte[]) {
            return ColumnValue.fromBinary((byte[]) propertyValue);
        } else if (propertyValue instanceof Double) {
            return ColumnValue.fromDouble((Double) propertyValue);
        } else if (propertyValue instanceof Boolean) {
            return ColumnValue.fromBoolean((Boolean) propertyValue);
        }
        return ColumnValue.fromString((String) propertyValue);
    }

    @Override
    public String toString() {
        return String.format("property[%s=%s]",
                propertyName,
                propertyValue.toString());
    }

}
