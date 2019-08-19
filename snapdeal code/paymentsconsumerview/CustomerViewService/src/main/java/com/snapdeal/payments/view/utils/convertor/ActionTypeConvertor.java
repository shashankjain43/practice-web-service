package com.snapdeal.payments.view.utils.convertor;

import java.beans.PropertyEditorSupport;
import java.util.List;

import com.snapdeal.payments.view.commons.enums.ActionType;

public class ActionTypeConvertor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		 List<ActionType> actionTypes = null;
		try {
			//long d = new Long(text);
			//date = new Date(d);
		} catch (Exception e) {

		}

		setValue(actionTypes);
	}
}
