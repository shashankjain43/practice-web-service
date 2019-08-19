package com.snapdeal.merchant.response;

import java.util.List;

import com.snapdeal.merchant.dto.MerchantRoleDTO;

import lombok.Data;

@Data
public class MerchantRoleResponse extends AbstractResponse{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9075713919882828856L;
	
	private List<MerchantRoleDTO> roles;

}
