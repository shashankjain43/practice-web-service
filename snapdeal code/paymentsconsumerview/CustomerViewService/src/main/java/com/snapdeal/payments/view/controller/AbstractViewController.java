package com.snapdeal.payments.view.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.snapdeal.payments.roleManagementModel.exceptions.RoleMgmtException;
import com.snapdeal.payments.view.commons.exception.service.AuthorizationException;
import com.snapdeal.payments.view.commons.exception.service.InternalServerExcetpion;
import com.snapdeal.payments.view.commons.exception.service.PaymentsViewGenericException;
import com.snapdeal.payments.view.commons.response.ServiceResponse;

@Slf4j
@ControllerAdvice
@RestController
public class AbstractViewController {

   @ExceptionHandler(RuntimeException.class)
   public <T> ServiceResponse<T> handleException(
		   RuntimeException exception,
		   HttpServletRequest request,
		   HttpServletResponse httpResponse) {
      ServiceResponse<T> response = new ServiceResponse<T>();
      PaymentsViewGenericException genericException = new InternalServerExcetpion();
      //genericException.setErrMsg(exception.getMessage());
      log.error("Error message is: " + exception.getMessage());
      response.setException(genericException);
      //httpResponse.setStatus(500);
      return response;
   }

   @ExceptionHandler(PaymentsViewGenericException.class)
   public <T> ServiceResponse<T> handleException(
		   PaymentsViewGenericException exception,
		   HttpServletRequest request,
		   HttpServletResponse httpResponse) {
      ServiceResponse<T> response = new ServiceResponse<T>();
      PaymentsViewGenericException genericException = new PaymentsViewGenericException(
               exception.getMessage());
      genericException.setErrCode(exception.getErrCode());
      log.error("Error message is: " + exception.getMessage());
      response.setException(genericException);
      //httpResponse.setStatus(400);
      return response;
   }
   
   @ExceptionHandler(RoleMgmtException.class)
   public  <T> ServiceResponse<T> handleException(RoleMgmtException exception,
		   HttpServletRequest request,
		   HttpServletResponse httpResponse) {
	      ServiceResponse<T> response = new ServiceResponse<T>();
	      PaymentsViewGenericException genericException = new PaymentsViewGenericException(
	    		  exception.getMessage());
	      genericException.setErrCode(exception.getErrorCode().getErrorCode());
	      log.error("Error message is: " + exception.getMessage());
	      response.setException(genericException);
	     // httpResponse.setStatus(500);
	      return response;
	   }
   
   @ExceptionHandler(AuthorizationException.class)
   public <T> ServiceResponse<T> handleException(
		   AuthorizationException exception,
		   HttpServletRequest request,
		   HttpServletResponse httpResponse) {
      ServiceResponse<T> response = new ServiceResponse<T>();
      PaymentsViewGenericException genericException = new AuthorizationException();
      genericException.setErrCode(exception.getErrCode());
      genericException.setErrMsg(exception.getErrMsg());
      log.error("Error message is: " + exception.getMessage());
      response.setException(genericException);
      //httpResponse.setStatus(401);
      return response;
   }

}
