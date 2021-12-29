package com.rany.ops.common.message.rabbit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RabbitConsumerProperties
 *
 * @author dick
 * @description RabbitConsumerProperties
 * @date 2021/12/24 10:35 下午
 * @email 18668485565@163.com
 */

public class RabbitConsumerProperties {

    private static final Logger logger = LoggerFactory.getLogger(RabbitConsumerProperties.class);

    private String host;
    /**
     * 默认端口。非加密端口为5672，加密端口为5671。
     */
    private int port = 5672;
    private String username;
    private String password;
    private String virtualHost;
    private String exchangeName;
    private String exchangeType;
    private String queueName;
    private String bindingKey;
    private String consumeTag;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getBindingKey() {
        return bindingKey;
    }

    public void setBindingKey(String bindingKey) {
        this.bindingKey = bindingKey;
    }

    public String getConsumeTag() {
        return consumeTag;
    }

    public void setConsumeTag(String consumeTag) {
        this.consumeTag = consumeTag;
    }

    public boolean validate() {
        if (StringUtils.isEmpty(host)) {
            logger.error("host is empty");
            return false;
        }
        if (StringUtils.isEmpty(virtualHost)) {
            logger.error("virtual host is empty");
            return false;
        }
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            logger.error("access key/secret is empty");
            return false;
        }
        return true;
    }

}
