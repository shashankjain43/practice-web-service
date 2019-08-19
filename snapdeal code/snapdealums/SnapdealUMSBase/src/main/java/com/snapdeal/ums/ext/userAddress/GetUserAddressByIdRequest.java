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

public class GetUserAddressByIdRequest extends ServiceRequest{

    /**
     * 
     */
    private static final long serialVersionUID = -2537286662742387964L;
    
    @Tag(3)
    private Integer userAddressId;

    
    public GetUserAddressByIdRequest() {
        super();
    }
    
    public GetUserAddressByIdRequest(Integer userAddressId) {
        super();
        this.userAddressId = userAddressId;
    }

    /**
     * @return the userAddressId
     */
    public Integer getUserAddressId() {
        return userAddressId;
    }

    /**
     * @param userAddressId the userAddressId to set
     */
    public void setUserAddressId(Integer userAddressId) {
        this.userAddressId = userAddressId;
    }
    
}
