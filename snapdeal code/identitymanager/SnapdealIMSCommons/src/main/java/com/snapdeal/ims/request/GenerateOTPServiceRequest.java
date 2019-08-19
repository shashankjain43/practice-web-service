package com.snapdeal.ims.request;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.ims.enums.First;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.enums.OTPRequestChannel;
import com.snapdeal.ims.enums.Second;
import com.snapdeal.ims.enums.SendOTPByEnum;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;
import com.snapdeal.ims.validator.annotation.Email;
import com.snapdeal.ims.validator.annotation.GenrateOTPRequestValidation;
import com.snapdeal.ims.validator.annotation.MobileNumber;
import com.snapdeal.ims.validator.annotation.UserId;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@GenrateOTPRequestValidation
@JsonPropertyOrder(alphabetic=true)
@GroupSequence(value={First.class,Second.class,GenerateOTPServiceRequest.class})
public class GenerateOTPServiceRequest extends AbstractOTPServiceRequest {

	private static final long serialVersionUID = -2118240620874477648L;
	
	@NotBlank(message=IMSRequestExceptionConstants.USER_ID_IS_BLANK, groups = First.class)
	@Size( max =127, message = IMSRequestExceptionConstants.USER_ID_MAX_LENGTH, groups = Second.class)
	@UserId
	private String userId;
	
	@MobileNumber
	private String mobileNumber;	
	
	@NotNull(message = IMSRequestExceptionConstants.PURPOSE_IS_BLANK)
	private OTPPurpose otpType;
	
	@Email(mandatory = false)
	private String emailId;
	
	private SendOTPByEnum sendOtpBy;
	
	private Merchant merchant ;
	
	private String name;
	
}