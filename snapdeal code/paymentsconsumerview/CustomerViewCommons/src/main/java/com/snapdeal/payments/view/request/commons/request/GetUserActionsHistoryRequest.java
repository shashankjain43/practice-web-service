package com.snapdeal.payments.view.request.commons.request;

import java.io.Serializable;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.validator.constraints.NotEmpty;

import com.snapdeal.payments.view.commons.enums.ActionType;
import com.snapdeal.payments.view.commons.exception.constants.PaymentsViewExceptionConstants;



@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class GetUserActionsHistoryRequest extends RequestViewAbstractRequest 
		implements Serializable{

	private static final long serialVersionUID = -1623382383913493005L;
	
	private List<ActionType> referenceType;

	
	@NotEmpty(message = PaymentsViewExceptionConstants.USER_ID_IS_BLANK)
	private String userId;
	
}
