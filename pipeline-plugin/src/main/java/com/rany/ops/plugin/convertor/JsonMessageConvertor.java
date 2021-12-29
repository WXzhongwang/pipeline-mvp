package com.rany.ops.plugin.convertor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Message;
import com.rany.ops.framework.core.MessageConvertor;
import com.rany.ops.framework.core.constants.Constants;
import com.rany.ops.framework.kv.KvRecord;
import com.rany.ops.framework.kv.KvRecords;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * JSON 消息转换器
 *
 * @author dick
 * @description 消息转换
 * @date 2021/12/24 9:13 下午
 * @email 18668485565@163.com
 */

public class JsonMessageConvertor extends MessageConvertor {

    private static final String KEY_MESSAGE_ID_KEY = "messageIdKey";
    private static final String KEY_FROM = "from";

    /**
     * 标识写入kv中的messageId字段名
     */
    private String messageIdKey;
    /**
     * 标识消息分支名称, 建议每个分支流出的消息都能有个分支
     */
    private String from;

    @Override
    public boolean init(Map<String, Object> configMap) {
        logger.info("json message parser is init ...");
        messageIdKey = configMap.containsKey(KEY_MESSAGE_ID_KEY) ?
                (String) configMap.get(KEY_MESSAGE_ID_KEY) : Constants.INTERNAL_KEY_MESSAGE_ID;
        from = configMap.containsKey(KEY_FROM) ? (String) configMap.get(KEY_FROM) : null;
        logger.info("json message parser has finished init");
        return true;
    }

    @Override
    public KvRecords convert(Object object) {
        Message message = (Message) object;
        if (message == null) {
            logger.error("message object type[{}] is not expected type[{}], discard this message",
                    object.getClass().getName(), Message.class.getName());
            return null;
        }
        String content = new String(message.getBody(), StandardCharsets.UTF_8);
        JSONObject messageObject = JSON.parseObject(content);
        KvRecord kvRecord = new KvRecord();
        for (Map.Entry<String, Object> entry : messageObject.entrySet()) {
            kvRecord.put(entry.getKey(), entry.getValue());
        }
        kvRecord.put(messageIdKey, message.getMsgID());
        if (!StringUtils.isEmpty(from)) {
            kvRecord.put(KEY_FROM, from);
        }
        KvRecords kvRecords = new KvRecords();
        kvRecords.addRecord(kvRecord);
        return kvRecords;
    }
}
