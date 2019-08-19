package com.snapdeal.merchant.dao.entity;

import java.util.Date;

import com.snapdeal.merchant.enums.RefundStatus;

import lombok.Data;

@Data
public class BulkRefundEntity {

    private Integer id;
	
	private String merchantId;
	
	private String userLoginName;
	
	private String fileName;
	
	private RefundStatus refundStatus;
	
	private String  fileIdemKey;
	
	private Date createdOn;
	
	private Date updatedOn;
	
	 
}
