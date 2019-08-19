package com.snapdeal.merchant.request;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.merchant.annotation.Email;
import com.snapdeal.merchant.errorcodes.ErrorConstants;

import lombok.Data;

@Data
public class GeneralUserContactUsRequest extends AbstractRequest{

	private static final long serialVersionUID = 1L;

	@Email(mandatory = true)
	private String emailId;
	
	@NotBlank(message = ErrorConstants.EMAIL_CONTENT_IS_BLANK_CODE)
	private String emailContent;
	
	@NotBlank(message = ErrorConstants.ISSUE_TYPE_IS_BLANK_CODE)
	private String issueType;
	
}
