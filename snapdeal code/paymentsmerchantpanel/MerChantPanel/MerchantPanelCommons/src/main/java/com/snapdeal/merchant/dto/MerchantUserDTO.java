package com.snapdeal.merchant.dto;

import java.util.List;
import lombok.Data;

@Data
public class MerchantUserDTO {

	private String userId;

	private String userName;

	private String emailId;
	
	private String loginName;
	
	private String integrationMode;

	private List<MerchantRoleDTO> roleList;

}
