package com.snapdeal.ims.migration.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.ims.migration.dao.MigrationDao;
import com.snapdeal.ims.migration.dbmapper.UpgradeUserStatusMapper;
import com.snapdeal.ims.migration.model.entity.UpgradeHistory;
import com.snapdeal.ims.migration.model.entity.UpgradeStatus;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

@Repository
public class MigrationDaoImpl implements MigrationDao {

	@Autowired
	UpgradeUserStatusMapper upgradeUserStatusMapper;
	
	@Override
   @Timed
   @Marked
	public UpgradeStatus getLatestUpgradeStatus(String email) {
		return upgradeUserStatusMapper.getUpgradeStatus(email);
	}

	@Override
   @Timed
   @Marked
	public void createUpgrateUser(UpgradeStatus upgradeStatus) {
		upgradeUserStatusMapper.createUpgrateUser(upgradeStatus);
		//after creating Upgrade User, insert in upgrade history table
		UpgradeHistory userUpgradeHistory=new UpgradeHistory();
		userUpgradeHistory.setUpgradeId(upgradeStatus.getId());
		userUpgradeHistory.setCurrentState(upgradeStatus.getCurrentState());
		userUpgradeHistory.setUpgradeStatus(upgradeStatus.getUpgradeStatus());
		createUpgrateUserHistory(userUpgradeHistory);

	}

	@Override
   @Timed
   @Marked
	public void updateUpgradationStatus(UpgradeStatus upgradeStatusFromDb) {
		upgradeUserStatusMapper.updateUpgradationStatus(upgradeStatusFromDb);
		
	   //Create Upgrade History
      UpgradeHistory upgradeHistory = new UpgradeHistory();
      upgradeHistory.setUpgradeStatus(upgradeStatusFromDb.getUpgradeStatus());
      upgradeHistory.setCreatedDate(upgradeStatusFromDb.getUpdatedDate());
      upgradeHistory.setCurrentState(upgradeStatusFromDb.getCurrentState());
      upgradeHistory.setUpgradeId(upgradeStatusFromDb.getId());
      createUpgrateUserHistory(upgradeHistory);
	}
	
	@Override
	@Timed
	@Marked
	public void updateUpgradationStatusWithEmailVerifiedCount(
			UpgradeStatus upgradeStatusFromDb) {
		upgradeUserStatusMapper
				.updateUpgradationStatusWithEmailVerifiedCount(upgradeStatusFromDb);

		// Create Upgrade History
		UpgradeHistory upgradeHistory = new UpgradeHistory();
		upgradeHistory.setUpgradeStatus(upgradeStatusFromDb.getUpgradeStatus());
		upgradeHistory.setCreatedDate(upgradeStatusFromDb.getUpdatedDate());
		upgradeHistory.setCurrentState(upgradeStatusFromDb.getCurrentState());
		upgradeHistory.setUpgradeId(upgradeStatusFromDb.getId());
		createUpgrateUserHistory(upgradeHistory);
	}
	

   private void createUpgrateUserHistory(UpgradeHistory upgradeHistory) {
      upgradeUserStatusMapper.createUpgradeUserHistory(upgradeHistory);
   }
}
