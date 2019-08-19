package com.snapdeal.cps.publisher.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 *
 */
public class Main {

    
    private static final Logger log = LoggerFactory.getLogger("googleDataFeed.logger");
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("application-context.xml");
        log.info("Loading configurations from application-context");

        appContext.registerShutdownHook();
        log.info("Registered shutdown hook");
    }

}
