package com.rany.ops.resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rany.ops.common.message.rocket.RocketProducer;
import com.rany.ops.common.message.rocket.RocketProducerProperties;
import com.rany.ops.framework.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * RocketMQ sender
 *
 * @author dick
 * @description RocketMQ sender
 * @date 2021/12/24 9:45 下午
 * @email 18668485565@163.com
 */

public class RocketProducerResource extends Resource<RocketProducer> {

    private static final Logger logger = LoggerFactory.getLogger(RocketProducerResource.class);

    public RocketProducerResource(String name) {
        super(name);
    }

    @Override
    protected RocketProducer create(Map<String, Object> config) {
        JSONObject jsonObject = new JSONObject(config);
        RocketProducerProperties properties = JSON.toJavaObject(jsonObject, RocketProducerProperties.class);
        if (properties == null) {
            logger.error("RocketMQ message producer config failed");
            return null;
        }
        if (!properties.validate()) {
            logger.error("RocketMQ message producer validate failed");
            return null;
        }
        RocketProducer producer = new RocketProducer();
        if (!producer.init(properties)) {
            logger.error("init RocketMQ message producer failed");
            return null;
        }
        if (!producer.start()) {
            logger.error("start RocketMQ message producer failed");
            return null;
        }
        logger.info("create message producer success");
        return null;
    }

    @Override
    public void destroy() {
        if (res != null && !res.stop()) {
            logger.error("stop message producer failed");
        }
        res = null;
    }
}
