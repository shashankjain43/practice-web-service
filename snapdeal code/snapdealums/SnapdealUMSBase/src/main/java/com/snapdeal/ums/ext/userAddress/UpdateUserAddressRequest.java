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
import com.snapdeal.ums.core.sro.user.UserAddressSRO;

public class UpdateUserAddressRequest extends ServiceRequest{
    
    /**
     * 
     */
    private static final long serialVersionUID = 46548191228580013L;
    
    @Tag(3)
    private UserAddressSRO userAddressSRO;
    
    @Tag(4)
    private String encryptedUserId;
    
    public UpdateUserAddressRequest(){
        super();
    }

    public UpdateUserAddressRequest(UserAddressSRO userAddressSRO, String encryptedUserId) {
        super();
        this.userAddressSRO = userAddressSRO;
        this.encryptedUserId = encryptedUserId;
    }
    
    public UpdateUserAddressRequest(UserAddressSRO userAddressSRO) {
        super();
        this.userAddressSRO = userAddressSRO;
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

    /**
     * @return the encryptedUserId
     */
    public String getEncryptedUserId() {
        return encryptedUserId;
    }

    /**
     * @param encryptedUserId the encryptedUserId to set
     */
    public void setEncryptedUserId(String encryptedUserId) {
        this.encryptedUserId = encryptedUserId;
    }

}
