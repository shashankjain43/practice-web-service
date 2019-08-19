package com.snapdeal.ims.request;

import javax.validation.GroupSequence;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.ims.enums.First;
import com.snapdeal.ims.enums.Second;
import com.snapdeal.ims.enums.UpgradeChannel;
import com.snapdeal.ims.enums.UpgradeSource;
import com.snapdeal.ims.enums.UserIdentityVerifiedThrough;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;
import com.snapdeal.ims.validator.annotation.Email;
import com.snapdeal.ims.validator.annotation.MobileNumber;
import com.snapdeal.ims.validator.annotation.Token;
import com.snapdeal.ims.validator.annotation.UserUpgradationRequestValidation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonPropertyOrder(alphabetic = true)
@GroupSequence(value={First.class,Second.class,UserUpgradeRequest.class})
@UserUpgradationRequestValidation
public class UserUpgradeRequest extends AbstractRequest {
	private static final long serialVersionUID = 1L;

	@Email(mandatory = false)
	private String emailId;

	@MobileNumber
	private String mobileNumber;

	@Token
	@Size(max = 154 , message = IMSRequestExceptionConstants.GLOBAL_TOKEN_MAX_LENGTH)
	private String token;

	private UpgradeSource upgradeSource;

	private UpgradeChannel upgradeChannel;

	private UserIdentityVerifiedThrough verifiedType;

	@NotBlank(message = IMSRequestExceptionConstants.OTP_ID_IS_BLANK)
	@Size(max = 255, message = IMSRequestExceptionConstants.OTP_ID_MAX_LENGTH)
	private String otpId;

	@NotBlank(message = IMSRequestExceptionConstants.OTP_IS_BLANK, groups = First.class)
	private String otp;
}

