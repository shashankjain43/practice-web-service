/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 22-Jan-2013
 *  @author ajinkya
 */
package com.snapdeal.test.standalone.activemq;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.snapdeal.ipms.base.common.ActiveMQConstants;

public class ProducerExecutor {

    private static Logger LOG              = LoggerFactory.getLogger(ProducerExecutor.class);

    public static int     mapCounter       = 0;

    private static int    threadRunCounter = 0;

    public static void main(String[] args) throws InterruptedException {

        int threadPoolSize = 10;
        int threshold = 1;

        //register publisher
        String queueName = "rankQueue";
        String url = "failover:(tcp://localhost:61616)";
        String userName = "user";
        String password = "user";
        
 /*       ArrayList<String> supcList = new ArrayList<String>();
		String[] supcArray = args[0].split(",");
		supcList.addAll(Arrays.asList(supcArray));*/

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);
        Connection connection = null;
        Session session = null;
        Destination queue = null;
        MessageProducer messProducer = null;
        try {
            connection = factory.createConnection(userName, password);
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            queue = session.createQueue(queueName);
            messProducer = session.createProducer(queue);
            messProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        } catch (JMSException e) {
            LOG.error("Could not create connection : {}", e);
        }

        ProducerThread.setSession(session);
        ProducerThread.setMessageProducer(messProducer);

        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
        for (int i = 0; i < threshold; i++) {
            executor.execute(new ProducerThread());
        }

        executor.shutdown();

        //You may need to change this time to termination if running a long task
        executor.awaitTermination(1, TimeUnit.DAYS);

        if (executor.isTerminated()) {
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

   /* public static void populateData(String filename, Map<Integer, MinPricingSRO> cachedMap) {
        try {
            FileReader fr = new FileReader(new File(filename));
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] sroArray = null;
            while ((line = br.readLine()) != null) {
                sroArray = line.split(",");
                // Require fields in the following order : supc, vendorCode, vendorSku, price, sellingPrice, vendorPrice
                try {
                    cachedMap.put(mapCounter, new MinPricingSRO(sroArray[0], sroArray[1], sroArray[2], sroArray[3], sroArray[4], sroArray[5]));
                    mapCounter++;
                } catch (Exception e) {
                    continue;
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/

    public synchronized static int getAndUpdateThreadRunCounter() {

        threadRunCounter++;

        return threadRunCounter - 1;
    }

}
