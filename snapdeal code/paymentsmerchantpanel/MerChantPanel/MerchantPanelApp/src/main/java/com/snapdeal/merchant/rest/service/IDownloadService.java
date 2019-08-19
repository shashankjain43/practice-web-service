package com.snapdeal.merchant.rest.service;

import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.MerchantBulkRefundDownloadFileRequest;
import com.snapdeal.merchant.request.MerchantBulkRefundDownloadInfoRequest;
import com.snapdeal.merchant.request.MerchantGetDownloadInfoRequest;
import com.snapdeal.merchant.request.MerchantInvoiceDetailsRequest;
import com.snapdeal.merchant.request.MerchantSettlementReportRequest;
import com.snapdeal.merchant.response.MerchantBulkDownloadInfoResponse;
import com.snapdeal.merchant.response.MerchantGetDownloadInfoResponse;
import com.snapdeal.merchant.response.MerchantInvoiceDetailsResponse;
import com.snapdeal.merchant.response.MerchantSettlementReportResponse;

public interface IDownloadService {
	
	public MerchantGetDownloadInfoResponse getDownloadInfo(MerchantGetDownloadInfoRequest request) throws MerchantException;
	
	public String downloadFile(MerchantGetDownloadInfoRequest request)
			throws MerchantException;
	
	public String downloadRefundTemplate() throws MerchantException;

	public MerchantBulkDownloadInfoResponse getBulkRefundDownloadInfo(MerchantBulkRefundDownloadInfoRequest request) throws MerchantException;

	
	public String downloadBulkRefundFile(MerchantBulkRefundDownloadFileRequest request)
			throws MerchantException;

	public MerchantSettlementReportResponse getSettlementReport(MerchantSettlementReportRequest request) throws MerchantException;

	public MerchantInvoiceDetailsResponse getMerchantInvoice(MerchantInvoiceDetailsRequest request) throws MerchantException;
}
