package com.snapdeal.merchant.dto;

import java.util.Date;

import com.snapdeal.merchant.enums.MerchantEnvType;

import lombok.Data;

@Data
public class MerchantKey {
    
	private String id;
    private String key;
    private Date expirationTime;
    private MerchantEnvType envType;
    private String clientId;
    private String domainId;
    private boolean active = true;
    
}
