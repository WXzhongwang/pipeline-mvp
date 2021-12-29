package com.rany.ops.framework.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 插件级别监控
 *
 * @author dick
 * @description 插件级别监控
 * @date 2021/12/27 9:15 下午
 * @email 18668485565@163.com
 */

public class Monitor {

    private static final Logger logger = LoggerFactory.getLogger(Monitor.class);

    private static final String DEFAULT_THREAD_NAME_PREFIX = "monitor";

    private Thread workThread;
    private boolean running = false;
    private String appName;
    private String component;

    /**
     * 应用名
     * 插件名
     *
     * @param appName
     * @param component
     */
    public Monitor(String appName, String component) {
        this.appName = appName;
        this.component = component;
    }

    public boolean start() {
        String workThreadName = String.format("%s_%s", DEFAULT_THREAD_NAME_PREFIX, component);
        running = true;
        workThread = new Thread(new Report(), workThreadName);
        workThread.start();
        return true;
    }

    public void stop() {
        logger.info("monitor is stopping ...");
        if (!running || workThread == null || !workThread.isAlive()) {
            logger.warn("monitor has been stopped by other, ignore stopping request");
            return;
        }
        running = false;
        if (workThread != null && workThread.isAlive()) {
            workThread.interrupt();
        }
        try {
            if (workThread != null) {
                workThread.join();
            }
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("monitor has been stopped");
    }

    static class Report implements Runnable {

        @Override
        public void run() {
        }

        /**
         * 性能指标上报
         */
        private void report() {
            // no implements now
        }
    }

    /**
     * 告警处理
     *
     * @param name      插件名称
     * @param type      告警等级
     * @param message   告警消息
     * @param throwable 异常
     */
    public void sendAlarm(String name, String type, String message, Throwable throwable) {
        logger.error("[{}] occur exception, exception level [{}], message [{}]",
                name, type, message, throwable);
        String content = String.format("[%s] occur exception, message [%s]",
                name, message);
        Alarm alarm = new Alarm();
        alarm.setAppName(type);
        alarm.setType(type);
        alarm.setContent(content);
        alarm.setTimestamp(new Date());
        MonitorManager.queue.offer(alarm);
    }
}
