package com.snapdeal.opspanel.userpanel.service.impl;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.ims.request.GetUserByIdRequest;
import com.snapdeal.ims.request.GetUserByMobileRequest;
import com.snapdeal.ims.request.UserUpgradeByEmailRequest;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.response.UserUpgradationResponse;
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.request.SearchUserRequest;
import com.snapdeal.opspanel.userpanel.response.SearchUserResponse;
import com.snapdeal.opspanel.userpanel.service.SearchUserServicesAgg;
import com.snapdeal.payments.disbursement.model.BankDetails;
import com.snapdeal.payments.sdmoney.service.model.BankAccountDetails;
import com.snapdeal.payments.sdmoney.service.model.GetUserBankDetailsResponse;
import com.snapdeal.payments.sdmoney.service.model.GetVoucherBalanceDetailsResponse;
import com.snapdeal.payments.sdmoney.service.model.VoucherBalanceDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("searchUserServices")
public class SearchUserServiceAggImpl implements SearchUserServicesAgg {
	
	@Autowired
	SearchUserServicesImpl searchUserServices;

	@Autowired
	HttpServletRequest request;

	public SearchUserResponse searchUser(SearchUserRequest searchUserRequest) throws InfoPanelException{
		
		boolean userFound = false;
		GetUserResponse response = new GetUserResponse();
		SearchUserResponse searchUserResponse = new SearchUserResponse();
		if(userFound ==false) {
			String email = searchUserRequest.getEmail();
			GetUserByEmailRequest request = new GetUserByEmailRequest();
			request.setEmailId(email);
			if(email != null) {
				response = searchUserServices.searchUserByEmail(request);
				userFound = true;
			}
		}
		
		if(userFound == false) {
			String mobileNumber = searchUserRequest.getMobileNumber();
			GetUserByMobileRequest request = new GetUserByMobileRequest();
			request.setMobileNumber(mobileNumber);
			if(mobileNumber != null) {
				response = searchUserServices.searchUserByMobile(request);
				userFound = true;
			}
		}
		
		if(userFound == false) {
			String userId = searchUserRequest.getUserId();
			GetUserByIdRequest request = new GetUserByIdRequest();
			request.setUserId(userId);
			if(userId != null) {
				response = searchUserServices.searchUserById(request);
				userFound = true;
			}	
		}
		if(userFound == false) {
			return null;
		}
		
		UserDetailsDTO userDTO = new UserDetailsDTO();
		String userId = null;
		if(response  != null){
			userDTO = response.getUserDetails();
			userId = userDTO.getUserId();
		}
		
		GetUserBankDetailsResponse userBankdetails;
		String walletAccountStatus;
		String pendingWalletLimit = null;
		String imsAccountStatus;
		BigDecimal generalAccountBalance;
		BigDecimal voucherAccountBalance;
		long voucherCount = 0;
		List<VoucherBalanceDetails> vouchersList=new ArrayList<VoucherBalanceDetails>();
		List<BankAccountDetails> userAccounts = new ArrayList<BankAccountDetails>();
		try {
			GetVoucherBalanceDetailsResponse getVoucherBalanceDetailsResponse = searchUserServices.getAllVouchers(userId);
			if(getVoucherBalanceDetailsResponse != null){
				vouchersList = getVoucherBalanceDetailsResponse.getVoucherBalanceDetails();
				if(vouchersList != null){
					voucherCount = vouchersList.size();
				}
			}
		} catch (InfoPanelException e) {
			voucherCount = 0;
		}
		
		try {
			walletAccountStatus = searchUserServices.getWalletAccountStatus(userId);
			pendingWalletLimit=searchUserServices.getPendingLimit(userId);
		
		} catch (InfoPanelException e) {
			walletAccountStatus = "Could not fetch";	
		}
		
		try {
			generalAccountBalance = searchUserServices.getGeneralAccountBalance(userId);
		} catch (InfoPanelException e) {
			generalAccountBalance = BigDecimal.ZERO;
		}
		
		try {
			voucherAccountBalance = searchUserServices.getVoucherAccountBalance(userId);
		} catch (InfoPanelException e) {
			voucherAccountBalance = BigDecimal.ZERO;
		}
		
		/*
		 * Fetching the bank account details of the user
		 */
		
		try{
			userBankdetails = searchUserServices.getUserBankDetails(userId);
			userAccounts = userBankdetails.getUserAccounts();
		}catch(InfoPanelException e){
			log.info("Unable to fetch user bank details" + e.getErrMessage() + e.getMessage());
		}
		
		String name = null;
		
		if(userDTO != null){
			name = userDTO.getFirstName() != null ? userDTO.getFirstName() : "";
			name.concat(userDTO.getMiddleName() != null? " " + userDTO.getMiddleName() : "");
			name.concat(userDTO.getLastName() != null? " " + userDTO.getLastName() : "");
			imsAccountStatus = userDTO.getAccountState();
			
			searchUserResponse.setName(name);
			searchUserResponse.setEmail(userDTO.getEmailId());
			searchUserResponse.setMobileNumber(userDTO.getMobileNumber());
			searchUserResponse.setUserId(userDTO.getUserId());
			searchUserResponse.setAccountCreationDate(userDTO.getCreatedTime());
			searchUserResponse.setWalletAccountStatus(walletAccountStatus);
			searchUserResponse.setImsAccountStatus(imsAccountStatus);
			searchUserResponse.setIsUserEnabled(userDTO.isEnabledState());
			searchUserResponse.setGeneralAccountBalance(generalAccountBalance);
			searchUserResponse.setGeneralVoucherBalance(voucherAccountBalance);
			searchUserResponse.setVoucherCount(voucherCount);
			searchUserResponse.setVouchersList(vouchersList);
			searchUserResponse.setAccountOwner(userDTO.getAccountOwner().getAccountOwnerName());
			searchUserResponse.setFbSocialId(userDTO.getFbSocialId());
			searchUserResponse.setGoogleSocialId(userDTO.getGoogleSocialId());
			searchUserResponse.setMobileVerified(userDTO.isMobileVerified());
			searchUserResponse.setEmailVerified(userDTO.isEmailVerified());
			searchUserResponse.setRemainingGeneralBalanceLimit(pendingWalletLimit);
		}
		
		searchUserResponse.setWalletAccountStatus(walletAccountStatus);
		searchUserResponse.setGeneralAccountBalance(generalAccountBalance);
		searchUserResponse.setGeneralVoucherBalance(voucherAccountBalance);
		searchUserResponse.setVoucherCount(voucherCount);
		searchUserResponse.setVouchersList(vouchersList);
		
		//Filling the Bank details of the User
		searchUserResponse.setUserAccounts(userAccounts);
		
		//social check
		
		searchUserResponse.setUserCreatedFrom("Email");
		
		if(userDTO.getFbSocialId()!=null){
			searchUserResponse.setUserCreatedFrom("Facebook");
		} 
		
		if(userDTO.getGoogleSocialId()!=null){
			searchUserResponse.setUserCreatedFrom("Google");
		} 
		 
		
		
		UserUpgradeByEmailRequest migrationRequest = new UserUpgradeByEmailRequest();
		migrationRequest.setEmailId(searchUserResponse.getEmail());
		
		UserUpgradationResponse upgradeResponse=searchUserServices.getUserMigrationStatus(migrationRequest);
		searchUserResponse.setMigrationStatus(null);
		if(upgradeResponse != null){
			searchUserResponse.setMigrationStatus(upgradeResponse.getUpgradationInformation().getState().toString());
		}
		//String baseUrl = String.format("%s://%s:%d/",request.getScheme(),  request.getServerName(), request.getServerPort());
		
		   searchUserResponse.setTransactionHistoryDownlaodUrl("/search/download?emailId=" + searchUserResponse.getEmail());
		
		return searchUserResponse;
	}
}
