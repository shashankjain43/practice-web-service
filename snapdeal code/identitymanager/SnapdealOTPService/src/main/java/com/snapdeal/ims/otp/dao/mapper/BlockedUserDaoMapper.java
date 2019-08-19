package com.snapdeal.ims.otp.dao.mapper;


public enum BlockedUserDaoMapper {
	FREEZE_ACCOUNT("frozen_account.freezeUser"),
	GET_FROZEN_ACCOUNT("frozen_account.getFreezedUser"),
	DROP_USER("frozen_account.dropfreezedUser"),
	UPDATE_FREEZE_ACCOUNT("frozen_account.updateFreezedUser");
	
	private String description;

	private BlockedUserDaoMapper(String desc) {
		this.description = desc;
	}

	@Override
	public String toString() {
		return description;
	}
}
