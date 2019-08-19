package com.snapdeal.payments.view.utils.metadata;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.snapdeal.mob.response.GetMerchantDetails;
import com.snapdeal.payments.metadata.escrowengine.EscrowEngineMetadata;
import com.snapdeal.payments.tsm.entity.NotificationMessage;
import com.snapdeal.payments.tsm.enums.TsmState;
import com.snapdeal.payments.view.commons.enums.TransactionType;
import com.snapdeal.payments.view.commons.exception.PaymentsViewServiceException;
import com.snapdeal.payments.view.commons.exception.service.PaymentsViewGenericException;
import com.snapdeal.payments.view.customer.commons.enums.MerchantTxnType;
import com.snapdeal.payments.view.customer.commons.enums.MerchantViewState;
import com.snapdeal.payments.view.entity.MerchantViewAuditEntity;
import com.snapdeal.payments.view.entity.TransactionDetailsEntity;
import com.snapdeal.payments.view.entity.TransactionStateDetailsEntity;
import com.snapdeal.payments.view.merchant.commons.dto.TransactionPaybleDto;
import com.snapdeal.payments.view.service.request.ExceptionHandlerRequest;
import com.snapdeal.payments.view.utils.clients.MOBClientUtil;

@Slf4j
@Component
public class PaymentsViewBuilder {

	@Autowired 
	ViewMetaDataInterprator metaDetaInterparator ;
	
	@Autowired
	private MOBClientUtil mobClientUtil;

   public TransactionDetailsEntity getTransactionDetailEntity(NotificationMessage notifactionMsg)
            throws JsonParseException, JsonMappingException, IOException {
      TransactionDetailsEntity entity = new TransactionDetailsEntity();
      fillMetaData(entity, notifactionMsg);
      entity.setTxnDate(new Date(notifactionMsg.getTsmTimestamp()));
      entity.setFcTxnId(notifactionMsg.getGlobalTxnId());
      String merchantId =  notifactionMsg.getMerchantId() ; ;
      entity.setMerchantId(merchantId);
      try{
    	  GetMerchantDetails merchant= mobClientUtil.getMerchantDisplayInfoById(merchantId);
          entity.setMerchantName(merchant.getBusinessInformationDTO().getMerchantName());
      }catch(PaymentsViewServiceException e){
    	  log.info(e.getMessage());
      }
      entity.setTxnAmount(notifactionMsg.getGlobalTxnAmount());
      entity.setTxnId(UUID.randomUUID().toString());
      entity.setTsmTimeStamp(new Date(notifactionMsg.getTsmTimestamp()));
      return entity;
   }

   private TransactionDetailsEntity fillMetaData(TransactionDetailsEntity entity,
            NotificationMessage notifactionMsg) throws JsonParseException, JsonMappingException,
            IOException {
      String metaData = notifactionMsg.getGlobalTxnMetaData();
      String txnType = notifactionMsg.getGlobalTxnType();
      entity.setTxnType(txnType);
      TransactionType type = TransactionType.valueOf(txnType);
         switch (type) {
            case CUSTOMER_PAYMENT:
               entity.setMerchantTxnType(MerchantTxnType.PAYMENT.toString());
               return metaDetaInterparator.fillAggregatorMetaData(metaData,entity) ;
            case CANCELLATION_REFUND:
               entity.setMerchantTxnType(MerchantTxnType.REFUND.toString());
               return metaDetaInterparator.fillAggregatorMetaData(metaData,entity) ;
            case OPS_WALLET_DEBIT_VOID_REVERSE:
            case OPS_WALLET_DEBIT:
               entity.setMerchantTxnType(MerchantTxnType.PAYMENT.toString());
               return metaDetaInterparator.fillOPSMetaData(metaData,entity);
            case OPS_WALLET_DEBIT_REVERSE:
            case OPS_WALLET_DEBIT_VOID:
               entity.setMerchantTxnType(MerchantTxnType.REFUND.toString());
               return metaDetaInterparator.fillOPSMetaData(metaData,entity);
            case OPS_WALLET_CREDIT:
            	entity.setMerchantTxnType(MerchantTxnType.LOAD_CASH.toString());
            	return metaDetaInterparator.fillOPSMetaData(metaData,entity);
            case OPS_WALLET_CREDIT_REVERSE:
            	entity.setMerchantTxnType(MerchantTxnType.LOAD_CASH_REVERSE.toString());
                return metaDetaInterparator.fillOPSMetaData(metaData,entity);
            case OPS_WALLET_CREDIT_VOID:
            	entity.setMerchantTxnType(MerchantTxnType.LOAD_CASH_REFUND.toString());
                return metaDetaInterparator.fillOPSMetaData(metaData,entity);
            case P2P_SEND_MONEY:
            case P2P_PAY_TO_MID:
            case P2P_REQUEST_MONEY:
            	entity.setMerchantTxnType(MerchantTxnType.PAYMENT.toString());
            	return metaDetaInterparator.fillP2MMetaData(metaData, entity,type);   
            case ESCROW_PAYMENT:
            	 entity.setMerchantTxnType(MerchantTxnType.PAYMENT.toString());
                 return metaDetaInterparator.fillEscrowMetaData(metaData,entity);
            default:
               return null;
         }
   }


