package com.snapdeal.merchant.request;

import java.util.List;

import com.snapdeal.merchant.dto.MPSearch;
import com.snapdeal.merchant.dto.MPViewFilters;
import com.snapdeal.merchant.enums.MPTransactionStatus;
import com.snapdeal.merchant.enums.MPTransactionType;

import lombok.Data;

@Data
public class MerchantGetTransactionRequest extends AbstractMerchantRequest {

	private static final long serialVersionUID = -478759399522396598L;

	private MPViewFilters filters;

	private MPSearch searchCriteria;

	private int orderby = 1;

	private Integer page = 1;

	private Integer limit = 10;
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + this.getMerchantId().hashCode();

		MPViewFilters mpViewFilters = getFilters();
		
		if(mpViewFilters == null)
			return result;

		if (mpViewFilters.getStartDate() != null) {
			result = 31 * result + (int) (mpViewFilters.getStartDate() ^ (mpViewFilters.getStartDate() >>> 32));
		}

		if (mpViewFilters.getEndDate() != null) {
			result = 31 * result + (int) (mpViewFilters.getEndDate() ^ (mpViewFilters.getEndDate() >>> 32));
		}

		List<MPTransactionType> txnTypeList = mpViewFilters.getTxnTypeList();

		if (txnTypeList != null) {

			for (MPTransactionType mpTransactionType : txnTypeList) {
				result = 31 * result + mpTransactionType.name().hashCode();
			}

		}
		List<MPTransactionStatus> txnStatusList = mpViewFilters.getTxnStatusList();

		if (txnStatusList != null) {

			for (MPTransactionStatus mpTransactionStatus : txnStatusList) {
				result = 31 * result + mpTransactionStatus.name().hashCode();
			}

		}

		if (mpViewFilters.getFromAmount() != null) {

			double fromAmount = mpViewFilters.getFromAmount().doubleValue();
			result = 31 * result + Long.valueOf(Double.doubleToLongBits(fromAmount)).hashCode();

		}

		if (mpViewFilters.getToAmount() != null) {

			double toAmount = mpViewFilters.getToAmount().doubleValue();
			result = 31 * result + Long.valueOf(Double.doubleToLongBits(toAmount)).hashCode();

		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (getClass() != obj.getClass()) {
	        return false;
	    }
	
		final MerchantGetFilterTransactionRequest request = (MerchantGetFilterTransactionRequest) obj;
		
		if(!this.getMerchantId().equals(request.getMerchantId()))
				return false;
		
		MPViewFilters targetFilter = request.getFilters();
		MPViewFilters sourceFilter = this.getFilters();
		
		
		if(sourceFilter == null && targetFilter == null)
			return true;
		
		if((sourceFilter != null && targetFilter == null)||(sourceFilter == null && targetFilter != null))
			return false;
		
		boolean status = sourceFilter.compareTo(targetFilter) == 0 ? true : false;
		
		return status;
		
	}
}
