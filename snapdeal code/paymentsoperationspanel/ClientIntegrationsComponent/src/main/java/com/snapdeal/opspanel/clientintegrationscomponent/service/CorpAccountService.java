package com.snapdeal.opspanel.clientintegrationscomponent.service;

import com.snapdeal.payments.sdmoney.service.model.CorpToCorpMoneyTransferResponse;
import com.snapdeal.payments.sdmoney.service.model.GetCorpAccountBalanceRequest;
import com.snapdeal.payments.sdmoney.service.model.GetCorpAccountBalanceResponse;
import com.snapdeal.payments.sdmoney.service.model.GetCorpAccountsForEntityRequest;
import com.snapdeal.payments.sdmoney.service.model.LoadCorpAccountResponse;
import com.snapdeal.payments.sdmoney.service.model.MerchantCorpLoadRequest;
import com.snapdeal.payments.sdmoney.service.model.MerchantRiskReserveCorpLoadRequest;
import com.snapdeal.payments.sdmoney.service.model.PGSettleCorpLoadRequest;
import com.snapdeal.payments.tsm.response.UpdateGlobalTxnResponse;
import com.snapdeal.payments.payables.model.response.PayableSettlementResponse;
import com.snapdeal.payments.sdmoney.service.model.CorpAccount;

import java.util.List;

import com.snapdeal.opspanel.clientintegrationscomponent.request.CorpToCorpMoneyTransferRequest;
import com.snapdeal.opspanel.clientintegrationscomponent.request.LoadMerchantNodalRequest;
import com.snapdeal.opspanel.clientintegrationscomponent.request.PGNodalRequest;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;

public interface CorpAccountService {

	public CorpToCorpMoneyTransferResponse corpToCorpMoneyTranfer( CorpToCorpMoneyTransferRequest corpToCorpAccountMoneyTransferRequest )
		throws OpsPanelException;
	
	public List<CorpAccount> getCorpAccountForEntity( GetCorpAccountsForEntityRequest getCorpAccountsForEntityRequest )
			throws OpsPanelException;
	
	public LoadCorpAccountResponse loadMerchantCorpAccount(MerchantCorpLoadRequest merchantCorpLoadRequest) throws OpsPanelException;
	
	public LoadCorpAccountResponse loadPGSettleCorpAccount(PGSettleCorpLoadRequest pgSettleCorpLoadRequest) throws OpsPanelException;
	
	public LoadCorpAccountResponse loadRiskReseveCorpAccount(MerchantRiskReserveCorpLoadRequest merchantRiskReserveCorpLoadRequest) throws OpsPanelException;
	
	public UpdateGlobalTxnResponse loadPGNodal(PGNodalRequest pgNodalRequest) throws OpsPanelException;

	public GetCorpAccountBalanceResponse getCorpAccountBalance( GetCorpAccountBalanceRequest getCorpAccountBalance ) throws OpsPanelException;


	public List<CorpAccount> mapCorpAccountsForEntity(List<CorpAccount> corpAccounts,
			List<String> corpAccountIds) throws OpsPanelException;

	public PayableSettlementResponse loadMerchantNodal( LoadMerchantNodalRequest loadMerchantNodalRequest ) throws OpsPanelException;

}
