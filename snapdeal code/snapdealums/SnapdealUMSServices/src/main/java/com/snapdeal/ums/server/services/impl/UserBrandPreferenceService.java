package com.snapdeal.ums.server.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.ums.constants.ErrorConstants;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.entity.UserBrand;
import com.snapdeal.ums.server.services.IUserServiceInternal;
import com.snapdeal.ums.server.services.convertor.IUMSConvertorService;
import com.snapdeal.ums.server.services.convertor.IUserBrandPreferenceService;
import com.snapdeal.ums.services.ValidationService;

/**
 * Service class to retrieve list of brands preference of user
 * 
 * @author lovey
 * 
 */
@Service
@Transactional
public class UserBrandPreferenceService implements IUserBrandPreferenceService {
	private static final Logger log = LoggerFactory
			.getLogger(UserBrandPreferenceService.class);

	@Autowired
	private IUserServiceInternal userService;

	@Autowired
	private ValidationService validationService;

	/**
	 * 
	 * @param user email or user Id
	 *         
	 * @return GetUserBrandResponse containing list of brands
	 */
	@Transactional
	public GetUserBrandResponse getUserBrandByEmailOrId(
			GetUserBrandRequest request) {

		GetUserBrandResponse userBrandResponse = new GetUserBrandResponse();

		String email = request.getValue();

		User user = null;
		user = userService.getUserByEmail(email);
		if (user == null) {
			// Entered value is UserID. Retrieving userId.
			Integer id = Integer.parseInt(request.getValue());
			user = userService.getUserById(id);
			if (user == null) {
				// User does not exist. Setting validation error.
				userBrandResponse.setSuccessful(false);
				validationService.addValidationError(userBrandResponse,
						ErrorConstants.USER_NOT_PRESENT);
				log.error(ErrorConstants.USER_NOT_PRESENT.getMsg());
				return userBrandResponse;

			} else {
				email = user.getEmail();
			}
		}

		// Fetching user list of brand preference
		List<UserBrand> listOfUserBrands = user.getBrandPreferences();
		List<String> listOfBrands = new ArrayList<String>();
		for (UserBrand e : listOfUserBrands) {
			listOfBrands.add(e.getBrand_id());
		}
		userBrandResponse.setListOfUserBrands(listOfBrands);
		userBrandResponse.setSuccessful(true);
		// List<String> listOfBrands=user.getBrandPreferences();
		log.info("brand" + user.getBrandPreferences());
		log.info("id" + user.getId());
		log.info("email" + user.getEmail());

		return userBrandResponse;

	}

}
