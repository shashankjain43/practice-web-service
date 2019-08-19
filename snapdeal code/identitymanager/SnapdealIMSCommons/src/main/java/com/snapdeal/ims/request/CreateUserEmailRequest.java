/**
 * 
 */
package com.snapdeal.ims.request;

import javax.validation.Valid;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import com.snapdeal.ims.request.dto.UserDetailsByEmailRequestDto;

/**
 * @author shachi
 *
 */
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonPropertyOrder(alphabetic=true)
public class CreateUserEmailRequest extends AbstractRequest {

	public CreateUserEmailRequest() {
		userDetailsByEmailDto=new UserDetailsByEmailRequestDto();
	}
	
	private static final long serialVersionUID = 2836430548626116729L;
	@Valid
	private UserDetailsByEmailRequestDto userDetailsByEmailDto;
	
	private String url;

}
