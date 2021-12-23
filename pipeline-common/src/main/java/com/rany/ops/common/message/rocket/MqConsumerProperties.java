package com.rany.ops.common.message.rocket;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dick
 * @description TODO
 * @date 2021/12/17 10:21 下午
 * @email 18668485565@163.com
 */

public class MqConsumerProperties {
    private static final Logger logger = LoggerFactory.getLogger(MqConsumerProperties.class);
    private final static int DEFAULT_CONSUME_THREAD_NUM = 1;
    private String nameSrvAddr;
    private String topic;
    private String groupId;
    private String tags = "*";

    private int consumeThreadNums = 1;
    private String accessKeyId;
    private String accessKeySecret;
    private Long maxCachedMessageAmount;

    public MqConsumerProperties() {
    }

    public MqConsumerProperties(String nameSrvAddr, String topic, String groupId, String accessKeyId, String accessKeySecret) {
        this.nameSrvAddr = nameSrvAddr;
        this.topic = topic;
        this.groupId = groupId;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
    }

    public String getNameSrvAddr() {
        return nameSrvAddr;
    }

    public void setNameSrvAddr(String nameSrvAddr) {
        this.nameSrvAddr = nameSrvAddr;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getConsumeThreadNums() {
        return consumeThreadNums < 0 ? DEFAULT_CONSUME_THREAD_NUM : consumeThreadNums;
    }

    public void setConsumeThreadNums(int consumeThreadNums) {
        this.consumeThreadNums = consumeThreadNums;
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

    public Long getMaxCachedMessageAmount() {
        return maxCachedMessageAmount;
    }

    public void setMaxCachedMessageAmount(Long maxCachedMessageAmount) {
        this.maxCachedMessageAmount = maxCachedMessageAmount;
    }

    public boolean validate() {
        if (StringUtils.isEmpty(nameSrvAddr)) {
            logger.error("name server address is empty");
            return false;
        }
        if (StringUtils.isEmpty(groupId)) {
            logger.error("group id is empty");
            return false;
        }
        if (StringUtils.isEmpty(topic)) {
            logger.error("topic is empty");
            return false;
        }
        if (StringUtils.isEmpty(accessKeyId) || StringUtils.isEmpty(accessKeyId)) {
            logger.error("access key/secret is empty");
            return false;
        }
        return true;
    }
}
