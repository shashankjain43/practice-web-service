/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 15-Oct-2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.ext.userAddress;

import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.user.UserAddressSRO;

public class GetUserAddressesByUserIdResponse extends ServiceResponse{

    /**
     * 
     */
    private static final long serialVersionUID = -1942820722748438284L;

    @Tag(5)
    private List<UserAddressSRO> userAddresses;
    
    public GetUserAddressesByUserIdResponse(){
        super();
    }

    /**
     * @return the userAddresses
     */
    public List<UserAddressSRO> getUserAddresses() {
        return userAddresses;
    }

    /**
     * @param userAddresses the userAddresses to set
     */
    public void setUserAddresses(List<UserAddressSRO> userAddresses) {
        this.userAddresses = userAddresses;
    }
    
}
