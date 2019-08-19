package com.snapdeal.opspanel.userpanel.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.userpanel.request.SearchShopoTransactionRequest;
import com.snapdeal.opspanel.userpanel.response.ShopoTransactionDetails;
import com.snapdeal.opspanel.userpanel.service.ShopoService;
import com.snapdeal.payments.escrowengine.model.ChildTransaction;
import com.snapdeal.payments.escrowengine.model.GetAssociatedTransactionsByTxnDisplayIdRequest;
import com.snapdeal.payments.escrowengine.model.GetAssociatedTransactionsByTxnDisplayIdResponse;
import com.snapdeal.payments.escrowengine.model.GetChildTransactionsByInitializeTxnIdRequest;
import com.snapdeal.payments.escrowengine.model.GetChildTransactionsByInitializeTxnIdResponse;
import com.snapdeal.payments.escrowengine.model.Transaction;
import com.snapdeal.payments.metadata.DisbursementMetadata;
import com.snapdeal.payments.p2pengine.exceptions.GenericException;
import com.snapdeal.payments.p2pengine.exceptions.service.ValidationException;
import com.snapdeal.payments.p2pengine.external.client.impl.EscrowEngineClient;
import com.snapdeal.payments.view.commons.enums.TransactionType;
import com.snapdeal.payments.view.commons.utils.TSMMetaDataParser;
import com.snapdeal.payments.view.merchant.commons.dto.MVTxnStatusDTO;
import com.snapdeal.payments.view.merchant.commons.dto.MVTxnWithMetaDataDTO;
import com.snapdeal.payments.view.merchant.commons.enums.MVTransactionStatus;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTxnStatusHistoryByTxnIdRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTxnsByOrderIdRequest;
import com.snapdeal.payments.view.merchant.commons.response.GetMerchantTxnStatusHistoryResponse;
import com.snapdeal.payments.view.merchant.commons.response.GetMerchantTxnsWithMetaDataResponse;
import com.snapdeal.payments.view.merchant.commons.service.IMerchantViewService;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

@Service("shoposervice")
@Slf4j
public class ShopoServiceImpl implements ShopoService {

	@Autowired
	private EscrowEngineClient escrowEngineClient;

	@Autowired
	private IMerchantViewService mvClient;

	private SimpleDateFormat sdf;

	private static final String sdfString = "EEE, MMM d, yyyy hh:mm:ss a z";
	private static final String timeZone = "IST";
	private static final String COULD_NOT_FETCH = "Could Not Fetch";

	@PostConstruct
	private void init() {
		sdf = new SimpleDateFormat( sdfString );
		sdf.setTimeZone( TimeZone.getTimeZone( timeZone ) );
	}

