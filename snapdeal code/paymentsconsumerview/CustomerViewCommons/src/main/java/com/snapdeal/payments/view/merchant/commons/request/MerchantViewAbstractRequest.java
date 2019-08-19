package com.snapdeal.payments.view.merchant.commons.request;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.payments.view.commons.exception.constants.PaymentsViewExceptionConstants;
import com.snapdeal.payments.view.commons.request.AbstractRequest;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class MerchantViewAbstractRequest extends AbstractRequest implements Serializable {

	private static final long serialVersionUID = 5800720770929744482L;

	@NotBlank(message = PaymentsViewExceptionConstants.MERCHANT_ID_IS_BLANK)
	private String merchantId;

	@NotNull(message = PaymentsViewExceptionConstants.START_DATE_IS_BLANK)
	@Past(message = PaymentsViewExceptionConstants.DATE_CAN_NOT_BE_IN_FUTURE)
	private Date startDate;
	
	@NotNull(message = PaymentsViewExceptionConstants.END_DATE_IS_BLANK)
	@Past(message = PaymentsViewExceptionConstants.DATE_CAN_NOT_BE_IN_FUTURE)
	private Date endDate;
	
	private Long lastEvaluatedkey;

	private boolean isPrevious = false;
	
	@Max(value=200,message=PaymentsViewExceptionConstants.MAX_LIMIT)
	private Integer limit=50;
}
