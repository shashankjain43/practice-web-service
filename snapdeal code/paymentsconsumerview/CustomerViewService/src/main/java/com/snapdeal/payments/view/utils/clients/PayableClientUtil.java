package com.snapdeal.payments.view.utils.clients;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.snapdeal.payments.payables.client.PayablesClient;
import com.snapdeal.payments.payables.client.utils.ClientDetails;
import com.snapdeal.payments.payables.model.request.GetPayableTransactionsForTimeIntervalRequest;
import com.snapdeal.payments.payables.model.response.GetPayableTransactionsForTimeIntervalResponse;
import com.snapdeal.payments.view.commons.enums.ExceptionType;
import com.snapdeal.payments.view.commons.exception.PaymentsViewServiceException;
import com.snapdeal.payments.view.commons.exception.codes.PaymentsViewServiceExceptionCodes;

@Component
@Slf4j
public class PayableClientUtil {

   private PayablesClient payableClient = null;

   @Value("${com.snapdeal.payments.payables.domain}")
   private String domain;
   @Value("${com.snapdeal.payments.payables.clientkey}")
   private String clientKey;
   @Value("${com.snapdeal.payments.payables.clientname}")
   private String clientName;
   @Value("${com.snapdeal.payments.payables.url}")
   private String url;
   @Value("${com.snapdeal.payments.payables.timeout}")
   private int timeOut;
   @Value("${com.snapdeal.payments.payables.connection.request.timeout}")
   private int requestTimeout;
   
   
   public GetPayableTransactionsForTimeIntervalResponse getTransactionList(
            GetPayableTransactionsForTimeIntervalRequest request)
            throws PaymentsViewServiceException {
      GetPayableTransactionsForTimeIntervalResponse response = null;
      try {
         response = payableClient.getPayableTransactionsForTimeInterval(request);
      } catch (Throwable th) {
         throw new PaymentsViewServiceException(
        		 PaymentsViewServiceExceptionCodes.PAYABLES_EXCEPTION.errCode(),
        		 th.getMessage(),
        		 ExceptionType.PAYABLE_EXCEPTION);
      }
      return response;

   }

   @PostConstruct
   public void getPayableServiceClient() throws Exception {
      payableClient = null;
      try {
         log.info("initialising Payable client");
         ClientDetails clientDetails = new ClientDetails();
         clientDetails.setClientKey(clientKey);
         clientDetails.setClientName(clientName);
         clientDetails.setUrl(url);
         clientDetails.setConnectTimeout(timeOut);
         clientDetails.setConnectionRequestTimeout(requestTimeout);
         clientDetails.setSocketTimeout(-1);
         payableClient = new PayablesClient(clientDetails);
      } catch (Exception e) {
         log.info(e.getMessage());
         throw e;
      }
   }
}
