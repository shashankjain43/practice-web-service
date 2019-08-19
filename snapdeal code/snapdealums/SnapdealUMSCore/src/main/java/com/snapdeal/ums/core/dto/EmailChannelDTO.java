/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *  @version     1.0, Apr 26, 2012
 *  @author amit
 */
package com.snapdeal.ums.core.dto;

import java.io.Serializable;

import com.snapdeal.base.entity.EmailChannel;

public class EmailChannelDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5766228360147156436L;

    private int               channelID;

    private String            channelName;

    public EmailChannelDTO() {

    }

    public EmailChannelDTO(EmailChannel channel) {
        this.channelID = channel.getId();
        this.channelName = channel.getName();
    }

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

}

