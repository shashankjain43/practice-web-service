/**
 * 
 */
package com.snapdeal.ims.request;

import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;
import com.snapdeal.ims.validator.annotation.Email;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonPropertyOrder(alphabetic=true)
public class CreateGuestUserEmailRequest extends AbstractRequest {

	private static final long serialVersionUID = 1L;
	@Email
	private String emailId;
	// Purpose of creating a guest user for e.g. OFFLINE, BUYER GUEST etc
	@Size(max = 255 , message = IMSRequestExceptionConstants.PURPOSE_MAX_LENGTH)
	private String purpose;

}