   public TransactionPaybleDto convertTransactionDetailsEntityToDto(TransactionDetailsEntity entity) {
      TransactionPaybleDto dto = new TransactionPaybleDto();
      dto.setMerchantFee(entity.getMerchantFee());
      dto.setServiceTax(entity.getServiceTax());
      dto.setSwachBharatCess(entity.getSwachBharatCess());
      dto.setNetDeduction(entity.getNetDeduction());
      dto.setTxnAmount(entity.getTxnAmount());
      dto.setAmountPayable(entity.getAmountPayable());
      return dto;
   }


   public TransactionStateDetailsEntity getTransactionDetailsStateEntity(
            NotificationMessage notifactionMsg) {
      TransactionStateDetailsEntity stateEntity = new TransactionStateDetailsEntity();
      TransactionType type  = TransactionType.valueOf(notifactionMsg.getGlobalTxnType());
      String viewState = null ;
      if(type != TransactionType.ESCROW_PAYMENT){
    	  viewState = getMerchantTSMSState(notifactionMsg);
      }else{
    	  viewState = getEscrowMerchantTxnState(notifactionMsg);
      }
      stateEntity.setTxnState(viewState);
      Date date = new Date(notifactionMsg.getTsmTimestamp());
      stateEntity.setTsmTimeStamp(date);
      return stateEntity;
   }
   
   public MerchantViewAuditEntity getMerchantViewEntity(
		   ExceptionHandlerRequest request,
		   PaymentsViewGenericException ex){
	   MerchantViewAuditEntity entity = new MerchantViewAuditEntity() ;
	   entity.setMerchantId(request.getMerchantId());
	   entity.setFcTxnId(request.getTxnId());
	   entity.setTxnType(request.getTxnType());
	   entity.setExceptionType(ex.getExceptionType().name());
	   entity.setExceptionCode(ex.getErrCode());
	   if(StringUtils.isBlank(ex.getMessage())){
		   entity.setExceptionMsg("UNKOWN ERROR");
	   }else{
		   entity.setExceptionMsg(ex.getMessage());
	   }
	   entity.setTsmTimeStamp(request.getTsmTimeStamp());
	   return entity ;
   }
   private String getEscrowMerchantTxnState(NotificationMessage notifactionMsg){
	   TsmState state = notifactionMsg.getTsmState();
	   String escrowTxnState = notifactionMsg.getGlobalTxnState();
	   EscrowEngineMetadata escrowEngineMetaData = 
			   metaDetaInterparator.getGlobalMetData(notifactionMsg.getGlobalTxnMetaData(), EscrowEngineMetadata.class);
	   switch (state) {
       case CREATED:
    		   return MerchantViewState.PENDING.getName();
       case IN_PROGRESS:
    	   if(escrowTxnState.equalsIgnoreCase("INITIALIZED")){
    		   return MerchantViewState.ON_HOLD.getName();
    	   }else if (escrowTxnState.equalsIgnoreCase("FAILED")){
    		   return MerchantViewState.PENDING.getName();
    	   }else if(escrowTxnState.equalsIgnoreCase("BLOCKED")){
    		   return MerchantViewState.PENDING.getName();
    	   }
       case DEEMED_SUCCESS:
          return MerchantViewState.PENDING.getName();
       case SUCCESS:
    	   if(escrowTxnState.equalsIgnoreCase("COMPLETED")){
    		   
    		   if(!(escrowEngineMetaData.getFinalizedAmount() == BigDecimal.ZERO) && 
    				   escrowEngineMetaData.getFinalizedAmount().equals(notifactionMsg.getGlobalTxnAmount())){
    			   return MerchantViewState.SUCCESS.getName();
    		   }else{
    			   return MerchantViewState.REFUND.getName();
    		   }
    	   }else if(escrowTxnState.equalsIgnoreCase("DECLINED")){
    		   return MerchantViewState.FAILED.getName();
    	   }
          
       case FAILED:
    	   if(escrowTxnState.equalsIgnoreCase("ROLLBACKED")){
    		   return MerchantViewState.FAILED.getName();
    	   }
       case ROLLED_BACK:
          return MerchantViewState.ROLLED_BACK.getName();
       default:
          return MerchantViewState.NOT_TO_REACH_HERE.getName();
    }
   }
   

   private String getMerchantTSMSState(NotificationMessage notifactionMsg) {
      TsmState state = notifactionMsg.getTsmState();
      switch (state) {
         case CREATED:
         case IN_PROGRESS:
         case DEEMED_SUCCESS:
            return MerchantViewState.PENDING.getName();
         case SUCCESS:
            return MerchantViewState.SUCCESS.getName();
         case FAILED:
            return MerchantViewState.FAILED.getName();
         case ROLLED_BACK:
            return MerchantViewState.ROLLED_BACK.getName();
         default:
            return MerchantViewState.NOT_TO_REACH_HERE.getName();
      }
   }
}
