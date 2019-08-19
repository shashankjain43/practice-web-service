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

public class DeleteUserAddressByIdRequest extends ServiceRequest {

    /**
     * 
     */
    private static final long serialVersionUID = 4154746663378450944L;

    @Tag(3)
    private Integer           userAddressId;

    @Tag(4)
    private Integer           userId;

    @Tag(5)
    private String            encryptedUserId;

    public DeleteUserAddressByIdRequest() {
        super();
    }

    public DeleteUserAddressByIdRequest(Integer userAddressId, Integer userId, String encryptedUserId) {
        super();
        this.userAddressId = userAddressId;
        this.userId = userId;
        this.encryptedUserId = encryptedUserId;
    }

    public DeleteUserAddressByIdRequest(Integer userAddressId, Integer userId) {
        super();
        this.userAddressId = userAddressId;
        this.userId = userId;
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
