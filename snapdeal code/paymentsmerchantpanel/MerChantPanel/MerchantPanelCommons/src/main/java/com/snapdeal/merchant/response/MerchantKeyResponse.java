package com.snapdeal.merchant.response;

import java.util.List;

import com.snapdeal.merchant.dto.MerchantKey;
import com.snapdeal.mob.dto.Key;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude="key")
public class MerchantKeyResponse extends AbstractResponse{


	private static final long serialVersionUID = -5488007249741461335L;
	
	private String url;
	
	private String key;

}
