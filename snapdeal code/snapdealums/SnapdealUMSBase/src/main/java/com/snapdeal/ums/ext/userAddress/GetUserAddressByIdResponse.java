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

public class GetUserAddressByIdResponse extends ServiceResponse{

    /**
     * 
     */
    private static final long serialVersionUID = 9083789368806776351L;
    
    @Tag(5)
    private UserAddressSRO userAddressSRO;
    
    public GetUserAddressByIdResponse(){
        super();
    }

    /**
     * @return the userAddressSRO
     */
    public UserAddressSRO getUserAddressSRO() {
        return userAddressSRO;
    }

    /**
     * @param userAddressSRO the userAddressSRO to set
     */
    public void setUserAddressSRO(UserAddressSRO userAddressSRO) {
        this.userAddressSRO = userAddressSRO;
    }
    
}
