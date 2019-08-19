package com.snapdeal.ums.server.services.impl;

import java.util.ArrayList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetAvailableBalanceInSDWalletByUserIdRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetAvailableBalanceInSDWalletByUserIdResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetCompleteSDWalletHistoryByUserIdRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetCompleteSDWalletHistoryByUserIdResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetSDWalletByUserIdRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetSDWalletByUserIdResponse;
import com.snapdeal.ums.admin.sdwallet.server.services.ISDWalletService;
import com.snapdeal.ums.admin.sdwallet.sro.SDWalletHistorySRO;
import com.snapdeal.ums.admin.sdwallet.sro.SDWalletSRO;
import com.snapdeal.ums.admin.userprofile.GetUserProfileRequest;
import com.snapdeal.ums.admin.userprofile.GetUserProfileResponse;
import com.snapdeal.ums.constants.ErrorConstants;
import com.snapdeal.ums.core.sro.user.UserSRO;
import com.snapdeal.ums.exception.userAddress.UserAddressException;
import com.snapdeal.ums.ext.user.GetUserByEmailRequest;
import com.snapdeal.ums.ext.user.GetUserByEmailResponse;
import com.snapdeal.ums.ext.user.GetUserByIdRequest;
import com.snapdeal.ums.ext.user.GetUserByIdResponse;
import com.snapdeal.ums.ext.userAddress.GetUserAddressesByUserIdRequest;
import com.snapdeal.ums.ext.userAddress.GetUserAddressesByUserIdResponse;
import com.snapdeal.ums.loyalty.LoyaltyUploadRs;
import com.snapdeal.ums.loyalty.LoyaltyUserStatusRequest;
import com.snapdeal.ums.loyalty.LoyaltyUserStatusResponse;
import com.snapdeal.ums.server.services.IUserAddressService;
import com.snapdeal.ums.server.services.IUserProfileService;
import com.snapdeal.ums.server.services.IUserService;
import com.snapdeal.ums.services.ValidationService;
import com.snapdeal.ums.services.loyalty.ILoyaltyUserService;
import com.snapdeal.ums.subscription.ext.subscription.GetEmailSubscriberRequest;
import com.snapdeal.ums.subscription.ext.subscription.GetEmailSubscriberResponse;
import com.snapdeal.ums.subscription.server.services.ISubscriptionsService;

/**
 * Service class for getting user profile
 * 
 * @author lovey
 * 
 */

@Service("umsUserProfileService")
@Transactional
public class UserProfileServiceImpl implements IUserProfileService {

	@Autowired
	private IUserAddressService userAddressService;
	@Autowired
	private ISubscriptionsService subscriptionService;
	@Autowired
	private ISDWalletService sdWalletService;
	@Autowired
	private IUserService userService;
	@Autowired
	private ILoyaltyUserService loyaltyService;

	private static final Logger log = LoggerFactory
			.getLogger(UserProfileServiceImpl.class);

	@Autowired
	private ValidationService validationService;