	@Override
	public List<ShopoTransactionDetails> searchShopoTransaction(SearchShopoTransactionRequest request)
			throws OpsPanelException {

		List<ShopoTransactionDetails> shopoTransactionDetailsList = null;

		switch (request.getIdentifier()) {
			case shopoOrderId:
				shopoTransactionDetailsList = getTransactionsFromP2PByOrderId( request.getSearchValue() );
				break;

			case parentTransactionId:
				shopoTransactionDetailsList = getTransactionsFromP2PByParentTransactionId( request.getSearchValue() );
				break;
		}

		String merchantId = shopoTransactionDetailsList.get( 0 ).getMerchantId();
		String orderId = shopoTransactionDetailsList.get( 0 ).getOrderId();
		String disbursementTransactionReference = shopoTransactionDetailsList.get( 0 ).getDisbursementTransactionReference();

		GetMerchantTxnsByOrderIdRequest getMerchantTxnsByOrderIdRequest = new GetMerchantTxnsByOrderIdRequest();
		GetMerchantTxnsWithMetaDataResponse getMerchantTxnsWithMetaDataResponse = null;

		getMerchantTxnsByOrderIdRequest.setMerchantId(merchantId);
		getMerchantTxnsByOrderIdRequest.setOrderId(orderId);

		Date productDeliveryDate = null;
		String settlementID = null;
		Date settlementDate = null;
		String bankURN = null;

		try {
			getMerchantTxnsWithMetaDataResponse = mvClient.getMerchantTransactionsByOrderId(getMerchantTxnsByOrderIdRequest);
			
			if(getMerchantTxnsWithMetaDataResponse.getMvTransactions() == null || getMerchantTxnsWithMetaDataResponse.getMvTransactions().size() == 0 ){
				throw new OpsPanelException("CI-0012", "No mvTransactions found"); 
			}
			
			for(MVTxnWithMetaDataDTO mvTxnWithMetaDataDTO:getMerchantTxnsWithMetaDataResponse.getMvTransactions()){
				if(mvTxnWithMetaDataDTO.getDisbursementMetaData() != null ){
	
					DisbursementMetadata disbursementMetadata = null;
					try {
						disbursementMetadata = TSMMetaDataParser.getDisburseMetaData(mvTxnWithMetaDataDTO.getDisbursementMetaData());
					} catch ( Exception e ) {
						log.info("Exception from searchShopoTransaction while mapping mvTxnWithMetaDataDTO to DisbursementMetadata " + ExceptionUtils.getFullStackTrace(e));
						throw new OpsPanelException("CI-0013", e.getMessage());
					}
	
					bankURN = disbursementMetadata.getTransactionReference();
					settlementID = mvTxnWithMetaDataDTO.getSettlementId();
					settlementDate = mvTxnWithMetaDataDTO.getSettlementDate();				
					break;
				}
			}
			
			GetMerchantTxnStatusHistoryByTxnIdRequest getMerchantTxnStatusHistoryByTxnIdRequest = new GetMerchantTxnStatusHistoryByTxnIdRequest();
			
			getMerchantTxnStatusHistoryByTxnIdRequest.setTxnRefType(TransactionType.ESCROW_PAYMENT);
			getMerchantTxnStatusHistoryByTxnIdRequest.setTxnRefId( disbursementTransactionReference );
			getMerchantTxnStatusHistoryByTxnIdRequest.setMerchantId(merchantId);
	
			GetMerchantTxnStatusHistoryResponse getMerchantTxnStatusHistoryResponse = mvClient.getMerchantTxnStatusHistoryByTxnId(getMerchantTxnStatusHistoryByTxnIdRequest);
	
			if(getMerchantTxnStatusHistoryResponse.getMvTxnStatusDTO() == null || getMerchantTxnStatusHistoryResponse.getMvTxnStatusDTO().size() == 0){
				throw new OpsPanelException("CI-0014", "No mvTxnStatusDTO found"); 
			}
 
			for(MVTxnStatusDTO mvTxnStatusDTO:getMerchantTxnStatusHistoryResponse.getMvTxnStatusDTO()){
				if(mvTxnStatusDTO.getTxnState().equalsIgnoreCase(MVTransactionStatus.SUCCESS.toString())){
					productDeliveryDate = mvTxnStatusDTO.getTxnDate();
					break;
				}
			}
					
			for(ShopoTransactionDetails shopoTransactionDetails : shopoTransactionDetailsList){
	
				shopoTransactionDetails.setBankURN(bankURN);
				shopoTransactionDetails.setSettlementID(settlementID);
				shopoTransactionDetails.setSettlementDate(sdf.format(settlementDate));
				shopoTransactionDetails.setProductDeliveryDate(sdf.format(productDeliveryDate));
			}
		} catch( Exception e ) {
			
			log.info("Exception from searchShopoTransaction while hitting merchant view" + ExceptionUtils.getFullStackTrace(e));
			for(ShopoTransactionDetails shopoTransactionDetails : shopoTransactionDetailsList){	
			
				shopoTransactionDetails.setBankURN(bankURN == null ? "Could not fetch" : bankURN);
				shopoTransactionDetails.setSettlementID(settlementID == null ? "Could not fetch" : settlementID);
				shopoTransactionDetails.setSettlementDate(settlementDate == null ? "Could not fetch" : sdf.format(settlementDate));
				shopoTransactionDetails.setProductDeliveryDate(productDeliveryDate == null ? "Could not fetch" : sdf.format(productDeliveryDate));
			}
		}
		return shopoTransactionDetailsList;
	}

