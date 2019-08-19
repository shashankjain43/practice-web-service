/**
 * 
 */
package com.snapdeal.ims.request;

import javax.validation.Valid;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.snapdeal.ims.request.dto.UserRequestDto;
import com.snapdeal.ims.validator.annotation.CreateUserRequestValidation;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonPropertyOrder(alphabetic=true)
public class CreateUserEmailMobileRequest extends AbstractRequest {

	private static final long serialVersionUID = -793502817330885971L;
	
	public  CreateUserEmailMobileRequest(){
		userRequestDto =new UserRequestDto();
	}
	@Valid
	private UserRequestDto userRequestDto;

}
