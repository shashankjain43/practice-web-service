package com.snapdeal.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.ums.admin.userprofile.GetUserProfileRequest;
import com.snapdeal.ums.admin.userprofile.GetUserProfileResponse;
import com.snapdeal.ums.exception.userAddress.UserAddressException;
import com.snapdeal.ums.server.services.IUserProfileService;

@Controller
@RequestMapping("/admin/user")
public class UserProfileServiceController {

	private static final Logger log = LoggerFactory
			.getLogger(UserProfileServiceController.class);
	@Autowired
	private IUserProfileService userProfileService;

	@RequestMapping("/userDetail")
	public String getUserEmail(ModelMap model) {

		return "admin/user/userDetail";
	}

	@RequestMapping(value = "getUserProfileByEmailOrId", method = RequestMethod.POST)
	public String getUserProfileByEmailOrId(
			@RequestParam("value") GetUserProfileRequest request, ModelMap model) {

		GetUserProfileResponse response = null;
		
		try {
			response = userProfileService.getUserProfileByEmailOrId(request);
		} catch (UserAddressException e) {

			e.printStackTrace();
		}

		response.setProtocol(request.getResponseProtocol());
		
		model.addAttribute("userDetailResponse", response);

		if (response.isSuccessful() == false) {
			model.addAttribute("user", null);
		} else {

			if (response.getUserResponse().getGetUserByEmail() != null) {
				model.addAttribute("user", response.getUserResponse()
						.getGetUserByEmail());
			} else {
				model.addAttribute("user", response.getUserIdResponse()
						.getGetUserById());
			}

			model.addAttribute("userLoyaltyStatus", response.getLoyaltyStatus()
					.getLoyaltyStatus());
			model.addAttribute("userLoyaltyProgram", response
					.getLoyaltyStatus().getLoyaltyProgram());
			model.addAttribute("userAddress", response.getUserAddressResponse()
					.getUserAddresses());
			model.addAttribute("userAvailableSdCash", response
					.getAvailableBalanceInSdWallet().getAvailableAmount());
			model.addAttribute("userEmailSubscriber", response
					.getEmailSubscriberResponse().getEmailSubscriber());
			model.addAttribute("userSdWalletList",
					response.getSdWalletSROList());
			model.addAttribute("userSdWalletHistoryList",
					response.getSdWalletHistorySROList());
		}

		return "admin/user/userDetail";
	}
}
