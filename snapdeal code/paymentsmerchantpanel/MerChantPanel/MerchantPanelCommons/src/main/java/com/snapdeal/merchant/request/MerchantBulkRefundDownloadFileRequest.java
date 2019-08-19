package com.snapdeal.merchant.request;

import lombok.Data;

@Data
public class MerchantBulkRefundDownloadFileRequest extends AbstractMerchantRequest{

	private static final long serialVersionUID = -5801546911901244786L;
	Integer id;
}
