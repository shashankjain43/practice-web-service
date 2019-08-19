package com.snapdeal.ims.service;

import com.snapdeal.ims.request.UpgradeUserByEmailRequest;
import com.snapdeal.ims.response.UpgradeUserByEmailResponse;

public interface IJobSchedularService {

	public UpgradeUserByEmailResponse upgradeUser(UpgradeUserByEmailRequest request);
}
