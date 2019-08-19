package com.snapdeal.payments.view.merchant.commons.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Get all txns for merchant with all latest statuses
 * @author jain.shashank
 *
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class GetMerchantTransactionsHistoryRequest extends
		MerchantViewAbstractRequest {
	private static final long serialVersionUID = 4513176499788848322L;

}
