package com.snapdeal.ims.cache.service;

import com.snapdeal.ims.entity.SdFcPasswordEntity;

public interface IPasswordUpgradeCacheService {

	String getSdSdHashedPassword(String emailId);

	String getSdFcHashedPassword(String emailId);

	String getFcSdHashedPassword(String emailId);

	String getFcFcHashedPassword(String emailId);
	
	String getOcSdHashedPassword(String emailId);

	void updateSdFcPasswordbyEmailId(String emailId,
			SdFcPasswordEntity sdFcPasswordEntity);

	void createSdFcPasswordbyEmailId(String emailId,
			SdFcPasswordEntity sdFcPasswordEntity);

	boolean getUserUpgradeStatus(String emailId);

    void setIsUpgradeinitialized(String emailId, boolean isUpgradeShown);

   void createImsSdHashed(String emailId, String password);

   String getImsSdHashedPassword(String emailId);

   void updateImsSdHashed(String emailId, String password);

}
