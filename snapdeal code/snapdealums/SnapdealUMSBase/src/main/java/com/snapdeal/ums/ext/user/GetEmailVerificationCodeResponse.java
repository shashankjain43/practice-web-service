
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.user.EmailVerificationCodeSRO;

public class GetEmailVerificationCodeResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5036648064595799560L;
	@Tag(5)
    private EmailVerificationCodeSRO getEmailVerificationCode;

    public GetEmailVerificationCodeResponse() {
    }

    public GetEmailVerificationCodeResponse(EmailVerificationCodeSRO getEmailVerificationCode) {
        super();
        this.getEmailVerificationCode = getEmailVerificationCode;
    }

    public EmailVerificationCodeSRO getGetEmailVerificationCode() {
        return getEmailVerificationCode;
    }

    public void setEmailVerificationCode(EmailVerificationCodeSRO getEmailVerificationCode) {
        this.getEmailVerificationCode = getEmailVerificationCode;
    }

}
