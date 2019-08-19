package com.snapdeal.merchant.dto;

import java.util.List;

import lombok.Data;

import com.snapdeal.mob.dto.UserDetailsDTO;
import com.snapdeal.mob.ui.response.MerchantDetails;

@Data
public class UserProfileDTO {
	
	private UserDetailsDTO userDetails;
	
	private List<MerchantDetails> merchants;

}
