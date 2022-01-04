package com.rany.ops.common.tablestore;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TableStore config
 *
 * @author dick
 * @description TableStore
 * @date 2021/12/17 10:31 下午
 * @email 18668485565@163.com
 */

public class TableStoreConfig {

    private static final Logger logger = LoggerFactory.getLogger(TableStoreConfig.class);

    private static final int DEFAULT_RETRY_CNT = 3;

    private String endpoint;
    private String instance;
    private String accessKeyId;
    private String accessKeySecret;
    private int retryCnt = DEFAULT_RETRY_CNT;

    public TableStoreConfig() {
    }

    public TableStoreConfig(String endpoint, String instance, String accessKeyId, String accessKeySecret) {
        this.endpoint = endpoint;
        this.instance = instance;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
    }

    /**
     * 验证OTS配置合法性
     *
     * @return 如果合法返回true，否则返回false
     */
    public boolean validate() {
        if (StringUtils.isEmpty(endpoint)) {
            logger.error("endpoint is empty");
            return false;
        }
        if (StringUtils.isEmpty(instance)) {
            logger.error("instance is empty");
            return false;
        }
        if (StringUtils.isEmpty(accessKeyId) || StringUtils.isEmpty(accessKeySecret)) {
            logger.error("access key/secret config is empty");
            return false;
        }
        return true;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public int getRetryCnt() {
        return retryCnt;
    }

    public void setRetryCnt(int retryCnt) {
        this.retryCnt = retryCnt;
    }
}
