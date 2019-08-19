package com.snapdeal.merchant.aero.exception;

import lombok.Data;

import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class MPAerospikeException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 265709100615720852L;
	
	private final String errMessage;

}
