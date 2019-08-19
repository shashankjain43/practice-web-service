package com.snapdeal.merchant.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

import com.snapdeal.mob.ui.response.MerchantDetails;
import com.snapdeal.payments.roleManagementModel.request.Permission;

@Data
public class UserMerchantPermissionDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7016100239811179627L;

	private MerchantDetails merchantInfo;
	
	private List<Permission> permissions;

}
