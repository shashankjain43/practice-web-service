/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 24-Oct-2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.ext.userAddress;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class AddUserAddressTagRequest extends ServiceRequest{

    /**
     * 
     */
    private static final long serialVersionUID = -7845581930881787922L;
    
    @Tag(3)
    private Integer userAddressId;
    
    @Tag(4)
    private Integer userId;
    
    @Tag(5)
    private String addressTag;
    
    @Tag(6)
    private String encryptedUserId;
    
    public AddUserAddressTagRequest() {
        super();
        // TODO Auto-generated constructor stub
    }

    public AddUserAddressTagRequest(Integer userAddressId, Integer userId, String addressTag, String encryptedUserId) {
        super();
        this.userAddressId = userAddressId;
        this.userId = userId;
        this.addressTag = addressTag;
        this.encryptedUserId = encryptedUserId;
    }

    
    public AddUserAddressTagRequest(Integer userAddressId, Integer userId, String addressTag) {
        super();
        this.userAddressId = userAddressId;
        this.userId = userId;
        this.addressTag = addressTag;
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
     * @return the addressTag
     */
    public String getAddressTag() {
        return addressTag;
    }

    /**
     * @param addressTag the addressTag to set
     */
    public void setAddressTag(String addressTag) {
        this.addressTag = addressTag;
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
