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

public class AddUserAddressRequest extends ServiceRequest{

    /**
     * 
     */
    private static final long serialVersionUID = -6814079527901481053L;
    
    @Tag(3)
    private UserAddressSRO userAddress;
    
    @Tag(4)
    private String encryptedUserId;
    
    public AddUserAddressRequest(){
        super();
    }

    public AddUserAddressRequest(UserAddressSRO userAddress, String encryptedUserId) {
        super();
        this.userAddress = userAddress;
        this.encryptedUserId = encryptedUserId;
    }
    
    public AddUserAddressRequest(UserAddressSRO userAddress) {
        super();
        this.userAddress = userAddress;
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
