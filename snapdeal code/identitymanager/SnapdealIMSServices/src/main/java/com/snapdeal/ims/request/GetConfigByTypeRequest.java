package com.snapdeal.ims.request;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;

@Setter@Getter@ToString
public class GetConfigByTypeRequest {

	@NotBlank(message = IMSRequestExceptionConstants.CONFIG_TYPE_BLANK)
	@Size(min = 1, message = IMSRequestExceptionConstants.CONFIG_TYPE_MUST_CONTAIN_ONE_LETTER)
	private String configType;
}
