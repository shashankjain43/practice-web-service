package com.snapdeal.merchant.dto;

import lombok.Data;
@Data
public class MerchantPermissionDTO {
	
	private Integer id;

	private String name;
	
	private String displayName;
	
	private boolean enabled;
}
