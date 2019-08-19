package com.snapdeal.payments.view.request.commons.request;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.snapdeal.payments.view.commons.exception.constants.PaymentsViewExceptionConstants;
import com.snapdeal.payments.view.commons.request.AbstractRequest;
import com.snapdeal.payments.view.request.commons.enums.TxnStatus;
import com.snapdeal.payments.view.request.commons.enums.TxnView;
import com.snapdeal.payments.view.request.commons.validator.annotation.RequestViewSearchRequestValidation;

@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@RequestViewSearchRequestValidation
public  class GetRequestViewSearchRequest extends AbstractRequest  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String fcTxnId ;
	private String srcPartyId;
	private String destPartyId ;
	private String srcEmailId;
	private String destEmailId;
	private String srcMobileNumber ;
	private String destMobileNumber ;
	// Transaction type in TSM.
	// Defines the view type.
	private String requestType;

	private Date startDate;
	private Date endDate ;
	
	private TxnStatus status = TxnStatus.ALL;
	private TxnView txnView = TxnView.USER ;
	@Min(value = 1 , message = PaymentsViewExceptionConstants.MIN_PAGE_SIZE)
	private Integer page = 1;
	@Max(value=1000,message=PaymentsViewExceptionConstants.MAX_LIMIT)
	@Min(value=0,message=PaymentsViewExceptionConstants.MIN_LIMIT)
	private int limit = 50;
	
	private int orderby= 1; //1 for descending(default) and ascending for others on txn_date

}
