package com.snapdeal.ims.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import com.snapdeal.ims.enums.LinkUserVerifiedThrough;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.UpgradeChannel;
import com.snapdeal.ims.enums.UpgradeSource;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;
import com.snapdeal.ims.validator.annotation.Email;
import com.snapdeal.ims.validator.annotation.Password;
import com.snapdeal.ims.validator.annotation.Token;
import com.snapdeal.ims.validator.annotation.VerifyUserUpgradationRequestValidation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonPropertyOrder(alphabetic=true)
@VerifyUserUpgradationRequestValidation
public class VerifyUserUpgradeRequest extends AbstractRequest {

	private static final long serialVersionUID = 1L;

	@Email(mandatory = false)
	private String emailId;

	@Password(mandatory = false)
	private String password;
	
	@Token
	@Size(max = 154 , message = IMSRequestExceptionConstants.GLOBAL_TOKEN_MAX_LENGTH)
	private String token;

   @NotNull(message = IMSRequestExceptionConstants.MERCHANT_EMPTY)
   private Merchant targetSrcToBeValidated;

   @NotNull(message = IMSRequestExceptionConstants.VERIFIED_TYPE_IS_BLANK)
   private LinkUserVerifiedThrough verifiedType;

   @Size(max = 255, message = IMSRequestExceptionConstants.OTP_ID_MAX_LENGTH)
   private String otpId;

   private String otp;

   private UpgradeSource upgradeSource;

   private UpgradeChannel upgradeChannel;

}