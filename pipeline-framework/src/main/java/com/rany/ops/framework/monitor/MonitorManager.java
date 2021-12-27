package com.rany.ops.framework.monitor;

import com.alibaba.fastjson.JSONObject;
import com.rany.ops.common.alert.DingConstants;
import com.rany.ops.common.alert.DingMsgRequest;
import com.rany.ops.common.alert.DingUtils;
import com.rany.ops.framework.config.MonitorConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 告警
 *
 * @author dick
 * @description TODO
 * @date 2021/12/25 11:47 下午
 * @email 18668485565@163.com
 */

public class MonitorManager {

    private static final Logger logger = LoggerFactory.getLogger(MonitorManager.class);
    private static final String HMAC_SHA256 = "HmacSHA256";
    private static final String UTF8_ENCODING = "UTF-8";
    private static final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final ConcurrentLinkedQueue<Alarm> queue = new ConcurrentLinkedQueue<>();
    /**
     * 告警配置
     */
    private MonitorConfig monitorConfig;

    public MonitorManager(MonitorConfig monitorConfig) {
        this.monitorConfig = monitorConfig;
    }

    private String generateDingTalkUrl() {
        if (StringUtils.isEmpty(monitorConfig.getDingTalkSecret())) {
            return monitorConfig.getDingTalkUrl();
        }
        long timestamp = System.currentTimeMillis();
        String string = timestamp + "\n" + monitorConfig.getDingTalkSecret();
        String sign = null;
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256);
            mac.init(new SecretKeySpec(monitorConfig.getDingTalkSecret().getBytes(UTF8_ENCODING), HMAC_SHA256));
            byte[] signData = mac.doFinal(string.getBytes(UTF8_ENCODING));
            sign = URLEncoder.encode(new String(Base64.getEncoder().encode(signData)), UTF8_ENCODING);

        } catch (Exception e) {
            logger.error("exception occurred, cause[{}]", e.getMessage(), e);
            return monitorConfig.getDingTalkUrl();
        }
        return String.format("%s&timestamp=%d&sign=%s", monitorConfig.getDingTalkUrl(), timestamp, sign);
    }

    public boolean start() {
        executor.scheduleAtFixedRate(() -> {
            List<Alarm> alarmsFromQueue = this.getAlarmsFromQueue();
            List<DingMsgRequest> msgRequests = this.combineAlarms(alarmsFromQueue);
            for (DingMsgRequest msgRequest : msgRequests) {
                DingUtils.send(msgRequest);
            }
        }, 1000, 5000, TimeUnit.MILLISECONDS);
        return true;
    }

    private List<Alarm> getAlarmsFromQueue() {
        List<Alarm> alertEntities = new ArrayList();
        do {
            Alarm alarm = queue.poll();
            if (alarm == null) {
                break;
            }
            alertEntities.add(alarm);
        } while (alertEntities.size() < 30);
        return alertEntities;
    }

    private List<DingMsgRequest> combineAlarms(List<Alarm> alarms) {
        List<Alarm> collect = alarms.stream().sorted(new Comparator<Alarm>() {
            @Override
            public int compare(Alarm o1, Alarm o2) {
                return o1.getTimestamp().before(o2.getTimestamp()) ? 1 : -1;
            }
        }).collect(Collectors.toList());

        Map<String, List<Alarm>> alarmMap = new HashMap<>();
        for (Alarm alarm : collect) {
            String key = alarm.buildKey();
            if (!alarmMap.containsKey(key)) {
                alarmMap.put(key, new ArrayList<>());
            }
            List<Alarm> value = alarmMap.get(key);
            value.add(alarm);
        }
        List<DingMsgRequest> msgRequests = new ArrayList<>();
        String alertUrl = generateDingTalkUrl();
        for (Map.Entry<String, List<Alarm>> entry : alarmMap.entrySet()) {
            msgRequests.add(generateDingMessage(alertUrl, entry.getValue()));
        }
        return msgRequests;
    }

    private DingMsgRequest generateDingMessage(String url, List<Alarm> alarms) {
        Alarm startAlarm = alarms.stream().findFirst().get();
        Alarm lastAlarm = alarms.stream().limit(alarms.size() - 1).findFirst().get();
        int size = alarms.size();
        String appName = startAlarm.getAppName();
        String content = startAlarm.getContent();
        Date startTime = lastAlarm.getTimestamp();
        Date endTime = startAlarm.getTimestamp();
        String title = String.format("%s 于 %s 至 %s 发生告警通知，等级[%s]\n",
                appName, FORMAT.format(startTime), FORMAT.format(endTime), startAlarm.getType());
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(title);
        messageBuilder.append(content).append("\n");
        String times = String.format("累计报警次数: [%d]\n",
                size);
        messageBuilder.append(times).append("\n");

        JSONObject request = new JSONObject();
        request.put(DingConstants.DING_REQ_KEY_MSG_TYPE, DingConstants.DING_MSG_TYPE_TEXT);
        JSONObject text = new JSONObject();
        text.put(DingConstants.TEXT_REQ_KEY_CONTENT, messageBuilder.toString());
        request.put(DingConstants.DING_REQ_KEY_TEXT, text);
        request.put(DingConstants.TEXT_REQ_KEY_AT, new JSONObject()
                .fluentPut(DingConstants.TEXT_REQ_KEY_AT_ALL, false)
                .fluentPut(DingConstants.TEXT_REQ_KEY_AT_MOBILES, monitorConfig.getMobiles()));
        return new DingMsgRequest(url, request);
    }
}
