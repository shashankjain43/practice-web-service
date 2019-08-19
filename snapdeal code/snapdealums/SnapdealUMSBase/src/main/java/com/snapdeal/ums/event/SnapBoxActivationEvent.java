/*
 * Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved. JASPER
 * INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snapdeal.ums.event;

/**
 * Event used to disseminate information about activation of snapbox loyalty
 * program for a user
 * 
 * @author ashish saxena
 */
public class SnapBoxActivationEvent extends AbstractUMSEvent
{

    // private final List<SUPCProductMapping> myNewProductMappings = new
    // ArrayList<>();

    private final String userEmailID;

    public SnapBoxActivationEvent(long eventPublishTimestamp, String userEmailID)
    {

        super(eventPublishTimestamp);
        this.userEmailID = userEmailID;
    }

    @Override
    protected String getEventDescription()
    {

        StringBuilder sb = new StringBuilder();
        sb.append("emailID: ").append(userEmailID);
        return sb.toString();
    }

    public String getUserEmailID()
    {

        return userEmailID;
    }

}
