package com.snapdeal.merchant.rest.controller;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.snapdeal.merchant.common.constant.RestURIConstants;
import com.snapdeal.merchant.dto.MPSearch;
import com.snapdeal.merchant.dto.MPViewFilters;
import com.snapdeal.merchant.entity.response.GenericMerchantResponse;
import com.snapdeal.merchant.enums.FileType;
import com.snapdeal.merchant.enums.MPTransactionStatus;
import com.snapdeal.merchant.enums.MPTransactionType;
import com.snapdeal.merchant.errorcodes.ErrorConstants;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.MerchantBulkRefundRequest;
import com.snapdeal.merchant.request.MerchantGetFilterTransactionRequest;
import com.snapdeal.merchant.request.MerchantGetSearchTransactionRequest;
import com.snapdeal.merchant.request.MerchantGetTransactionRequest;
import com.snapdeal.merchant.request.MerchantRefundAmountForTxnRequest;
import com.snapdeal.merchant.request.MerchantRefundAmountRequest;
import com.snapdeal.merchant.response.MerchantBulkRefundResponse;
import com.snapdeal.merchant.response.MerchantExportTxnResponse;
import com.snapdeal.merchant.response.MerchantGetTransactionResponse;
import com.snapdeal.merchant.response.MerchantRefundAmountForTxnResponse;
import com.snapdeal.merchant.response.MerchantRefundAmountResponse;
import com.snapdeal.merchant.rest.service.IBulkRefundService;
import com.snapdeal.merchant.rest.service.ITransactionService;
import com.snapdeal.merchant.util.AppConstants;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.RequestAware;
import com.snapdeal.payments.metrics.annotations.Timed;
import com.snapdeal.payments.roleManagementModel.commons.PreAuthorize;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/api/txn")
public class TransactionController extends AbstractController {

	@Autowired
	ITransactionService txnService;
	
	@Autowired
	IBulkRefundService bulkService;
	

	@RequestAware
	@Marked
	@Logged
	@Timed
	@RequestMapping(method = RequestMethod.GET, value = "/v1/view")
	public @ResponseBody GenericMerchantResponse getTxnsOfMerchant(
			@RequestParam(value = "startDate", required = false) Long startDate,
			@RequestParam(value = "orderBy", required = false) Integer orderBy,
			@RequestParam(value = "endDate", required = false) Long endDate,
			@RequestParam(value = "fromAmount", required = false) BigDecimal fromAmount,
			@RequestParam(value = "toAmount", required = false) BigDecimal toAmount,
			@RequestParam(value = "txnTypeList", required = false) List<MPTransactionType> txnTypeList,
			@RequestParam(value = "txnStatusList", required = false) List<MPTransactionStatus> txnStatusList,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit, MerchantGetFilterTransactionRequest request,
			HttpServletRequest servRequest) throws MerchantException {

		MPViewFilters filters = new MPViewFilters();
		if (startDate != null)
			filters.setStartDate(startDate);
		if (endDate != null)
			filters.setEndDate(endDate);
		filters.setFromAmount(fromAmount);
		filters.setToAmount(toAmount);
		filters.setTxnStatusList(txnStatusList);
		filters.setTxnTypeList(txnTypeList);

		request.setFilters(filters);

		if (orderBy != null)
			request.setOrderby(orderBy);
		if (page != null)
			request.setPage(page);
		if (limit != null)
			request.setLimit(limit);

		// call the service
		MerchantGetTransactionResponse response = txnService.getTxnsOfMerchant(request);
		

		return getResponse(response);
	}

