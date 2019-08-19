package com.snapdeal.ums.admin.server.services.ext.csadmin;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.user.ZendeskUserSRO;

public class ZendeskUserResponse extends ServiceResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 3734273092870499993L;
    /**
     * 
     */
    @Tag(5)
    private ZendeskUserSRO    getZendeskUser;

    public ZendeskUserResponse() {
    }

    public ZendeskUserResponse(ZendeskUserSRO getZendeskUser) {
        super();
        this.getZendeskUser = getZendeskUser;
    }

    public ZendeskUserSRO getGetZendeskUser() {
        return getZendeskUser;
    }

    public void setGetZendeskUser(ZendeskUserSRO getZendeskUser) {
        this.getZendeskUser = getZendeskUser;
    }

}
