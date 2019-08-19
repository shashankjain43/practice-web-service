package com.snapdeal.opspanel.userpanel.response;

import com.snapdeal.ims.dto.KycDetailsDTO;
import com.snapdeal.ims.enums.KycMode;

import lombok.Data;

@Data
public class GetUserByVerifiedMobileResponse {

	private String firstName;
	private String lastName;
	private String dob;
	private String gender;
	private String userId;
	private String currentKycStatus;
	private KycDetailsDTO kycDetailsDTO;
	private KycMode kycMode;

}
