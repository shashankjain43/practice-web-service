package com.snapdeal.ims.dashboard.dbmapper;

import org.apache.ibatis.annotations.Param;

import com.snapdeal.ims.dbmapper.entity.User;
import com.snapdeal.ims.entity.GetUpgradeStatusEntity;
import com.snapdeal.ims.entity.GetUserStatusEntity;
import com.snapdeal.ims.migration.model.entity.UpgradeStatus;
import com.snapdeal.ims.request.UserStatusRequest;

public interface ICheckUserDetailsByEmailIdDao {
    public int getSocialStatusAsFacebook(UserStatusRequest request);
    public int getSocialStatusAsGoogle(UserStatusRequest request);
    public int getFortknoxStatusByEmailId(String taskId);
	public int getWalletStatus(UserStatusRequest request);
	public int getUpgradeStatusByEmailID(UserStatusRequest request);
    public GetUserStatusEntity getUserStatus(UserStatusRequest request);
    public GetUpgradeStatusEntity getUpgradeStatus(UserStatusRequest request);
    public int getWalletMobileVerifiedStatus(UserStatusRequest request);
    public int getUserMobileVerifiedStatus(UserStatusRequest request);
    public int getFcIdUpgradeStatus(UserStatusRequest request);
    public int getSdIdUpgradeStatus(UserStatusRequest request);
    public String getUserIdByEmail(String emailId);
    public UpgradeStatus getUpgradeStatus(String email);
    public User getUserByEmail(@Param("emailId") String emailId);
	
}
