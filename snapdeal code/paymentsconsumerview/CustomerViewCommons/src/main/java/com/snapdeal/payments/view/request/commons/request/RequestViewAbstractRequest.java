package com.snapdeal.payments.view.request.commons.request;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.validator.constraints.NotEmpty;

import com.snapdeal.payments.view.commons.enums.ActionType;
import com.snapdeal.payments.view.commons.exception.constants.PaymentsViewExceptionConstants;
import com.snapdeal.payments.view.commons.request.AbstractRequest;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class RequestViewAbstractRequest extends AbstractRequest implements Serializable{

	private static final long serialVersionUID = 7014231441848712039L;
	
	@NotNull(message = PaymentsViewExceptionConstants.START_DATE_IS_BLANK)
	private Date startDate;
	
	@NotNull(message = PaymentsViewExceptionConstants.END_DATE_IS_BLANK)
	private Date endDate;
	
	@Max(value=200,message=PaymentsViewExceptionConstants.MAX_LIMIT)
	@Min(value=0,message=PaymentsViewExceptionConstants.MIN_LIMIT)
	private int limit=50;
	
	@NotEmpty(message = PaymentsViewExceptionConstants.ACTION_TYPE_IS_MISSING)
	private List<ActionType> actionType;
	
	//Last evaluated timestamp, now need records before/after this timestamp
	private Long lastEvaluatedkey;
	
	//flag to move forward/backward in result set
	private boolean isPrevious=false;

}
