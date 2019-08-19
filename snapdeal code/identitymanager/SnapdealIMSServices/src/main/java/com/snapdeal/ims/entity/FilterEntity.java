package com.snapdeal.ims.entity;

import java.util.ArrayList;

import com.snapdeal.ims.constants.CreateWalletStatus;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.UpgradeChannel;
import com.snapdeal.ims.enums.UpgradeSource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterEntity {

	private Merchant originating_src;
	private CreateWalletStatus status;
	private ArrayList<UpgradeChannel> upgradeChannel;
	private ArrayList<UpgradeSource> upgradeSource;	
}
