package com.snapdeal.payments.view.merchant.commons.request;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.snapdeal.payments.view.commons.exception.constants.PaymentsViewExceptionConstants;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class GetTotalRefundedAmountForTxnRequest extends
		AbstractMerchantViewRequest implements Serializable {

	private static final long serialVersionUID = -7299202642361820076L;

	@NotBlank(message = PaymentsViewExceptionConstants.INPUT_TXN_ID_IS_BLANK)
	private String fcTxnId;
	
	@NotBlank(message = PaymentsViewExceptionConstants.INPUT_TXN_REF_TYPE_IS_BLANK)
	private String fcTxnRefType;
	
	@NotBlank(message = PaymentsViewExceptionConstants.INPUT_ORDER_ID_IS_BLANK)
	private String orderId;

}
