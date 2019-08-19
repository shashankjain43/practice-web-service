package com.snapdeal.opspanel.promotion.enums;

public enum FCMerchantsComment {

	SELF_ATTESTATION_MISSING("self attestation missing"),
	DOC_NOT_CLEAR("Doc not clear"),
	DOC_NOT_MATCHING_WITH_GIVEN_DETAILS("Doc not matching with given details"),
	OSV_SEAL_INVALID("OSV seal invalid"),
	OSV_SEAL_MISSING("OSV seal missing"),
	INCORRECT_DOCUMENT("Incorrect document"),
	SIGNATURE_MISMATCH("Signature mismatch"),
	DETAILS_MISSING("Details missing");

	private String comment;
	
	FCMerchantsComment( String comment ){
		this.comment = comment;
	}
	
	public String getFCMerchantsComment(){
		return comment;
	}
	
	@Override
    public String toString() {
		return getFCMerchantsComment();
	}
}
