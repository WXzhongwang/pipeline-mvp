package com.rany.ops.common.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * dubbo 客户端泛化调用
 *
 * @author dick
 * @description 客户端泛化调用
 * @date 2021/12/17 9:02 下午
 * @email 18668485565@163.com
 */

public class ClientExporter {

    public static <T> T export(DubboClientProperties config, Class<T> clazz) {
        ApplicationConfig applicationConfig = new ApplicationConfig(config.getAppName());
        String[] registryAddressList = config.getRegisterAddress().split(",");
        List<RegistryConfig> registryConfigList = new ArrayList<>();
        for (String registryAddress : registryAddressList) {
            RegistryConfig registryConfig = new RegistryConfig(registryAddress.trim());
            registryConfig.setProtocol(config.getProtocol());
            if (config.timeout > 0) {
                registryConfig.setTimeout(config.timeout);
            }
            registryConfigList.add(registryConfig);
        }
        ReferenceConfig<T> reference = new ReferenceConfig<>();
        reference.setApplication(applicationConfig);
        reference.setRegistries(registryConfigList);
        reference.setInterface(clazz);
        reference.setVersion(config.getVersion());
        reference.setCheck(config.check);
        if (!StringUtils.isEmpty(config.loadBalance)) {
            reference.setLoadbalance(config.loadBalance);
        }
        if (!StringUtils.isEmpty(config.getGroup())) {
            reference.setGroup(config.getGroup());
        }
        if (!StringUtils.isEmpty(config.getId())) {
            reference.setId(config.getId());
        }
        return reference.get();
    }
}
