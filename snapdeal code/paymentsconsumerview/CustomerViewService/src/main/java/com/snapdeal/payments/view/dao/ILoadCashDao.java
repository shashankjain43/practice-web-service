package com.snapdeal.payments.view.dao;

import java.util.List;

import com.snapdeal.payments.view.load.dto.LoadCashTxnDTO;
import com.snapdeal.payments.view.load.request.GetLoadCashTxnsByUserIdRequest;
import com.snapdeal.payments.view.load.request.GetLoadCashTxnsRequest;
import com.snapdeal.payments.view.load.response.GetLoadCashTxnsByTxnIdRequest;

public interface ILoadCashDao {
	
	List<LoadCashTxnDTO> getLoadCashTransactions(GetLoadCashTxnsRequest request);
	List<LoadCashTxnDTO> getLoadCashTxnsByUserId(GetLoadCashTxnsByUserIdRequest request);
	List<LoadCashTxnDTO> getLoadCashTxnsByTxnId(GetLoadCashTxnsByTxnIdRequest request);
}
