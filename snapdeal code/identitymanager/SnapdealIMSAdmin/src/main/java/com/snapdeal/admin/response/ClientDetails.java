package com.snapdeal.admin.response;

import lombok.Getter;
import lombok.Setter;

import com.snapdeal.admin.dao.entity.Client.ClientPlatform;
import com.snapdeal.admin.dao.entity.Client.ClientStatus;
import com.snapdeal.admin.dao.entity.Client.ClientType;
import com.snapdeal.ims.enums.Merchant;

@Getter
@Setter
public class ClientDetails {
	private String clientId;

	private String clientName;

	private String clientKey;

	private ClientType clientType;
	
	private ClientPlatform clientPlatform;

	private Merchant merchant;

	private ClientStatus clientStatus;

	private String createdTime;

	private String updatedTime;
	
	private String imsInternalAlias;
}
