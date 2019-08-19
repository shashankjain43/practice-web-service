package com.snapdeal.opspanel.clientintegrationscomponent.service.impl;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.clientintegrationscomponent.enums.GlobalTxnState;
import com.snapdeal.opspanel.clientintegrationscomponent.request.CorpToCorpMoneyTransferRequest;
import com.snapdeal.opspanel.clientintegrationscomponent.request.LoadMerchantNodalRequest;
import com.snapdeal.opspanel.clientintegrationscomponent.request.PGNodalRequest;
import com.snapdeal.opspanel.clientintegrationscomponent.service.CorpAccountService;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.payments.metadata.aggregator.PGNodalMetadata;
import com.snapdeal.payments.metadata.interpreter.PGNodalMetadataInterpreter;
import com.snapdeal.payments.payables.client.PayablesClient;
import com.snapdeal.payments.payables.exception.PayablesException;
import com.snapdeal.payments.payables.model.request.GetPayableAccountIdByMerchantIdRequest;
import com.snapdeal.payments.payables.model.request.PayableSettlementRequest;
import com.snapdeal.payments.payables.model.response.GetPayableAccountIdByMerchantIdResponse;
import com.snapdeal.payments.payables.model.response.PayableSettlementResponse;
import com.snapdeal.payments.sdmoney.client.SDMoneyClient;
import com.snapdeal.payments.sdmoney.exceptions.SDMoneyException;
import com.snapdeal.payments.sdmoney.service.model.CorpAccount;
import com.snapdeal.payments.sdmoney.service.model.CorpToCorpMoneyTransferResponse;
import com.snapdeal.payments.sdmoney.service.model.GetCorpAccountBalanceRequest;
import com.snapdeal.payments.sdmoney.service.model.GetCorpAccountBalanceResponse;
import com.snapdeal.payments.sdmoney.service.model.GetCorpAccountsForEntityRequest;
import com.snapdeal.payments.sdmoney.service.model.LoadCorpAccountResponse;
import com.snapdeal.payments.sdmoney.service.model.MerchantCorpLoadRequest;
import com.snapdeal.payments.sdmoney.service.model.MerchantRiskReserveCorpLoadRequest;
import com.snapdeal.payments.sdmoney.service.model.PGSettleCorpLoadRequest;
import com.snapdeal.payments.tsm.client.ITxnServiceClient;
import com.snapdeal.payments.tsm.enums.ClientPlatform;
import com.snapdeal.payments.tsm.enums.LegalEntity;
import com.snapdeal.payments.tsm.enums.PartyType;
import com.snapdeal.payments.tsm.enums.TxnTransferDirection;
import com.snapdeal.payments.tsm.exception.ClientRequestParameterException;
import com.snapdeal.payments.tsm.exception.ServiceException;
import com.snapdeal.payments.tsm.request.CreateTxnRequest;
import com.snapdeal.payments.tsm.request.LocalTxnRequest;
import com.snapdeal.payments.tsm.request.UpdateGlobalTxnRequest;
import com.snapdeal.payments.tsm.response.CreateTxnResponse;
import com.snapdeal.payments.tsm.response.UpdateGlobalTxnResponse;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

@Slf4j
@Service("CorpAccountService")
public class CorpAccountServiceImpl implements CorpAccountService {

	@Autowired
	SDMoneyClient sdMoneyClient;

	@Autowired
	ITxnServiceClient txnServiceClient;

	@Autowired
	PayablesClient payablesClient;

	private static final String CORP_ACCOUNT = "CORP ACCOUNT";

