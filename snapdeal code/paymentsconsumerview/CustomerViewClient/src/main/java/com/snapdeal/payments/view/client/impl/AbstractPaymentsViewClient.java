package com.snapdeal.payments.view.client.impl;

import org.apache.mina.http.api.HttpMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.snapdeal.payments.view.commons.exception.PaymentsViewServiceException;
import com.snapdeal.payments.view.commons.exception.service.PaymentsViewGenericException;
import com.snapdeal.payments.view.commons.request.AbstractRequest;
import com.snapdeal.payments.view.commons.response.AbstractResponse;
import com.snapdeal.payments.view.commons.response.ServiceResponse;
import com.snapdeal.payments.view.utils.HttpUtil;

public class AbstractPaymentsViewClient {

	/**
	 * This method prepare the response on the basis of request, this method
	 * hits the service through http and return the response.
	 * @throws PaymentsViewServiceException 
	 */
	protected <T extends AbstractRequest, R> R prepareResponseNew(
			T request, TypeReference<ServiceResponse<R>> typeReference, HttpMethod httpMthod, String uri) throws PaymentsViewServiceException{

		final String completeURL = HttpUtil.getInstance().getCompleteUrl(uri);
        ServiceResponse<R> obj = (ServiceResponse<R>) HttpUtil.getInstance().processHttpRequestNew(completeURL, typeReference, request, httpMthod);
        PaymentsViewGenericException exception = obj.getException();
        if(exception != null){
         throw exception;
        }
        return obj.getResponse();
	}
	
	protected <T extends AbstractRequest, R extends AbstractResponse> R prepareResponse(T request,
			Class<R> response, HttpMethod httpMthod, String uri)
			throws PaymentsViewServiceException {

		final String completeURL = HttpUtil.getInstance().getCompleteUrl(uri);

		R obj = (R) HttpUtil.getInstance().processHttpRequest(completeURL,
				response, request, httpMthod);
		return obj;
		
	}
}
