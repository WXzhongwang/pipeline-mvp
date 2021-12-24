package com.rany.ops.common.message.rabbit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dick
 * @description TODO
 * @date 2021/12/24 10:22 下午
 * @email 18668485565@163.com
 */

public class RabbitProducerProperties {

    private static final Logger logger = LoggerFactory.getLogger(RabbitProducerProperties.class);

    private String host;
    private int port;
    private String username;
    private String password;
    private String virtualHost;

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
