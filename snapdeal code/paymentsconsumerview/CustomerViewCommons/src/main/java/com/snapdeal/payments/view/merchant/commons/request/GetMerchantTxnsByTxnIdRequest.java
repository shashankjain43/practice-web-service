package com.snapdeal.payments.view.merchant.commons.request;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.payments.view.commons.exception.constants.PaymentsViewExceptionConstants;
import com.snapdeal.payments.view.commons.request.AbstractRequest;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class GetMerchantTxnsByTxnIdRequest extends AbstractRequest implements
		Serializable {

	private static final long serialVersionUID = 3936184099978407063L;

	@NotBlank(message = PaymentsViewExceptionConstants.MERCHANT_ID_IS_BLANK)
	private String merchantId;
	
	@NotBlank(message = PaymentsViewExceptionConstants.INPUT_TXN_ID_IS_BLANK)
	private String fcTxnRefId;

}
