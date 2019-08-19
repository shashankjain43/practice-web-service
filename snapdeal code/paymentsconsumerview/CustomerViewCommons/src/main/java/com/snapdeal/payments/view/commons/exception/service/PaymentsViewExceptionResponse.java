package com.snapdeal.payments.view.commons.exception.service;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 
 * @author abhishek
 * will be visible to client
 *
 */
@ToString
@Getter
@Setter
public class PaymentsViewExceptionResponse implements Serializable{
	
	private String errCode ;
	private String errMsg ;
	private static final long serialVersionUID = 1L;

	
}
