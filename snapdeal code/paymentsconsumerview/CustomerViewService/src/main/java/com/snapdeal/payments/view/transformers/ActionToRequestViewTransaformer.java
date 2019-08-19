/**
 * 
 */
package com.snapdeal.payments.view.transformers;

import org.springframework.stereotype.Service;

import com.snapdeal.payments.view.entity.ActionDetailsEntity;
import com.snapdeal.payments.view.request.commons.dto.ActionDto;
import com.snapdeal.payments.view.request.commons.enums.PartyType;

/**
 * @author shubham.bansal
 *
 */
@Service("ActionToRequestViewTransaformer")
public class ActionToRequestViewTransaformer implements
		ObjectTransformer<ActionDetailsEntity, ActionDto> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.snapdeal.payments.view.transformers.ObjectTransformer#transforme(
	 * java.lang.Object)
	 */
	@Override
	public ActionDto transforme(ActionDetailsEntity src) {

		ActionDto dest = new ActionDto();
		dest.setActionId(src.getActionId());
		dest.setActionType(src.getActionType());
		dest.setActionState(src.getActionState());
		dest.setActionViewState(src.getActionViewState());
		dest.setActionTaken(src.getActionTaken());
		dest.setValidActionCommands(src.getValidActionCommands());
		dest.setActionInitiationTimestamp(src.getActionInitiationTimestamp());
		dest.setUserId(src.getUserId());
		dest.setUserType(PartyType.valueOf(src.getUserType()));
		dest.setMetadata(src.getActionContext());
		return dest;
	}

}
