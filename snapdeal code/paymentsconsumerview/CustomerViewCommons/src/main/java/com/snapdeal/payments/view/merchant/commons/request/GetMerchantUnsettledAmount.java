package com.snapdeal.payments.view.merchant.commons.request;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.payments.view.commons.exception.constants.PaymentsViewExceptionConstants;
import com.snapdeal.payments.view.commons.request.AbstractRequest;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class GetMerchantUnsettledAmount extends AbstractRequest implements
		Serializable {

	private static final long serialVersionUID = 1L;
	@NotBlank(message = PaymentsViewExceptionConstants.MERCHANT_ID_IS_BLANK)
	private String merchantId;

}
