package com.snapdeal.ums.ext.user;

public class CreateSubsidieryUserRequest extends CreateUserRequest{

	private static final long serialVersionUID = -4072362537828694629L;
	
	private Boolean isEmailVarified;
	private String emailVerificationCode;
	
	
	public CreateSubsidieryUserRequest() {
		super();
	}


	public Boolean isEmailVarified() {
		return isEmailVarified;
	}


	public void setEmailVarified(Boolean isEmailVarified) {
		this.isEmailVarified = isEmailVarified;
	}


	public String getEmailVerificationCode() {
		return emailVerificationCode;
	}


	public void setEmailVerificationCode(String emailVerificationCode) {
		this.emailVerificationCode = emailVerificationCode;
	}
	
}
