package com.snapdeal.ims.migration.dbmapper;

import com.snapdeal.ims.migration.model.entity.UpgradeHistory;
import com.snapdeal.ims.migration.model.entity.UpgradeStatus;

/**
 * @author Kishan
 *
 */

public interface UpgradeUserStatusMapper {
   /**
    * This function will create UpgradeUserStatus in database
    * 
    * @param UpgradeStatus
    */
   public void createUpgrateUser(UpgradeStatus upgradeStatus);

   public void updateUpgradationStatus(UpgradeStatus upgradeStatusFromDb);

   public UpgradeStatus getUpgradeStatus(String email);

   public void createUpgradeUserHistory(UpgradeHistory upgradeHistory);   

   public void updateUpgradationStatusWithEmailVerifiedCount(UpgradeStatus upgradeStatusFromDb);

}