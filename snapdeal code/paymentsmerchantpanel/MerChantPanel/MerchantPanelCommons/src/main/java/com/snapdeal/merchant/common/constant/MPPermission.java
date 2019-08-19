package com.snapdeal.merchant.common.constant;

public enum MPPermission {
	
	MCNT_ADD_USER_PERM("mnct_add_user_perm"),
	MCNT_EDIT_USER_PERM("mcnt_edit_user_perm"),
	MCNT_VIEW_USER_PERM("mcnt_view_user_perm"),
	MCNT_VIEWALL_USER_PERM("mcnt_view_all_user_perm"),
	FORGET_PWD_PERM("forget_pwd_perm"),
	CHANGE_PWD_PERM("change_pwd_perm"),
	MCNT_PROFILE_VIEW_PERM("mcnt_detail_view_perm");

    private final String permName;

	private MPPermission(String permName) {
		this.permName = permName;
	}

	public String getPermName() {
		return permName;
	}

}
