package com.response;

import java.io.Serializable;

import com.exception.ServiceException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceResponse<T extends BaseResponse> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private T response;
	private ServiceException exception;
	
}
