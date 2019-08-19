package com.snapdeal.merchant.rest.stub.service.impl;

import org.springframework.stereotype.Service;

import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.MerchantBulkRefundDownloadFileRequest;
import com.snapdeal.merchant.request.MerchantBulkRefundDownloadInfoRequest;
import com.snapdeal.merchant.request.MerchantGetDownloadInfoRequest;
import com.snapdeal.merchant.response.MerchantBulkDownloadInfoResponse;
import com.snapdeal.merchant.response.MerchantGetDownloadInfoResponse;
import com.snapdeal.merchant.rest.service.IDownloadService;

@Service
public class StubDownloadService implements IDownloadService{

	@Override
	public MerchantGetDownloadInfoResponse getDownloadInfo(MerchantGetDownloadInfoRequest request)
			throws MerchantException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String downloadFile(MerchantGetDownloadInfoRequest request) throws MerchantException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String downloadRefundTemplate() throws MerchantException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MerchantBulkDownloadInfoResponse getBulkRefundDownloadInfo(MerchantBulkRefundDownloadInfoRequest request)
			throws MerchantException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String downloadBulkRefundFile(MerchantBulkRefundDownloadFileRequest request) throws MerchantException {
		// TODO Auto-generated method stub
		return null;
	}

}