	@Override
	public CorpToCorpMoneyTransferResponse corpToCorpMoneyTranfer(
			CorpToCorpMoneyTransferRequest corpToCorpAccountMoneyTransferRequest) throws OpsPanelException {
		try {
			CorpToCorpMoneyTransferResponse corpToCorpMoneyTransferResponse = sdMoneyClient.corpToCorpMoneyTranfer( corpToCorpAccountMoneyTransferRequest.getCorpToCorpMoneyTransferRequest() );
			if( corpToCorpAccountMoneyTransferRequest.getEnablePayablesEntry() ) {
				PayableSettlementRequest settlePayableBalanceForWalletRequest = new PayableSettlementRequest();
				settlePayableBalanceForWalletRequest.setAmount( corpToCorpAccountMoneyTransferRequest.getCorpToCorpMoneyTransferRequest().getAmount() );
				settlePayableBalanceForWalletRequest.setEventContext( corpToCorpAccountMoneyTransferRequest.getCorpToCorpMoneyTransferRequest().getEventContext() );
				settlePayableBalanceForWalletRequest.setIdempotencyId( corpToCorpAccountMoneyTransferRequest.getCorpToCorpMoneyTransferRequest().getIdempotencyId() );
	
				GetPayableAccountIdByMerchantIdRequest getPayableAccountIdByMerchantIdRequest = new GetPayableAccountIdByMerchantIdRequest();
				getPayableAccountIdByMerchantIdRequest.setMerchantId( corpToCorpAccountMoneyTransferRequest.getDestinationMerchantId() );
				getPayableAccountIdByMerchantIdRequest.setMerchantDomain( "Payables" );
				GetPayableAccountIdByMerchantIdResponse getPayableAccountIdByMerchantIdResponse = payablesClient.getPayableAccountIdByMerchantId( getPayableAccountIdByMerchantIdRequest );
	
				settlePayableBalanceForWalletRequest.setPayableAccountId( getPayableAccountIdByMerchantIdResponse.getPayableAccountId() );
				settlePayableBalanceForWalletRequest.setTransactionReference( corpToCorpAccountMoneyTransferRequest.getCorpToCorpMoneyTransferRequest().getTransactionReference() );
				payablesClient.settlePayableBalanceForWallet( settlePayableBalanceForWalletRequest );
			}
			return corpToCorpMoneyTransferResponse;
		} catch (SDMoneyException e) {
			log.info("Exception catched while corpToCorpMoneyTransfer in sdmoneyclient "
					+ ExceptionUtils.getFullStackTrace(e));
			throw new OpsPanelException("CI-0001", e.getMessage());
		}  catch( PayablesException pe ) {
			log.info( "Exception catched while making entry to payables system. " + ExceptionUtils.getFullStackTrace( pe ) );
			throw new OpsPanelException( "CI-0010", "Could Not load money in payables. Please try again with same idempotency Id" + pe.getMessage() );
		} catch (Exception e) {
			log.info("Exception catched while corpToCorpMoneyTransfer in sdmoneyclient "
					+ ExceptionUtils.getFullStackTrace(e));
			throw new OpsPanelException("CI-0002", e.getMessage());
		}
	}

