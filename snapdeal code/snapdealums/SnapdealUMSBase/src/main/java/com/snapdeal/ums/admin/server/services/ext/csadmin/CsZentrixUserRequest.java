
package com.snapdeal.ums.admin.server.services.ext.csadmin;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.CsZentrixSRO;

public class CsZentrixUserRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 4996665426895910566L;
    @Tag(3)
    private int userId;
    
    @Tag(4)
    private CsZentrixSRO cs;

    public CsZentrixUserRequest() {
        super();
    }
    
    public CsZentrixUserRequest(CsZentrixSRO cs){
        super();
        this.cs=cs;
    }
    
    public CsZentrixUserRequest(int userId){
        super();
        this.userId=userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public CsZentrixSRO getCs() {
        return cs;
    }

    public void setCs(CsZentrixSRO cs) {
        this.cs = cs;
    }

}
