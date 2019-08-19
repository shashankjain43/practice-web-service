/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 17-Jan-2013
 *  @author himanshu
 */
package com.snapdeal.ums.admin.sdwallet.ext.sdwallet;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class NumberOfRecordsRequest extends ServiceRequest {

    /**
     * 
     */
    private static final long serialVersionUID = -969885378113532368L;
    @Tag(6)
    private Integer           userId;
    @Tag(7)
    private String            mode;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public NumberOfRecordsRequest() {
        super();
    }

}
