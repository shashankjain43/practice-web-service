package com.snapdeal.opspanel.promotion.constants;

import com.snapdeal.ims.enums.Gender;
import com.snapdeal.ims.enums.KycInitiatedBy;

public class KYCConstants {

	public static final String KYC = "/kyc";
	public static final String GET_KYC_ENUMS = "/getKYCEnums";
	public static final Class[] enumsToParse = {Gender.class};
	public static final String GET_USER_KYC_DETAILS = "/getUserKYCDetails";
	public static final String INITIATE_KYC_PROCESS = "/initiateKYCProcess";
	public static final String UPDATE_USER_BY_ID = "/updateUserById";
	public static final String CREATE_OR_UPDATE_KYC_USER = "/createorUpdateKYCUser";
	public static final String UNDO_FULL_KYC_FOR_USER = "/undoFullKYCForUser";
	public static final String GET_USER_BY_VERIFIED_MOBILE = "/getUserByVerifiedMobile";
	public static final KycInitiatedBy kycInitiatedBy = KycInitiatedBy.INTERNAL_AGENT;

}
