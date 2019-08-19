package com.snapdeal.opspanel.promotion.enums;

public enum OfflineOnlineMerchantsComment {

	SELF_ATTESTATION_MISSING("Self attestation missing"),
	COMPANY_SEAL_MISSING("Company seal missing"),
	INCORRECT_DOCUMENT("Incorrect document"),
	INCOMPLETE_DOCUMENT("Incomplete document"),
	SIGNATURE_MISMATCH("Signature mismatch"),
	DOC_NOT_MATCHING_WITH_GIVEN_DETAILS("Doc not matching with given details"),
	DOC_NOT_CLEAR("Doc not clear");
	
	private String comment;
	
	OfflineOnlineMerchantsComment( String comment ){
		this.comment = comment;
	}
	
	public String getOfflineOnlineMerchantsComment(){
		return comment;
	}
	
	@Override
    public String toString() {
		return getOfflineOnlineMerchantsComment();
	}
}
