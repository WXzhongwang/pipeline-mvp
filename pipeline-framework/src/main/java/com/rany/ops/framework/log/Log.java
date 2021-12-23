package com.rany.ops.framework.log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.rany.ops.framework.kv.KvRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 流程框架日志统一收口
 *
 * @author dick
 * @description 流程框架日志
 * @date 2021/12/18 9:50 下午
 * @email 18668485565@163.com
 */

public class Log {

    private Log() {
    }

    private static final Logger logger = LoggerFactory.getLogger(Log.class);

    private static final Set<String> SLS_LOG_PROPS = new HashSet<>();

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    static {
        SLS_LOG_PROPS.add(LoggerKeys.SLS_LOGGER_TIME);
        SLS_LOG_PROPS.add(LoggerKeys.SLS_PROCESS_TIME_MS);
        SLS_LOG_PROPS.add(LoggerKeys.SLS_START_PROCESS_TIME_MS);
        SLS_LOG_PROPS.add(LoggerKeys.SLS_PROCESS_PLUGINS);
        SLS_LOG_PROPS.add(LoggerKeys.SLS_PLUGIN_TIMES);
        SLS_LOG_PROPS.add(LoggerKeys.SLS_FILTER_PLUGIN);
        SLS_LOG_PROPS.add(LoggerKeys.SLS_DISCARD_PLUGIN);
        SLS_LOG_PROPS.add(LoggerKeys.SLS_EXCEPTION_PLUGIN);
        SLS_LOG_PROPS.add(LoggerKeys.SLS_ERROR_REASON);
        SLS_LOG_PROPS.add(LoggerKeys.SLS_FILTER_REASON);
        SLS_LOG_PROPS.add(LoggerKeys.SLS_DISCARD_REASON);
        SLS_LOG_PROPS.add(LoggerKeys.SLS_DATA_SUPPLEMENT);
    }

    private static String toJsonString(KvRecord kvRecord, List<String> loggerKeys) {
        JSONObject jsonObject = new JSONObject();
        for (String prop : SLS_LOG_PROPS) {
            if (kvRecord.has(prop)) {
                jsonObject.put(prop, kvRecord.get(prop));
            }
        }
        jsonObject.put(LoggerKeys.SLS_LOGGER_TIME, formatter.format(LocalDateTime.now()));
        if (loggerKeys == null) {
            return JSON.toJSONString(jsonObject, SerializerFeature.DisableCircularReferenceDetect);
        }
        for (String loggerKey : loggerKeys) {
            if (kvRecord.has(loggerKey)) {
                jsonObject.put(loggerKey, kvRecord.get(loggerKey));
            }
        }
        return JSON.toJSONString(jsonObject, SerializerFeature.DisableCircularReferenceDetect);
    }


    /**
     * 打印记录sls字段 json格式
     *
     * @param kvRecord   打印记录
     * @param loggerKeys 打印字段列表
     */
    public static void info(KvRecord kvRecord, List<String> loggerKeys) {
        logger.info("{}", toJsonString(kvRecord, loggerKeys));
    }

}
