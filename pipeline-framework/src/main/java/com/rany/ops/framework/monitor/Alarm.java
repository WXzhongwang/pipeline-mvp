package com.rany.ops.framework.monitor;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * 告警
 *
 * @author dick
 * @description 告警
 * @date 2021/12/27 9:18 下午
 * @email 18668485565@163.com
 */

public class Alarm implements Serializable {

    public static final String ALERT_TYPE_INFO = "[通知]";
    public static final String ALERT_TYPE_ERROR = "[错误]";
    public static final String ALERT_TYPE_FATAL = "[严重]";

    private String appName;
    private String type = ALERT_TYPE_INFO;
    private String content;
    private Date timestamp;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String buildKey() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(content.getBytes());
            return String.format("%s_%s_%d", appName, type, new BigInteger(1, md.digest()).toString(16));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
