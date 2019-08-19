package com.snapdeal.ims.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.snapdeal.ims.constants.RestURIConstants;
import com.snapdeal.ims.entity.FilterEntity;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.request.WalletFilterRequest;
import com.snapdeal.ims.response.FailedEmailResponse;
import com.snapdeal.ims.response.WalletCountResponse;
import com.snapdeal.ims.service.IWalletCountService;
import com.snapdeal.ims.utils.UpgradeFilterUtil;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;



@RestController
@RequestMapping(RestURIConstants.USER)
public class WalletDashBoardController extends AbstractController {

	@Autowired
	IWalletCountService walletCountService;
	
	@RequestMapping(value = "/upgradeCount",produces = "application/json", method = RequestMethod.POST)
	public WalletCountResponse getWalletCountBasedOnFilter(
			@RequestBody WalletFilterRequest walletFilterRequest,
			HttpServletRequest request) throws IOException {
		ArrayList<String> upgradeChannelList = null;
		ArrayList<String> upgradeSourceList = null;
		if (walletFilterRequest != null) {
			upgradeChannelList = walletFilterRequest.getUpgradeChannel();
			upgradeSourceList = walletFilterRequest.getUpgradeSource();
		}
		FilterEntity filterEntity = UpgradeFilterUtil.getFilterEntity(
				upgradeChannelList, upgradeSourceList);
		WalletCountResponse countEntity = walletCountService
				.retrieveTotalWalletCount(filterEntity);
		return countEntity;
	}

	@RequestMapping(value = "/upgradeCount/failedEmailList", produces = "application/json", method = RequestMethod.POST)
	public FailedEmailResponse retrieveFailedEmailBasedOnFilter(
			@RequestBody WalletFilterRequest walletFilterRequest,
			HttpServletRequest request) throws IOException {
		if(walletFilterRequest.getMerchant()==null){
			throw new IMSServiceException(IMSServiceExceptionCodes.MERCHANT_NOT_ENTERED.errCode(), IMSServiceExceptionCodes.MERCHANT_NOT_ENTERED.errMsg());
		}
		ArrayList<String> upgradeChannelList = null;
		ArrayList<String> upgradeSourceList = null;
		if (walletFilterRequest != null) {
			upgradeChannelList = walletFilterRequest.getUpgradeChannel();
			upgradeSourceList = walletFilterRequest.getUpgradeSource();
		}
		FilterEntity filterEntity = UpgradeFilterUtil.getFilterEntity(
				upgradeChannelList, upgradeSourceList);
		filterEntity.setOriginating_src(Merchant.valueOf(walletFilterRequest.getMerchant()));
		List<String> emailList = new ArrayList<String>();
		emailList = walletCountService
				.getEmailListForUpgradeFilter(filterEntity);
		FailedEmailResponse downloadEmailResponse = new FailedEmailResponse();
		downloadEmailResponse.setFailedEmailList(emailList);
		return downloadEmailResponse;
	}

}
