
package com.snapdeal.ums.admin.server.services.ext.csadmin;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.ZendeskUserSRO;

public class ZendeskUserRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -4494989024867031353L;
    /**
     * 
     */
    @Tag(3)
    private int userId;
    
    @Tag(4)
    private ZendeskUserSRO zendeskUser;
    

    public ZendeskUserRequest() {
        super();
    }
    
    public ZendeskUserRequest(ZendeskUserSRO zendeskUserSRO){
        super();
        this.zendeskUser=zendeskUserSRO;
    }

    public ZendeskUserRequest(int userId){
        super();
        this.userId=userId;
    }
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ZendeskUserSRO getZendeskUser() {
        return zendeskUser;
    }

    public void setZendeskUser(ZendeskUserSRO zendeskUser) {
        this.zendeskUser = zendeskUser;
    }

}
