package com.snapdeal.opspanel.userpanel.filters;

import java.util.Iterator;
import java.util.List;

import com.snapdeal.opspanel.userpanel.request.SearchTransactionRequest;
import com.snapdeal.opspanel.userpanel.response.TransactionDetails;

public class TransactionDetailsFilter implements Filter<TransactionDetails, SearchTransactionRequest> {

   @Override
   public List<TransactionDetails> filterData(List<TransactionDetails> transactionDetailsList, SearchTransactionRequest criterion) {

      String transactionReference = null;
      if( criterion.getFcLoadingTransactionId() != null ) {
         transactionReference = criterion.getFcLoadingTransactionId();
      } else if ( criterion.getFcPaymentTransactionId() != null ) {
            transactionReference = criterion.getFcPaymentTransactionId();
      } else if ( criterion.getFcWithdrawlTransactionId() != null ) {
            transactionReference = criterion.getFcWithdrawlTransactionId();
      } else if ( criterion.getSdPaymentOrderId() != null ) {
         transactionReference = criterion.getSdPaymentOrderId();
      }

      for( Iterator<TransactionDetails> i = transactionDetailsList.iterator(); i.hasNext(); ) {

         TransactionDetails transactionDetails = i.next();

         if( ( criterion.getEmailId() != null && transactionDetails.getEmailId() != null
                  && !transactionDetails.getEmailId().equals( criterion.getEmailId() ) )
                  || ( criterion.getUserId() != null && transactionDetails.getUserId() != null
                  && !transactionDetails.getUserId().equals( criterion.getUserId() ) )
                  || ( criterion.getWalletTransactionId() != null && transactionDetails.getWalletTransactionId() != null
                  && !transactionDetails.getWalletTransactionId().equals( criterion.getWalletTransactionId() ) )
                  || ( transactionReference != null && transactionDetails.getTransactionReference() != null
                  && !transactionDetails.getTransactionReference().equals( transactionReference ) )
                  || ( criterion.getTransactionStartDate() != null && transactionDetails.getTransactionDate() != null 
                  && !transactionDetails.getTransactionDate().after( criterion.getTransactionStartDate() ) )
                  || ( criterion.getTransactionEndDate() != null && transactionDetails.getTransactionDate() != null 
                  && !transactionDetails.getTransactionDate().before( criterion.getTransactionEndDate() ) )
                  || ( criterion.getWalletTransactionType() != null && transactionDetails.getWalletTransactionType() != null 
                  && !transactionDetails.getWalletTransactionType().equals( criterion.getWalletTransactionType() ) )
                  || ( criterion.getInstrumentType() != null && transactionDetails.getInstrumentType() != null 
                  && !transactionDetails.getInstrumentType().contains( criterion.getInstrumentType() ) )
                  || ( criterion.getMerchantName() != null && transactionDetails.getMerchantName() != null 
                  && !transactionDetails.getMerchantName().equals( criterion.getMerchantName() ) )
                  ) {
            i.remove();
         }
      }
      return transactionDetailsList;
   }



}
