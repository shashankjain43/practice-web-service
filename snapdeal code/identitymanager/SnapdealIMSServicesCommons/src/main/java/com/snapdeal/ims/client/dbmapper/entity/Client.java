package com.snapdeal.ims.client.dbmapper.entity;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

import com.snapdeal.ims.client.dbmapper.entity.info.ClientPlatform;
import com.snapdeal.ims.client.dbmapper.entity.info.ClientStatus;
import com.snapdeal.ims.client.dbmapper.entity.info.ClientType;
import com.snapdeal.ims.enums.Merchant;

/**
 * @author subhash
 */
@Setter
@Getter
public class Client implements java.io.Serializable {

	private static final long serialVersionUID = -2304911600307800509L;

	private String clientId;
	private String clientName; 
	private String clientKey; 
	private Merchant merchant;
	private ClientType clientType;
	private ClientStatus clientStatus;
	private ClientPlatform clientPlatform;
	private Timestamp createdTime;
	private Timestamp updatedTime;
	private String imsInternalAlias;
}
