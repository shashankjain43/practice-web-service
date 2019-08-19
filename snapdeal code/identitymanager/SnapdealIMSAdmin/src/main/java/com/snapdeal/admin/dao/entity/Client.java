package com.snapdeal.admin.dao.entity;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

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

	//TODO: All enums should be created in separate file
	public enum ClientType {
	   USER_FACING("USER_FACING"),NON_USER_FACING("NON_USER_FACING");

		private String clientType;

		ClientType(String clientType) {
			this.clientType = clientType;
		}

		public String getValue() {
			return clientType;
		}
	}
	private ClientType clientType;


	public enum ClientStatus {
		ACTIVE("ACTIVE"),INACTIVE("INACTIVE");

		private String clientStatus;

		ClientStatus(String clientStatus) {
			this.clientStatus = clientStatus;
		}

		public String getValue() {
			return clientStatus;
		}
	}
	private ClientStatus clientStatus;
	
	public enum ClientPlatform {
		WEB("WEB"), WAP("WAP"), APP("APP");

		private String clientPlatform;

		ClientPlatform(String clientPlatform) {
			this.clientPlatform = clientPlatform;
		}

		public String getValue() {
			return clientPlatform;
		}
	}
	private ClientPlatform clientPlatform;
	
	
	private Timestamp createdTime;
	private Timestamp updatedTime;
	private String imsInternalAlias;

}
