package com.snapdeal.merchant.rest.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.merchant.entity.response.GenericMerchantResponse;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.MerchantBulkRefundDownloadFileRequest;
import com.snapdeal.merchant.request.MerchantBulkRefundDownloadInfoRequest;
import com.snapdeal.merchant.request.MerchantGetDownloadInfoRequest;
import com.snapdeal.merchant.request.MerchantInvoiceDetailsRequest;
import com.snapdeal.merchant.request.MerchantSettlementReportRequest;
import com.snapdeal.merchant.response.MerchantBulkDownloadInfoResponse;
import com.snapdeal.merchant.response.MerchantBulkRefundURLResponse;
import com.snapdeal.merchant.response.MerchantGetDownloadInfoResponse;
import com.snapdeal.merchant.response.MerchantGetURLResponse;
import com.snapdeal.merchant.response.MerchantInvoiceDetailsResponse;
import com.snapdeal.merchant.response.MerchantSettlementReportResponse;
import com.snapdeal.merchant.rest.service.IDownloadService;
import com.snapdeal.merchant.util.AppConstants;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.RequestAware;
import com.snapdeal.payments.metrics.annotations.Timed;
import com.snapdeal.payments.roleManagementModel.commons.PreAuthorize;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/api/data")
public class DownloadController extends AbstractController {

	@Autowired
	IDownloadService service;

	@RequestAware
	@Marked
	@Logged
	@Timed
	@RequestMapping(method = RequestMethod.GET, value = "/v1/get/downloads")
	public @ResponseBody GenericMerchantResponse getDownloadInfo(MerchantGetDownloadInfoRequest request,
			BindingResult result, HttpServletRequest servRequest) throws MerchantException {

		// apply validation check
		verifyError(result, servRequest);
		// request.setUserId((String)servRequest.getAttribute(AppConstants.userId));
		request.setUserId((String) servRequest.getAttribute(AppConstants.userId));
		MerchantGetDownloadInfoResponse response = service.getDownloadInfo(request);

		return getResponse(response);

	}

	@RequestAware
	@Marked
	@Logged
	@Timed
	@RequestMapping(method = RequestMethod.GET, value = "/v1/download")
	public @ResponseBody GenericMerchantResponse downloadFile(@RequestParam(value = "id", required = true) Integer id,
			MerchantGetDownloadInfoRequest request, BindingResult result, HttpServletRequest servRequest,
			HttpServletResponse servResponse) throws Exception {

		// apply validation check
		verifyError(result, servRequest);
		request.setUserId((String) servRequest.getAttribute(AppConstants.userId));
		request.setId(id);
		MerchantGetURLResponse response = null;
		
		String url = service.downloadFile(request);
		response = new MerchantGetURLResponse();
		response.setUrl(url);
			
		return  getResponse(response);

	}
	
	
	@RequestAware
	@Marked
	@Logged
	@Timed
	@PreAuthorize("(hasPermission('MCNT_INIT_REFUND'))")
	@RequestMapping(method = RequestMethod.GET, value = "/v1/get/bulkrefund/listdownloads")
	public @ResponseBody GenericMerchantResponse getBulkRefundDownloadInfo(MerchantBulkRefundDownloadInfoRequest request,
			BindingResult result, HttpServletRequest servRequest) throws MerchantException {

		// apply validation check
		verifyError(result, servRequest);
		
		MerchantBulkDownloadInfoResponse response = service.getBulkRefundDownloadInfo(request);

		return getResponse(response);

	}
	
	@RequestAware
	@Marked
	@Logged
	@Timed
	@PreAuthorize("(hasPermission('MCNT_INIT_REFUND'))")
	@RequestMapping(method = RequestMethod.GET, value = "/v1/bulkrefund/download")
	public @ResponseBody GenericMerchantResponse downloadBulkRefundFile(@RequestParam(value = "id", required = true)  Integer id,
			MerchantBulkRefundDownloadFileRequest request, BindingResult result, HttpServletRequest servRequest,
			HttpServletResponse servResponse) throws Exception {

		// apply validation check
		verifyError(result, servRequest);
		
		request.setId(id);
		
		MerchantBulkRefundURLResponse response = null;
		
		String url = service.downloadBulkRefundFile(request);
		response = new MerchantBulkRefundURLResponse();
		response.setUrl(url);
			
		return  getResponse(response);

	}

	@RequestAware
	@Marked
	@Logged
	@Timed
	@PreAuthorize("(hasPermission('MCNT_INIT_REFUND'))")
	@RequestMapping(method = RequestMethod.GET, value = "/v1/download/refundtmpl")
	public void downloadRefundTemplateFile(HttpServletRequest servRequest,
			HttpServletResponse servResponse) throws MerchantException {

		try {
			String url = service.downloadRefundTemplate();
			File file = new File(url);
		    InputStream is = new FileInputStream(file);
		    
		    servResponse.setContentType("application/vnd.ms-excel");
	        // Response header
			servResponse.setHeader("Content-Disposition", "attachment; filename=\""
	                + file.getName() + "\"");
			OutputStream os = servResponse.getOutputStream();
	        byte[] buffer = new byte[1024];
	        int len;
	        while ((len = is.read(buffer)) != -1) {
	            os.write(buffer, 0, len);
	        }
	        os.flush();
	        os.close();
	        is.close();
		} catch (Exception e) {
			log.error("could not get the template file {} {}",e.getMessage(),e);
			throw new MerchantException("Error ! can not download template file");
		}
		return;

	}

	@RequestAware
	@Marked
	@Logged
	@Timed
	@PreAuthorize("(hasPermission('VIEW_TRANSACTION'))")
	@RequestMapping(method = RequestMethod.GET, value = "/v2/get/settlementreport")
	public @ResponseBody GenericMerchantResponse getSettlementReport(MerchantSettlementReportRequest request,
			 HttpServletRequest servRequest) throws MerchantException {
		
		MerchantSettlementReportResponse response = service.getSettlementReport(request);

		return getResponse(response);

	}
	
	@RequestAware
	@Marked
	@Logged
	@Timed
	@PreAuthorize("(hasPermission('VIEW_TRANSACTION'))")
	@RequestMapping(method = RequestMethod.GET, value = "/v2/get/invoice")
	public @ResponseBody GenericMerchantResponse getMerchantInvoice(MerchantInvoiceDetailsRequest request,
			 HttpServletRequest servRequest) throws MerchantException {
		
		MerchantInvoiceDetailsResponse response = service.getMerchantInvoice(request);

		return getResponse(response);

	}
}
