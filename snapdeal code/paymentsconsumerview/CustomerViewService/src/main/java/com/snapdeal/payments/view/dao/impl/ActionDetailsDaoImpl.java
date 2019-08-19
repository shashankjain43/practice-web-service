package com.snapdeal.payments.view.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.payments.view.dao.IActionDetailsDao;
import com.snapdeal.payments.view.entity.ActionDetailsEntity;
import com.snapdeal.payments.view.mapper.IActionDetailsDaoMapper;
import com.snapdeal.payments.view.request.commons.dto.ActionFilter;
import com.snapdeal.payments.view.request.commons.dto.ActionTxnDetailsDTO;

@Repository
public class ActionDetailsDaoImpl implements IActionDetailsDao{

	@Autowired
	private IActionDetailsDaoMapper actionViewDaoMapper ;
	
	@Override
	public List<ActionTxnDetailsDTO> getActiontTxnDetails(
			Map<String, String> txnDetails) {
		return actionViewDaoMapper.getActiontViewTxnDetails(txnDetails);
	}

	@Override
	public void updateActionTxnDetails(
			List<Map<String, Object>> updateActionTxnDetails) {
		for(Map<String,Object> updateDetails  : updateActionTxnDetails)
			actionViewDaoMapper.updateActionTxnDetails(updateDetails);
	}

	@Override
	public void saveActionEntity(List<ActionDetailsEntity> ActionViewEntity) {
		actionViewDaoMapper.saveActionViewEntity(ActionViewEntity);
	}

	/* (non-Javadoc)
	 * @see com.snapdeal.payments.view.dao.IActionDetailsDao#getActionsByFilter(com.snapdeal.payments.view.request.commons.dto.ActionFilter)
	 */
	@Override
	public List<ActionDetailsEntity> getActionsByFilter(ActionFilter filter) {
	    return actionViewDaoMapper.getActionsByFilter(filter);
	}

}
