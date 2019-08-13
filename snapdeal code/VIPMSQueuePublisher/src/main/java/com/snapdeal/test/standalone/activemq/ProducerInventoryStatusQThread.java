/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 22-Jan-2013
 *  @author ajinkya
 */
package com.snapdeal.test.standalone.activemq;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snapdeal.ipms.base.queue.update.SUPCSellerUpdateEventsSRO;

public class ProducerInventoryStatusQThread implements Runnable {

    private static Session session;

    private static MessageProducer messageProducer;
    
    SUPCSellerUpdateEventsSRO supcSellerUpdateEventsSRO;

    private Logger LOG = LoggerFactory.getLogger(ProducerInventoryStatusQThread.class);

    public ProducerInventoryStatusQThread(SUPCSellerUpdateEventsSRO supcSellerUpdateEventsSRO) {
        this.supcSellerUpdateEventsSRO = supcSellerUpdateEventsSRO;
    }

    public void run() {

        try {
            ObjectMessage obj = session.createObjectMessage((Serializable) supcSellerUpdateEventsSRO);
            messageProducer.send(obj);
            System.out.println("sent");
        } catch (JMSException e) {
            LOG.error("Error in sending message : {}", e);
        }

    }

    public static Session getSession() {
        return session;
    }

    public static void setSession(Session session) {
        ProducerInventoryStatusQThread.session = session;
    }

    public static MessageProducer getMessageProducer() {
        return messageProducer;
    }

    public static void setMessageProducer(MessageProducer messageProducer) {
        ProducerInventoryStatusQThread.messageProducer = messageProducer;
    }

}
