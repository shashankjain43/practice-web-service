package com.snapdeal.ims.entity;

import java.util.Date;

import com.snapdeal.ims.constants.UserEnabledStatus;
import com.snapdeal.ims.enums.CreateWalletStatus;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.Upgrade;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserEntity {

	private String user_id;
	private int sd_user_id;
	private int fc_user_id;
	private int sd_fc_user_id;
	private int is_enabled;
	private Merchant originating_src;
	private String email;
	private String mobile_number;
	private String display_name;
	private Date created_time;
	private Date updated_time;
	private Upgrade migration_status;
	private UserEnabledStatus userEnabledStatus;
	private CreateWalletStatus create_wallet_status;
}
