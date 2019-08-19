package com.snapdeal.payments.view.merchant.commons.request;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.snapdeal.payments.view.commons.enums.ExceptionType;
import com.snapdeal.payments.view.commons.enums.RetryTaskStatus;
import com.snapdeal.payments.view.commons.enums.TransactionType;
import com.snapdeal.payments.view.commons.enums.ViewTypeEnum;
import com.snapdeal.payments.view.commons.request.AbstractRequest;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonPropertyOrder(alphabetic=true)
public class RetryPaymentsViewAuditRequest extends AbstractRequest{
	
	private static final long serialVersionUID = 1L;
	
	public int retryCount = 3;
	public List<String> errorCode ;
	public List<ExceptionType> exceptionType ;
	public List<TransactionType> txnTypeList ;
	public int limit ;
	public RetryTaskStatus status=RetryTaskStatus.PENDING;
	public List<String> txnType ;
	public ViewTypeEnum viewType = ViewTypeEnum.MERCHANTVIEW ;
	
}
