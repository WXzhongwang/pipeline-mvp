package com.rany.ops.common.message.rocket;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.PropertyValueConst;
import com.aliyun.openservices.ons.api.SendResult;
import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Predicates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * RocketProducer
 *
 * @author dick
 * @description RocketProducer
 * @date 2021/12/24 9:46 下午
 * @email 18668485565@163.com
 */

public class RocketProducer {

    private static final Logger logger = LoggerFactory.getLogger(RocketProducer.class);

    private Producer producer;

    private RocketProducerProperties properties;

    public RocketProducer() {

    }

    public boolean init(RocketProducerProperties properties) {
        logger.info("RocketMQ message producer is init ...");
        if (!properties.validate()) {
            logger.info("message producer config error");
            return false;
        }
        this.properties = properties;
        logger.info("RocketMQ message producer has finished init...");
        return true;
    }

    public boolean start() {
        logger.info("RocketMQ message producer is starting ...");
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.GROUP_ID, this.properties.getGroupId());
        properties.put(PropertyKeyConst.AccessKey, this.properties.getAccessKeyId());
        properties.put(PropertyKeyConst.SecretKey, this.properties.getAccessKeyId());
        properties.put(PropertyKeyConst.SendMsgTimeoutMillis, this.properties.getRequestTimeout());
        properties.put(PropertyKeyConst.NAMESRV_ADDR, this.properties.getNameSrvAddr());
        properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.CLUSTERING);
        try {
            this.producer = ONSFactory.createProducer(properties);
            this.producer.start();
        } catch (Exception ex) {
            logger.error("RocketMQ create and start message producer failed", ex);
            return false;
        }
        logger.info("RocketMQ message producer has been started");
        return true;
    }

    public boolean stop() {
        logger.info("RocketMQ message producer is destroying ...");
        if (this.producer != null) {
            this.producer.shutdown();
        }

        logger.info("RocketMQ message producer has been destroyed");
        return true;
    }

    public String sendMessage(String topic, String tags, byte[] content) {
        Message message = new Message(topic, tags, content);
        return sendMessage(message);
    }

    public String sendMessage(String topic, String tags, String key, byte[] content) {
        Message message = new Message(topic, tags, key, content);
        return sendMessage(message);
    }

    private String sendMessage(Message message) {
        Retryer<String> retryer = RetryerBuilder.<String>newBuilder()
                .retryIfException()
                .retryIfResult(Predicates.isNull())
                .withWaitStrategy(WaitStrategies.fixedWait(this.properties.getRequestTimeout(), TimeUnit.SECONDS))
                .withStopStrategy(StopStrategies.stopAfterAttempt(this.properties.getRetryCnt()))
                .build();
        String msgId = null;
        try {
            msgId = retryer.call(() -> {
                SendResult result = this.producer.send(message);
                if (result != null) {
                    logger.debug("send message[{}] success to topic[{}] and tags[{}]", new Object[]{result.getMessageId(), message.getTopic(), message.getTag()});
                }

                return result == null ? null : result.getMessageId();
            });
        } catch (ExecutionException | RetryException e) {
            logger.error("send message to topic[{}] and tags[{}] failed", message.getTopic(), message.getTag(), e);
        }
        return msgId;
    }
}
