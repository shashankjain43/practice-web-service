package com.snapdeal.merchant.request;

import lombok.Data;

@Data
public class MerchantUploadDocumentRequest extends AbstractMerchantRequest{

	private  String name;
	
	private String docCategory;
	
	private String docType;
	
	private boolean approvalStatus;

}
