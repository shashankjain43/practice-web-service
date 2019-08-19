package com.snapdeal.ums.userNeftDetails;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.audit.annotation.AuditableClass;
import com.snapdeal.base.audit.annotation.AuditableField;
import com.snapdeal.base.model.common.ServiceResponse;

/**
 * Response after requesting add neft details
 * 
 * @author ashish
 * 
 */
@AuditableClass
public class AddUserNEFTDetailsResponse extends ServiceResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 81341134566535063L;

	@AuditableField("NEFT details")
	@Tag(5)
	private EnhancedUserNEFTDetailsSRO enhancedUserNEFTDetails;

	public AddUserNEFTDetailsResponse(
			EnhancedUserNEFTDetailsSRO enhancedUserNEFTDetails) {
		super();
		this.enhancedUserNEFTDetails = enhancedUserNEFTDetails;
	}

	public AddUserNEFTDetailsResponse() {
		super();
	}

	public AddUserNEFTDetailsResponse(boolean successful, String message) {
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
