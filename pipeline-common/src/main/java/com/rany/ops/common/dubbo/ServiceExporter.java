package com.rany.ops.common.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务端注册
 * @author dick
 * @description TODO
 * @date 2021/12/17 10:00 下午
 * @email 18668485565@163.com
 */

public class ServiceExporter {

    private static final Logger logger = LoggerFactory.getLogger(ServiceExporter.class);

    /**
     * 服务导出
     * @param config
     * @param objectRef
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> boolean export(DubboServerProperties config, Object objectRef, Class<T> clazz) {
        if (!config.validate()) {
            logger.error("invalid dubbo service config");
            return false;
        }
        if (clazz != objectRef.getClass() && !clazz.isAssignableFrom(objectRef.getClass())) {
            logger.error("object reference class[{}] does matches interface[{}]",
                    objectRef.getClass().getName(), clazz.getName());
            return false;
        }
        ApplicationConfig applicationConfig = new ApplicationConfig(config.getAppName());
        RegistryConfig registryConfig = new RegistryConfig(config.getRegisterAddress());
        registryConfig.setProtocol(config.getProtocol());
        if (!StringUtils.isEmpty(config.getGroup())) {
            registryConfig.setGroup(config.getGroup());
        }
        if (!StringUtils.isEmpty(config.getId())) {
            registryConfig.setId(config.getId());
        }
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName(config.getName());
        protocolConfig.setPort(config.getPort());
        if (config.getThreadNum() > 0) {
            protocolConfig.setThreads(config.getThreadNum());
        }
        ServiceConfig<T> serviceConfig = new ServiceConfig();
        serviceConfig.setApplication(applicationConfig);
        serviceConfig.setRegistry(registryConfig);
        serviceConfig.setProtocol(protocolConfig);
        serviceConfig.setInterface(clazz);
        serviceConfig.setRef((T) objectRef);
        serviceConfig.setVersion(config.getVersion());
        serviceConfig.setTimeout(config.getTimeout());
        if (!StringUtils.isEmpty(config.getLoadBalance())) {
            serviceConfig.setLoadbalance(config.getLoadBalance());
        }
        serviceConfig.export();
        return true;
    }
}
