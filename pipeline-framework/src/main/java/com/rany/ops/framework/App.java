package com.rany.ops.framework;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.rany.ops.framework.config.ApplicationConfig;
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
        options.addOption(Option.builder("h").longOpt("help").
                desc("help information for program pipeline app").build());
        options.addOption(Option.builder("c").hasArg().desc("config file path for program").build());
        CommandLineParser parser = new DefaultParser();
        HelpFormatter helpFormatter = new HelpFormatter();
        try {
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("h") || !line.hasOption("c")) {
                helpFormatter.printHelp("pipeline", options, true);
                System.exit(0);
            }
            String configFile = line.getOptionValue("c");
            if (StringUtils.isEmpty(configFile)) {
                helpFormatter.printHelp("pipeline", options, true);
                System.exit(0);
            }
            File file = new File(configFile);
            String content = FileUtils.readFileToString(file, Charset.forName("UTF-8"));
            ApplicationConfig applicationConfig = JSON.parseObject(content, ApplicationConfig.class);
            
        } catch (Exception ex) {
            logger.error("start pipeline app failed", ex);
            helpFormatter.printHelp("pipeline", options, true);
            System.exit(1);
        }
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
