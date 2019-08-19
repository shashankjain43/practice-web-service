package com.snapdeal.payments.view.request.commons.request;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.payments.view.commons.exception.constants.PaymentsViewExceptionConstants;


@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class GetPendingActionsBetweenPartyRequest extends RequestViewAbstractRequest 
		implements Serializable{

	private static final long serialVersionUID = -1623382383913493005L;
	
	@NotBlank(message = PaymentsViewExceptionConstants.USER_ID_IS_BLANK)
	private String userId;
	
	@NotBlank(message = PaymentsViewExceptionConstants.USER_ID_IS_BLANK)
	private String otherPartyId;
	
}
