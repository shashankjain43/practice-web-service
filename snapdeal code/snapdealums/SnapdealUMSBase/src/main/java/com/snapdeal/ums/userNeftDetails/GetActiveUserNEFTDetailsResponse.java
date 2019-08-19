package com.snapdeal.ums.userNeftDetails;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.audit.annotation.AuditableClass;
import com.snapdeal.base.audit.annotation.AuditableField;
import com.snapdeal.base.model.common.ServiceResponse;

/**
 * Response after verifying existing NEFT details
 * 
 * @author ashish
 * 
 */
@AuditableClass
public class GetActiveUserNEFTDetailsResponse extends ServiceResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 81341134566535063L;

	@AuditableField
	@Tag(5)
	private EnhancedUserNEFTDetailsSRO enhancedUserNEFTDetails;

	public GetActiveUserNEFTDetailsResponse(
			EnhancedUserNEFTDetailsSRO enhancedUserNEFTDetails) {
		super();
		this.enhancedUserNEFTDetails = enhancedUserNEFTDetails;
	}

	public GetActiveUserNEFTDetailsResponse() {
		super();
	}

	public GetActiveUserNEFTDetailsResponse(boolean successful, String message) {
		super(successful, message);
	}

	public EnhancedUserNEFTDetailsSRO getEnhancedUserNEFTDetails() {
		return enhancedUserNEFTDetails;
	}

	public void setEnhancedUserNEFTDetails(
			EnhancedUserNEFTDetailsSRO enhancedUserNEFTDetails) {
		this.enhancedUserNEFTDetails = enhancedUserNEFTDetails;
	}
	
	

}
