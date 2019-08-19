package com.snapdeal.payments.view.authorize.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.authorize.IAuthorizeRequest;
import com.snapdeal.payments.authorize.core.model.RequestHeaders;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.RequestAware;
import com.snapdeal.payments.metrics.annotations.Timed;
import com.snapdeal.payments.view.cache.service.impl.ClientConfiguration;
import com.snapdeal.payments.view.commons.constant.PaymentsViewConstants;
import com.snapdeal.payments.view.commons.enums.ClientStatus;
import com.snapdeal.payments.view.commons.enums.PaymentsViewRequestHeaders;
import com.snapdeal.payments.view.commons.exception.codes.PaymentsViewAuthorizationExceptionCodes;
import com.snapdeal.payments.view.commons.exception.service.AuthorizationException;
import com.snapdeal.payments.view.commons.request.AbstractRequest;
import com.snapdeal.payments.view.commons.request.Client;
import com.snapdeal.payments.view.commons.utils.CheckSumUtil;

@Component
public class PaymentsViewRequestAuthroizeImpl implements IAuthorizeRequest {

	@Autowired
	private AuthorizationContext context;
	
	@Autowired
	private ClientConfiguration clientConfiguration ;

	private static final String CLIENT_SECRET_ID = "CLIENT_SECRET";

	@Override
	@Marked
	@Timed
	@RequestAware
	public boolean authorizeHeadersAndApi(HttpServletRequest request,
			String requestedMethod) {
		populateAuthContextFromHeaders(request);
		Client client = clientConfiguration.getClientByName(context
				.get(PaymentsViewRequestHeaders.CLIENT_NAME.toString()));

		if (client != null) {
			if(client.getClientStatus() == ClientStatus.INACTIVE){
				throw new AuthorizationException(
						PaymentsViewAuthorizationExceptionCodes.UNAUTHORIZED_CLIENT
								.errCode(),
						PaymentsViewAuthorizationExceptionCodes.UNAUTHORIZED_CLIENT
								.errMsg());
			}
			context.set(CLIENT_SECRET_ID, client.getClientKey());
		} else {
			throw new AuthorizationException(
					PaymentsViewAuthorizationExceptionCodes.UNAUTHORIZED_CLIENT
							.errCode(),
					PaymentsViewAuthorizationExceptionCodes.UNAUTHORIZED_CLIENT
							.errMsg());
		}

		// if API is restricted then send false;
		 
		return true;
	}

	private boolean isRequestExpired() {

		long currentTime = System.currentTimeMillis();
		long timeDiff = currentTime
				- Long.parseLong(context
						.get(PaymentsViewRequestHeaders.TIMESTAMP.toString()));

		// get expiry time in seconds from database
		if (timeDiff / 1000 < Long.parseLong(PaymentsViewConstants.REQUEST_TTL)) {
			return true;
		} else {
			throw new AuthorizationException(
					PaymentsViewAuthorizationExceptionCodes.REQUEST_TIMEOUT
							.errCode(),
					PaymentsViewAuthorizationExceptionCodes.REQUEST_TIMEOUT
							.errMsg());
		}
	}

	@Override
	public boolean isValidCheckSum(Object obj) throws AuthorizationException {

		boolean isValidRequest;

		String clientHash = context.get(PaymentsViewRequestHeaders.HASH
				.toString());
		if (!(obj instanceof AbstractRequest)) {
			isValidRequest = checkSumMatched(clientHash, null);
		} else {
			AbstractRequest request = (AbstractRequest) obj;
			isValidRequest = checkSumMatched(clientHash, request);
		}

		return isValidRequest;
	}

	private boolean checkSumMatched(String checkSum, AbstractRequest request)
			throws AuthorizationException {

		String clientSecretKey = context.get(CLIENT_SECRET_ID);
		if (clientSecretKey == null) {
			throw new AuthorizationException(
					PaymentsViewAuthorizationExceptionCodes.CLIENT_SECRET_NOT_PROVIDED
							.errCode(),
					PaymentsViewAuthorizationExceptionCodes.CLIENT_SECRET_NOT_PROVIDED
							.errMsg());
		}
		String calculatedCheckSum;
		if (context.get(PaymentsViewRequestHeaders.HTTPMETHOD.toString())
				.equals(HttpMethod.GET.toString())
				|| context
						.get(PaymentsViewRequestHeaders.HTTPMETHOD.toString())
						.equals(HttpMethod.DELETE.toString())) {
			calculatedCheckSum = CheckSumUtil.generateChecksum(request,
					context.get("CLIENT_SECRET"));

			if (calculatedCheckSum.equals(checkSum)) {
				return true;
			}
		} else {
			try {
				calculatedCheckSum = CheckSumUtil.generateChecksum(request,
						context.get("CLIENT_SECRET"));
				if (calculatedCheckSum.equals(checkSum)) {
					return true;
				}
			} catch (Exception e) {
				throw new AuthorizationException(
						PaymentsViewAuthorizationExceptionCodes.INVALID_REQUEST
								.errCode(),
						PaymentsViewAuthorizationExceptionCodes.INVALID_REQUEST
								.errMsg());
			}
		}
		return false;
	}

	private void populateAuthContextFromHeaders(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		if (ipAddress == null) {
			throw new AuthorizationException(
					PaymentsViewAuthorizationExceptionCodes.CLIENT_IP_ADDRESS_NOT_PROVIDED
							.errCode(),
					PaymentsViewAuthorizationExceptionCodes.CLIENT_IP_ADDRESS_NOT_PROVIDED
							.errMsg());
		}
		context.set(PaymentsViewRequestHeaders.CLIENT_IP_ADDRESS.toString(),
				ipAddress);
		String clientName = request
				.getHeader((PaymentsViewRequestHeaders.CLIENT_NAME.toString()));
		if (clientName == null) {
			throw new AuthorizationException(
					PaymentsViewAuthorizationExceptionCodes.CLIENT_ID_NOT_PROVIDED
							.errCode(),
					PaymentsViewAuthorizationExceptionCodes.CLIENT_ID_NOT_PROVIDED
							.errMsg());
		}
		context.set(PaymentsViewRequestHeaders.CLIENT_NAME.toString(), clientName);
		context.set(PaymentsViewRequestHeaders.USER_AGENT.toString(), request
				.getHeader(PaymentsViewRequestHeaders.USER_AGENT.toString()));
		context.set(PaymentsViewRequestHeaders.USER_MACHINE_IDENTIFIER
				.toString(), request
				.getHeader(PaymentsViewRequestHeaders.USER_MACHINE_IDENTIFIER
						.toString()));
		context.set(PaymentsViewRequestHeaders.USER_MACHINE_IDENTIFIER
				.toString(), request
				.getHeader(PaymentsViewRequestHeaders.USER_MACHINE_IDENTIFIER
						.toString()));
		context.set(PaymentsViewRequestHeaders.HASH.toString(),
				request.getHeader(PaymentsViewRequestHeaders.HASH.toString()));
		context.set(PaymentsViewRequestHeaders.TIMESTAMP.toString(), request
				.getHeader(PaymentsViewRequestHeaders.TIMESTAMP.toString()));
		context.set(PaymentsViewRequestHeaders.HTTPMETHOD.toString(), request
				.getMethod().toString());
		context.set(PaymentsViewRequestHeaders.SERVER_IP_ADDRESS.toString(),
				request.getLocalAddr());
		context.set(PaymentsViewRequestHeaders.APP_REQUEST_ID.toString(),
				request.getHeader(RequestHeaders.APP_REQUEST_ID.getName()));
	}
}
