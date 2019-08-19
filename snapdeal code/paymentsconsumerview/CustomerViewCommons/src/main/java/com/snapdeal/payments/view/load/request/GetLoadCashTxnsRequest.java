package com.snapdeal.payments.view.load.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.validator.constraints.NotEmpty;

import com.snapdeal.payments.view.commons.exception.constants.PaymentsViewExceptionConstants;
import com.snapdeal.payments.view.commons.request.AbstractRequest;
import com.snapdeal.payments.view.load.dto.LCFilterCriteria;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class GetLoadCashTxnsRequest extends AbstractRequest {

	private static final long serialVersionUID = -3740542791122039022L;

	@NotEmpty(message = PaymentsViewExceptionConstants.MERCHANT_ID_IS_BLANK)
	private String merchantId;

	private LCFilterCriteria filters;

	@Min(value = 1, message = PaymentsViewExceptionConstants.MIN_PAGE_SIZE)
	private Integer page = 1;

	@Max(value = 200, message = PaymentsViewExceptionConstants.MAX_LIMIT)
	private Integer limit = 50;

	private int orderby = 1; // 1 for descending(default) and ascending for
								// others on txn_date

}
