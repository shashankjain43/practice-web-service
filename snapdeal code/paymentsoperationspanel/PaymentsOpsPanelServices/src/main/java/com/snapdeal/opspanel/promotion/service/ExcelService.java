package com.snapdeal.opspanel.promotion.service;

import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.promotion.exception.WalletServiceException;
import com.snapdeal.opspanel.promotion.request.FormData;

@Service
public interface ExcelService {

	public void processFile(String pathToFile,FormData formRequest) throws WalletServiceException, OpsPanelException;
	
}
