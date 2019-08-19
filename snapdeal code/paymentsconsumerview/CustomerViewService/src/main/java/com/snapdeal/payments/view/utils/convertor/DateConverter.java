package com.snapdeal.payments.view.utils.convertor;


import java.beans.PropertyEditorSupport;
import java.util.Date;

public class DateConverter extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Date date = null;
		try {
			long d = new Long(text);
			date = new Date(d);
		} catch (Exception e) {

		}

		setValue(date);
	}

}