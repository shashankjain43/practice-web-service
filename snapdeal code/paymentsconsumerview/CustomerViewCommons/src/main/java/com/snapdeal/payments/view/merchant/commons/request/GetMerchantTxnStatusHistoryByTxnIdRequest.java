package com.snapdeal.payments.view.merchant.commons.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.payments.view.commons.enums.TransactionType;
import com.snapdeal.payments.view.commons.exception.constants.PaymentsViewExceptionConstants;
import com.snapdeal.payments.view.commons.request.AbstractRequest;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class GetMerchantTxnStatusHistoryByTxnIdRequest extends
		AbstractRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@NotBlank(message = PaymentsViewExceptionConstants.MERCHANT_ID_IS_BLANK)
	private String merchantId;

	@NotBlank(message = PaymentsViewExceptionConstants.TRANSACTION_REFERENCE_IS_BLANK)
	private String txnRefId;
	
	private TransactionType txnRefType  = TransactionType.CUSTOMER_PAYMENT; 

}