	private List<ShopoTransactionDetails> getTransactionsFromP2PByOrderId( String orderId ) throws OpsPanelException {

		GetAssociatedTransactionsByTxnDisplayIdRequest getAssociatedTransactionsByTxnDisplayIdRequest = new GetAssociatedTransactionsByTxnDisplayIdRequest();
		getAssociatedTransactionsByTxnDisplayIdRequest.setTransactionDisplayIdentifier( orderId );
		GetAssociatedTransactionsByTxnDisplayIdResponse getAssociatedTransactionsByTxnDisplayIdResponse = null;

		try {
			getAssociatedTransactionsByTxnDisplayIdResponse = escrowEngineClient.getAssociatedTransactionsByTxnDisplayId(getAssociatedTransactionsByTxnDisplayIdRequest);
		}  catch ( GenericException e ) {
			log.info("GenericException catched in searchShopoTransaction with identifier orderId and searchValue " + orderId + ExceptionUtils.getFullStackTrace(e));
			if( e.getError() != null && e.getError().getMessage() != null ) {
				throw new OpsPanelException( "CI-0010", e.getError().getMessage() );
			} else {
				throw new OpsPanelException( "CI-0010", "Could not fetch results from P2P" );
			}
		} catch (ValidationException e) {
			log.info("Exception catched in searchShopoTransaction with identifier orderId and searchValue " + orderId + ExceptionUtils.getFullStackTrace(e));
			if( e.getErrors() != null && e.getErrors().get( 0 ) != null ) {
				throw new OpsPanelException( "CI-0011", e.getErrors().get(0).getMessage() );
			} else {
				throw new OpsPanelException( "CI-0011", "Could not fetch results from P2P" );
			}
		}

		List<Transaction> transactionList = null;

		if( getAssociatedTransactionsByTxnDisplayIdResponse != null ) {
			transactionList = getAssociatedTransactionsByTxnDisplayIdResponse.getTransactionList();
		} else {
			throw new OpsPanelException( "CI-0011", "Could not fetch results from P2P" );
		}
		List<ShopoTransactionDetails> shopoTransactionDetailsList = new ArrayList<ShopoTransactionDetails>();
		String disbursementTransactionReference = null;

		for(Transaction transaction : transactionList){
			if( transaction != null ) {
				ShopoTransactionDetails shopoTransactionDetails = new ShopoTransactionDetails();

				shopoTransactionDetails.setTransactionId(transaction.getWalletTransactionId());
				shopoTransactionDetails.setAmount(transaction.getAmount());
				shopoTransactionDetails.setPaymentType(transaction.getTransactionType());
				shopoTransactionDetails.setTxnStatus(transaction.getTransactionStatus());
				shopoTransactionDetails.setOrderId(transaction.getTransactionDisplayIdentifier());
				shopoTransactionDetails.setTimestamp(sdf.format(transaction.getTransactionTimeStamp()));
				shopoTransactionDetails.setMerchantId( transaction.getDestinationImsId() );
				shopoTransactionDetails.setIdempotencyId( transaction.getIdempotencyId() );
				if( transaction.getParentTransactionId() != null && disbursementTransactionReference == null ) {
					disbursementTransactionReference = transaction.getParentTransactionId();
				}

				shopoTransactionDetailsList.add(shopoTransactionDetails);
			}
		}

		for( ShopoTransactionDetails shopoTransactionDetails : shopoTransactionDetailsList ) {
			shopoTransactionDetails.setDisbursementTransactionReference( disbursementTransactionReference );
		}

		return shopoTransactionDetailsList;
	}

