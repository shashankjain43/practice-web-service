package com.snapdeal.merchant.rest.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.snapdeal.merchant.entity.response.GenericMerchantError;
import com.snapdeal.merchant.entity.response.GenericMerchantResponse;
import com.snapdeal.merchant.errorcodes.RequestExceptionCodes;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.MerchantGetUserMerchantRequest;
import com.snapdeal.merchant.rest.http.util.MOBUtil;
import com.snapdeal.merchant.util.AppConstants;
import com.snapdeal.mob.response.GetUserMerchantResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserMerchantMappingInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private MOBUtil mobUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		boolean status = false;

		String requestURI = request.getRequestURI();
		String merchantId = request.getHeader(AppConstants.merchantId);
		String token = request.getHeader(AppConstants.token);

		GenericMerchantResponse merchantResponse = null;

		try {
			log.info("request uri is : {}, merchant id is {} ", requestURI, merchantId);

			if ( merchantId==null) {
				log.error("Merchant is null");
				throw new MerchantException(RequestExceptionCodes.MERCHANT_ID_IN_REQUEST_INVALID.getErrCode(),
						RequestExceptionCodes.MERCHANT_ID_IN_REQUEST_INVALID.getErrMsg());
			}
			String userId = (String) request.getAttribute(AppConstants.userId);
			
			GetUserMerchantResponse mobResponse = null;

			MerchantGetUserMerchantRequest merchantRequest = new MerchantGetUserMerchantRequest();
			merchantRequest.setUserId(userId);
			merchantRequest.setToken(token);

			mobResponse = mobUtil.getMerchantForUser(merchantRequest);

			String merchantIdFromMob = mobResponse.getMerchantDetails().getMerchantId();

			if (merchantId.equals(merchantIdFromMob)) {

				status = true;	
			}

			
			if (!status) {
				throw new MerchantException(RequestExceptionCodes.USER_PERMISSION_INVALID.getErrCode(),
						RequestExceptionCodes.USER_PERMISSION_INVALID.getErrMsg());
			}

		} catch (MerchantException e) {
			log.error("Error while checking permission for user {}", e);
			merchantResponse = new GenericMerchantResponse();
			merchantResponse.setData(null);
			GenericMerchantError error = new GenericMerchantError(e.getErrCode(), e.getErrMessage());
			merchantResponse.setError(error);
		}

		if (!status) {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(merchantResponse);
			
			if (log.isDebugEnabled()) {
				log.debug("Authorization failure, response is {} ", json);
			}
			response.setStatus(HttpStatus.OK.value());
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		}
		return status;

	}
}
