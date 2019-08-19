package com.snapdeal.payments.view.request.commons.request;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.payments.view.commons.exception.constants.PaymentsViewExceptionConstants;

@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class GetSplitViewTransactionsRequest extends RequestViewAbstractRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	@NotBlank(message =  PaymentsViewExceptionConstants.SPLIT_ID_IS_BLANK)
	private String splitId ;

}
