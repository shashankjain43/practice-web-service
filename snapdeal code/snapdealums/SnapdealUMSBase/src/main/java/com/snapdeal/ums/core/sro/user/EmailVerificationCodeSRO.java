/*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 13-Oct-2012
*  @author naveen
*/
package com.snapdeal.ums.core.sro.user;

import java.io.Serializable;

import com.dyuproject.protostuff.Tag;

public class EmailVerificationCodeSRO implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -452531020513997081L;
    @Tag(1)
    private String            code             = "";
    @Tag(2)
    private String            source           = "";
    @Tag(3)
    private String            targetUrl        = "";

    public EmailVerificationCodeSRO() {
      
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

}
