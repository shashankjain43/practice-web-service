/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 15-Oct-2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.ext.userAddress;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.user.UserAddressSRO;

public class UpdateUserAddressResponse extends ServiceResponse{

    /**
     * 
     */
    private static final long serialVersionUID = 1595961587553492391L;

    @Tag(5)
    private UserAddressSRO userAddress;
    
    public UpdateUserAddressResponse(){
        super();
    }

    /**
     * @return the userAddress
     */
    public UserAddressSRO getUserAddress() {
        return userAddress;
    }

    /**
     * @param userAddress the userAddress to set
     */
    public void setUserAddress(UserAddressSRO userAddress) {
        this.userAddress = userAddress;
    }
    
}
