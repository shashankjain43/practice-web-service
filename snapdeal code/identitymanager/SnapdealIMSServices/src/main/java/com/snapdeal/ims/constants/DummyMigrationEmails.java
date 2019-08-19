package com.snapdeal.ims.constants;

/*
 * for email snapsduseronlyupgrade1001@snapupgrade.com for email
 * snapsdmigrateduserupgrade1001@snapupgrade.com
 * 
 * for email snapfcuseronlyupgrade1001@snapupgrade.com for email
 * snapfcmigrateduserupgrade1001@snapupgrade.com
 * 
 * for email snapsdfcuserupgrade1001@snapupgrade.com for email
 * snapsdfcmigrateduserupgrade1001@snapupgrade.com
 */
public enum DummyMigrationEmails {

	SD_USER_MIGRATION_SUCCESS("snapsduseronlyupgrade1001success@snapupgrade.com"), 
	SD_USER_MIGRATION_FAILURE("snapsduseronlyupgrade1001failure@snapupgrade.com"),
	
	FC_USER_MIGRATION_SUCCESS("snapfcuseronlyupgrade1001success@snapupgrade.com"), 
	FC_USER_MIGRATION_FAILURE("snapfcuseronlyupgrade1001failure@snapupgrade.com"),
	
	SD_FC_USER_MIGRATION_SUCCESS("snapsdfcuserupgrade1001success@snapupgrade.com"), 
	SD_FC_USER_MIGRATION_FAILURE("snapsdfcuserupgrade1001failure@snapupgrade.com"),
	
	MIGRATED_SD_USER_MIGRATION_SUCCESS("snapsdmigrateduserupgrade1001success@snapupgrade.com"), 
	MIGRATED_SD_USER_MIGRATION_FAILURE("snapsdmigrateduserupgrade1001failure@snapupgrade.com"),
	
	MIGRATED_FC_USER_MIGRATION_SUCCESS("snapfcmigrateduserupgrade1001success@snapupgrade.com"), 
	MIGRATED_FC_USER_MIGRATION_FAILURE("snapfcmigrateduserupgrade1001failure@snapupgrade.com"),
	
	MIGRATED_SD_FC_USER_MIGRATION_SUCCESS("snapsdfcmigrateduserupgrade1001success@snapupgrade.com"),
	MIGRATED_SD_FC_USER_MIGRATION_FAILURE("snapsdfcmigrateduserupgrade1001failure@snapupgrade.com");

	String email;

	private DummyMigrationEmails(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

}