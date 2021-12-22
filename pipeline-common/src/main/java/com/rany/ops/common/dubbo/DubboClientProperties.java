package com.rany.ops.common.dubbo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * dubbo客户端配置
 * @author dick
 * @description dubbo客户端配置
 * @date 2021/12/17 8:55 下午
 * @email 18668485565@163.com
 */
public class DubboClientProperties implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(DubboClientProperties.class);

    private static final int DEFAULT_TIMEOUT_MS = 6000;
    private static final String DEFAULT_PROTOCOL = "zookeeper";

    private String registerAddress;
    private String protocol = DEFAULT_PROTOCOL;
    private String appName;
    private String id;
    private String group;
    private String version;
    public String loadBalance;
    public boolean check = false;
    public int timeout = DEFAULT_TIMEOUT_MS;

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLoadBalance() {
        return loadBalance;
    }

    public void setLoadBalance(String loadBalance) {
        this.loadBalance = loadBalance;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public boolean validate() {
        if (StringUtils.isEmpty(registerAddress)) {
            logger.error("register address is empty");
            return false;
        }
        if (StringUtils.isEmpty(protocol)) {
            logger.error("protocol is empty");
            return false;
        }
        if (StringUtils.isEmpty(appName)) {
            logger.error("app name is empty");
            return false;
        }
        if (StringUtils.isEmpty(version)) {
            logger.error("version is empty");
            return false;
        }
        return true;
    }
}
