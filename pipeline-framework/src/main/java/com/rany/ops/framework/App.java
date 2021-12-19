package com.rany.ops.framework;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.rany.ops.framework.admin.Admin;
import com.rany.ops.framework.config.ApplicationConfig;
import com.rany.ops.framework.config.ResourceConfig;
import com.rany.ops.framework.core.constants.Constants;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 应用程序框架入口
 *
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
        JSON.DEFAULT_PARSER_FEATURE &= ~Feature.UseBigDecimal.getMask();
        Options options = new Options();
        options.addOption(Option.builder("h").longOpt("help").desc("help information for program pipeline app").build());
        options.addOption(Option.builder("c").longOpt("config").desc("config file path for program, required param").build());
        options.addOption(Option.builder("r").longOpt("res").desc("res file path for program, required param").build());
        CommandLineParser parser = new DefaultParser();
        HelpFormatter helpFormatter = new HelpFormatter();
        try {
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("h")) {
                helpFormatter.printHelp("pipeline", options, true);
                System.exit(0);
            }
            String configFile = line.getOptionValue("c");
            if (StringUtils.isEmpty(configFile)) {
                helpFormatter.printHelp("pipeline", options, true);
                System.exit(0);
            }
            String resourceFile = line.getOptionValue("r");
            if (StringUtils.isEmpty(resourceFile)) {
                helpFormatter.printHelp("pipeline", options, true);
                System.exit(0);
            }
            File config = new File(configFile);
            File resource = new File(resourceFile);
            String configContent = FileUtils.readFileToString(config, Charset.forName("UTF-8"));
            ApplicationConfig applicationConfig = JSON.parseObject(configContent, ApplicationConfig.class);
            String resourceContent = FileUtils.readFileToString(resource, Charset.forName("UTF-8"));
            List<ResourceConfig> resourceConfigList = JSON.parseObject(resourceContent,
                    new TypeReference<List<ResourceConfig>>() {
                    });
            applicationConfig.setResourceConfigList(resourceConfigList);
            Admin admin = new Admin(applicationConfig);
            String name = applicationConfig.getApp().getName();
            if (!admin.init()) {
                logger.error("init pipeline app [{}] failed", name);
                System.exit(1);
            }
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                logger.info("shutdown hook thread is running");
                if (!admin.shutdown()) {
                    logger.error("stop pipeline app scheduler[{}] failed", name);
                }
                admin.shutdown();
                activate();
            }));
            if (!admin.startUp()) {
                logger.error("start pipeline app[{}] failed", name);
                System.exit(1);
            }
        } catch (Exception ex) {
            logger.error("start pipeline app failed", ex);
            helpFormatter.printHelp("pipeline", options, true);
            System.exit(1);
        }

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
