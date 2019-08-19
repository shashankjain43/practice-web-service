package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.cache.EmailVerificationCode;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class CreateUserWithDetailsResponse extends ServiceResponse {

	private static final long serialVersionUID = 6478916169417872322L;

	@Tag(5)
    private UserSRO savedUser;
	
	@Tag(6)
	private EmailVerificationCode verificationCode;
	
	
	public CreateUserWithDetailsResponse() {
    }


	public UserSRO getSavedUser() {
		return savedUser;
	}


	public void setSavedUser(UserSRO savedUser) {
		this.savedUser = savedUser;
	}


	public EmailVerificationCode getVerificationCode() {
		return verificationCode;
	}


	public void setVerificationCode(EmailVerificationCode verificationCode) {
		this.verificationCode = verificationCode;
	}

}