	/**
	 * Get user details by passing user email or user ID
	 * 
	 * @param userEmail
	 *            or UserID
	 * @return User Profile Response
	 */
	public GetUserProfileResponse getUserProfileByEmailOrId(
			GetUserProfileRequest request) {

		String email = request.getValue();

		GetUserProfileResponse userProfileResponse = new GetUserProfileResponse();

		GetUserByEmailRequest userEmailRequest = new GetUserByEmailRequest();

		userEmailRequest.setEmail(email);
		GetUserByEmailResponse userEmailResponse = userService
				.getUserByEmail(userEmailRequest);
		GetUserByIdResponse userIdResponse = null;
		UserSRO user = null;
		user = userEmailResponse.getGetUserByEmail();

		/**
		 * Checking if the passed parameter in request object is not email, but
		 * user Id
		 */

		if (userEmailResponse.isSuccessful() == false || user == null) {
			Integer id = Integer.parseInt(request.getValue());

			GetUserByIdRequest userIdRequest = new GetUserByIdRequest(id);
			userIdResponse = userService.getUserById(userIdRequest);
			userProfileResponse.setUserIdResponse(userIdResponse);
			user = userIdResponse.getGetUserById();
			if (user == null) {

				/**
				 * If the user email or user Id does not exists.
				 */

				userProfileResponse.setSuccessful(false);
				validationService.addValidationError(userProfileResponse,
						ErrorConstants.USER_NOT_PRESENT);
				log.error(ErrorConstants.USER_NOT_PRESENT.getMsg());
				return userProfileResponse;
			} else {
				email = user.getEmail();
			}

		} else {
			userProfileResponse.setUserResponse(userEmailResponse);

		}

		/**
		 * Calling the get user address service
		 */
		GetUserAddressesByUserIdRequest userAddressRequest = new GetUserAddressesByUserIdRequest(
				user.getId());
		
		log.info("-- Going to fetch user address.");

		GetUserAddressesByUserIdResponse userAddressResponse = null;
		try {
			userAddressResponse = userAddressService
					.getUserAddressesByUserId(userAddressRequest);

		} catch (UserAddressException e) {
			log.error(ErrorConstants.USER_ADDRESS_EXCEPTION.getMsg(), e);

			validationService.addValidationError(userProfileResponse,
					ErrorConstants.USER_ADDRESS_EXCEPTION);

		}

		/**
		 * Calling available balance in sd wallet service
		 */

		GetAvailableBalanceInSDWalletByUserIdRequest availableBalanceInSdWalletRequest = new GetAvailableBalanceInSDWalletByUserIdRequest(
				user.getId());
		
		log.info("-- Going to fetch user available balance in SDWallet.");

		GetAvailableBalanceInSDWalletByUserIdResponse availableBalanceInSdWalletResponse = sdWalletService
				.getAvailableBalanceInSDWalletByUserId(availableBalanceInSdWalletRequest);

		/**
		 * Calling loyalty status service
		 */

		
		LoyaltyUserStatusRequest loyaltyStatusRequest = new LoyaltyUserStatusRequest();
		log.info("-- Going to fetch user loyalty status.");
		loyaltyStatusRequest.setUserEmailID(email);
		LoyaltyUserStatusResponse loyaltyStatusResponse = loyaltyService
				.getLoyaltyStatus(loyaltyStatusRequest);

		/**
		 * Calling sd wallet service
		 */

		GetSDWalletByUserIdRequest sdWalletRequest = new GetSDWalletByUserIdRequest();
		log.info("-- Going to fetch user SDWallet.");
		
		sdWalletRequest.setUserId(user.getId());
		GetSDWalletByUserIdResponse sdWalletResponse = sdWalletService
				.getSDWalletByUserId(sdWalletRequest);

		/**
		 * Calling complete sd wallet history service
		 */

		GetCompleteSDWalletHistoryByUserIdRequest sdWalletCompleteRequest = new GetCompleteSDWalletHistoryByUserIdRequest();
		log.info("-- Going to fetch user complete history of SDWallet.");
		
		sdWalletCompleteRequest.setUserId(user.getId());
		GetCompleteSDWalletHistoryByUserIdResponse completeSdWalletHistoryResponse = sdWalletService
				.getCompleteSDWalletHistoryByUserId(sdWalletCompleteRequest);

		/**
		 * Calling subscriber service
		 */
		GetEmailSubscriberRequest emailSubscriberRequest = new GetEmailSubscriberRequest();
		log.info("-- Going to fetch user Subscriber information.");
		emailSubscriberRequest.setEmail(email);
		GetEmailSubscriberResponse emailSubscriberResponse = subscriptionService
				.getEmailSubscriber(emailSubscriberRequest);

		/**
		 * Calling complete sd wallet history service
		 */

		List<SDWalletSRO> sdWalletSROList = sdWalletResponse
				.getSdWalletInfoSRO().getSdWalletSRO();
		
		List<SDWalletHistorySRO> sdWalletHistorySROList = completeSdWalletHistoryResponse
				.getSdWalletHistoryInfoSRO().getSdWalletHistorySRO();

		List<SDWalletSRO> reverseSDWalletSROList = new ArrayList<SDWalletSRO>();

		List<SDWalletHistorySRO> reverseSDWalletHistorySROList = new ArrayList<SDWalletHistorySRO>();

		/**
		 * Printing the sdWalletSROList in reverse to get the recent top 5
		 * entries
		 */
		for (int i = sdWalletSROList.size() - 1; i > sdWalletSROList.size() - 6; i--) {
			if (sdWalletSROList.get(i) != null) {
				reverseSDWalletSROList.add(sdWalletSROList.get(i));
			}
		}

		/**
		 * Printing the sdWalletHistorySROList in reverse to get the recent top
		 * 5 entries
		 */
		for (int i = sdWalletHistorySROList.size() - 1; i > sdWalletHistorySROList
				.size() - 6; i--) {
			if (sdWalletHistorySROList.get(i) != null) {
				reverseSDWalletHistorySROList
						.add(sdWalletHistorySROList.get(i));
			}

		}
		userProfileResponse
				.setSdWalletHistorySROList(reverseSDWalletHistorySROList);
		userProfileResponse.setSdWalletSROList(reverseSDWalletSROList);
		userProfileResponse.setUserResponse(userEmailResponse);
		userProfileResponse.setUserIdResponse(userIdResponse);
		userProfileResponse.setUserAddressResponse(userAddressResponse);
		userProfileResponse.setLoyaltyStatus(loyaltyStatusResponse);
		userProfileResponse.setEmailSubscriberResponse(emailSubscriberResponse);
		userProfileResponse
				.setAvailableBalanceInSdWallet(availableBalanceInSdWalletResponse);
		userProfileResponse.setSdWalletResponse(sdWalletResponse);
		userProfileResponse
				.setCompleteSdWalletResponse(completeSdWalletHistoryResponse);
		// response.setAvailableBalanceInSdWallet(availableBalanceInSdWallet);
		userProfileResponse.setSuccessful(true);

		return userProfileResponse;
	}

}
