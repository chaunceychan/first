package com.tiza.chen.spring;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import com.tiza.chen.spring.batch.BatchInsert;
import com.tiza.chen.spring.nio.APConnector;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Descriptions
 * Created by Chauncey on 2014/7/15.
 */
public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static GuavaCacheManager cacheManager;

    public static void main(String[] args) {
        startLog();
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        cacheManager = (GuavaCacheManager) ctx.getBean("cacheManager");
        BatchInsert bid = (BatchInsert) ctx.getBean("batchInsert");
        bid.batchInsert();
        APConnector connector = (APConnector) ctx.getBean("connectContain");
        connector.run();
    }

    public static void startLog() {
        try {
            ILoggerFactory loggerFactory = LoggerFactory.getILoggerFactory();
            LoggerContext loggerContext = (LoggerContext) loggerFactory;
            loggerContext.reset();
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(loggerContext);
            configurator.doConfigure(ClassLoader.getSystemResource("logback.xml"));
            logger.info("成功启动日志记录器");
        } catch (Exception e) {
            System.out.println("启动日志记录器时发生错误:" + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }
}