package com.rany.ops.common.message.rabbit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * RabbitProducer
 * 未完，后续拓展
 *
 * @author dick
 * @description RabbitProducer
 * @date 2021/12/24 10:33 下午
 * @email 18668485565@163.com
 */

public class RabbitProducer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitProducer.class);

    public RabbitProducer() {
    }

    private Connection connection;
    private Channel channel;

    private RabbitProducerProperties properties;

    public boolean init(RabbitProducerProperties properties) {
        logger.info("RabbitMQ message producer is init ...");
        if (!properties.validate()) {
            logger.info("message producer config error");
            return false;
        }
        this.properties = properties;
        logger.info("RabbitMQ message producer has finished init...");
        return true;
    }

    public boolean start() {
        logger.info("RabbitMQ message producer is starting ...");
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(properties.getHost());
            factory.setPort(properties.getPort());
            factory.setUsername(properties.getUsername());
            factory.setPassword(properties.getPassword());
            // 设置Vhost名称，请确保已在消息队列RabbitMQ版控制台上创建完成。
            factory.setVirtualHost(properties.getVirtualHost());
            //设置为true，开启Connection自动恢复功能；设置为false，关闭Connection自动恢复功能。
            factory.setAutomaticRecoveryEnabled(true);
            factory.setNetworkRecoveryInterval(5000);
            factory.setConnectionTimeout(300 * 1000);
            factory.setHandshakeTimeout(300 * 1000);
            factory.setShutdownTimeout(0);
            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.exchangeDeclare(properties.getExchangeName(),
                    properties.getExchangeType(), true, false, false, null);
            channel.queueDeclare(properties.getQueueName(), true, false, false, new HashMap<String, Object>());
            channel.queueBind(properties.getQueueName(),
                    properties.getExchangeName(),
                    properties.getBindingKey());

        } catch (Exception ex) {
            logger.error("RabbitMQ create and start message producer failed", ex);
            return false;
        }
        logger.info("RabbitMQ message producer has been started");
        return true;
    }

    public boolean sendMessage(String message) {
        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().messageId(UUID.randomUUID().toString()).build();
        try {
            channel.basicPublish(properties.getExchangeName(), properties.getBindingKey(), true, props,
                    (message).getBytes(StandardCharsets.UTF_8));
        } catch (IOException exception) {
            logger.error("send rabbit message occur an error", exception);
            return false;
        }
        return true;
    }

    public boolean stop() {
        logger.info("RocketMQ message producer is destroying ...");
        try {
            if (this.channel != null) {
                this.channel.close();
            }
            if (this.connection != null) {
                this.connection.close();
            }
        } catch (IOException | TimeoutException e) {
            logger.error("RocketMQ message producer stop error", e);
            return false;
        }
        logger.info("RocketMQ message producer has been destroyed");
        return true;
    }
}
