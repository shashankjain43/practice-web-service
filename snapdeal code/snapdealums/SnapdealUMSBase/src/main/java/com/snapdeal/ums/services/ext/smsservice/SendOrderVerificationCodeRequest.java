
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendOrderVerificationCodeRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 5220247585370472059L;
    @Tag(3)
    private String mobile;
    @Tag(4)
    private String verificationCode;

    
    public SendOrderVerificationCodeRequest(String mobile, String verificationCode) {
        super();
        this.mobile = mobile;
        this.verificationCode = verificationCode;
    }

    public SendOrderVerificationCodeRequest() {
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
