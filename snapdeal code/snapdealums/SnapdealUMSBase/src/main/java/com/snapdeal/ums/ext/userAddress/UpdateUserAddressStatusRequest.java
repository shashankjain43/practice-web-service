package com.snapdeal.ums.ext.userAddress;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class UpdateUserAddressStatusRequest extends ServiceRequest {
	
	private static final long serialVersionUID = -2999858920566528292L;

	@Tag(3)
	private int userAddressId;

	@Tag(4)
	private int userId;

	@Tag(5)
	private UserAddressStatus status;

	public UpdateUserAddressStatusRequest() {
		super();
	}

	public UpdateUserAddressStatusRequest(int userAddressId, int userId,
			UserAddressStatus status) {
		super();
		this.userAddressId = userAddressId;
		this.userId = userId;
		this.status = status;
	}

	public int getUserAddressId() {
		return userAddressId;
	}

	public void setUserAddressId(int userAddressId) {
		this.userAddressId = userAddressId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public UserAddressStatus getStatus() {
		return status;
	}

	public void setStatus(UserAddressStatus status) {
		this.status = status;
	}
	

}
