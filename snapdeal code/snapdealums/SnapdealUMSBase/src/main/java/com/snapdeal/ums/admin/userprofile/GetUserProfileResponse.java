package com.snapdeal.ums.admin.userprofile;

import java.util.List;

import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetAvailableBalanceInSDWalletByUserIdResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetCompleteSDWalletHistoryByUserIdResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetSDWalletByUserIdResponse;
import com.snapdeal.ums.admin.sdwallet.sro.SDWalletHistorySRO;
import com.snapdeal.ums.admin.sdwallet.sro.SDWalletSRO;
import com.snapdeal.ums.ext.user.GetUserByEmailResponse;
import com.snapdeal.ums.ext.user.GetUserByIdResponse;
import com.snapdeal.ums.ext.userAddress.GetUserAddressesByUserIdResponse;
import com.snapdeal.ums.loyalty.LoyaltyUserStatusResponse;
import com.snapdeal.ums.subscription.ext.subscription.GetEmailSubscriberResponse;

public class GetUserProfileResponse extends ServiceResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6561246209272641529L;

	private GetUserByIdResponse userIdResponse;

	private GetUserByEmailResponse userResponse;
	private GetUserAddressesByUserIdResponse userAddressResponse;
	private LoyaltyUserStatusResponse loyaltyStatusResponse;
	private GetEmailSubscriberResponse emailSubscriberResponse;
	private GetSDWalletByUserIdResponse sdWalletResponse;
	private GetAvailableBalanceInSDWalletByUserIdResponse availableBalanceInSdWallet;
	private GetCompleteSDWalletHistoryByUserIdResponse completeSdWalletResponse;
	private List<SDWalletSRO> sdWalletSROList;
	private List<SDWalletHistorySRO> sdWalletHistorySROList;

	public GetUserProfileResponse() {

	}

	public GetUserProfileResponse(GetUserByIdResponse userIdResponse) {
		super();
		this.userIdResponse = userIdResponse;
	}

	public GetUserProfileResponse(GetUserByEmailResponse userResponse) {
		super();
		this.userResponse = userResponse;
	}

	public GetUserProfileResponse(
			GetUserAddressesByUserIdResponse userAddressResponse) {
		super();
		this.userAddressResponse = userAddressResponse;
	}

	public GetUserProfileResponse(
			LoyaltyUserStatusResponse loyaltyStatusResponse) {
		super();
		this.loyaltyStatusResponse = loyaltyStatusResponse;
	}

	public GetUserProfileResponse(
			GetEmailSubscriberResponse emailSubscriberResponse) {
		super();
		this.emailSubscriberResponse = emailSubscriberResponse;
	}

	public GetUserProfileResponse(GetSDWalletByUserIdResponse sdWalletResponse) {
		super();
		this.sdWalletResponse = sdWalletResponse;
	}

	public GetUserProfileResponse(
			GetAvailableBalanceInSDWalletByUserIdResponse availableBalanceInSdWallet) {
		super();
		this.availableBalanceInSdWallet = availableBalanceInSdWallet;
	}

	public GetUserProfileResponse(
			GetCompleteSDWalletHistoryByUserIdResponse completeSdWalletResponse) {
		super();
		this.completeSdWalletResponse = completeSdWalletResponse;
	}

	// Setter and Getter

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
		return loyaltyStatusResponse;
	}

	public void setLoyaltyStatus(LoyaltyUserStatusResponse loyaltyStatusResponse) {
		this.loyaltyStatusResponse = loyaltyStatusResponse;
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

	public void setEmailSubscriberResponse(
			GetEmailSubscriberResponse emailSubscriberResponse) {
		this.emailSubscriberResponse = emailSubscriberResponse;
	}

	public GetUserByIdResponse getUserIdResponse() {
		return userIdResponse;
	}

	public void setUserIdResponse(GetUserByIdResponse userIdResponse) {
		this.userIdResponse = userIdResponse;
	}

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

}
