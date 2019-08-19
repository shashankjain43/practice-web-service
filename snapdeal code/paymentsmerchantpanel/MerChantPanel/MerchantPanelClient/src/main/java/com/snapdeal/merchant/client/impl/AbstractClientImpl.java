package com.snapdeal.merchant.client.impl;


import java.lang.reflect.Type;

import org.apache.mina.http.api.HttpMethod;

import com.google.gson.reflect.TypeToken;
import com.snapdeal.merchant.client.util.http.HttpUtil;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.AbstractRequest;
import com.snapdeal.merchant.response.AbstractResponse;

public class AbstractClientImpl {

   /**
    * This method prepare the response on the basis of request, this method
    * hits the service through http and return the response.
    */
   protected <T extends AbstractRequest, R extends AbstractResponse> R prepareResponse(T request,
                                                                                       Class<R> response,
                                                                                       Type type,
                                                                                       HttpMethod httpMthod,
                                                                                       String uri)
      throws MerchantException {

      final String completeURL = HttpUtil.getInstance().getCompleteUrl(uri);

      R obj = (R) HttpUtil.getInstance().processHttpRequest(completeURL,
    		  												type,
                                                            response,
                                                            request,
                                                            httpMthod);
      return obj;
   }

}

