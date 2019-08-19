/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 15-Jan-2013
 *  @author himanshu
 */
package com.snapdeal.ums.admin.sdwallet.ext.sdwallet;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class IsActivityTypeCodeExistsRequest extends ServiceRequest {

    /**
     * 
     */
    private static final long serialVersionUID = 440726747423131536L;
    @Tag(6)
    private String            code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public IsActivityTypeCodeExistsRequest() {
    }

}
