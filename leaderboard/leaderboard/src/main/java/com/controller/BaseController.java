package com.controller;

import com.exception.ServiceException;
import com.response.BaseResponse;
import com.response.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class BaseController {

	@ExceptionHandler(RuntimeException.class)
	public <T extends BaseResponse> ServiceResponse<T> handleException(RuntimeException exception) {
		ServiceResponse<T> response = new ServiceResponse<T>();
		ServiceException genericException = new ServiceException(exception.getMessage());
		log.error("Error message is: " + exception.getMessage());
		response.setException(genericException);
		return response;
	}

	@ExceptionHandler(ServiceException.class)
	public <T extends BaseResponse> ServiceResponse<T> handleException(ServiceException exception) {
		ServiceResponse<T> response = new ServiceResponse<T>();
		log.error("Error message is: " + exception.getMessage());
		response.setException(exception);
		return response;
	}

}
