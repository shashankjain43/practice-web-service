package com.snapdeal.ums.userNeftDetails;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.audit.annotation.AuditableClass;
import com.snapdeal.base.audit.annotation.AuditableField;
import com.snapdeal.base.model.common.ServiceRequest;

/**
 * Request to add user NEFT details
 * 
 * @author ashish
 * 
 */
@AuditableClass
public class AddUserNEFTDetailsRequest extends ServiceRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 813414536535063L;
	@AuditableField
	@Tag(3)
	private UserNEFTDetailsSRO userNEFTDetails;

	public AddUserNEFTDetailsRequest() {

		super();
	}

	public AddUserNEFTDetailsRequest(UserNEFTDetailsSRO userNEFTDetails) {

		super();
		this.userNEFTDetails = userNEFTDetails;
	}

	public UserNEFTDetailsSRO getUserNEFTDetails() {

		return userNEFTDetails;
	}

	public void setUserNEFTDetails(UserNEFTDetailsSRO userNEFTDetails) {

		this.userNEFTDetails = userNEFTDetails;
	}

}