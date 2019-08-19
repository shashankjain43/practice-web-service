package com.snapdeal.merchant.entity.cache;

import java.io.Serializable;
import java.util.List;

import com.snapdeal.merchant.dto.PermissionDTO;
import com.snapdeal.merchant.dto.UserMerchantPermissionDTO;
import com.snapdeal.mob.ui.response.MerchantDetails;

import lombok.Data;

@Data
public class UserInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -566229676612239979L;

	private String email;
	
	private String name;
	
	private String loginName;
	
	private List<UserMerchantPermissionDTO> merchantInfo;

}
