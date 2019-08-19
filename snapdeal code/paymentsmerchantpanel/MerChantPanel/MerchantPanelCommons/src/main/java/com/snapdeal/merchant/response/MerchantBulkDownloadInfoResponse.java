package com.snapdeal.merchant.response;

import java.util.List;

import com.snapdeal.merchant.entity.BulkDownloadInfo;

import lombok.Data;

@Data
public class MerchantBulkDownloadInfoResponse extends AbstractResponse{
	

	private static final long serialVersionUID = -7963796360052778270L;
	List<BulkDownloadInfo> info ;
}