	private List<ShopoTransactionDetails> getTransactionsFromP2PByParentTransactionId( String parentTransactionId ) throws OpsPanelException {

		GetChildTransactionsByInitializeTxnIdRequest getChildTransactionsByInitializeTxnIdRequest = new GetChildTransactionsByInitializeTxnIdRequest();
		getChildTransactionsByInitializeTxnIdRequest.setTransactionId( parentTransactionId );
		GetChildTransactionsByInitializeTxnIdResponse getChildTransactionsByInitializeTxnIdResponse = null;
		try {
			getChildTransactionsByInitializeTxnIdResponse = escrowEngineClient.getChildTransactionsByInitializeTxnId(getChildTransactionsByInitializeTxnIdRequest);
		}  catch ( GenericException e ) {
			log.info("GenericException catched in searchShopoTransaction with identifier parentTransactionId and searchValue " + parentTransactionId + ExceptionUtils.getFullStackTrace(e));
			if( e.getError() != null && e.getError().getMessage() != null ) {
				throw new OpsPanelException( "CI-0010", e.getError().getMessage() );
			} else {
				throw new OpsPanelException( "CI-0010", "Could not fetch results from P2P" );
			}
		} catch (ValidationException e) {
			log.info("Exception catched in searchShopoTransaction with identifier parentTransactionId and searchValue " + parentTransactionId + ExceptionUtils.getFullStackTrace(e));
			if( e.getErrors() != null && e.getErrors().get( 0 ) != null ) {
				throw new OpsPanelException( "CI-0011", e.getErrors().get(0).getMessage() );
			} else {
				throw new OpsPanelException( "CI-0011", "Could not fetch results from P2P" );
			}
		}

		List<ChildTransaction> childTransactionList = null;

		if( getChildTransactionsByInitializeTxnIdResponse != null
				&& getChildTransactionsByInitializeTxnIdResponse.getChildTransactions() != null
				&& getChildTransactionsByInitializeTxnIdResponse.getChildTransactions().size() > 0 ) {
			childTransactionList = getChildTransactionsByInitializeTxnIdResponse.getChildTransactions();
		} else {
			throw new OpsPanelException( "CI-0011", "Could not fetch results from P2P" );
		}

		try {
			return getTransactionsFromP2PByOrderId( childTransactionList.get( 0 ).getTransactionDisplayIdentifier() );
		} catch( OpsPanelException ope ) {
			log.info( "OpsPanelException catched in getTransactionsFromP2PByParentTransactionId " + ExceptionUtils.getFullStackTrace( ope ) );
			List<ShopoTransactionDetails> shopoTransactionDetailsList = new ArrayList<ShopoTransactionDetails>();
			String disbursementTransactionReference = null;
			String orderId = null;
			for( ChildTransaction childTransaction : childTransactionList ) {

				disbursementTransactionReference = childTransaction.getParentTransactionId();
				orderId = childTransaction.getTransactionDisplayIdentifier();
				ShopoTransactionDetails shopoTransactionDetails = new ShopoTransactionDetails();
				shopoTransactionDetails.setTransactionId( childTransaction.getWalletTransactionId() );
				shopoTransactionDetails.setAmount( childTransaction.getAmount() );
				shopoTransactionDetails.setPaymentType( childTransaction.getTransactionType() );
				shopoTransactionDetails.setTxnStatus( childTransaction.getTransactionStatus() );
				shopoTransactionDetails.setOrderId( orderId );
				shopoTransactionDetails.setTimestamp( sdf.format( childTransaction.getTransactionTimeStamp() ) );
				shopoTransactionDetails.setMerchantId( getChildTransactionsByInitializeTxnIdResponse.getDestinationImsId() );
				shopoTransactionDetails.setDisbursementTransactionReference( disbursementTransactionReference );
				shopoTransactionDetailsList.add( shopoTransactionDetails );

			}

			ShopoTransactionDetails shopoTransactionDetails = new ShopoTransactionDetails();
			shopoTransactionDetails.setTransactionId( getChildTransactionsByInitializeTxnIdResponse.getTransactionId() );
			shopoTransactionDetails.setAmount( getChildTransactionsByInitializeTxnIdResponse.getInitializedAmount() );
			shopoTransactionDetails.setPaymentType( COULD_NOT_FETCH );
			shopoTransactionDetails.setTimestamp( COULD_NOT_FETCH );
			shopoTransactionDetails.setMerchantId( getChildTransactionsByInitializeTxnIdResponse.getDestinationImsId() );
			shopoTransactionDetails.setDisbursementTransactionReference( disbursementTransactionReference );
			shopoTransactionDetails.setOrderId( orderId );

			if( getChildTransactionsByInitializeTxnIdResponse.getOrderDetails() != null ) {
				shopoTransactionDetails.setOrderId( getChildTransactionsByInitializeTxnIdResponse.getOrderDetails().getOrderId() );
			} else {
				shopoTransactionDetails.setOrderId( COULD_NOT_FETCH );
			}

			shopoTransactionDetailsList.add( shopoTransactionDetails );
			return shopoTransactionDetailsList;
		}
	}
}
