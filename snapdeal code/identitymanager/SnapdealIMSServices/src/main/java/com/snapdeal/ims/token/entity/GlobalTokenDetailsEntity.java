package com.snapdeal.ims.token.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import com.snapdeal.ims.enums.Merchant;

@Data
public class GlobalTokenDetailsEntity implements Serializable {

	private static final long serialVersionUID = 1981385536868792757L;

	private String globalTokenId;

	private String globalToken;

	private String userAgent;

	private String machineIdentifier;
	
	private Merchant merchant;

	private Date expiryTime;

	private String userId;

	private Date createdTime;
}