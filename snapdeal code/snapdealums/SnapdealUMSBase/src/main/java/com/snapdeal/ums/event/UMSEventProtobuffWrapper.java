/*
 * Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved. JASPER
 * INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snapdeal.ums.event;

/**
 * Wrapper used to encapsulate UMS event during protobuff serialization /
 * de-serialization.
 * 
 * @author Umesh Kumar
 */
public class UMSEventProtobuffWrapper
{

    private UMSEvent myWrappedUMSEvent;

    public UMSEvent getWrappedUMSEvent()
    {

        return myWrappedUMSEvent;
    }

    public void setWrappedUMSEvent(UMSEvent event)
    {

        myWrappedUMSEvent = event;
    }
}
