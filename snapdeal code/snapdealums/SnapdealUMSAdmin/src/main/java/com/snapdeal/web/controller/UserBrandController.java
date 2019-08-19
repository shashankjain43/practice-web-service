package com.snapdeal.web.controller;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.CreditSDWalletResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.CreditSDWalletSendEmailRequest;
import com.snapdeal.ums.admin.sdwallet.server.services.ISDWalletService;
import com.snapdeal.ums.admin.userprofile.GetUserProfileRequest;
import com.snapdeal.ums.admin.userprofile.GetUserProfileResponse;
import com.snapdeal.ums.exception.userAddress.UserAddressException;
import com.snapdeal.ums.server.services.IUserProfileService;
import com.snapdeal.ums.server.services.convertor.IUserBrandPreferenceService;
import com.snapdeal.ums.server.services.impl.GetUserBrandRequest;
import com.snapdeal.ums.server.services.impl.GetUserBrandResponse;

@Controller
@RequestMapping("/admin/user")
public class UserBrandController {

	private static final Logger log = LoggerFactory
			.getLogger(UserProfileServiceController.class);
	@Autowired
	private IUserBrandPreferenceService userBrandPreferenceService;

	@Autowired
	private ISDWalletService sdWalletService;

	@RequestMapping("/brandPreference")
	public String getUserEmail(ModelMap model) {

		return "admin/user/brandPreference";
	}

	@RequestMapping(value = "getUserBrandByEmailOrId", method = RequestMethod.POST)
	public String getUserBrandByEmail(
			@RequestParam("value") GetUserBrandRequest request, ModelMap model) {

		GetUserBrandResponse userBrandResponse = userBrandPreferenceService
				.getUserBrandByEmailOrId(request);

		model.addAttribute("userBrandResponse", userBrandResponse);
		model.addAttribute("listOfBrands",
				userBrandResponse.getListOfUserBrands());

		log.info("Done:");
		return "admin/user/brandPreference";
	}

}
