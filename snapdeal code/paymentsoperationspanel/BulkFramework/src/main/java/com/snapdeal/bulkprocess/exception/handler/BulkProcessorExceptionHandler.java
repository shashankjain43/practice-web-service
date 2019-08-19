package com.snapdeal.bulkprocess.exception.handler;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.bulkprocess.exception.BulkProcessorException;
import com.snapdeal.bulkprocess.model.BulkFrameworkResponse;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

@ControllerAdvice
@Slf4j
											
public class BulkProcessorExceptionHandler {

	@ExceptionHandler({ BulkProcessorException.class })
	@ResponseBody
	public ResponseEntity<BulkFrameworkResponse> handleException(BulkProcessorException ex, HttpServletResponse response) {
		BulkFrameworkResponse appResponse = new BulkFrameworkResponse();
		appResponse.setData(null);
		String finalMsg;
		if (ex.getMessage() == null || ex.getMessage().trim().equals("")) {
			finalMsg = "Error Occured, please try after sometime.";
		} else {
			finalMsg = ex.getMessage();
		}

		appResponse.setError(ex.getErrorCode() +" : "+ finalMsg );
		log.info("Generic exception catched in AppExceptionHandler " + ExceptionUtils.getFullStackTrace(ex));
		return new ResponseEntity<BulkFrameworkResponse>(appResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
