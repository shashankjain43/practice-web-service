package com.snapdeal.merchant.response;

import java.util.List;

import com.snapdeal.merchant.entity.DownloadInfo;
import com.snapdeal.merchant.enums.DownloadStatus;

import lombok.Data;

@Data
public class MerchantGetDownloadInfoResponse extends AbstractResponse {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5126357281617099811L;

	List<DownloadInfo> info;

}
