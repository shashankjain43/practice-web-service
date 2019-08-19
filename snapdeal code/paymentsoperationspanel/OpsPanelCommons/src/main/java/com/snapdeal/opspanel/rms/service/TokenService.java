package com.snapdeal.opspanel.rms.service;

import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;

public interface TokenService {

	String getEmailFromToken(String token) throws OpsPanelException;
}
