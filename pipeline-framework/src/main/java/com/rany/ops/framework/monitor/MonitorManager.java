package com.rany.ops.framework.monitor;

import com.alibaba.fastjson.JSONObject;
import com.rany.ops.common.alert.DingConstants;
import com.rany.ops.common.alert.DingMsgRequest;
import com.rany.ops.common.alert.DingUtils;
import com.rany.ops.framework.config.MonitorConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 告警
 *
 * @author dick
 * @description 告警
 * @date 2021/12/25 11:47 下午
 * @email 18668485565@163.com
 */

public class MonitorManager {

    private static final Logger logger = LoggerFactory.getLogger(MonitorManager.class);
    private static final String HMAC_SHA256 = "HmacSHA256";
    private static final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

    public static final Queue<Alarm> queue = new ConcurrentLinkedQueue<>();
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
            mac.init(new SecretKeySpec(monitorConfig.getDingTalkSecret().getBytes(StandardCharsets.UTF_8), HMAC_SHA256));
            byte[] signData = mac.doFinal(string.getBytes(StandardCharsets.UTF_8));
            sign = URLEncoder.encode(new String(Base64.getEncoder().encode(signData)), "UTF-8");

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
        List<Alarm> alertEntities = new ArrayList<>();
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
        List<Alarm> collect = alarms.stream().sorted((o1, o2) -> o1.getTimestamp().before(o2.getTimestamp()) ? 1 : -1).collect(Collectors.toList());
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Alarm startAlarm = alarms.stream().findFirst().get();
        Alarm lastAlarm = alarms.stream().limit(alarms.size() - 1).findFirst().get();
        int size = alarms.size();
        String appName = startAlarm.getAppName();
        String content = startAlarm.getContent();
        Date startTime = lastAlarm.getTimestamp();
        Date endTime = startAlarm.getTimestamp();
        String title = String.format("【%s】应用异常监控", appName);
        String contentHead = String.format("##【%s】告警，等级%s", appName, startAlarm.getType());
        String contentTail = String.format("#### 告警时间：%s至%s", format.format(startTime), format.format(endTime));
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(contentHead).append("\n\n");
        messageBuilder.append(content).append("\n\n");
        messageBuilder.append(contentTail).append("\n\n");
        String times = String.format("> 合并报警次数: [%d]", size);
        messageBuilder.append(times).append("\n");

        if (CollectionUtils.isNotEmpty(monitorConfig.getMobiles())) {
            for (String mobile : monitorConfig.getMobiles()) {
                messageBuilder.append("@").append(mobile);
            }
            messageBuilder.append("\n");
        }

        JSONObject request = new JSONObject();
        request.put(DingConstants.DING_REQ_KEY_MSG_TYPE, DingConstants.DING_MSG_TYPE_MARKDOWN);
        JSONObject markdown = new JSONObject();
        request.put(DingConstants.DING_REQ_KEY_MARKDOWN, markdown);
        markdown.put(DingConstants.MARKDOWN_REQ_KEY_TITLE, title);
        markdown.put(DingConstants.MARKDOWN_REQ_KEY_TEXT, messageBuilder.toString());
        request.put(DingConstants.MARKDOWN_REQ_KEY_AT, new JSONObject()
                .fluentPut(DingConstants.MARKDOWN_REQ_KEY_AT_ALL, false)
                .fluentPut(DingConstants.MARKDOWN_REQ_KEY_AT_MOBILES, monitorConfig.getMobiles()));
        return new DingMsgRequest(url, request);
    }
}
