package com.snapdeal.payments.view.request.commons.request;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

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
public  class GetRequestViewSearchCursorRequest extends AbstractRequest  implements Serializable{

	private static final long serialVersionUID = -1280048600701321475L;
	
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

	private TxnStatus status = TxnStatus.ALL;
	private TxnView txnView = TxnView.USER ;
	

	@NotNull(message = PaymentsViewExceptionConstants.START_DATE_IS_MISSING)
	private Date fromDate;
	
	private Date toDate=new Date();
	
	private Date cursorKey;
	
	private boolean forward=true;

}
