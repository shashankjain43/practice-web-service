package com.snapdeal.admin.commons;

import java.util.Comparator;

import com.snapdeal.admin.response.ClientDetails;


public enum ClientDetailsComparator implements Comparator<ClientDetails> {

	clientId_sort{
		public int compare(ClientDetails details1, ClientDetails details2) {
		
			if(details1.getClientId() == null){
				return -1;
			}
			if(details2.getClientId() == null){
				return 1;
			}	

			return (details1.getClientId().compareTo(details2.getClientId()));
		}
	},
	
	clientName_sort{
		public int compare(ClientDetails details1, ClientDetails details2) {
		
			if(details1.getClientName() == null){
				return -1;
			}
			if(details2.getClientName() == null){
				return 1;
			}	

			return (details1.getClientName().compareTo(details2.getClientName()));
		}
	},
	
	clientType_sort{
		public int compare(ClientDetails details1, ClientDetails details2) {
			
			if(details1.getClientType() == null){
				return -1;
			}
			if(details2.getClientType() == null){
				return 1;
			}	

			return (details1.getClientType().compareTo(details2.getClientType()));
		}
	},
	
	clientPlatform_sort{
		public int compare(ClientDetails details1, ClientDetails details2) {

			if(details1.getClientPlatform() == null){
				return -1;
			}
			if(details2.getClientPlatform() == null){
				return 1;
			}	
			
			return (details1.getClientPlatform().compareTo(details2.getClientPlatform()));
		}
	},
	
	merchant_sort{
		public int compare(ClientDetails details1, ClientDetails details2) {
			
			if(details1.getMerchant() == null){
				return -1;
			}
			if(details2.getMerchant() == null){
				return 1;
			}	

			return (details1.getMerchant().compareTo(details2.getMerchant()));
		}
	},
	
	clientStatus_sort{
		public int compare(ClientDetails details1, ClientDetails details2) {
			
			if(details1.getClientStatus() == null){
				return -1;
			}
			if(details2.getClientStatus() == null){
				return 1;
			}	

			return (details1.getClientStatus().compareTo(details2.getClientStatus()));
		}
	},
	
	createdTime_sort{
		public int compare(ClientDetails details1, ClientDetails details2) {

			if(details1.getCreatedTime() == null){
				return -1;
			}
			if(details2.getCreatedTime() == null){
				return 1;
			}	
			
			return (details1.getCreatedTime().compareTo(details2.getCreatedTime()));
		}
	},
	
	updatedTime_sort{
		public int compare(ClientDetails details1, ClientDetails details2) {

			if(details1.getUpdatedTime() == null){
				return -1;
			}
			if(details2.getUpdatedTime() == null){
				return 1;
			}	
			
			return (details1.getUpdatedTime().compareTo(details2.getUpdatedTime()));
		}
	};
	
	 public static Comparator<ClientDetails> descending(final Comparator<ClientDetails> other) {
	        return new Comparator<ClientDetails>() {
	            public int compare(ClientDetails details1, ClientDetails details2) {
	                return -1 * other.compare(details1, details2);
	            }
	        };
}
}