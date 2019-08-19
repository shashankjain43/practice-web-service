package com.snapdeal.ims.service;

import com.snapdeal.ims.dbmapper.entity.User;
import com.snapdeal.ims.migration.model.entity.UpgradeStatus;
import com.snapdeal.ims.request.UserStatusRequest;
import com.snapdeal.ims.response.UserStatusResponse;

public interface IUserDetailsService {
    public UserStatusResponse getUserStateByEmail(UserStatusRequest request);
	boolean getSdMismatchStatusbyEmailId(User user, UpgradeStatus upgrade);
	boolean getFcMisMatchStatusByEmailId(User user, UpgradeStatus upgrade);
	boolean getFcIdUpgradeStatus(UpgradeStatus upgradeStatus);
	boolean getSdIdUpgradeStatus(UpgradeStatus upgradeStatus);
	boolean getSocialStatusAsFacebook(UserStatusRequest request, User user);
	boolean getSocialStatusAsGoogle(UserStatusRequest request, User user);
	boolean getUpgradeStatusByEmailID(UpgradeStatus upgradeStatus);
	boolean getWalletMobileVerifiedStatus(User user);
	boolean getFortknoxStatusByEmailId(UserStatusRequest request, String userId);
}
