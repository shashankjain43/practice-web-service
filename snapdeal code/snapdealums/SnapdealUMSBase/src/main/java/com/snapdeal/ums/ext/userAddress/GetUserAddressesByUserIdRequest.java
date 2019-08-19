/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 15-Oct-2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.ext.userAddress;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetUserAddressesByUserIdRequest extends ServiceRequest{

    /**
     * 
     */
    private static final long serialVersionUID = -6285396953186903493L;

    @Tag(3)
    private Integer userId;
    
    public GetUserAddressesByUserIdRequest(){
        super();
    }

    public GetUserAddressesByUserIdRequest(Integer userId) {
        super();
        this.userId = userId;
    }

    /**
     * @return the userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
}
