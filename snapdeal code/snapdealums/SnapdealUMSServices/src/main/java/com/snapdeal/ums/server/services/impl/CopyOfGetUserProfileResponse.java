package com.snapdeal.ums.server.services.impl;

import java.util.List;

import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetAvailableBalanceInSDWalletByUserIdResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetCompleteSDWalletHistoryByUserIdResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetSDWalletByUserIdResponse;
import com.snapdeal.ums.admin.sdwallet.sro.SDWalletHistorySRO;
import com.snapdeal.ums.admin.sdwallet.sro.SDWalletSRO;
import com.snapdeal.ums.ext.user.GetUserByEmailResponse;
import com.snapdeal.ums.ext.userAddress.GetUserAddressesByUserIdResponse;
import com.snapdeal.ums.loyalty.LoyaltyUserStatusResponse;
import com.snapdeal.ums.subscription.ext.subscription.GetEmailSubscriberResponse;

public class CopyOfGetUserProfileResponse extends ServiceResponse{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private GetUserByEmailResponse userResponse;
	private GetUserAddressesByUserIdResponse userAddressResponse;
	private LoyaltyUserStatusResponse loyaltyStatus;
	private GetEmailSubscriberResponse emailSubscriberResponse;
	private GetSDWalletByUserIdResponse sdWalletResponse;
	private GetAvailableBalanceInSDWalletByUserIdResponse availableBalanceInSdWallet;
	private GetCompleteSDWalletHistoryByUserIdResponse completeSdWalletResponse;
	private List<SDWalletSRO> sdWalletSROList;
	private List<SDWalletHistorySRO> sdWalletHistorySROList;
	


	public List<SDWalletHistorySRO> getSdWalletHistorySROList() {
		return sdWalletHistorySROList;
	}

	public void setSdWalletHistorySROList(
			List<SDWalletHistorySRO> sdWalletHistorySROList) {
		this.sdWalletHistorySROList = sdWalletHistorySROList;
	}

	public List<SDWalletSRO> getSdWalletSROList() {
		return sdWalletSROList;
	}

	public void setSdWalletSROList(List<SDWalletSRO> sdWalletSROList) {
		this.sdWalletSROList = sdWalletSROList;
	}

	public CopyOfGetUserProfileResponse() {
		
	}

	public CopyOfGetUserProfileResponse(GetUserByEmailResponse userResponse) {
		super();
		this.userResponse=userResponse;
	}
	
	public CopyOfGetUserProfileResponse( GetUserAddressesByUserIdResponse userAddressResponse) {
		super();
		this.userAddressResponse = userAddressResponse;
	}
	public CopyOfGetUserProfileResponse(LoyaltyUserStatusResponse loyaltyStatus) {
		super();
		this.loyaltyStatus = loyaltyStatus;
	}

	public CopyOfGetUserProfileResponse(GetEmailSubscriberResponse emailSubscriberResponse) {
		super();
		this.emailSubscriberResponse = emailSubscriberResponse;
	}

	public CopyOfGetUserProfileResponse(GetSDWalletByUserIdResponse sdWalletResponse) {
		super();
		this.sdWalletResponse = sdWalletResponse;
	}

	public CopyOfGetUserProfileResponse(GetAvailableBalanceInSDWalletByUserIdResponse availableBalanceInSdWallet) {
		super();
		this.availableBalanceInSdWallet= availableBalanceInSdWallet;
	}
	
	
	public CopyOfGetUserProfileResponse(
			GetCompleteSDWalletHistoryByUserIdResponse completeSdWalletResponse) {
		super();
		this.completeSdWalletResponse = completeSdWalletResponse;
	}

	//Setter and Getter

	public void setAvailableBalanceInSdWallet(
			GetAvailableBalanceInSDWalletByUserIdResponse availableBalanceInSdWallet) {
		this.availableBalanceInSdWallet = availableBalanceInSdWallet;
	}


	public GetAvailableBalanceInSDWalletByUserIdResponse getAvailableBalanceInSdWallet() {
		return availableBalanceInSdWallet;
	}


	
	public GetSDWalletByUserIdResponse getSdWalletResponse() {
		return sdWalletResponse;
	}

	public void setSdWalletResponse(GetSDWalletByUserIdResponse sdWalletResponse) {
		this.sdWalletResponse = sdWalletResponse;
	}

	public GetCompleteSDWalletHistoryByUserIdResponse getCompleteSdWalletResponse() {
		return completeSdWalletResponse;
	}

	public void setCompleteSdWalletResponse(
			GetCompleteSDWalletHistoryByUserIdResponse completeSdWalletResponse) {
		this.completeSdWalletResponse = completeSdWalletResponse;
	}

	public GetUserByEmailResponse getUserResponse() {
		return userResponse;
	}

	public void setUserResponse(GetUserByEmailResponse userResponse) {
		this.userResponse = userResponse;
	}

	public LoyaltyUserStatusResponse getLoyaltyStatus() {
		return loyaltyStatus;
	}

	public void setLoyaltyStatus(LoyaltyUserStatusResponse loyaltyStatus) {
		this.loyaltyStatus = loyaltyStatus;
	}

	public GetUserAddressesByUserIdResponse getUserAddressResponse() {
		return userAddressResponse;
	}

	public void setUserAddressResponse(
			GetUserAddressesByUserIdResponse userAddressResponse) {
		this.userAddressResponse = userAddressResponse;
	}

	public GetEmailSubscriberResponse getEmailSubscriberResponse() {
		return emailSubscriberResponse;
	}

	public void setEmailSubscriberResponse(GetEmailSubscriberResponse emailSubscriberResponse) {
		this.emailSubscriberResponse = emailSubscriberResponse;
	}





	
	
}
