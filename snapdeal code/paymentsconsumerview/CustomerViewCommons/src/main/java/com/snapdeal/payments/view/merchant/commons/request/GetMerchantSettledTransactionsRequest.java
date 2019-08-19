package com.snapdeal.payments.view.merchant.commons.request;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.snapdeal.payments.view.commons.exception.constants.PaymentsViewExceptionConstants;
import com.snapdeal.payments.view.commons.request.AbstractRequest;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class GetMerchantSettledTransactionsRequest extends AbstractRequest {

	private static final long serialVersionUID = 4513176499788848322L;

	@NotBlank(message = PaymentsViewExceptionConstants.MERCHANT_ID_IS_BLANK)
	private String merchantId;

	@NotNull(message = PaymentsViewExceptionConstants.START_DATE_IS_BLANK)
	@Past(message = PaymentsViewExceptionConstants.DATE_CAN_NOT_BE_IN_FUTURE)
	private Date startDate;

	@NotNull(message = PaymentsViewExceptionConstants.END_DATE_IS_BLANK)
	@Past(message = PaymentsViewExceptionConstants.DATE_CAN_NOT_BE_IN_FUTURE)
	private Date endDate;

	@Max(value = 100000, message = PaymentsViewExceptionConstants.MAX_LIMIT)
	private Integer limit = 50;

	@Min(value = 1, message = PaymentsViewExceptionConstants.MIN_PAGE_SIZE)
	private Integer page = 1;

	private int orderby = 1; // 1 for descending(default) and ascending for
								// others
								// on txn_date

}
