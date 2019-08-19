package com.snapdeal.ims.wallet.request;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum WalletUserMigrationStatus {
	SD_MIGRATED("SD_MIGRATED"), FC_MIGRATED("FC_MIGRATED"), SD_FC_MIGRATED(
			"SD_FC_MIGRATED"), EMAIL_VERIFIED("EMAIL_VERIFIED");

	private final String migrationStatus;

	WalletUserMigrationStatus(String migrationStatus) {
		this.migrationStatus = migrationStatus;
	}

	@org.codehaus.jackson.annotate.JsonValue
	public String getWalletUserMigrationStatus() {
		return this.migrationStatus;
	}

	@JsonCreator
	public static WalletUserMigrationStatus forValue(String value) {
		if (null != value) {
			for (WalletUserMigrationStatus eachPurpose : values()) {
				if (eachPurpose.getWalletUserMigrationStatus().equals(value)) {
					return eachPurpose;
				}
			}
		}
		return null;
	}
}
