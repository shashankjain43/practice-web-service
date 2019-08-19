package com.snapdeal.merchant.request;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.merchant.annotation.Email;
import com.snapdeal.merchant.annotation.MobileNumber;
import com.snapdeal.merchant.errorcodes.ErrorConstants;

import lombok.Data;

@Data
public class CreateOfflineMerchantRequest extends AbstractRequest {

	private static final long serialVersionUID = 1L;

	@NotBlank(message=ErrorConstants.COMPANY_NAME_BLANK_CODE)
	private String companyName;
		
	@NotBlank(message=ErrorConstants.CUSTOMER_NAME_BLANK_CODE)
	private String contactPersonName;
	
	@MobileNumber(mandatory=true)
	private String contactPersonMobile;
	
	@Email(mandatory=true)	
	private String contactPersonEmail;
	
	@Size(min=6, max=8, message=ErrorConstants.LANDLINE_NUMBER_LENGTH_CODE)
	@Pattern(regexp="[0-9]*", message=ErrorConstants.LANDLINE_INVALID_CODE)
	private String landline;
	
	@NotBlank(message = ErrorConstants.BUSINESS_ADDRESS_BLANK_CODE)
	private String businessAddress;
	
	@NotBlank(message = ErrorConstants.BUSINESS_CATEGORY_BLANK_CODE)
	private String businessCategory;

	@NotBlank(message = ErrorConstants.NO_OF_STORE_BLANK_CODE)
	private String noOfStores;
	
	@NotBlank(message = ErrorConstants.DAILY_NO_OF_TXN_BLANK_CODE)
	private String dailyTxnVol;
	
	
}
