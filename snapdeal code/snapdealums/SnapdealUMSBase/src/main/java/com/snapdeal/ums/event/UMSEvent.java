/*
 * Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved. JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is
 * subject to license terms.
 */

package com.snapdeal.ums.event;

/**
 * @author ashish saxena
 */
public interface UMSEvent
{
//    public EventType getEventType();

    public long getPublishTimestamp();

    public interface Handler<T extends UMSEvent>
    {
        void handleEvent(T e);
    }
}
