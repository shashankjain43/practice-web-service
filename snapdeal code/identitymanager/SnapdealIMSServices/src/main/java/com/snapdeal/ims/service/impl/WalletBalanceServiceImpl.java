package com.snapdeal.ims.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.ims.dbmapper.entity.User;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.service.IWalletBalance;
import com.snapdeal.payments.sdmoney.client.SDMoneyClient;
import com.snapdeal.payments.sdmoney.exceptions.AccountBlockedException;
import com.snapdeal.payments.sdmoney.service.model.GetAccountBalanceRequest;
import com.snapdeal.payments.sdmoney.service.model.GetAccountBalanceResponse;
import com.snapdeal.payments.sdmoney.service.model.SuspendSDMoneyAccountRequest;

@Component
@Slf4j
public class WalletBalanceServiceImpl implements IWalletBalance{

	@Autowired
	private SDMoneyClient sdMoneyServiceClient;
	
	  /*
	     This method is mainly responsible for computing amount in wallet 
	  */  
	@Override
	public void checkWalletBalance(User user) {
		GetAccountBalanceRequest request = new GetAccountBalanceRequest();
		request.setSdIdentity(user.getUserId());
		GetAccountBalanceResponse response = sdMoneyServiceClient
				.getAccountBalance(request);

		if (response != null && response.getBalance() != null
				&& response.getBalance().getTotalBalance().compareTo(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_DOWN))==0){
			SuspendSDMoneyAccountRequest suspendRequest = new SuspendSDMoneyAccountRequest();
			suspendRequest.setSdIdentity(user.getUserId());
			suspendRequest.setSuspendDuration(null);
			try {
				sdMoneyServiceClient.suspendSDMoneyAccount(suspendRequest);
			} catch (Exception e) {
				if (e instanceof AccountBlockedException) {
					log.error(
							"Wallet Account Already Suspended for user with ID "
									+ user.getUserId(), e);
				} else {
					throw new IMSServiceException(
							IMSRequestExceptionCodes.SUSPEND_FAIL.errCode(),
							IMSRequestExceptionCodes.SUSPEND_FAIL.errMsg());
				}
			}
		} else {
			log.error("Wallet balance fetched for user with id : "
					+ user.getUserId() + " is : " + response);
			throw new IMSServiceException(
					IMSRequestExceptionCodes.WALLET_BALANCE_EXISTS.errCode(),
					IMSRequestExceptionCodes.WALLET_BALANCE_EXISTS.errMsg());
		}
	}	
}