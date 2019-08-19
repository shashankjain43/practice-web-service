package com.snapdeal.ims.client.impl;



import org.apache.mina.http.api.HttpMethod;

import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.AbstractRequest;
import com.snapdeal.ims.response.AbstractResponse;
import com.snapdeal.ims.utils.HttpUtil;

public class AbstractClientImpl {

   /**
    * This method prepare the response on the basis of request, this method
    * hits the service through http and return the response.
    */
   protected <T extends AbstractRequest, R extends AbstractResponse> R prepareResponse(T request,
                                                                                       Class<R> response,
                                                                                       HttpMethod httpMthod,
                                                                                       String uri)
      throws ServiceException,HttpTransportException {

      final String completeURL = HttpUtil.getInstance().getCompleteUrl(uri);

      R obj = (R) HttpUtil.getInstance().processHttpRequest(completeURL,
                                                            response,
                                                            request,
                                                            httpMthod);
      return obj;
   }

}