package com.snapdeal.ims.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ims.dashboard.dbmapper.ICheckUserDetailsByEmailIdDao;
import com.snapdeal.ims.dbmapper.entity.User;
import com.snapdeal.ims.enums.CreateWalletStatus;
import com.snapdeal.ims.enums.Upgrade;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.migration.model.entity.UpgradeStatus;
import com.snapdeal.ims.request.UserStatusRequest;
import com.snapdeal.ims.response.UserStatusResponse;
import com.snapdeal.ims.service.IUserDetailsService;

@Service
public class CheckUserDetailsByEmailIdService implements IUserDetailsService {
	private static final boolean True = true;
	private static final boolean False = false;

	@Autowired
	private ICheckUserDetailsByEmailIdDao checkUserDetailsByEmailIdDao;

	public boolean getWalletStatus(User user) {

		if (user != null
				&& user.getWalletStatus() == CreateWalletStatus.CREATED) {
			return true;
		}
		return false;
	}

	@Override
	public boolean getSocialStatusAsFacebook(UserStatusRequest request,
			User user) {
		if (user.isFacebookUser()) {
			int result = checkUserDetailsByEmailIdDao
					.getSocialStatusAsFacebook(request);
			if (result >= 1)
				return true;
		}
		return false;
	}

	@Override
	public boolean getSocialStatusAsGoogle(UserStatusRequest request, User user) {
		if (user.isGoogleUser()) {
			int result = checkUserDetailsByEmailIdDao
					.getSocialStatusAsGoogle(request);
			if (result >= 1)
				return true;
		}
		return false;
	}

	@Override
	public boolean getUpgradeStatusByEmailID(UpgradeStatus upgradeStatus) {
		if (upgradeStatus.getUpgradeStatus() == Upgrade.LINK_FC_ACCOUNT
				|| upgradeStatus.getUpgradeStatus() == Upgrade.LINK_FC_ACCOUNT
				|| upgradeStatus.getUpgradeStatus() == Upgrade.UPGRADE_COMPLETED) {
			return true;
		}
		return false;

	}

	@Override
	public boolean getFortknoxStatusByEmailId(UserStatusRequest request,
			String userId) {
		if (userId != null) {
			String taskId = "IMSFortKnoxMerge." + userId;
			int result = checkUserDetailsByEmailIdDao
					.getFortknoxStatusByEmailId(taskId);
			if (result >= 1)
				return True;
			else
				return False;
		} else {
			return False;
		}
	}

	@Override
	public boolean getSdMismatchStatusbyEmailId(User user, UpgradeStatus upgrade) {
		if ((user.getSdUserId() > 0 && upgrade.getSdId() == null)
				|| (user.getSdUserId() == 0 && upgrade.getSdId() != null)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean getFcMisMatchStatusByEmailId(User user, UpgradeStatus upgrade) {
		if ((user.getFcUserId() > 0 && upgrade.getSdId() == null)
				|| (user.getFcUserId() == 0 && upgrade.getFcId() != null)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean getWalletMobileVerifiedStatus(User user) {

		if (user.isMobileVerified()) {
			return true;
		}
		return false;

	}

	@Override
	public boolean getFcIdUpgradeStatus(UpgradeStatus upgradeStatus) {

		if (upgradeStatus.getUpgradeStatus() == Upgrade.LINK_FC_ACCOUNT
				&& upgradeStatus.getFcId() == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean getSdIdUpgradeStatus(UpgradeStatus upgradeStatus) {

		if (upgradeStatus.getUpgradeStatus() == Upgrade.LINK_SD_ACCOUNT
				&& upgradeStatus.getSdId() == null) {
			return false;
		}
		return true;

	}

	public UserStatusResponse getUserStateByEmail(UserStatusRequest request) {
		UserStatusResponse response = new UserStatusResponse();
		// fetch user details

		User user = checkUserDetailsByEmailIdDao.getUserByEmail(request
				.getEmailId());
		// fetch user upgrade status
		UpgradeStatus upgradeStatus = checkUserDetailsByEmailIdDao
				.getUpgradeStatus(request.getEmailId());
		if (user == null) {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
					IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
		}

		response.setWalletStatus(getWalletStatus(user));
		response.setSocialStatusAsFacebook(getSocialStatusAsFacebook(request,
				user));
		response.setSocialStatusAsGoogle(getSocialStatusAsGoogle(request, user));
		response.setUpgradeStatusByEmailId(getUpgradeStatusByEmailID(upgradeStatus));
		response.setFortknoxStatusByEmailId(getFortknoxStatusByEmailId(request,
				user.getUserId()));
		response.setSdMismatchStatusByEmailId(getSdMismatchStatusbyEmailId(
				user, upgradeStatus));
		response.setFcMismatchStatusByEmailId(getFcMisMatchStatusByEmailId(
				user, upgradeStatus));
		response.setWalletMobileVerifiedStatus(getWalletStatus(user)
				&& user.isMobileVerified());
		response.setUserMobileVerifiedStatus(user.isMobileVerified());
		response.setFcIdUpgradeStatus(getFcIdUpgradeStatus(upgradeStatus));
		response.setSdIdUpgradeStatus(getSdIdUpgradeStatus(upgradeStatus));

		return response;

	}
}
