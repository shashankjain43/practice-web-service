package com.snapdeal.payments.view.dao;

import java.util.List;
import java.util.Map;

import com.snapdeal.payments.view.entity.ActionDetailsEntity;
import com.snapdeal.payments.view.request.commons.dto.ActionFilter;
import com.snapdeal.payments.view.request.commons.dto.ActionTxnDetailsDTO;

public interface IActionDetailsDao {

	public List<ActionTxnDetailsDTO> getActiontTxnDetails(
			Map<String,String> txnDetails);
	
	public void updateActionTxnDetails(List<Map<String,Object>> updateActionTxnDetails);
	
	public void saveActionEntity(List<ActionDetailsEntity> ActionViewEntity) ;
	
	public List<ActionDetailsEntity> getActionsByFilter(ActionFilter filter);

	
}
