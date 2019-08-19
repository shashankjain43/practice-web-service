
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.user.EmailVerificationCodeSRO;

public class CreateEmailVerificationCodeResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6244124040140437857L;
	@Tag(5)
    private EmailVerificationCodeSRO createEmailVerificationCode;

    public CreateEmailVerificationCodeResponse() {
    }

    public CreateEmailVerificationCodeResponse(EmailVerificationCodeSRO createEmailVerificationCode) {
        super();
        this.createEmailVerificationCode = createEmailVerificationCode;
    }

    public EmailVerificationCodeSRO getCreateEmailVerificationCode() {
        return createEmailVerificationCode;
    }

    public void setEmailVerificationCode(EmailVerificationCodeSRO createEmailVerificationCode) {
        this.createEmailVerificationCode = createEmailVerificationCode;
    }

}
