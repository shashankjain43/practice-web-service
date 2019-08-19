package com.snapdeal.merchant.data.handler.impl;

import org.springframework.stereotype.Component;

import com.snapdeal.merchant.file.handler.IFileDecorator;
import com.snapdeal.merchant.file.handler.exception.FileHandlingException;
import com.snapdeal.payments.view.merchant.commons.response.GetTransactionsResponse;

@Component
public class PDFTxnFileDecorator implements IFileDecorator<GetTransactionsResponse,Void> {

	@Override
	public Void decorate(GetTransactionsResponse request) throws FileHandlingException {
		
		return null;
	}

	@Override
	public void save(Void request,String fileName) throws FileHandlingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String filePath) {
		// TODO Auto-generated method stub
		
	}

}