package com.snapdeal.admin.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.ims.exception.AuthorizationException;
import com.snapdeal.ims.exception.IMSExceptionResponse;
import com.snapdeal.ims.exception.ValidationException;

public class AbstractController {
	private final static Logger LOG = LoggerFactory
			.getLogger(AbstractController.class);

	@ExceptionHandler({ IOException.class })
	@ResponseBody
	public IMSExceptionResponse handleIOException(IOException ex,
			HttpServletResponse httpResponse) {
		LOG.error("Exception occoured :", ex);
		IMSExceptionResponse exception = new IMSExceptionResponse();
		httpResponse.setStatus(500);
		return exception;
	}

	@ExceptionHandler({ ValidationException.class })
	@ResponseBody
	public IMSExceptionResponse handleValidationException(
			ValidationException ex, HttpServletResponse httpResponse) {
		LOG.error("Exception occoured while validating request", ex);
		IMSExceptionResponse exception = new IMSExceptionResponse();
		exception.setMessage(ex.getErrMsg());
		exception.setErrorCode(ex.getErrCode());
		httpResponse.setStatus(400);
		return exception;
	}

	@ExceptionHandler({ AuthorizationException.class })
	@ResponseBody
	public IMSExceptionResponse handleAuthorizationException(
			AuthorizationException ex, HttpServletResponse httpResponse) {
		LOG.error("User Authorization Error", ex);
		IMSExceptionResponse exception = new IMSExceptionResponse();
		exception.setMessage(ex.getErrMsg());
		exception.setErrorCode(ex.getErrCode());
		httpResponse.setStatus(401);
		return exception;
	}

	@ExceptionHandler({ RuntimeException.class })
	@ResponseBody
	public IMSExceptionResponse handleRuntimeException(RuntimeException ex,
			HttpServletResponse httpResponse) {
		LOG.error("Exception occoured" + ex.getMessage(), ex);
		IMSExceptionResponse exception = new IMSExceptionResponse();
		httpResponse.setStatus(500);
		return exception;
	}

	@ExceptionHandler({ Exception.class })
	@ResponseBody
	public IMSExceptionResponse handleException(Exception ex,
			HttpServletResponse httpResponse) {
		LOG.error("Exception occoured", ex);
		IMSExceptionResponse exception = new IMSExceptionResponse();
		httpResponse.setStatus(500);
		return exception;
	}
}
