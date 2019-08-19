package com.snapdeal.merchant.test.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.snapdeal.merchant.dto.MPSearch;
import com.snapdeal.merchant.dto.MPTransactionDTO;
import com.snapdeal.merchant.dto.MPViewFilters;
import com.snapdeal.merchant.enums.MPTransactionStatus;
import com.snapdeal.merchant.enums.MPTransactionType;
import com.snapdeal.merchant.enums.UserMappingDirection;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.util.AppConstants;
import com.snapdeal.payments.view.merchant.commons.dto.MVTransactionDTO;
import com.snapdeal.payments.view.merchant.commons.enums.MVTransactionStatus;
import com.snapdeal.payments.view.merchant.commons.enums.MVTransactionType;
import com.snapdeal.payments.view.merchant.commons.request.MerchantViewFilters;
import com.snapdeal.payments.view.merchant.commons.request.MerchantViewSearch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MVMapper {

	public static MerchantViewSearch MPToMVSearchMapping(MPSearch mpSearch) {

		if(mpSearch == null)
		{
			return null;
		}
		MerchantViewSearch mvSearch = new MerchantViewSearch();
		mvSearch.setCustomerId(mpSearch.getCustomerId());
		mvSearch.setMerchantTxnId(mpSearch.getMerchantTxnId());
		mvSearch.setOrderId(mpSearch.getOrderId());
		mvSearch.setProductId(mpSearch.getProductId());
		mvSearch.setSettlementId(mpSearch.getSettlementId());
		mvSearch.setStoreId(mpSearch.getStoreId());
		mvSearch.setTerminalId(mpSearch.getTerminalId());
		mvSearch.setTransactionId(mpSearch.getTransactionId());

		return mvSearch;

	}

	public static MerchantViewFilters MPToMVFilterMapping(MPViewFilters mpFilters) throws MerchantException {

		MerchantViewFilters mvFilters = new MerchantViewFilters();
		if(mpFilters.getEndDate() != null)
			mvFilters.setEndDate(new Date(mpFilters.getEndDate()));
		
		if(mpFilters.getStartDate() != null)
			mvFilters.setStartDate(new Date(mpFilters.getStartDate()));
	
		mvFilters.setFromAmount(mpFilters.getFromAmount());	
		mvFilters.setToAmount(mpFilters.getToAmount());

		List<com.snapdeal.payments.view.merchant.commons.enums.MVTransactionStatus> mvTxnStatusList = new ArrayList<MVTransactionStatus>();
		List<MPTransactionStatus> mpTxnStatusList = mpFilters.getTxnStatusList();
		if (mpTxnStatusList != null) {
			for (MPTransactionStatus mpTransactionStatus : mpTxnStatusList) {
				MVTransactionStatus mvTxnStatus = (MVTransactionStatus) MVMapper
						.MPAndMVTxnStatusMapping(mpTransactionStatus, UserMappingDirection.MP_TO_MV);
				mvTxnStatusList.add(mvTxnStatus);
			}
		}
		mvFilters.setTxnStatusList(mvTxnStatusList);

		List<MVTransactionType> mvTxnTypeList = new ArrayList<MVTransactionType>();
		List<MPTransactionType> mpTxnTypeList = mpFilters.getTxnTypeList();
		if (mpTxnTypeList != null) {
			for (MPTransactionType mpTransactionType : mpTxnTypeList) {
				MVTransactionType mvTxnType = (MVTransactionType) MVMapper.MPAndMVTxnTypeMapping(mpTransactionType,
						UserMappingDirection.MP_TO_MV);
				mvTxnTypeList.add(mvTxnType);
			}
		}
		mvFilters.setTxnTypeList(mvTxnTypeList);
		
		mvFilters.setMerchantTag(mpFilters.getMerchantTag());

		return mvFilters;

	}

	public static Object MPAndMVTxnStatusMapping(Object object, UserMappingDirection userMappingDirection)
			throws MerchantException {

		if (userMappingDirection == UserMappingDirection.MP_TO_MV) {
			MPTransactionStatus mpTxnStatus = (MPTransactionStatus) object;
			MVTransactionStatus mvTxnStatus;
			try {
				mvTxnStatus = MVTransactionStatus.valueOf(mpTxnStatus.toString());

			} catch (Exception e) {
				log.error("Exception when converting Mpanel Status Enum {}  to MV Enum : {}",mpTxnStatus.toString(), e);
				throw e;
			}

			return mvTxnStatus;
		}

		else if (userMappingDirection == UserMappingDirection.MV_TO_MP) {
			MVTransactionStatus mvTxnStatus = (MVTransactionStatus) object;
			MPTransactionStatus mpTxnStatus;
			try {
				mpTxnStatus = MPTransactionStatus.valueOf(mvTxnStatus.toString());

			} catch (Exception e) {
				log.error("Exception when converting Mview Status Enum {}  to MPanel Enum : {}",mvTxnStatus.toString(), e);
				throw e;
			}

			return mpTxnStatus;

		} else
			return null;

	}

	public static Object MPAndMVTxnTypeMapping(Object object, UserMappingDirection userMappingDirection)
			throws MerchantException {
		if (userMappingDirection == UserMappingDirection.MP_TO_MV) {
			MPTransactionType mpTxnType = (MPTransactionType) object;
			MVTransactionType mvTxnType;
			try {
				mvTxnType = MVTransactionType.valueOf(mpTxnType.toString());

			} catch (Exception e) {
				log.error("Exception when converting Mpanel Txn Enum {}  to MV Enum : {}",mpTxnType.toString(), e);
				throw e;
			}

			return mvTxnType;
		} else if (userMappingDirection == UserMappingDirection.MV_TO_MP) {
			MVTransactionType mvTxnType = (MVTransactionType) object;
			MPTransactionType mpTxnType;
			try {
				mpTxnType = MPTransactionType.valueOf(mvTxnType.toString());

			} catch (Exception e) {
				log.error("Exception when converting Mview Txn Enum {}  to MPanel Enum : {}",mvTxnType.toString(), e);
				throw e;
			}

			return mpTxnType;
		} else
			return null;
	}

	public static MPTransactionDTO MVToMPTxnMapping(MVTransactionDTO mvTxnDto) throws MerchantException {
		MPTransactionDTO mpTxnDto = new MPTransactionDTO();
		mpTxnDto.setCustId(mvTxnDto.getCustId());
		mpTxnDto.setCustIP(mvTxnDto.getCustIP());
		mpTxnDto.setCustName(mvTxnDto.getCustName());
		mpTxnDto.setFcTxnId(mvTxnDto.getFcTxnId());
		mpTxnDto.setLocation(mvTxnDto.getLocation());
		mpTxnDto.setMerchantFee(mvTxnDto.getMerchantFee());
		mpTxnDto.setMerchantId(mvTxnDto.getMerchantId());
		mpTxnDto.setMerchantName(mvTxnDto.getMerchantName());
		mpTxnDto.setMerchantTxnId(mvTxnDto.getMerchantTxnId());
		mpTxnDto.setNetDeduction(mvTxnDto.getNetDeduction());
		mpTxnDto.setOrderId(mvTxnDto.getOrderId());
		mpTxnDto.setPayableAmount(mvTxnDto.getPayableAmount());
		mpTxnDto.setProductId(mvTxnDto.getProductId());
		mpTxnDto.setServiceTax(mvTxnDto.getServiceTax());
		mpTxnDto.setSettlementId(mvTxnDto.getSettlementId());
		mpTxnDto.setShippingCity(mvTxnDto.getShippingCity());
		mpTxnDto.setStoreId(mvTxnDto.getStoreId());
		mpTxnDto.setStoreName(mvTxnDto.getStoreName());
		mpTxnDto.setSwachBharatCess(mvTxnDto.getSwachBharatCess());
		mpTxnDto.setTerminalId(mvTxnDto.getTerminalId());
		mpTxnDto.setTotalTxnAmount(mvTxnDto.getTotalTxnAmount());
		mpTxnDto.setTxnDate(mvTxnDto.getTxnDate());
		mpTxnDto.setTxnStatus((MPTransactionStatus) MVMapper.MPAndMVTxnStatusMapping(mvTxnDto.getTxnStatus(),
				UserMappingDirection.MV_TO_MP));
		mpTxnDto.setTxnType((MPTransactionType) MVMapper.MPAndMVTxnTypeMapping(mvTxnDto.getTxnType(),
				UserMappingDirection.MV_TO_MP));
		
		mpTxnDto.setTxnRefId(mvTxnDto.getTxnRefId());
		mpTxnDto.setTxnRefType(mvTxnDto.getTxnRefType());
		
		Gson gson = new Gson();
		Map dispInfoMap = gson.fromJson(mvTxnDto.getDisplayInfo(), Map.class);
		String email = (String) dispInfoMap.get(AppConstants.txnDispInfoEmail);
		String mobile = (String) dispInfoMap.get(AppConstants.txnDispInfoMobile);
		
		mpTxnDto.setEmail(email);
		mpTxnDto.setMobile(mobile);
		
		return mpTxnDto;
	}
}
