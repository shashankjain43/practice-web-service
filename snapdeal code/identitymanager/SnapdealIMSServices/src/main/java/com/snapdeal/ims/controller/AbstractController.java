package com.snapdeal.ims.controller;


import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.freecharge.umsclient.exception.UmsException;
import com.snapdeal.ims.enums.IMSRequestHeaders;
import com.snapdeal.ims.errorcodes.IMSDefaultExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.AuthorizationException;
import com.snapdeal.ims.exception.IMSExceptionResponse;
import com.snapdeal.ims.exception.IMSGenericException;
import com.snapdeal.ims.exception.ValidationException;

@Slf4j
public class AbstractController {

   private static Properties properties;

   static {
      properties = new Properties();
      InputStream inputStream = AbstractController.class.getClassLoader().getResourceAsStream(
               "fcexception.properties");
      if (inputStream != null) {
         try {
            properties.load(inputStream);
         } catch (IOException e) {

         }
      }
   }
   
	@ExceptionHandler({ IOException.class })
	@ResponseBody
	public IMSExceptionResponse handleIOException(IOException ex, HttpServletRequest request,
			HttpServletResponse httpResponse) {
      printHeaders(request);
		log.error("IO Exception occoured :", ex);
		IMSExceptionResponse exception = new IMSExceptionResponse();
		exception.setMessage(ex.getMessage());
		exception.setErrorCode(IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode());
		httpResponse.setStatus(500);
		return exception;
	}
	
   @ExceptionHandler({ UmsException.class })
   @ResponseBody
   public IMSExceptionResponse handleUmsException(UmsException ex, HttpServletRequest request, HttpServletResponse httpResponse) {
      printHeaders(request);
      log.error("Exception occoured while handling ums request in freecharge :", ex);
      IMSExceptionResponse exception = new IMSExceptionResponse();
      String exceptionEnumString = properties.getProperty(ex.getErrorCode(), "INTERNAL_SERVER");
      if(exceptionEnumString!=null && exceptionEnumString.equalsIgnoreCase("INTERNAL_SERVER")){
         exception.setMessage(IMSDefaultExceptionCodes.valueOf(exceptionEnumString).errMsg());
         exception.setErrorCode(IMSDefaultExceptionCodes.valueOf(exceptionEnumString).errCode());
      } else {
         if (IMSServiceExceptionCodes.forName(exceptionEnumString) != null) {
            exception.setMessage(IMSServiceExceptionCodes.valueOf(exceptionEnumString).errMsg());
            exception.setErrorCode(IMSServiceExceptionCodes.valueOf(exceptionEnumString).errCode());
         } else if (IMSRequestExceptionCodes.forName(exceptionEnumString) != null) {
            exception.setMessage(IMSRequestExceptionCodes.valueOf(exceptionEnumString).errMsg());
            exception.setErrorCode(IMSRequestExceptionCodes.valueOf(exceptionEnumString).errCode());
         } else {
            exception.setMessage(IMSRequestExceptionCodes.UNKNOWN_ERROR_ON_FC.errMsg());
            exception.setErrorCode(IMSRequestExceptionCodes.UNKNOWN_ERROR_ON_FC.errCode());
         }
      }
      httpResponse.setStatus(500);
      return exception;
   }
   
   @ExceptionHandler({ ValidationException.class })
	@ResponseBody
	public IMSExceptionResponse handleValidationException(
			ValidationException ex, HttpServletRequest request, HttpServletResponse httpResponse) {
      printHeaders(request);httpResponse.setHeader("test", System.currentTimeMillis()+"");
		log.error("Exception occoured while validating request :", ex);
		IMSExceptionResponse exception = new IMSExceptionResponse();
		exception.setMessage(ex.getErrMsg());
		exception.setErrorCode(ex.getErrCode());
		httpResponse.setStatus(400);
		return exception;
	}

