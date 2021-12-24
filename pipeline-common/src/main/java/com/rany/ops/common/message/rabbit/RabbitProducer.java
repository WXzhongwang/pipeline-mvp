package com.rany.ops.common.message.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost(properties.getHost());
            connectionFactory.setPort(properties.getPort());
            connectionFactory.setUsername(properties.getUsername());
            connectionFactory.setPassword(properties.getPassword());
            connection = connectionFactory.newConnection();

        } catch (Exception ex) {
            logger.error("RabbitMQ create and start message producer failed", ex);
            return false;
        }
        logger.info("RabbitMQ message producer has been started");
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
