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
import com.snapdeal.merchant.request.MerchantUserByTokenRequest;
import com.snapdeal.merchant.rest.http.util.RMSUtil;
import com.snapdeal.merchant.util.AppConstants;
import com.snapdeal.payments.roleManagementModel.exceptions.RoleMgmtException;
import com.snapdeal.payments.roleManagementModel.response.GetUserByTokenResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private RMSUtil rmsUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		boolean status = false;

		String requestURI = request.getRequestURI();
		String token = request.getHeader(AppConstants.token);

		
		GenericMerchantResponse merchantResponse = null;

		try {
			log.info("request uri is : {}", requestURI);

			if (token == null) {
				log.error("Token is null");
				throw new MerchantException(RequestExceptionCodes.SESSION_INVALID.getErrCode(),
						RequestExceptionCodes.SESSION_INVALID.getErrMsg());
			}

			GetUserByTokenResponse rmsResponse = null;

			MerchantUserByTokenRequest merchantTokenrequest = new MerchantUserByTokenRequest();
			merchantTokenrequest.setToken(token);
			rmsResponse = rmsUtil.getUserByToken(merchantTokenrequest);
			String userId = rmsResponse.getUser().getId();

			request.setAttribute(AppConstants.userId, userId);
			request.setAttribute(AppConstants.loginName, rmsResponse.getUser().getUserName());
			
			status = true;

		} catch (MerchantException e) {
			log.error("Error while checking permission for user {}", e);
			merchantResponse = new GenericMerchantResponse();
			merchantResponse.setData(null);
			GenericMerchantError error = new GenericMerchantError(e.getErrCode(), e.getErrMessage());
			merchantResponse.setError(error);
		} catch (RoleMgmtException ex) {
			log.error("Role mgmt exception on verifying token {} {}", ex.getMessage(), ex);
			merchantResponse = new GenericMerchantResponse();
			merchantResponse.setData(null);
			GenericMerchantError error = new GenericMerchantError(ex.getErrorCode().getErrorCode(), ex.getMessage());
			merchantResponse.setError(error);
		} catch (Exception e) {
			log.error("Exception on verifying token {} {}", e.getMessage(), e);
			merchantResponse = new GenericMerchantResponse();
			merchantResponse.setData(null);
			GenericMerchantError error = new GenericMerchantError(
					RequestExceptionCodes.GENERIC_INTERNAL_SERVER.getErrCode(),
					RequestExceptionCodes.GENERIC_INTERNAL_SERVER.getErrMsg());
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
