package com.snapdeal.merchant.response;

import java.util.List;

import com.snapdeal.merchant.dto.MerchantUserDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MerchantAllUsersResponse extends AbstractResponse{

	private static final long serialVersionUID = 1L;
	
	private List<MerchantUserDTO> users;

}
