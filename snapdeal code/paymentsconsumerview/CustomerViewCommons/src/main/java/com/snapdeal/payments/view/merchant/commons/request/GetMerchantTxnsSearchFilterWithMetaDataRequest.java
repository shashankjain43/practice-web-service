package com.snapdeal.payments.view.merchant.commons.request;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import com.snapdeal.payments.view.commons.exception.constants.PaymentsViewExceptionConstants;
import com.snapdeal.payments.view.commons.request.AbstractRequest;
import com.snapdeal.payments.view.merchant.commons.dto.MVFilterCriteria;
import com.snapdeal.payments.view.merchant.commons.dto.MVSearchCriteria;

@Getter
@Setter
public class GetMerchantTxnsSearchFilterWithMetaDataRequest extends
		AbstractRequest implements Serializable {

	private static final long serialVersionUID = 4513176499788848322L;

	private MVFilterCriteria filters;

	private MVSearchCriteria searchCriteria;

	@NotBlank(message = PaymentsViewExceptionConstants.MERCHANT_ID_IS_BLANK)
	private String merchantId;

	@Min(value = 1, message = PaymentsViewExceptionConstants.MIN_PAGE_SIZE)
	private Integer page = 1;

	@Max(value = 200, message = PaymentsViewExceptionConstants.MAX_LIMIT)
	private Integer limit = 50;
	
	private int orderby = 1; // 1 for descending(default) and ascending for
								// others on txn_date

}
