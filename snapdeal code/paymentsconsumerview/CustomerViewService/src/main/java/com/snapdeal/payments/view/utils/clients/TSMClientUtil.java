package com.snapdeal.payments.view.utils.clients;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.snapdeal.payments.tsm.client.impl.TxnServiceClientImpl;
import com.snapdeal.payments.tsm.entity.NotificationMessage;
import com.snapdeal.payments.tsm.entity.NotificationMessageLinkTxnDetails;
import com.snapdeal.payments.tsm.exception.ClientRequestParameterException;
import com.snapdeal.payments.tsm.exception.HttpTransportException;
import com.snapdeal.payments.tsm.exception.ServiceException;
import com.snapdeal.payments.tsm.request.GetTxnByTxnIdRequest;
import com.snapdeal.payments.tsm.response.GetTxnByTxnIdResponse;
import com.snapdeal.payments.tsm.response.LinkTxnResponse;
import com.snapdeal.payments.tsm.utils.ClientDetails;
import com.snapdeal.payments.view.commons.enums.ExceptionType;
import com.snapdeal.payments.view.commons.exception.PaymentsViewServiceException;
import com.snapdeal.payments.view.commons.exception.codes.PaymentsViewServiceExceptionCodes;

@Slf4j
@Component
public class TSMClientUtil {


   private TxnServiceClientImpl tsmClient;
   @Value("${com.snapdeal.payments.tsm.clientkey}")
   private String tsmClientKey ;
   @Value("${com.snapdeal.payments.tsm.secretkey}")
   private String tsmClientSecretKey ;
   @Value("${com.snapdeal.payments.tsm.url}")
   private String tsmClientIp ;
   @Value("${com.snapdeal.payments.tsm.port}")
   private String tsmClientPort ;
   @Value("${com.snapdeal.payments.tsm.timeout}")
   private int tsmTimeOut;

   @PostConstruct
   public void initialiseTSMClient() {
      try {
    	  ClientDetails.init(tsmClientIp, tsmClientPort,
         		 tsmClientSecretKey,tsmClientKey,
         		 tsmTimeOut);
         tsmClient = new TxnServiceClientImpl();
      } catch (Exception e) {
         log.info("exception occured while initialising TSM client ", e);
      }
   }

   public NotificationMessage getTransactionFromTSM(String txnId, String txnType) {
      try {
         log.info("Parameters are : " + txnId + " type : " + txnType);
         GetTxnByTxnIdResponse response = tsmClient.getTxnByTxnId(txnId, txnType,
                  new GetTxnByTxnIdRequest());
         NotificationMessage msg = new NotificationMessage();
         msg.setMerchantId(response.getMerchantId());
         List<LinkTxnResponse> uplinkTxns = response.getUplinkTxns();
         List<NotificationMessageLinkTxnDetails> uplinkDetails = new LinkedList<NotificationMessageLinkTxnDetails>();
         for (LinkTxnResponse linkTxn : uplinkTxns) {
            NotificationMessageLinkTxnDetails linkTxnDetails = new NotificationMessageLinkTxnDetails();
            linkTxnDetails.setTxnId(linkTxn.getTxnId());
            linkTxnDetails.setTxnType(linkTxn.getTxnType());
            uplinkDetails.add(linkTxnDetails);
         }
         if (uplinkDetails.size() > 0) {
            msg.setUplinkGlobalTxnIds(uplinkDetails);
         }
         msg.setGlobalTxnAmount(response.getGlobalTxnAmount());
         msg.setGlobalTxnId(response.getGlobalTxnId());
         msg.setGlobalTxnMetaData(response.getGlobalTxnMetaData());
         msg.setGlobalTxnReconKey(response.getGlobalTxnReconKey());
         msg.setGlobalTxnState(response.getGlobalTxnState());
         msg.setGlobalTxnStateType(response.getGlobalTxnStateType());
         msg.setGlobalTxnType(response.getGlobalTxnType());
         msg.setSourceSystem(response.getSourceSystem());
         msg.setTsmState(response.getTsmState());
         msg.setTsmTimestamp(response.getLastUpdatedTime().getTime());
         return msg;

      } catch (HttpTransportException e) {
         log.info(e + "");
         throw new PaymentsViewServiceException(
        		 PaymentsViewServiceExceptionCodes.TSM_EXCEPTION.errCode(),
        		 e.getErrMsg(),
        		 ExceptionType.TSM_EXCEPTION);
      } catch (ServiceException e) {
         log.info(e + "");
         throw new PaymentsViewServiceException(
        		 PaymentsViewServiceExceptionCodes.TSM_EXCEPTION.errCode(),
        		 e.getErrMsg(),
        		 ExceptionType.TSM_EXCEPTION);
      } catch (ClientRequestParameterException e) {
         log.info(e + "");
         throw new PaymentsViewServiceException(
        		 PaymentsViewServiceExceptionCodes.TSM_EXCEPTION.errCode(),
        		 e.getMessage(),
        		 ExceptionType.TSM_EXCEPTION);
      }
   }
}
