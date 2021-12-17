package com.rany.ops.common.dubbo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * dubbo 服务端配置
 * @author zhongshengwang
 * @description 服务端配置
 * @date 2021/12/17 8:59 下午
 * @email 18668485565@163.com
 */

public class DubboServerProperties {

    private static final  Logger logger = LoggerFactory.getLogger(DubboServerProperties.class);

    private static final int DEFAULT_PORT = 20880;
    private static final int DEFAULT_TIMEOUT_MS = 3000;
    private static final int DEFAULT_THREAD_NUM = 200;
    private static final String DEFAULT_PROTOCOL = "zookeeper";
    private static final String DEFAULT_NAME = "dubbo";

    private int port = DEFAULT_PORT;
    private int timeout = DEFAULT_TIMEOUT_MS;
    private int threadNum = DEFAULT_THREAD_NUM;
    private String protocol = DEFAULT_PROTOCOL;
    private String name = DEFAULT_NAME;
    private String appName;
    private String group;
    private String id;
    private String registerAddress;
    private String version;
    private String loadBalance;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
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



    /**
     * 验证DUBBO服务配置合理性
     *
     * @return 如果合理返回true，否则返回false
     */
    public boolean validate() {
        if (StringUtils.isEmpty(appName)) {
            logger.error("app name is empty");
            return false;
        }
        if (StringUtils.isEmpty(registerAddress)) {
            logger.error("register address is empty");
            return false;
        }
        if (StringUtils.isEmpty(version)) {
            logger.error("version is empty");
            return false;
        }
        if (StringUtils.isEmpty(name)) {
            logger.error("service name is empty");
            return false;
        }
        if (port <= 0) {
            logger.error("invalid service port[{}]", port);
            return false;
        }
        if (threadNum <= 0) {
            logger.error("invalid thread num[{}]", threadNum);
            return false;
        }
        if (StringUtils.isEmpty(protocol)) {
            logger.error("protocol is empty");
            return false;
        }
        return true;
    }
}
