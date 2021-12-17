package com.rany.ops.common.oss;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OSS config
 * @author zhongshengwang
 * @description OSS config
 * @date 2021/12/17 10:09 下午
 * @email 18668485565@163.com
 */

public class OssConfig {

    private static final  Logger logger = LoggerFactory.getLogger(OssConfig.class);
    private static final int DEFAULT_RETRY_CNT = 3;
    private static final int DEFAULT_CONNECTION_TIMEOUT_MS = 3000;
    private static final int DEFAULT_SOCKET_TIMEOUT_MS = 10000;


    private int retryCnt = DEFAULT_RETRY_CNT;
    private int connectionTimeoutMs = DEFAULT_CONNECTION_TIMEOUT_MS;
    private int socketTimeoutMs = DEFAULT_SOCKET_TIMEOUT_MS;

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;

    public OssConfig() {
    }

    public OssConfig(String endpoint, String accessKeyId, String accessKeySecret) {
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
    }

    public int getRetryCnt() {
        return retryCnt;
    }

    public void setRetryCnt(int retryCnt) {
        this.retryCnt = retryCnt;
    }

    public int getConnectionTimeoutMs() {
        return connectionTimeoutMs;
    }

    public void setConnectionTimeoutMs(int connectionTimeoutMs) {
        this.connectionTimeoutMs = connectionTimeoutMs;
    }

    public int getSocketTimeoutMs() {
        return socketTimeoutMs;
    }

    public void setSocketTimeoutMs(int socketTimeoutMs) {
        this.socketTimeoutMs = socketTimeoutMs;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
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

    public boolean validate() {
        if (StringUtils.isEmpty(endpoint)) {
            logger.error("endpoint is empty");
            return false;
        }
        if (StringUtils.isEmpty(accessKeyId) || StringUtils.isEmpty(accessKeySecret) ) {
            logger.error("access key/secret is empty");
            return false;
        }
        return true;
    }
}
