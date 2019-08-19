package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class DeleteUserRoleByIdRequest extends ServiceRequest {

    /**
     * 
     */
    private static final long serialVersionUID = -7799413844670048658L;
    
    @Tag(3)
    private Integer userRoleId;
    
    public DeleteUserRoleByIdRequest(){
        super();
    }
    public DeleteUserRoleByIdRequest(Integer userRoleId) {
        super();
        this.userRoleId = userRoleId;
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }
    
}
