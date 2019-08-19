package com.snapdeal.ims.migration.dao;

import com.snapdeal.ims.migration.model.entity.UpgradeStatus;

public interface MigrationDao {

	public UpgradeStatus getLatestUpgradeStatus(String email);

	public void createUpgrateUser(UpgradeStatus upgradeStatus);

	public void updateUpgradationStatus(UpgradeStatus upgradeStatusFromDb);

	void updateUpgradationStatusWithEmailVerifiedCount(
			UpgradeStatus upgradeStatusFromDb);

}