	@RequestAware
	@Marked
	@Logged
	@Timed
	@RequestMapping(method = RequestMethod.GET, value = "/v1/search")
	public @ResponseBody GenericMerchantResponse getTxnsOfMerchantBySearch(
			@RequestParam(value = "orderBy", required = false) Integer orderBy,
			@RequestParam(value = "transactionId", required = false) String transactionId,
			@RequestParam(value = "merchantTxnId", required = false) String merchantTxnId,
			@RequestParam(value = "settlementId", required = false) String settlementId,
			@RequestParam(value = "customerId", required = false) String customerId,
			@RequestParam(value = "orderId", required = false) String orderId,
			@RequestParam(value = "productId", required = false) String productId,
			@RequestParam(value = "terminalId", required = false) String terminalId,
			@RequestParam(value = "storeId", required = false) String storeId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit, MerchantGetSearchTransactionRequest request,
			HttpServletRequest servRequest) throws MerchantException {

		if (orderBy != null)
			request.setOrderby(orderBy);

		MPSearch mpSearch = new MPSearch();
		mpSearch.setCustomerId(customerId);
		mpSearch.setMerchantTxnId(merchantTxnId);
		mpSearch.setSettlementId(settlementId);
		mpSearch.setTransactionId(transactionId);
		mpSearch.setOrderId(orderId);
		mpSearch.setProductId(productId);
		mpSearch.setStoreId(storeId);
		mpSearch.setTerminalId(terminalId);

		request.setSearchCriteria(mpSearch);

		if (page != null)
			request.setPage(page);
		if (limit != null)
			request.setLimit(limit);

		// call the service
		MerchantGetTransactionResponse response = txnService.getTxnsOfMerchantBySearch(request);
		return getResponse(response);
	}

	
	@RequestAware
	@Marked
	@Logged
	@Timed
	@PreAuthorize("(hasPermission('VIEW_TRANSACTION'))")
	@RequestMapping(method = RequestMethod.GET, value = "/v1/gettxns")
	public @ResponseBody GenericMerchantResponse getMerchantTxnsByFilterAndSearch(
			@RequestParam(value = "startDate", required = false) Long startDate,
			@RequestParam(value = "orderBy", required = false) Integer orderBy,
			@RequestParam(value = "endDate", required = false) Long endDate,
			@RequestParam(value = "fromAmount", required = false) BigDecimal fromAmount,
			@RequestParam(value = "toAmount", required = false) BigDecimal toAmount,
			@RequestParam(value = "txnTypeList", required = false) List<MPTransactionType> txnTypeList,
			@RequestParam(value = "txnStatusList", required = false) List<MPTransactionStatus> txnStatusList,
			@RequestParam(value = "transactionId", required = false) String transactionId,
			@RequestParam(value = "merchantTxnId", required = false) String merchantTxnId,
			@RequestParam(value = "settlementId", required = false) String settlementId,
			@RequestParam(value = "customerId", required = false) String customerId,
			@RequestParam(value = "orderId", required = false) String orderId,
			@RequestParam(value = "productId", required = false) String productId,
			@RequestParam(value = "terminalId", required = false) String terminalId,
			@RequestParam(value = "storeId", required = false) String storeId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit, MerchantGetTransactionRequest request,
			HttpServletRequest servRequest) throws MerchantException {

		MPViewFilters filters = new MPViewFilters();
		if (startDate != null)
			filters.setStartDate(startDate);
		if (endDate != null)
			filters.setEndDate(endDate);
		filters.setFromAmount(fromAmount);
		filters.setToAmount(toAmount);
		filters.setTxnStatusList(txnStatusList);
		filters.setTxnTypeList(txnTypeList);

		request.setFilters(filters);

		if (orderBy != null)
			request.setOrderby(orderBy);
		if (page != null)
			request.setPage(page);
		if (limit != null)
			request.setLimit(limit);

			MPSearch mpSearch = new MPSearch();
			mpSearch.setCustomerId(customerId);
			mpSearch.setMerchantTxnId(merchantTxnId);
			mpSearch.setSettlementId(settlementId);
			mpSearch.setTransactionId(transactionId);
			mpSearch.setOrderId(orderId);
			mpSearch.setProductId(productId);
			mpSearch.setStoreId(storeId);
			mpSearch.setTerminalId(terminalId);
			request.setSearchCriteria(mpSearch);


		if (page != null)
			request.setPage(page);
		if (limit != null)
			request.setLimit(limit);

		// call the service
		MerchantGetTransactionResponse response = txnService.getMerchantTxns(request);
		return getResponse(response);

	}
	
	

	
	
	
	@RequestAware
	@Marked
	@Logged
	@Timed
	@PreAuthorize("(hasPermission('MCNT_INIT_REFUND'))")
	@RequestMapping(method = RequestMethod.POST, value = "/v1/refund")
	public @ResponseBody GenericMerchantResponse refundAmount(@RequestBody @Valid MerchantRefundAmountRequest request,
			BindingResult result, HttpServletRequest servRequest) throws MerchantException {

		// apply validation check
		verifyError(result, servRequest);

		MerchantRefundAmountResponse response = txnService.refundMoney(request);
		return getResponse(response);
	}

