package com.rany.ops.framework;

import com.rany.ops.framework.core.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 应用程序框架入口
 * @author zhongshengwang
 * @description 应用程序框架入口
 * @date 2021/12/17 10:42 下午
 * @email 18668485565@163.com
 */

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    private static boolean waitFlag = true;
    private static Object waitObject = new Object();

    public static void main(String[] args) {
        logger.info("pipeline application is starting...");

        // TODO: 应用程序启动

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("shutdown hook thread is running");
            // TODO: 清理现场
            activate();
        }));

        synchronized (waitObject) {
            logger.info("pipeline application [{}] is waiting for stopping ...", Constants.SYSTEM_NAME);
            if (waitFlag) {
                try {
                    waitObject.wait();
                } catch (InterruptedException ignore) {
                }
            }
        }
        logger.info("pipeline application stop finished...");
    }

    public static void activate() {
        synchronized (waitObject) {
            waitObject.notifyAll();
            waitFlag = false;
        }
    }
}
