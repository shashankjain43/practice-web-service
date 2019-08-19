package com.snapdeal.vanila.bulk.BulkTID;

import lombok.Data;

@Data
public class CustomTID {
	
	private String providerMerchantId;
	
	private String platformId;
	
	private String terminalId;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomTID other = (CustomTID) obj;
		if (platformId == null) {
			if (other.platformId != null)
				return false;
		} else if (!platformId.equals(other.platformId))
			return false;
		if (providerMerchantId == null) {
			if (other.providerMerchantId != null)
				return false;
		} else if (!providerMerchantId.equals(other.providerMerchantId))
			return false;
		if (terminalId == null) {
			if (other.terminalId != null)
				return false;
		} else if (!terminalId.equals(other.terminalId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((platformId == null) ? 0 : platformId.hashCode());
		result = prime
				* result
				+ ((providerMerchantId == null) ? 0 : providerMerchantId
						.hashCode());
		result = prime * result
				+ ((terminalId == null) ? 0 : terminalId.hashCode());
		return result;
	}
	
	

}