	@Override
	public List<CorpAccount> getCorpAccountForEntity(GetCorpAccountsForEntityRequest getCorpAccountsForEntityRequest)
			throws OpsPanelException {
		// TODO Auto-generated method stub
		try {
			return sdMoneyClient.getCorpAccountsForEntity(getCorpAccountsForEntityRequest);
		} catch (SDMoneyException e) {
			if (log.isInfoEnabled()) {
				log.info("Exception catched while GettingCorpAccountForEntity in sdmoneyclient"
						+ ExceptionUtils.getFullStackTrace(e));
			}
			throw new OpsPanelException("CT-0003", e.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			if (log.isInfoEnabled()) {
				log.info("Exception catched while GettingCorpAccountForEntity in sdmoneyclient"
						+ ExceptionUtils.getFullStackTrace(e));
			}
			throw new OpsPanelException("CT-0004", e.getMessage());

		}

	}

	@Override
	public LoadCorpAccountResponse loadMerchantCorpAccount(MerchantCorpLoadRequest merchantCorpLoadRequest)
			throws OpsPanelException {
		// TODO Auto-generated method stub
		try {
			return sdMoneyClient.loadMerchantCorpAccount(merchantCorpLoadRequest);
		} catch (SDMoneyException e) {
			if (log.isInfoEnabled()) {
				log.info("Exception catched while loadMerchantCorpAccount in sdmoneyclient"
						+ ExceptionUtils.getFullStackTrace(e));
			}
			throw new OpsPanelException("CT-0005", e.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			if (log.isInfoEnabled()) {
				log.info("Exception catched while loadMerchantCorpAccount in sdmoneyclient"
						+ ExceptionUtils.getFullStackTrace(e));
			}
			throw new OpsPanelException("CT-0006", e.getMessage());

		}
	}

	@Override
	public LoadCorpAccountResponse loadPGSettleCorpAccount(PGSettleCorpLoadRequest pgSettleCorpLoadRequest)
			throws OpsPanelException {
		try {
			return sdMoneyClient.loadPGSettleCorpAccount(pgSettleCorpLoadRequest);
		} catch (SDMoneyException e) {
			if (log.isInfoEnabled()) {
				log.info("Exception catched while loadPGSettleCorpAccount in sdmoneyclient"
						+ ExceptionUtils.getFullStackTrace(e));
			}
			throw new OpsPanelException("CT-0007", e.getMessage());
		} catch (Exception e) {
			if (log.isInfoEnabled()) {
				log.info("Exception catched while loadPGSettleCorpAccount in sdmoneyclient"
						+ ExceptionUtils.getFullStackTrace(e));
			}
			throw new OpsPanelException("CT-0008", e.getMessage());

		}

	}

	@Override
	public LoadCorpAccountResponse loadRiskReseveCorpAccount(
			MerchantRiskReserveCorpLoadRequest merchantRiskReserveCorpLoadRequest) throws OpsPanelException {
		try {
			return sdMoneyClient.loadMerchantRiskReserveCorpAccount(merchantRiskReserveCorpLoadRequest);
		} catch (SDMoneyException e) {
			if (log.isInfoEnabled()) {
				log.info("Exception catched while loadMerchantRiskReserveCorpAccount in sdmoneyclient"
						+ ExceptionUtils.getFullStackTrace(e));
			}
			throw new OpsPanelException("CT-0009", e.getMessage());
		} catch (Exception e) {
			if (log.isInfoEnabled()) {
				log.info("Exception catched while loadMerchantRiskReserveCorpAccount in sdmoneyclient"
						+ ExceptionUtils.getFullStackTrace(e));
			}
			throw new OpsPanelException("CT-0010", e.getMessage());

		}
	}

	@Override
	public UpdateGlobalTxnResponse loadPGNodal(PGNodalRequest req) throws OpsPanelException {
		CreateTxnResponse response = new CreateTxnResponse();
		CreateTxnRequest request = new CreateTxnRequest();
		//String globalTxnId = generateId(req.getNeftId(), req.getTimestamp(), 1);
		String globalTxnId = req.getNeftId();

		List<LocalTxnRequest> localTxns = new ArrayList<LocalTxnRequest>();
		LocalTxnRequest localTxnRequestforAmount = new LocalTxnRequest();
		localTxnRequestforAmount.setLocalTxnAmount(req.getAmount());
		localTxnRequestforAmount.setLocalTxnPartyType(PartyType.PG);
		localTxnRequestforAmount.setLocalTxnLegalEntity(LegalEntity.KP);
		localTxnRequestforAmount.setLocalTxnTransferDirection(TxnTransferDirection.PARTY_TO_LE);
		localTxnRequestforAmount.setLocalTxnState(GlobalTxnState.INITIATED.toString());
		localTxnRequestforAmount.setLocalTxnId(globalTxnId + "PG");
		localTxnRequestforAmount.setLocalTxnPartyId("PG");
		localTxns.add(localTxnRequestforAmount);

		LocalTxnRequest localTxnRequestforFeeAmount = new LocalTxnRequest();
		localTxnRequestforFeeAmount.setLocalTxnAmount(req.getFeeAmount());
		localTxnRequestforFeeAmount.setLocalTxnPartyType(PartyType.PG_FEE);
		localTxnRequestforFeeAmount.setLocalTxnLegalEntity(LegalEntity.KP);
		localTxnRequestforFeeAmount.setLocalTxnTransferDirection(TxnTransferDirection.PARTY_TO_LE);
		localTxnRequestforFeeAmount.setLocalTxnState(GlobalTxnState.INITIATED.toString());
		localTxnRequestforFeeAmount.setLocalTxnId(globalTxnId + "PGFee");
		localTxnRequestforFeeAmount.setLocalTxnPartyId("PGFee");
		localTxns.add(localTxnRequestforFeeAmount);

		request.setLocalTxns(localTxns);

		request.setGlobalTxnAmount(
				localTxnRequestforAmount.getLocalTxnAmount().add(localTxnRequestforFeeAmount.getLocalTxnAmount()));

		String globalTxnMetadata = getMetadataString(req);
		request.setGlobalTxnMetaData(globalTxnMetadata);

		request.setGlobalTxnId(globalTxnId);
		String globalTxnType = "DEBUGGER_DASHBOARD_PG_LOAD_NODAL_ACCOUNT";
		request.setGlobalTxnType(globalTxnType);
		request.setMerchantId("MERCHANT_NODAL");
		request.setGlobalTxnState(GlobalTxnState.INITIATED.toString());
		request.setSourceSystem("PGNodal");
		request.setClientPlatform(ClientPlatform.WEB);
		try {
			response = txnServiceClient.createTxn(request);
		} catch (ServiceException se) {
			if (log.isInfoEnabled()) {
				log.info("Exception catched while loadPGNodal in TSM Client"
						+ ExceptionUtils.getFullStackTrace(se));
			}
			throw new OpsPanelException(String.valueOf(se.getErrCode()), se.getErrMsg());
		}  catch (Exception e) {
			if (log.isInfoEnabled()) {
				log.info("Exception catched while loadPGNodal in TSM Client"
						+ ExceptionUtils.getFullStackTrace(e));
			}
			throw new OpsPanelException("CT-0011", e.getMessage());

		}

		UpdateGlobalTxnRequest updateGlobalTxnRequest = new UpdateGlobalTxnRequest();
		updateGlobalTxnRequest.setDoNotify(true);
		updateGlobalTxnRequest.setGlobalTxnMetaData(globalTxnMetadata);
		updateGlobalTxnRequest.setGlobalTxnState(GlobalTxnState.COMPLETED.toString());

		try {
			return txnServiceClient.updateGlobalTxn(globalTxnId, globalTxnType, updateGlobalTxnRequest);
		} catch (ServiceException se) {
			if (log.isInfoEnabled()) {
				log.info("Exception catched while loadPGNodal in TSM Client"
						+ ExceptionUtils.getFullStackTrace(se));
			}
			throw new OpsPanelException(String.valueOf(se.getErrCode()), se.getErrMsg());
		} catch (ClientRequestParameterException se) {
			if (log.isInfoEnabled()) {
				log.info("Exception catched while loadPGNodal in TSM Client"
						+ ExceptionUtils.getFullStackTrace(se));
			}
			throw new OpsPanelException("CLIENT_REQUEST_PARAMETER_EXCEPTION", se.getMessage());
		} catch (Exception e) {
			if (log.isInfoEnabled()) {
				log.info("Exception catched while loadPGNodal in TSM Client"
						+ ExceptionUtils.getFullStackTrace(e));
			}
			throw new OpsPanelException("CT-0012", e.getMessage());

		}

	}

	public String getMetadataString(PGNodalRequest request) {

		PGNodalMetadataInterpreter interpreter = new PGNodalMetadataInterpreter();

		PGNodalMetadata metadata = new PGNodalMetadata();
		metadata.setNeftId(request.getNeftId());
		metadata.setPgName(request.getPgType());
		metadata.setTimestamp(request.getTimestamp());
	//	metadata.setReferenceId(request.getIdempotencyId());
		metadata.setDomain("Payables");

		return interpreter.getString(metadata);

	}

	public String generateId(String neftId, long timestamp, int size) {
		return new StringBuilder().append(neftId).append('_').append(timestamp).append('_').append(size).toString();

	}

	@Override
	public List<CorpAccount> mapCorpAccountsForEntity(List<CorpAccount> corpAccounts, List<String> corpAccountIds)
			throws OpsPanelException {

		ArrayList<CorpAccount> mappedCorpAccounts = new ArrayList<CorpAccount>();

		for (String corpAccountId : corpAccountIds) {

			for (CorpAccount corpAccount : corpAccounts) {

				if (corpAccount.getAccountId().equals(corpAccountId)) {

					mappedCorpAccounts.add(corpAccount);
				}

			}

		}

		return mappedCorpAccounts;
	}

	@Override
	public GetCorpAccountBalanceResponse getCorpAccountBalance(GetCorpAccountBalanceRequest getCorpAccountBalanceRequest )
			throws OpsPanelException {
		try {
			return sdMoneyClient.getCorpAccountBalance(getCorpAccountBalanceRequest);
		} catch (SDMoneyException e) {
			log.info("Exception catched while getCorpAccountBalance in sdmoneyclient "
					+ ExceptionUtils.getFullStackTrace(e));
			throw new OpsPanelException("CI-0013", e.getMessage());
		} catch (Exception e) {
			log.info("Exception catched while getCorpAccountBalance in sdmoneyclient "
					+ ExceptionUtils.getFullStackTrace(e));
			throw new OpsPanelException("CI-0014", e.getMessage());
		}
	}

	@Override
	public PayableSettlementResponse loadMerchantNodal(LoadMerchantNodalRequest loadMerchantNodalRequest)
			throws OpsPanelException {

		try {

			GetPayableAccountIdByMerchantIdRequest getPayableAccountIdByMerchantIdRequest = new GetPayableAccountIdByMerchantIdRequest();
			getPayableAccountIdByMerchantIdRequest.setMerchantId( loadMerchantNodalRequest.getMerchantId() );
			getPayableAccountIdByMerchantIdRequest.setMerchantDomain( "Payables" );
			GetPayableAccountIdByMerchantIdResponse getPayableAccountIdByMerchantIdResponse = payablesClient.getPayableAccountIdByMerchantId( getPayableAccountIdByMerchantIdRequest );
			PayableSettlementRequest payableSettlementRequest = loadMerchantNodalRequest.getPayableSettlementRequest();
			payableSettlementRequest.setPayableAccountId( getPayableAccountIdByMerchantIdResponse.getPayableAccountId() );
			return payablesClient.settlePayableBalanceForPG( payableSettlementRequest );

		} catch( PayablesException payablesException ) {

			log.info( "Exception occurred while load merchant nodal in corp account service " + ExceptionUtils.getFullStackTrace( payablesException ) );
			throw new OpsPanelException( payablesException.getErrorCode().getErrorCode().toString(), payablesException.getMessage(), CORP_ACCOUNT );

		}
	}
}
