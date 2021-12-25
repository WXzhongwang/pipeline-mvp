package com.rany.ops.plugin.source;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rany.ops.common.message.rabbit.RabbitConsumerProperties;
import com.rany.ops.framework.core.source.Source;
import com.rany.ops.framework.kv.KvRecord;
import com.rany.ops.framework.kv.KvRecords;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Rabbit consumer source
 *
 * @author dick
 * @description TODO
 * @date 2021/12/25 10:56 下午
 * @email 18668485565@163.com
 */

public class RabbitConsumerSource extends Source {

    private Connection connection;

    private Channel channel;

    private RabbitConsumerProperties properties;

    protected RabbitConsumerSource(String name) {
        super(name);
    }

    @Override
    protected KvRecord doExecute(KvRecord o) {
        return null;
    }

    @Override
    public boolean init(Map<String, Object> config) {
        JSONObject configMap = new JSONObject(config);
        properties = JSON.toJavaObject(configMap, RabbitConsumerProperties.class);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(properties.getHost());
        factory.setAutomaticRecoveryEnabled(true);
        factory.setNetworkRecoveryInterval(5000);
        // 设置Vhost名称，请确保已在消息队列RabbitMQ版控制台上创建完成。
        factory.setVirtualHost(properties.getVirtualHost());
        // 默认端口，非加密端口5672，加密端口5671。
        factory.setPort(properties.getPort());
        factory.setUsername(properties.getUsername());
        factory.setPassword(properties.getPassword());
        factory.setConnectionTimeout(300 * 1000);
        factory.setHandshakeTimeout(300 * 1000);
        factory.setShutdownTimeout(0);
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            // 创建${ExchangeName}，该Exchange必须在消息队列RabbitMQ版控制台上已存在，并且Exchange的类型与控制台上的类型一致。
            AMQP.Exchange.DeclareOk exchangeDeclareOk = channel.exchangeDeclare(properties.getExchangeName(),
                    properties.getExchangeType(), true, false, false, null);
            // 创建${QueueName} ，该Queue必须在消息队列RabbitMQ版控制台上已存在。
            AMQP.Queue.DeclareOk queueDeclareOk = channel.queueDeclare(properties.getQueueName(), true, false, false, new HashMap<String, Object>());
            // Queue与Exchange进行绑定，并确认绑定的BindingKeyTest。
            AMQP.Queue.BindOk bindOk = channel.queueBind(properties.getQueueName(),
                    properties.getExchangeName()
                    , properties.getBindingKey());
        } catch (IOException | TimeoutException exception) {
            logger.error("rabbit consumer error", exception);
            return false;
        }
        return true;
    }

    @Override
    public boolean start() {
        // 开始消费消息。
        try {
            channel.basicConsume(properties.getQueueName(), false, properties.getConsumeTag(), new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    // 接收到的消息，进行业务逻辑处理。
                    String message = new String(body, StandardCharsets.UTF_8);
                    JSONObject messageObject = new JSONObject()
                            .fluentPut("message", message)
                            .fluentPut("deliveryTag", envelope.getDeliveryTag())
                            .fluentPut("messageId", properties.getMessageId());
                    KvRecords kvRecords = convertor.convert(messageObject);
                    for (int i = 0; i < kvRecords.getRecordCnt(); i++) {
                        KvRecord record = kvRecords.getRecord(i);
                        execute(record.copy());
                    }
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            });
        } catch (IOException exception) {
            logger.error("consumer start failed...", exception);
            return false;
        }
        return super.start();
    }

    @Override
    public boolean stop() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (IOException exception) {
            logger.error("connection close failed...");
            return false;
        }
        return super.stop();
    }
}
