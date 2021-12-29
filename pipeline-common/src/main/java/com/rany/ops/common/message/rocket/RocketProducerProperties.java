package com.rany.ops.common.message.rocket;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * RocketMQ producer
 *
 * @author dick
 * @description RocketMQ producer
 * @date 2021/12/17 10:14 下午
 * @email 18668485565@163.com
 */

public class RocketProducerProperties implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(RocketProducerProperties.class);

    private static final int DEFAULT_RETRY_CNT = 3;
    private static final int DEFAULT_REQUEST_TIMEOUT_MS = 3000;

    private int retryCnt = DEFAULT_RETRY_CNT;
    private int requestTimeout = DEFAULT_REQUEST_TIMEOUT_MS;

    private String nameSrvAddr;
    private String groupId;
    private String accessKeyId;
    private String accessKeySecret;

    public RocketProducerProperties() {
    }

    public RocketProducerProperties(String nameSrvAddr, String groupId, String accessKeyId, String accessKeySecret) {
        this.nameSrvAddr = nameSrvAddr;
        this.groupId = groupId;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
    }

    public int getRetryCnt() {
        return retryCnt;
    }

    public void setRetryCnt(int retryCnt) {
        this.retryCnt = retryCnt;
    }

    public int getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public String getNameSrvAddr() {
        return nameSrvAddr;
    }

    public void setNameSrvAddr(String nameSrvAddr) {
        this.nameSrvAddr = nameSrvAddr;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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
        if (StringUtils.isEmpty(groupId)) {
            logger.error("group id is empty");
            return false;
        }
        if (StringUtils.isEmpty(nameSrvAddr)) {
            logger.error("name server address is empty");
            return false;
        }
        if (StringUtils.isEmpty(accessKeyId) || StringUtils.isEmpty(accessKeyId)) {
            logger.error("access key/secret is empty");
            return false;
        }
        return true;
    }
}
