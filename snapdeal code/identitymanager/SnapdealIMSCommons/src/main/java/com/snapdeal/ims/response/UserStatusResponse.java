package com.snapdeal.ims.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class UserStatusResponse extends AbstractResponse{
	boolean walletStatus;
	boolean socialStatusAsFacebook;
	boolean socialStatusAsGoogle;
	boolean upgradeStatusByEmailId;
	boolean fortknoxStatusByEmailId;
	boolean sdMismatchStatusByEmailId;
	boolean fcMismatchStatusByEmailId;
    boolean walletMobileVerifiedStatus;
    boolean userMobileVerifiedStatus;
    boolean fcIdUpgradeStatus;
    boolean sdIdUpgradeStatus;
    
}
