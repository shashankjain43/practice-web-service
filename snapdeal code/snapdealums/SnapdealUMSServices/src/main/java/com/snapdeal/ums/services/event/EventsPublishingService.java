/*
 * Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved. JASPER
 * INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snapdeal.ums.services.event;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.ums.core.cache.UMSPropertiesCache;
import com.snapdeal.ums.event.ProtoBufferWiringTool;
import com.snapdeal.ums.event.SnapBoxActivationEvent;
import com.snapdeal.ums.event.UMSEvent;
import com.snapdeal.ums.event.UMSEventProtobuffWrapper;

/**
 * Service used for publishing events
 * 
 * @author Ashish
 */
@Service
public class EventsPublishingService
{

    private static final Logger LOG = LoggerFactory.getLogger(EventsPublishingService.class);

    @Autowired
    protected JmsTemplate jmsTemplateESB;

    @Autowired
    private ActiveMQConnectionFactory activeMQConnectionFactory;

    private class UMSEventMessageCreator implements MessageCreator
    {

        private final UMSEvent myEvent;

        public UMSEventMessageCreator(UMSEvent event)
        {

            myEvent = event;
        }

        @Override
        public Message createMessage(Session session) throws JMSException
        {

            UMSEventProtobuffWrapper eventWrapper = new UMSEventProtobuffWrapper();
            eventWrapper.setWrappedUMSEvent(myEvent);

            byte[] objectBytes = ProtoBufferWiringTool.serialize(eventWrapper, UMSEventProtobuffWrapper.class);

            BytesMessage msg = session.createBytesMessage();
            msg.writeBytes(objectBytes);

            return msg;
        }
    }

    public void publishSnapBoxActivationEvent(SnapBoxActivationEvent event) throws JmsException
    {

        initializeConnectionFactory();
        LOG.info("Publishing {} on destination: {}", event, jmsTemplateESB.getDefaultDestination());
        try {
            jmsTemplateESB.send(new UMSEventMessageCreator(event));
        }
        catch (JmsException jmse) {
            LOG.warn("Failed to publish event: {}, {}", event, ExceptionUtils.getFullStackTrace(jmse));
            throw jmse;
        }
    }

    private void initializeConnectionFactory()
    {

        //
        // <property name="brokerURL"
        // value="tcp://54.251.153.22:61616?wireFormat.maxInactivityDuration=0"
        // />
        // <property name="userName" value="snapdeal_coms_esb" />
        // <property name="password" value="coms_esb" />
        //

        UMSPropertiesCache propertiesCache = CacheManager.getInstance().getCache(UMSPropertiesCache.class);
        String brokerURL = "tcp://" + propertiesCache.getActiveMQURL() + ":" + propertiesCache.getActiveMQPort()
            + "?wireFormat.maxInactivityDuration=0";

        activeMQConnectionFactory.setBrokerURL(brokerURL);
        activeMQConnectionFactory.setUserName(propertiesCache.getActiveMQUsername());
        activeMQConnectionFactory.setPassword(propertiesCache.getActiveMQpassword());

        LOG.info("Initializing activeMQ connection factory to:" + brokerURL);

    }
}
