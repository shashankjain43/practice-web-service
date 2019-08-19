package com.snapdeal.opspanel.userpanel.stolenInstrument.model;

import java.util.List;

import lombok.Data;

import com.snapdeal.payments.sdmoney.service.model.TransactionSummary;
@Data
public class CategoryWiseTxnsInfo {
	
	private List<SentMoneyRecipient> sendMoneyTxns;
	
	private List<FreechargedUser> rechargeTxns;
	
	private List<WithdrawToBankTxn> withdrawTxns;
	
	private List<MerchantTxn> onlineOfflineMerchantsTxns;
	
	private List<VirtualCardsTxn> virtualCardTxns;
	
	private List<TransactionSummary> otherTxns;
	
	private CategorizedTxnSum categorizedTxnSum;

}
