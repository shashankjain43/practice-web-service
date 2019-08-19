package com.snapdeal.ims.response;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.snapdeal.ims.client.dbmapper.entity.info.ClientPlatform;
import com.snapdeal.ims.client.dbmapper.entity.info.ClientStatus;
import com.snapdeal.ims.client.dbmapper.entity.info.ClientType;
import com.snapdeal.ims.enums.Merchant;


@Getter
@Setter
@ToString(exclude={"clientKey"})
public class ClientDetails {
	private String clientId;

	private String clientName;

	private String clientKey;

	private ClientType clientType;
	
	private ClientPlatform clientPlatform;

	private Merchant merchant;

	private ClientStatus clientStatus;

	private Timestamp createdTime;

	private Timestamp updatedTime;
	
	private String imsIntenalAlias;
}
