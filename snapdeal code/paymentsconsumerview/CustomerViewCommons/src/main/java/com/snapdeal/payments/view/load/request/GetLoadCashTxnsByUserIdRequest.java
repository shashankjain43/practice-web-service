package com.snapdeal.payments.view.load.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.snapdeal.payments.view.commons.exception.constants.PaymentsViewExceptionConstants;
import com.snapdeal.payments.view.commons.request.AbstractRequest;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class GetLoadCashTxnsByUserIdRequest extends AbstractRequest{

	private static final long serialVersionUID = 7262281558929239301L;
	
	@NotEmpty(message = PaymentsViewExceptionConstants.MERCHANT_ID_IS_BLANK)
	private String merchantId;
	
	@NotEmpty(message = PaymentsViewExceptionConstants.USER_ID_IS_BLANK)
	private String userId;
	
	@Min(value = 1, message = PaymentsViewExceptionConstants.MIN_PAGE_SIZE)
	private Integer page = 1;

	@Max(value = 200, message = PaymentsViewExceptionConstants.MAX_LIMIT)
	private Integer limit = 50;

	private int orderby = 1; // 1 for descending(default) and ascending for
								// others on txn_date

}
