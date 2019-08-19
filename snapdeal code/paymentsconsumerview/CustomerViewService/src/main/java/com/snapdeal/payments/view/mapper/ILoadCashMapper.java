package com.snapdeal.payments.view.mapper;

import java.util.List;

import com.snapdeal.payments.view.load.dto.LoadCashTxnDTO;
import com.snapdeal.payments.view.service.request.GetLoadCashTxnsMapperRequest;

public interface ILoadCashMapper {
	
	List<LoadCashTxnDTO> getLoadCashTransactions(GetLoadCashTxnsMapperRequest request);
	List<LoadCashTxnDTO> getLoadCashTxnsByUserId(GetLoadCashTxnsMapperRequest request);
	List<LoadCashTxnDTO> getLoadCashTxnsByTxnId(GetLoadCashTxnsMapperRequest request);

}
