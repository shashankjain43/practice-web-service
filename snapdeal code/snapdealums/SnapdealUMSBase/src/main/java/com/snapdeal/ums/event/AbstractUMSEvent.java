/*
 * Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved. JASPER
 * INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snapdeal.ums.event;

import java.sql.Timestamp;

/**
 * Skeletal implementation for UMSEvent interface.
 * 
 */
public abstract class AbstractUMSEvent implements UMSEvent
{

    // private EventType myEventType;
    private long myPublishTimestamp;

    public AbstractUMSEvent()
    {

    }

    // public AbstractUMSEvent(EventType eventType, long publishTimestamp)
    // {
    // myEventType = eventType;
    // myPublishTimestamp = publishTimestamp;
    // }
    public AbstractUMSEvent(long publishTimestamp)
    {

        myPublishTimestamp = publishTimestamp;
    }

    // @Override
    // public EventType getEventType()
    // {
    // return myEventType;
    // }

    @Override
    public long getPublishTimestamp()
    {

        return myPublishTimestamp;
    }

    @Override
    public String toString()
    {

        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass())
            .append(": [")
            // .append("Event Type: ")
            // .append(myEventType)
            // .append(", Publish Timestamp: ")
            .append(" Publish Timestamp: ")

            .append(new Timestamp(myPublishTimestamp))

            .append(", ")
            .append(getEventDescription())
            .append("]");
        return sb.toString();
    }

    protected abstract String getEventDescription();
}
