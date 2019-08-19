package com.snapdeal.payments.view.mapper;

import java.util.List;
import java.util.Map;

import com.snapdeal.payments.view.entity.ActionDetailsEntity;
import com.snapdeal.payments.view.request.commons.dto.ActionFilter;
import com.snapdeal.payments.view.request.commons.dto.ActionTxnDetailsDTO;

public interface IActionDetailsDaoMapper {
	public List<ActionTxnDetailsDTO> getActiontViewTxnDetails(
			Map<String, String> txnDetails);

	public void updateActionTxnDetails(
			Map<String, Object> updateActionTxnDetails);

	public void saveActionViewEntity(List<ActionDetailsEntity> ActionViewEntity);
	
	
	public List<ActionDetailsEntity> getActionsByFilter(ActionFilter filter);
}
