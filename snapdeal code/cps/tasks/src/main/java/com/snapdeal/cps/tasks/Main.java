package com.snapdeal.cps.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 *
 */
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);
    
    public static void main(String[] args) {
        
        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("application-context.xml");
        log.info("Loading configurations from application-context");

        appContext.registerShutdownHook();
        log.info("Registered shutdown hook");
    }
}
