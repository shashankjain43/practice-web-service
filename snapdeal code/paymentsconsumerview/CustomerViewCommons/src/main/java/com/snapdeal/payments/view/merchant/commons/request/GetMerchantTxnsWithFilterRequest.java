package com.snapdeal.payments.view.merchant.commons.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.snapdeal.payments.view.merchant.commons.enums.MVTransactionType;


@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class GetMerchantTxnsWithFilterRequest extends MerchantViewAbstractRequest {

	private static final long serialVersionUID = -5013491861632917574L;
	
	private List<MVTransactionType> txnTypeList;

}
