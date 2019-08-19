/**
 * 
 */
package com.snapdeal.ims.enums;

/**
 * @author shachi
 *
 */
public enum Upgrade {

	UPGRADE_RECOMMENDED, 
	UPGRADE_COMPLETED, 
	LINK_SD_ACCOUNT, 
	LINK_FC_ACCOUNT, 
	NO_UPGRADE_REQRUIRED,
	NO_UPGRADE_REQRUIRED_BLACK_LISTED_USER,
	FORCE_UPGRADE;

   public static boolean isLinkState(Upgrade upgradeState) {
      return upgradeState == LINK_FC_ACCOUNT || upgradeState == LINK_SD_ACCOUNT;
   }
}