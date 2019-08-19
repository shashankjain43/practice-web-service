package com.snapdeal.ims.response;

import com.snapdeal.ims.response.AbstractResponse;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WalletCountResponse extends AbstractResponse {

	private int upgradeStatusTotal;
	private int upgradeStatusSuccessTotal;
	private int upgradeStatusFailedTotal;
	
	private int upgradeStatusSnapdealCount;
	private int upgradeStatusFreechargeCount;
	private int upgradeStatusOneCheckCount;

	private int upgradeStatusSnapdealSuccessCount;
	private int upgradeStatusFreechargeSuccessCount;
	private int upgradeStatusOneCheckSuccessCount;
	
	private int upgradeStatusSnapdealFailedCount;
	private int upgradeStatusFreechargeFailedCount;
	private int upgradeStatusOneCheckFailedCount;
	
}
