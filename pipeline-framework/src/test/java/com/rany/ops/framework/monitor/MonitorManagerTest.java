package com.rany.ops.framework.monitor;


import com.google.common.collect.Sets;
import com.rany.ops.framework.config.MonitorConfig;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MonitorManagerTest {

    @Test
    public void test() throws InterruptedException {
        MonitorConfig config = new MonitorConfig();
        config.setEnable(true);
        config.setDingTalkUrl("https://oapi.dingtalk.com/robot/send?access_token=b729a1e337c48afeef8f1f400aa75b08f8dc1f3366f0000223a6bef79a015");
        config.setDingTalkSecret("SEC2ac0e12b0e49330361d9f80bd98cfed198d48897c8b7f00a2d35925161f3e");
        config.setMobiles(Sets.newHashSet("1866848xxxx"));
        MonitorManager manager = new MonitorManager(config);
        manager.start();
        for (int i = 0; i < 30; i++) {
            Alarm alarm = new Alarm();
            alarm.setAppName("APP测试");
            alarm.setContent("图片处理异常");
            alarm.setTimestamp(new Date());
            alarm.setType(Alarm.ALERT_TYPE_ERROR);
            MonitorManager.queue.offer(alarm);
        }
        while (true) {
            TimeUnit.SECONDS.sleep(1);
        }
    }

}