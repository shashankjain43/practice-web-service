package com.snapdeal.payments.view.service.request;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.snapdeal.payments.view.merchant.commons.enums.MVTransactionStatus;
import com.snapdeal.payments.view.merchant.commons.enums.MVTransactionType;
import com.snapdeal.payments.view.merchant.commons.request.MerchantViewAbstractRequest;

@Getter
@Setter
public class GetMerchantTransactionWithCursor extends MerchantViewAbstractRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<MVTransactionType> txnTypeList;
	
	private List<MVTransactionStatus> txnStatusList;
	
	private Date cursorKey;

}