	@RequestAware
	@Marked
	@Logged
	@Timed
	@RequestMapping(method = RequestMethod.GET, value = "/v1/export")
	public @ResponseBody GenericMerchantResponse exportToFile(
			@RequestParam(value = "fileType", required = true) FileType fileType,
			@RequestParam(value = "startDate", required = false) Long startDate,
			@RequestParam(value = "orderBy", required = false) Integer orderBy,
			@RequestParam(value = "endDate", required = false) Long endDate,
			@RequestParam(value = "fromAmount", required = false) BigDecimal fromAmount,
			@RequestParam(value = "toAmount", required = false) BigDecimal toAmount,
			@RequestParam(value = "txnTypeList", required = false) List<MPTransactionType> txnTypeList,
			@RequestParam(value = "txnStatusList", required = false) List<MPTransactionStatus> txnStatusList,
			@RequestParam(value = "transactionId", required = false) String transactionId,
			@RequestParam(value = "merchantTxnId", required = false) String merchantTxnId,
			@RequestParam(value = "settlementId", required = false) String settlementId,
			@RequestParam(value = "customerId", required = false) String customerId,
			@RequestParam(value = "orderId", required = false) String orderId,
			@RequestParam(value = "productId", required = false) String productId,
			@RequestParam(value = "terminalId", required = false) String terminalId,
			@RequestParam(value = "storeId", required = false) String storeId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit,
		    MerchantGetTransactionRequest request,
			HttpServletRequest servRequest) throws MerchantException {
		
		MPViewFilters filters = new MPViewFilters();
		if (startDate != null)
			filters.setStartDate(startDate);
		else
			throw new MerchantException(ErrorConstants.START_DATE_CANNOT_BE_NULL_CODE, ErrorConstants.START_DATE_CANNOT_BE_NULL_MSG);
		
		if (endDate != null)
			filters.setEndDate(endDate);
		else
			throw new MerchantException(ErrorConstants.END_DATE_CANNOT_BE_NULL_CODE, ErrorConstants.END_DATE_CANNOT_BE_NULL_MSG);
		
		filters.setFromAmount(fromAmount);
		filters.setToAmount(toAmount);
		filters.setTxnStatusList(txnStatusList);
		filters.setTxnTypeList(txnTypeList);

		request.setFilters(filters);

		if (orderBy != null)
			request.setOrderby(orderBy);
		if (page != null)
			request.setPage(page);
		if (limit != null)
			request.setLimit(limit);

			MPSearch mpSearch = new MPSearch();
			mpSearch.setCustomerId(customerId);
			mpSearch.setMerchantTxnId(merchantTxnId);
			mpSearch.setSettlementId(settlementId);
			mpSearch.setTransactionId(transactionId);
			mpSearch.setOrderId(orderId);
			mpSearch.setProductId(productId);
			mpSearch.setStoreId(storeId);
			mpSearch.setTerminalId(terminalId);
			request.setSearchCriteria(mpSearch);

		MerchantExportTxnResponse response = txnService.exportTxn(request, fileType,
				(String) servRequest.getAttribute(AppConstants.userId));
		return getResponse(response);
	}
	
	
	@RequestAware
	@Marked
	@Logged
	@Timed
	@PreAuthorize("(hasPermission('MCNT_INIT_REFUND'))")
	@RequestMapping(value = "/v1/bulkrefund", method = RequestMethod.POST)
	public @ResponseBody GenericMerchantResponse bulkRefund(
            @RequestParam(value="file",required=true) MultipartFile file,
			MerchantBulkRefundRequest request, HttpServletRequest servRequest)
					throws MerchantException {

		if (!file.isEmpty()) {
			try {
				
				FileInputStream ioStream = (FileInputStream) file.getInputStream();
				
				MerchantBulkRefundResponse response = bulkService.bulkRefund(request, ioStream);
				/*ioStream.close();*/
				return getResponse(response);

			}
			catch (Exception e) {
				log.error("file not uploaded successfully : {} {}", e, request);
				throw new MerchantException("You failed to upload file");
			}
		} else {
			throw new MerchantException("You failed to upload file because the file was empty");
		}

	}
	
	
	@RequestAware
	@Marked
	@Logged
	@Timed
	@PreAuthorize("(hasPermission('MCNT_INIT_REFUND'))")
	@RequestMapping(method = RequestMethod.GET, value = "/v1/refundamount")
	public @ResponseBody GenericMerchantResponse getRefundAmountForTxn(
			@RequestParam(value = "txnRefId", required = true) String txnRefId,
			@RequestParam(value = "txnRefType", required = true) String txnRefType,
			@RequestParam(value = "orderId", required = true) String orderId,
			 MerchantRefundAmountForTxnRequest request,
			HttpServletRequest servRequest) throws MerchantException {
				
		request.setTxnRefId(txnRefId);
		request.setTxnRefType(txnRefType);
		request.setOrderId(orderId);
		
		MerchantRefundAmountForTxnResponse response = txnService.getRefundAmountForTxn(request);
		return getResponse(response);

	}



}
