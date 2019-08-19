/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 09-Jan-2013
 *  @author himanshu
 */
package com.snapdeal.ums.admin.sdwallet.ext.sdwallet;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class AddSDWalletActivityTypeRequest extends ServiceRequest {

    /**
     * 
     */
    private static final long serialVersionUID = 176403671531703387L;
    @Tag(6)
    private String            name;
    @Tag(7)
    private String            code;
    @Tag(8)
    private String            sdCash;
    @Tag(9)
    private Integer           expiryDays;
    @Tag(10)
    private boolean           async;
    @Tag(11)
    private boolean           enabled;

    public AddSDWalletActivityTypeRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSdCash() {
        return sdCash;
    }

    public void setSdCash(String sdCash) {
        this.sdCash = sdCash;
    }

    public Integer getExpiryDays() {
        return expiryDays;
    }

    public void setExpiryDays(Integer expiryDays) {
        this.expiryDays = expiryDays;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
