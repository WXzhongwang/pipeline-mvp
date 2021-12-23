package com.rany.ops.plugin.source;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.PropertyValueConst;
import com.rany.ops.common.message.rocket.MqConsumerProperties;
import com.rany.ops.framework.core.source.Source;
import com.rany.ops.framework.kv.KvRecord;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * RocketMQ source
 *
 * @author zhongshengwang
 * @description TODO
 * @date 2021/12/23 10:53 下午
 * @email zhongshengwang@shuwen.com
 */

public class RocketSource extends Source {

    private Consumer consumer;

    public RocketSource(String name) {
        super(name);
    }

    @Override
    protected KvRecord doExecute(KvRecord kvRecord) {
        return kvRecord;
    }

    @Override
    public boolean init(Map<String, Object> config) {
        JSONObject configMap = new JSONObject(config);
        MqConsumerProperties consumerConfig = JSON.toJavaObject(configMap, MqConsumerProperties.class);
        Properties properties = new Properties();
        if (!StringUtils.isEmpty(consumerConfig.getNameSrvAddr())) {
            properties.put(PropertyKeyConst.NAMESRV_ADDR, consumerConfig.getNameSrvAddr());
        }
        properties.put(PropertyKeyConst.GROUP_ID, consumerConfig.getGroupId());
        properties.put(PropertyKeyConst.AccessKey, consumerConfig.getAccessKeyId());
        properties.put(PropertyKeyConst.SecretKey, consumerConfig.getAccessKeySecret());
        properties.put(PropertyKeyConst.ConsumeThreadNums, consumerConfig.getConsumeThreadNums());
        properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.CLUSTERING);
        if (!Objects.isNull(consumerConfig.getMaxCachedMessageAmount())) {
            properties.put(PropertyKeyConst.MaxCachedMessageAmount, consumerConfig.getMaxCachedMessageAmount());
        }
        try {
            consumer = ONSFactory.createConsumer(properties);
            logger.info("RocketMQ group id [{}], topic [{}], tags [{}]", consumerConfig.getGroupId(),
                    consumerConfig.getTopic(), consumerConfig.getTags());
            consumer.subscribe(consumerConfig.getTopic(), consumerConfig.getTags(), (message, consumeContext) -> {
                try {
                    KvRecord kvRecord = new KvRecord();
                    // TODO: message convert to kv
                    this.execute(kvRecord);
                    return Action.CommitMessage;
                } catch (Throwable e) {
                    logger.error("process RocketMQ data failed for topic[{}] and msgId[{}], cause[{}]",
                            message.getTopic(), message.getMsgID(), e.getMessage());
                    logger.error(e.getMessage(), e);
                    return Action.ReconsumeLater;
                }
            });

        } catch (Exception e) {
            logger.error("create and start RocketMQ receiver failed", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean start() {
        logger.info("RocketMQ receiver is starting ...");
        consumer.start();
        logger.info("RocketMQ receiver start success ...");
        return true;
    }

    @Override
    public boolean stop() {
        logger.info("RocketMQ receiver is stopping ...");
        if (consumer != null) {
            consumer.shutdown();
        }
        logger.info("RocketMQ receiver has been stopped");
        return super.stop();
    }
}
