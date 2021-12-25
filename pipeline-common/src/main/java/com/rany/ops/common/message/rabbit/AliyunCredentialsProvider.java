package com.rany.ops.common.message.rabbit;

import com.alibaba.mq.amqp.utils.UserUtils;
import com.rabbitmq.client.impl.CredentialsProvider;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * RAM 角色会用到
 * 阿里云UserName、Password生成类（动态变化）。
 *
 * @author dick
 */
public class AliyunCredentialsProvider implements CredentialsProvider {

    private static final Logger logger = LoggerFactory.getLogger(AliyunCredentialsProvider.class);
    /**
     * Access Key ID.
     */
    private final String accessKeyId;
    /**
     * Access Key Secret.
     */
    private final String accessKeySecret;
    /**
     * security temp token. (optional)
     */
    private final String securityToken;
    /**
     * 实例ID（从消息队列RabbitMQ版控制台获取）。
     */
    private final String instanceId;

    public AliyunCredentialsProvider(final String accessKeyId, final String accessKeySecret,
                                     final String instanceId) {
        this(accessKeyId, accessKeySecret, null, instanceId);
    }

    public AliyunCredentialsProvider(final String accessKeyId, final String accessKeySecret,
                                     final String securityToken, final String instanceId) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.securityToken = securityToken;
        this.instanceId = instanceId;
    }

    @Override
    public String getUsername() {
        if (StringUtils.isNotEmpty(securityToken)) {
            return UserUtils.getUserName(accessKeyId, instanceId, securityToken);
        } else {
            return UserUtils.getUserName(accessKeyId, instanceId);
        }
    }

    @Override
    public String getPassword() {
        try {
            return UserUtils.getPassord(accessKeySecret);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            logger.error("get password error", e);
        }
        return null;
    }
}