	@ExceptionHandler({ AuthorizationException.class,com.snapdeal.ims.authorize.exception.AuthorizationException.class })
	@ResponseBody
	public IMSExceptionResponse handleAuthorizationException(
			AuthorizationException ex, HttpServletRequest request, HttpServletResponse httpResponse) {
      printHeaders(request);
		log.error("User Authorization Error :", ex);
		IMSExceptionResponse exception = new IMSExceptionResponse();
		exception.setMessage(ex.getErrMsg());
		exception.setErrorCode(ex.getErrCode());
		httpResponse.setStatus(401);
		return exception;
	}

	@ExceptionHandler({ IMSGenericException.class })
	@ResponseBody
	public IMSExceptionResponse handleAuthorizationException(
			IMSGenericException ex, HttpServletRequest request, HttpServletResponse httpResponse) {
      printHeaders(request);
		log.error("User Authorization Error :", ex);
		IMSExceptionResponse exception = new IMSExceptionResponse();
		exception.setMessage(ex.getErrMsg());
		exception.setErrorCode(ex.getErrCode());
		httpResponse.setStatus(401);
		return exception;
	}

	@ExceptionHandler({ RuntimeException.class })
	@ResponseBody
	public IMSExceptionResponse handleRuntimeException(RuntimeException ex, HttpServletRequest request,
			HttpServletResponse httpResponse) {
      printHeaders(request);
		log.error("Runtime exception occoured :", ex);
		IMSExceptionResponse exception = new IMSExceptionResponse();
		exception.setMessage(IMSDefaultExceptionCodes.INTERNAL_SERVER
				.errMsg());
		exception.setErrorCode(IMSDefaultExceptionCodes.INTERNAL_SERVER
				.errCode());
		httpResponse.setStatus(500);
		return exception;
	}

	@ExceptionHandler({ Exception.class })
	@ResponseBody
	public IMSExceptionResponse handleException(Exception ex, HttpServletRequest request,
			HttpServletResponse httpResponse) {
      printHeaders(request);
		log.error("Exception occoured :", ex);
		IMSExceptionResponse exception = new IMSExceptionResponse();
		exception.setMessage(ex.getMessage());
		exception.setErrorCode(IMSDefaultExceptionCodes.INTERNAL_SERVER
				.errCode());
		httpResponse.setStatus(500);
		return exception;
	}
	
	//TO Do: Need to Define Error Code for SQL Exception
	@ExceptionHandler({ SQLException.class })
	@ResponseBody
	public IMSExceptionResponse handleSQLException(Exception ex, HttpServletRequest request,
			HttpServletResponse httpResponse) {
	   printHeaders(request);
	   log.error("Exception occoured :", ex);
		IMSExceptionResponse exception = new IMSExceptionResponse();
		exception.setMessage(IMSDefaultExceptionCodes.INTERNAL_SERVER
				.errMsg());
		exception.setErrorCode(IMSDefaultExceptionCodes.INTERNAL_SERVER
				.errCode());
		httpResponse.setStatus(500);
		return exception;
	}
	
	private void printHeaders(HttpServletRequest request) {
	   Map<String, String> headersMap = new HashMap<String, String>();
      headersMap.put("CLIENT_ID",request.getHeader(IMSRequestHeaders.CLIENT_ID.toString()));
      headersMap.put("CLIENT_SDK_VERSION",request.getHeader(IMSRequestHeaders.CLIENT_SDK_VERSION.toString()));
      headersMap.put("CONTENT_TYPE",request.getHeader(IMSRequestHeaders.CONTENT_TYPE.toString()));
      headersMap.put("HTTPMETHOD",request.getHeader(IMSRequestHeaders.HTTPMETHOD.toString()));
      headersMap.put("REQUEST_URI", request.getRequestURI());
      headersMap.put("TIMESTAMP",request.getHeader(IMSRequestHeaders.TIMESTAMP.toString()));
      headersMap.put("USER_AGENT",request.getHeader(IMSRequestHeaders.USER_AGENT.toString()));
      headersMap.put("USER_MACHINE_IDENTIFIER",request.getHeader(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString()));
      
      ObjectMapper mapper = new ObjectMapper();
      JsonNode node = mapper.convertValue(headersMap, JsonNode.class);
      
      log.error(node.toString());
   }
}
