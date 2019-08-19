package com.snapdeal.payments.view.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.payments.view.commons.enums.ViewTypeEnum;
import com.snapdeal.payments.view.commons.exception.service.PaymentsViewGenericException;
import com.snapdeal.payments.view.commons.request.AbstractRequest;
import com.snapdeal.payments.view.dao.IPersistanceManager;
import com.snapdeal.payments.view.load.dto.LoadCashTxnDTO;
import com.snapdeal.payments.view.load.request.GetLoadCashTxnsByUserIdRequest;
import com.snapdeal.payments.view.load.request.GetLoadCashTxnsRequest;
import com.snapdeal.payments.view.load.response.GetLoadCashTxnsByTxnIdRequest;
import com.snapdeal.payments.view.load.response.GetLoadCashTxnsResponse;
import com.snapdeal.payments.view.load.service.ILoadCashService;
import com.snapdeal.payments.view.utils.PaymentsViewShardContextHolder;
import com.snapdeal.payments.view.utils.PaymentsViewShardUtil;
import com.snapdeal.payments.view.utils.validator.GenericValidator;

@Service
public class LoadCashServiceImpl implements ILoadCashService {

	@Autowired
	private IPersistanceManager persistManager;

	@Autowired
	GenericValidator<AbstractRequest> validator;

	@Autowired
	private PaymentsViewShardUtil viewShardUtil;

	public GetLoadCashTxnsResponse getLoadCashTransactions(
			GetLoadCashTxnsRequest request) throws PaymentsViewGenericException {
		GetLoadCashTxnsResponse response = new GetLoadCashTxnsResponse();
		return response;
		/*validator.validate(request);
		if (request != null) {
			MapRollbackWithFailed(request.getFilters());

			viewShardUtil.setDataBaseSource(request.getMerchantId(),
					ViewTypeEnum.MERCHANTVIEW);
		}
		//log.info("Fetching load cash txns from db: "+PaymentsViewShardContextHolder.getShardKey());
		List<LoadCashTxnDTO> transactions = persistManager.
				getLoadCashTransactions(request);
		PaymentsViewShardContextHolder.clearShardKey();
		GetLoadCashTxnsResponse response = new GetLoadCashTxnsResponse();
		response.setLcTransactions(transactions);
		return response;*/
	}
	
	/*private void MapRollbackWithFailed(LCFilterCriteria request) {
		// inducing rollback along with failed status
		if (request != null && request.getTxnStatusList() != null
				&& !request.getTxnStatusList().isEmpty()
				&& request.getTxnStatusList().contains(LoadCashTxnStatus.FAILED)) {
			request.getTxnStatusList().add(LoadCashTxnStatus.ROLLED_BACK);
		}
	}*/

	@Override
	public GetLoadCashTxnsResponse getLoadCashTxnsByUserId(
			GetLoadCashTxnsByUserIdRequest request)
			throws PaymentsViewGenericException {
		validator.validate(request);
		viewShardUtil.setDataBaseSource(request.getMerchantId(),
				ViewTypeEnum.MERCHANTVIEW);
		List<LoadCashTxnDTO> transactions = persistManager.
				getLoadCashTxnsByUserId(request);
		PaymentsViewShardContextHolder.clearShardKey();
		GetLoadCashTxnsResponse response = new GetLoadCashTxnsResponse();
		response.setLcTransactions(transactions);
		return response;
	}

	@Override
	public GetLoadCashTxnsResponse getLoadCashTxnsByTxnId(
			GetLoadCashTxnsByTxnIdRequest request)
			throws PaymentsViewGenericException {
		validator.validate(request);
		viewShardUtil.setDataBaseSource(request.getMerchantId(),
				ViewTypeEnum.MERCHANTVIEW);
		List<LoadCashTxnDTO> transactions = persistManager.
				getLoadCashTxnsByTxnId(request);
		PaymentsViewShardContextHolder.clearShardKey();
		GetLoadCashTxnsResponse response = new GetLoadCashTxnsResponse();
		response.setLcTransactions(transactions);
		return response;
	}
	

	
}