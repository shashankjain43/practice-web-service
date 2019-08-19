
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendVerificationCodeRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 4102032112689633706L;
    @Tag(3)
    private String mobile;
    @Tag(4)
    private String verificationCode;
    
    public SendVerificationCodeRequest(String mobile, String verificationCode) {
        super();
        this.mobile = mobile;
        this.verificationCode = verificationCode;
    }

    public SendVerificationCodeRequest() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

}
