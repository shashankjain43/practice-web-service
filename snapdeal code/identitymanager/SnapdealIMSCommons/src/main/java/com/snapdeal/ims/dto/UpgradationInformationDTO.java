package com.snapdeal.ims.dto;

import com.snapdeal.ims.enums.Action;
import com.snapdeal.ims.enums.Skip;
import com.snapdeal.ims.enums.State;
import com.snapdeal.ims.enums.Upgrade;
import com.snapdeal.ims.enums.Wallet;

import java.io.Serializable;

import lombok.Data;

/**
 * UpgradationInformation: This gives information related to user migration into
 * ims and various details of associations with other system.
 */
@Data
public class UpgradationInformationDTO implements Serializable {
	private static final long serialVersionUID = -5455925357576208338L;

	private State state;
	private Upgrade upgrade;
	private Action action;
	private Wallet wallet;
	private Skip skip;
   private String suggestedMobileNumber;
   private boolean blackListed;
}