package com.snapdeal.ims.request;

import javax.validation.GroupSequence;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.ims.enums.First;
import com.snapdeal.ims.enums.Second;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;
import com.snapdeal.ims.validator.annotation.Token;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@GroupSequence(value={First.class,Second.class,GetAuthCodeRequest.class})
public class GetAuthCodeRequest extends AbstractRequest {

	private static final long serialVersionUID = 1L;

	@Token
	@NotBlank(message = IMSRequestExceptionConstants.TOKEN_IS_BLANK, groups = First.class)
	@Size(max = 154, message = IMSRequestExceptionConstants.TOKEN_MAX_LENGTH, groups = Second.class)
	private String token;

	@NotBlank(message = IMSRequestExceptionConstants.MERCHANT_NOT_BLANK)
	@Size(max = 255, message = IMSRequestExceptionConstants.MERCHANT_MAX_LENGTH)
	private String merchantId;

}
