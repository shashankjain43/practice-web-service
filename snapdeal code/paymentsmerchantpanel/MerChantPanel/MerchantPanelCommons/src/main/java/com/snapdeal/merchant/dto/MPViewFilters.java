package com.snapdeal.merchant.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

import com.snapdeal.merchant.enums.MPTransactionStatus;
import com.snapdeal.merchant.enums.MPTransactionType;

@Data
public class MPViewFilters implements Comparable<MPViewFilters>{
	
	private List<MPTransactionType>   txnTypeList;
	private List<MPTransactionStatus> txnStatusList;
	private BigDecimal fromAmount;
	private BigDecimal toAmount;
	private Long startDate;
	private Long endDate;
	
	private String merchantTag ;
	
	@Override
	public int compareTo(MPViewFilters o) {
		int status = -1;
		if(this.startDate != null)
			status = this.startDate.compareTo(o.getStartDate());
		else if(o.startDate == null) {
			status = 0;
		} 
		
		if(status == -1)
			return status;
		
		if(this.endDate != null)
			status = this.endDate.compareTo(o.getEndDate());
		else if(o.endDate == null) {
			status = 0;
		} 
		
		if(status == -1)
			return status;
		
		if(this.fromAmount != null)
			status = this.fromAmount.compareTo(o.getFromAmount());
		else if(o.fromAmount == null) {
			status = 0;
		} 
		
		if(status == -1)
			return status;
		
		if(this.toAmount != null)
			status = this.toAmount.compareTo(o.getToAmount());
		else if(o.toAmount == null) {
			status = 0;
		} 
		
		if(status == -1)
			return status;
		
		List<MPTransactionType> sourceTxnTypeList = o.getTxnTypeList();
		List<MPTransactionType> targetTxnTypeList = this.getTxnTypeList();

		Map<String,MPTransactionType> tempTxns = new HashMap<String,MPTransactionType>();
		if(targetTxnTypeList != null){
			for (MPTransactionType mpTransactionType : targetTxnTypeList) {
				tempTxns.put(mpTransactionType.name(), mpTransactionType);
			}
		}
		
		if(sourceTxnTypeList != null) {
			for (MPTransactionType mpTransactionType : sourceTxnTypeList) {
				MPTransactionType removedTxn = tempTxns.remove(mpTransactionType.name());
				if(removedTxn == null)
					return -1;
			}
		}
		
		if(tempTxns.size() > 0)
			return -1;
		
		
		
		List<MPTransactionStatus> sourceTxnStatusList = o.getTxnStatusList();
		List<MPTransactionStatus> targetTxnStatusList = this.getTxnStatusList();

		Map<String,MPTransactionStatus> tempStatus = new HashMap<String,MPTransactionStatus>();
		if(targetTxnTypeList != null){
			for (MPTransactionStatus mpTransactionStatus : targetTxnStatusList) {
				tempStatus.put(mpTransactionStatus.name(), mpTransactionStatus);
			}
		}
		
		if(sourceTxnStatusList != null) {
			for (MPTransactionStatus mpTransactionStatus : sourceTxnStatusList) {
				MPTransactionStatus removedStatus = tempStatus.remove(mpTransactionStatus.name());
				if(removedStatus == null)
					return -1;
			}
		}
		
		if(tempStatus.size() > 0)
			return -1;
		
		
		return 0;
	}
	
}
